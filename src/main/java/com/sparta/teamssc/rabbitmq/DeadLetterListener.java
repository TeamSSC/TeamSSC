package com.sparta.teamssc.rabbitmq;

import com.sparta.teamssc.domain.chat.entity.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeadLetterListener {

    private final SlackNotificationService slackNotificationService;

    @RabbitListener(queues = RabbitMQConfig.DEAD_LETTER_QUEUE)
    public void handleDeadLetter(Message message) {

        log.error("Dead letter를 받았습니다 {}", message);

        // Slack 알림 전송
        slackNotificationService.sendNotification("채팅 데드레터 왔습니다 " + message);
    }
}
