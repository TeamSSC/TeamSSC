package com.sparta.teamssc.domain.chat.service;

import com.sparta.teamssc.domain.chat.entity.Message;
import com.sparta.teamssc.domain.chat.entity.RoomType;
import com.sparta.teamssc.domain.chat.repository.MessageRepository;
import com.sparta.teamssc.domain.period.service.PeriodService;
import com.sparta.teamssc.domain.team.service.TeamService;
import com.sparta.teamssc.domain.user.auth.util.JwtUtil;
import com.sparta.teamssc.domain.user.user.entity.User;
import com.sparta.teamssc.domain.user.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
    public void sendTeamMessage(Long teamId, String content, @Header("Authorization") String token) {
        User user = getCurrentUserFromToken(token);

        if (!teamService.isUserInTeam(user.getId(), teamId)) {
            throw new IllegalArgumentException("사용자가 해당 팀에 속해 있지 않습니다.");
        }

        Message message = Message.builder()
                .content(content)
                .sender(user.getUsername())
                .roomId(teamId)
                .roomType(RoomType.TEAM)
                .build();

        messageRepository.save(message);
        messagingTemplate.convertAndSend("/topic/team/" + teamId, message);
    }

    @Transactional
    public void sendPeriodMessage(Long periodId, String content, @Header("Authorization") String token) {
        User user = getCurrentUserFromToken(token);

        if (!periodService.isUserInPeriod(user.getId(), periodId)) {
            throw new IllegalArgumentException("사용자가 해당 기간에 속해 있지 않습니다.");
        }

        Message message = Message.builder()
                .content(content)
                .sender(user.getUsername())
                .roomId(periodId)
                .roomType(RoomType.PERIOD)
                .build();

        messageRepository.save(message);
        messagingTemplate.convertAndSend("/topic/period/" + periodId, message);
    }

    private User getCurrentUserFromToken(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            String username = JwtUtil.getUsernameFromToken(token);
            return userService.getUserByEmail(username);
        }
        throw new IllegalStateException("JWT 토큰이 제공되지 않았습니다.");
    }
}
