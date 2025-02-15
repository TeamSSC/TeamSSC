package com.sparta.teamssc.domain.chat.service;

import com.sparta.teamssc.domain.chat.entity.CircuitBreakerState;
import com.sparta.teamssc.domain.chat.entity.Message;
import com.sparta.teamssc.domain.chat.entity.RoomType;
import com.sparta.teamssc.domain.chat.repository.MessageRepository;
import com.sparta.teamssc.domain.period.service.PeriodService;
import com.sparta.teamssc.domain.team.service.TeamService;
import com.sparta.teamssc.domain.user.user.entity.User;
import com.sparta.teamssc.domain.user.user.service.UserService;
import com.sparta.teamssc.rabbitmq.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageServiceImpl implements MessageService {

    private final SimpMessagingTemplate messagingTemplate;

    private final MessageRepository messageRepository;
    private final TeamService teamService;
    private final PeriodService periodService;
    private final UserService userService;

    private final RabbitTemplate rabbitTemplate;
    private static final int MAX_RETRY_ATTEMPTS = 3;
    private static final int HALF_OPEN_ATTEMPT_LIMIT = 2; // HALF_OPEN 상태에서 2개 메시지만 허용
    private static final long OPEN_STATE_DURATION = 10000; // 10초 동안 OPEN 상태 유지

    private int reconnectAttempts = 0;
    private static final int MAX_RECONNECT_ATTEMPTS = 5;
    private WebSocketSession session;
    private static final int MAX_FAILURE_BEFORE_DLQ = 5; // DLQ로 이동하는 최대 실패 횟수


    private AtomicInteger failureCount = new AtomicInteger(0);
    private AtomicInteger halfOpenAttemptCount = new AtomicInteger(0);
    private AtomicBoolean circuitBreakerOpen = new AtomicBoolean(false);
    private CircuitBreakerState state = CircuitBreakerState.CLOSED;

    public CircuitBreakerState getCircuitBreakerState() {
        return state;
    }
    public void sendMessage(String destination, Object message, boolean isCircuitTest) {
        switch (state) {
            case OPEN:
                log.warn("STOMP 메시지 전송 차단됨 (서킷 브레이커 OPEN 상태)");
                return;

            case HALF_OPEN:
                if (!isCircuitTest) {
                    log.warn("HALF_OPEN 상태에서는 일반 메시지 전송 불가");
                    return;
                }

                if (halfOpenAttemptCount.incrementAndGet() > HALF_OPEN_ATTEMPT_LIMIT) {
                    log.warn("STOMP 테스트 메시지 전송 제한 (서킷 브레이커 HALF_OPEN 상태)");
                    return;
                }
                break;

            default:
                // CLOSED 상태에서 정상 메시지 전송
                break;
        }

        boolean success = false;
        int attempts = 0;
        int backoff = 1000; // 초기 백오프 시간 (1초)

        while (attempts < MAX_RETRY_ATTEMPTS) {
            try {
                messagingTemplate.convertAndSend(destination, message);
                success = true;
                failureCount.set(0); // 성공하면 실패 카운트 초기화
                state = CircuitBreakerState.CLOSED;
                log.info("STOMP 메시지 전송 성공: {}", destination);
                return;
            } catch (Exception e) {
                attempts++;
                log.error("STOMP 메시지 전송 실패 (시도 {}): {}", attempts, destination, e);

                try {
                    Thread.sleep(backoff);
                    backoff *= 2; // 지수 백오프 적용 (1초 → 2초 → 4초)
                } catch (InterruptedException interruptedException) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        failureCount.incrementAndGet();
        if (failureCount.get() >= MAX_RETRY_ATTEMPTS) {
            if (failureCount.get() >= MAX_FAILURE_BEFORE_DLQ) {
                moveToDeadLetterQueue(destination, message);
            } else {
                activateCircuitBreaker();
            }
        }
    }
    public void activateCircuitBreaker() {
        log.error("서킷 브레이커 활성화됨: 일정 시간 동안 STOMP 메시지 전송 차단");
        state = CircuitBreakerState.OPEN;
        circuitBreakerOpen.set(true);
        failureCount.set(0);
        scheduleHalfOpenState();
    }

    //  서킷 브레이커 상태를 변경
    public void setCircuitBreakerState(CircuitBreakerState newState) {
        log.info("서킷 브레이커 상태 변경: {} → {}", state, newState);
        this.state = newState;
    }

    public void resetCircuitBreaker() {
        log.info("RabbitMQ 브로커 복구! 서킷 브레이커 CLOSED 상태로 변경");
        state = CircuitBreakerState.CLOSED;
    }

    public boolean sendCircuitTestMessage() {
        if (state == CircuitBreakerState.HALF_OPEN) {
            log.info(" HALF_OPEN 상태에서 테스트 메시지 전송 중...");
            try {
                messagingTemplate.convertAndSend("/topic/circuit-test", "circuit-test");
                resetCircuitBreaker(); // 테스트 메시지 성공 시 CLOSED 상태로 변경
                return true;
            } catch (Exception e) {
                log.error(" 서킷 테스트 메시지 전송 실패 → OPEN 상태 유지", e);
                state = CircuitBreakerState.OPEN;
                return false;
            }
        }
        return false;
    }

    @Scheduled(fixedDelay = OPEN_STATE_DURATION) // 10초 후 HALF_OPEN 상태로 변경
    private void scheduleHalfOpenState() {
        if (state == CircuitBreakerState.OPEN) {
            log.info("서킷 브레이커 HALF_OPEN 상태로 전환: 제한된 메시지만 허용");
            state = CircuitBreakerState.HALF_OPEN;
            circuitBreakerOpen.set(false);
            halfOpenAttemptCount.set(0);

            // 서킷 테스트 메시지 전송
            sendMessage("/topic/circuit-test", "circuit-test", true);
        }
    }

    private void moveToDeadLetterQueue(String destination, Object message) {
        log.error("메시지를 DLQ로 이동: {}", message);
        rabbitTemplate.convertAndSend("dead-letter-exchange", "dead-letter-routing-key", message);
    }

    @Scheduled(fixedDelay = 10000) // 10초마다 Ping 전송
    public void sendPing() {
        if (session != null && session.isOpen()) {
            try {
                session.sendMessage(new TextMessage("ping"));
                log.info("Ping 메시지 전송 완료");
            } catch (IOException e) {
                log.error("Ping 전송 실패", e);
            }
        }
    }

    @Scheduled(fixedDelay = 1000) // 주기적으로 WebSocket 상태 확인
    public void checkWebSocketConnection() {
        if (!isWebSocketConnected() && reconnectAttempts < MAX_RECONNECT_ATTEMPTS) {
            reconnect();
        }
    }

    private boolean isWebSocketConnected() {
        // WebSocket이 정상적으로 작동하는지 확인하는 로직 (Ping/Pong으로 체크 가능)
        return true; // TODO: 실제 구현
    }

    private void reconnect() {
        if (reconnectAttempts >= MAX_RECONNECT_ATTEMPTS) {
            log.error("최대 WebSocket 재연결 횟수 초과. 사용자에게 새로고침 요청.");
            alertUserToRefresh(); // 클라이언트에게 새로고침 요청
            return;
        }

        int delay = (int) Math.min(1000 * Math.pow(2, reconnectAttempts), 30000); // 지수 백오프 (최대 30초)
        reconnectAttempts++;

        log.info("WebSocket 재연결 시도... (시도 횟수: {})", reconnectAttempts);

        try {
            Thread.sleep(delay); // 일정 시간 대기 후 재연결 시도
            connectWebSocket();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("WebSocket 재연결 중 오류 발생", e);
        }
    }

    private void connectWebSocket() {
        // TODO: 실제 WebSocket 재연결 로직 구현 필요
        log.info("WebSocket 재연결 성공!");
        reconnectAttempts = 0; // 재연결 성공 시 카운트 초기화
    }

    private void alertUserToRefresh() {
        log.warn("WebSocket 재연결 실패: 사용자에게 새로고침 요청");
        // TODO: 클라이언트에 새로고침 메시지 전달하는 로직 추가
    }

    @Transactional
    public void sendTeamMessage(Long teamId, String content) {

        User user = getCurrentUser();

        if (!teamService.isUserInTeam(user.getId(), teamId)) {
            throw new IllegalArgumentException("사용자가 해당 팀에 속해 있지 않습니다.");
        }

        Message message = Message.builder()
                .content(content)
                .sender(user.getUsername())
                .roomId(teamId)
                .roomType(RoomType.TEAM)
                .build();


        // 메시지를 RabbitMQ로 발행
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.QUEUE_NAME, message);
        log.info("팀Message 보내기 RabbitMQ: {}", message);
        log.info("메시지를 보낸 사람 이름: {}", user.getUsername());
    }

    @Transactional
    public void sendPeriodMessage(Long periodId, String content) {

        User user = getCurrentUser();

        if (!periodService.isUserInPeriod(user.getId(), periodId)) {
            throw new IllegalArgumentException("사용자가 해당 기간에 속해 있지 않습니다.");
        }

        Message message = Message.builder()
                .content(content)
                .sender(user.getUsername())
                .roomId(periodId)
                .roomType(RoomType.PERIOD)
                .build();

        // 메시지를 RabbitMQ로 발행
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.QUEUE_NAME, message);


        log.info("기수Message 보내기 RabbitMQ: {}", message);

    }

    // 팀 메시지을 불러오기
    @Override
    @Transactional(readOnly = true)
    public List<Message> getMessagesForTeam(Long teamId) {

        User user = getCurrentUser();

        if (!teamService.isUserInTeam(user.getId(), teamId)) {
            throw new IllegalArgumentException("사용자가 해당 팀에 속해 있지 않습니다.");
        }

        return messageRepository.findByRoomIdAndRoomType(teamId, RoomType.TEAM);
    }

    // 기수 메시지 불러오기
    @Override
    @Transactional(readOnly = true)
    public List<Message> getMessagesForPeriod(Long periodId) {

        User user = getCurrentUser();

        if (!periodService.isUserInPeriod(user.getId(), periodId) && !isManager(user)) {
            throw new IllegalArgumentException("사용자가 해당 기수에 속해 있지 않거나 관리자 권한이 없습니다.");
        }
        return messageRepository.findByRoomIdAndRoomType(periodId, RoomType.PERIOD);
    }

    /**
     * 3일지난 메시지는 삭제
     */
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
