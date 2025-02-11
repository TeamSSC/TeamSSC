package com.sparta.teamssc.rabbitmq;

import com.sparta.teamssc.domain.chat.entity.Message;
import com.sparta.teamssc.domain.chat.service.MessageServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeadLetterListener {

    private final SimpMessagingTemplate messagingTemplate;
    private final RabbitTemplate rabbitTemplate;
    private final SlackNotificationService slackNotificationService;
    private final MessageServiceImpl messageService;
    private static final int MAX_DLQ_RETRY_ATTEMPTS = 3;

    @RabbitListener(queues = RabbitMQConfig.DEAD_LETTER_QUEUE)
    public void handleDeadLetter(MessageDTO messageDTO) {

        log.error("Dead Letter Queue에서 메시지 감지: {}", messageDTO);

        if (messageDTO.getRetryCount() >= MAX_DLQ_RETRY_ATTEMPTS) {
            log.error("DLQ 메시지 최대 재시도 횟수 초과. 관리자에게 알림 전송: {}", messageDTO);
            slackNotificationService.sendNotification("⚠️ DLQ 메시지 최대 재시도 초과: " + messageDTO);
            return;
        }


        // 서킷 브레이커를 사용하는 메시지만 서킷 상태 확인
        if (messageDTO.isCircuitBreakerUsed()) {
            if (messageService.getCircuitBreakerState() == MessageServiceImpl.CircuitBreakerState.OPEN) {
                log.warn("서킷 브레이커가 열려 있음. DLQ 메시지 재전송 대기: {}", messageDTO);
                requeueMessage(messageDTO);
                return;
            }
        }

        try {
            messagingTemplate.convertAndSend("/topic/recovered-messages", messageDTO);
            log.info("DLQ 메시지 복구 성공: {}", messageDTO);
        } catch (Exception e) {
            log.error("DLQ 메시지 복구 실패, 다시 DLQ에 추가: {}", messageDTO, e);
            requeueMessage(messageDTO);
        }
    }

    private void requeueMessage(MessageDTO messageDTO) {
        messageDTO.setRetryCount(messageDTO.getRetryCount() + 1);
        rabbitTemplate.convertAndSend(RabbitMQConfig.DEAD_LETTER_EXCHANGE, RabbitMQConfig.DEAD_LETTER_QUEUE, messageDTO);
    }
}
