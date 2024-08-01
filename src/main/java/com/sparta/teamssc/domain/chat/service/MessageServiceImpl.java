package com.sparta.teamssc.domain.chat.service;

import com.sparta.teamssc.domain.chat.entity.Message;
import com.sparta.teamssc.domain.chat.entity.RoomType;
import com.sparta.teamssc.domain.chat.repository.MessageRepository;
import com.sparta.teamssc.domain.period.service.PeriodService;
import com.sparta.teamssc.domain.team.service.TeamService;
import com.sparta.teamssc.domain.user.user.entity.User;
import com.sparta.teamssc.domain.user.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final TeamService teamService;
    private final PeriodService periodService;
    private final UserService userService;

    @Transactional
    public void sendTeamMessage(Long teamId, String content) {
       // UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      //  User user = userService.getUserByEmail(userDetails.getUsername());

        if (!teamService.isUserInTeam(3l, teamId)) {
            throw new IllegalArgumentException("사용자가 해당 팀에 속해 있지 않습니다.");
        }

        Message message = Message.builder()
                .content(content)
                .sender("박다미")
                .roomId(teamId)
                .roomType(RoomType.TEAM)
                .build();

        messageRepository.save(message);
        messagingTemplate.convertAndSend("/topic/team/" + teamId, message);
    }

    @Transactional
    public void sendPeriodMessage(Long periodId, String content) {
       // UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       // User user = userService.getUserByEmail(userDetails.getUsername());

        if (!periodService.isUserInPeriod(3l, periodId)) {
            throw new IllegalArgumentException("사용자가 해당 기간에 속해 있지 않습니다.");
        }

        Message message = Message.builder()
                .content(content)
                .sender("박다미")
                .roomId(periodId)
                .roomType(RoomType.PERIOD)
                .build();

        messageRepository.save(message);
        messagingTemplate.convertAndSend("/topic/period/" + periodId, message);
    }
}
