package com.sparta.teamssc.domain.chat.service;

import com.sparta.teamssc.domain.chat.entity.Message;
import com.sparta.teamssc.domain.chat.entity.RoomType;
import com.sparta.teamssc.domain.chat.repository.MessageRepository;
import com.sparta.teamssc.domain.track.service.PeriodService;
import com.sparta.teamssc.domain.team.service.TeamService;
import com.sparta.teamssc.domain.user.entity.User;
import com.sparta.teamssc.domain.user.service.UserService;
import com.sparta.teamssc.domain.rabbitmq.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final TeamService teamService;
    private final PeriodService periodService;
    private final UserService userService;

    private final RabbitTemplate rabbitTemplate;


    @Transactional
    public void sendTeamMessage(Long teamId, String content, StompHeaderAccessor accessor) {
        sendMessage(teamId, content, RoomType.TEAM, accessor, user -> teamService.isUserInTeam(user.getId(), teamId));
    }

    @Transactional
    public void sendPeriodMessage(Long periodId, String content, StompHeaderAccessor accessor) {
        sendMessage(periodId, content, RoomType.PERIOD, accessor, user -> periodService.isUserInPeriod(user.getId(), periodId));
    }

    private void sendMessage(Long roomId, String content, RoomType roomType, StompHeaderAccessor accessor, java.util.function.Predicate<User> isUserInRoom) {
        SecurityContext securityContext = getSecurityContextFromAccessor(accessor);
        User user = authenticateAndRetrieveUser(securityContext);
        validateUserInRoom(user, isUserInRoom);

        Message messageToSend = buildMessage(content, user, roomId, roomType);

        sendToRabbitMQ(messageToSend);
    }

    private SecurityContext getSecurityContextFromAccessor(StompHeaderAccessor accessor) {
        SecurityContext securityContext = (SecurityContext) accessor.getSessionAttributes().get("SPRING_SECURITY_CONTEXT");
        if (securityContext == null) {
            throw new IllegalStateException("WebSocket 세션에서 SecurityContext를 찾을 수 없습니다.");
        }
        return securityContext;
    }

    private User authenticateAndRetrieveUser(SecurityContext securityContext) {
        Authentication authentication = securityContext.getAuthentication();
        if (authentication == null) {
            throw new IllegalStateException("SecurityContext에 인증 정보가 없습니다.");
        }

        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        return userService.getUserByEmail(username);
    }

    //Predicate 사용, test메서드로 참 거짓
    private void validateUserInRoom(User user, java.util.function.Predicate<User> isUserInRoom) {
        if (!isUserInRoom.test(user)) {
            throw new IllegalArgumentException("사용자가 해당 룸에 속해 있지 않습니다.");
        }
    }

    private Message buildMessage(String content, User user, Long roomId, RoomType roomType) {
        return Message.builder()
                .content(content)
                .sender(user.getUsername())
                .roomId(roomId)
                .roomType(roomType)
                .build();
    }

    private void sendToRabbitMQ(Message message) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.QUEUE_NAME, message);
    }

    // 팀 메시지을 불러오기
    @Override
    @Transactional(readOnly = true)
    public List<Message> getMessagesForTeam(Long teamId) {

        return getMessages(teamId, RoomType.TEAM);
    }

    // 기수 메시지 불러오기
    @Override
    @Transactional(readOnly = true)
    public List<Message> getMessagesForPeriod(Long periodId) {

        return getMessages(periodId, RoomType.PERIOD);
    }

    private List<Message> getMessages(Long roomId, RoomType roomType) {
        User user = getCurrentUser();
        if (roomType == RoomType.TEAM && !teamService.isUserInTeam(user.getId(), roomId)) {
            throw new IllegalArgumentException("사용자가 해당 팀에 속해 있지 않습니다.");
        } else if (roomType == RoomType.PERIOD && !periodService.isUserInPeriod(user.getId(), roomId) && !isManager(user)) {
            throw new IllegalArgumentException("사용자가 해당 기수에 속해 있지 않거나 관리자 권한이 없습니다.");
        }

        return messageRepository.findByRoomIdAndRoomType(roomId, roomType);
    }

    @Override
    @Transactional
    public void deleteOldMessages() {
        LocalDateTime threeDaysAgo = LocalDateTime.now().minusDays(3);
        messageRepository.deleteByCreateAtBefore(threeDaysAgo);
    }

    private User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            return userService.getUserByEmail(username);
        } else {
            throw new IllegalStateException("인증된 사용자가 없습니다.");
        }
    }

    private boolean isManager(User user) {
        return user.getRoles().stream()
                .anyMatch(role -> role.getRole().equals("MANAGER"));
    }
}
