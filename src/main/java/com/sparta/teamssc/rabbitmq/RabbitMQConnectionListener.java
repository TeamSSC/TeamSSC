package com.sparta.teamssc.rabbitmq;

import com.sparta.teamssc.domain.chat.service.MessageServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

@Component
@RequiredArgsConstructor
@Slf4j
public class RabbitMQConnectionListener implements ConnectionListener {

    private final SlackNotificationService slackNotificationService;
    private final MessageServiceImpl messageService;

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
        if (isBrokerDown.get() && messageService.getCircuitBreakerState() == MessageServiceImpl.CircuitBreakerState.OPEN) {
            log.info(" 브로커 복구 테스트: HALF_OPEN 상태로 전환");
            messageService.setCircuitBreakerState(MessageServiceImpl.CircuitBreakerState.HALF_OPEN);
            messageService.sendCircuitTestMessage();
        }
    }
}
