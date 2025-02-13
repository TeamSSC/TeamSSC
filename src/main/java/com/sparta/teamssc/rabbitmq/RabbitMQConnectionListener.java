package com.sparta.teamssc.rabbitmq;

import com.sparta.teamssc.domain.chat.entity.CircuitBreakerState;
import com.sparta.teamssc.domain.chat.service.MessageServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@RequiredArgsConstructor
@Slf4j
public class RabbitMQConnectionListener implements ConnectionListener {

    private final SlackNotificationService slackNotificationService;
    private final MessageServiceImpl messageService;
    private static final int MAX_CIRCUIT_TEST_FAILURES = 3; // 서킷 테스트 실패 허용 횟수
    private AtomicInteger circuitTestFailures = new AtomicInteger(0);
    private final RabbitTemplate rabbitTemplate;
    private static final long OPEN_STATE_DURATION = 10000; // 10초 후 HALF_OPEN 상태로 변경
    private final AtomicBoolean isBrokerDown = new AtomicBoolean(false);

    @Override
    public void onCreate(Connection connection) {
        log.info("✅ RabbitMQ 브로커 연결 성공: {}", connection);
        isBrokerDown.set(false);
        messageService.resetCircuitBreaker(); // 브로커 복구 시 서킷 브레이커 초기화
    }

    @Override
    public void onClose(Connection connection) {
        log.error(" RabbitMQ 브로커 장애 발생! 서킷 브레이커 OPEN 상태로 변경.");
        isBrokerDown.set(true);
        messageService.activateCircuitBreaker();
        slackNotificationService.sendNotification("⚠️ RabbitMQ 브로커 장애 발생! 메시지 처리가 중단되었습니다.");
    }

    // 일정 시간이 지나면 HALF_OPEN 상태로 변경하여 테스트 메시지 전송
    @Scheduled(fixedDelay = OPEN_STATE_DURATION)
    public void checkBrokerRecovery() {
        if (isBrokerDown.get() && messageService.getCircuitBreakerState() == CircuitBreakerState.OPEN) {
            log.info("🔄 브로커 복구 테스트: HALF_OPEN 상태로 전환");
            messageService.setCircuitBreakerState(CircuitBreakerState.HALF_OPEN);

            boolean testSuccess = messageService.sendCircuitTestMessage();
            if (!testSuccess) {
                int failures = circuitTestFailures.incrementAndGet();
                log.warn("⚠️ 서킷 테스트 메시지 실패 횟수: {}", failures);

                if (failures >= MAX_CIRCUIT_TEST_FAILURES) {
                    log.error("❌ 브로커 장애로 DLQ 이동 & Slack 알림 전송");
                    sendToDeadLetterQueueForBrokerFailure();
                    slackNotificationService.sendNotification("🚨 *RabbitMQ 브로커 장애 지속됨!* DLQ로 이동되었습니다.");
                    circuitTestFailures.set(0);
                }
            } else {
                circuitTestFailures.set(0);
            }
        }
    }

    // 브로커 장애 메시지를 DLQ로 이동하는 메서드
    private void sendToDeadLetterQueueForBrokerFailure() {
        MessageDTO errorMessageDTO = new MessageDTO();
        errorMessageDTO.setContent("[브로커 장애 발생] 시스템 메시지");
        errorMessageDTO.setRetryCount(3); // 즉시 DLQ로 이동하도록 설정
        errorMessageDTO.setCircuitBreakerUsed(true); // 브로커 장애 표시
        rabbitTemplate.convertAndSend(RabbitMQConfig.DEAD_LETTER_EXCHANGE, RabbitMQConfig.DEAD_LETTER_QUEUE, errorMessageDTO);
    }
}
