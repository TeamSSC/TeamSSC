package com.sparta.teamssc.domain.track.service;

import com.sparta.teamssc.domain.track.dto.TrackRequestDto;
import com.sparta.teamssc.domain.track.dto.TrackResponseDto;
import com.sparta.teamssc.domain.track.entity.Track;
import com.sparta.teamssc.domain.track.repository.TrackRepository;
import com.sparta.teamssc.domain.user.user.entity.User;
import com.sparta.teamssc.domain.user.user.service.UserService;
import com.sparta.teamssc.domain.user.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrackServiceImpl implements TrackService{

    private final TrackRepository trackRepository;
    private final UserService userService;

    public TrackResponseDto createTrack(String username, TrackRequestDto trackRequestDto) {

        User user = userService.findByUsername(username);

        if (checkAdmin(user).equals(Boolean.FALSE)) {
            throw new AccessDeniedException("관리자만 트랙을 생성할 수 있습니다.");
        }

        Track track = Track.builder()
                .name(trackRequestDto.getName())
                .build();

        trackRepository.save(track);

        return TrackResponseDto.builder().name(track.getName()).build();

    }

    // TODO : User Role 타입 String에서 Enum으로 바뀌면 수정 예정
    public Boolean checkAdmin(User user){
        if(user.getRoles().equals("ADMIN")){
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }
}
