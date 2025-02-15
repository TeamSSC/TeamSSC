package com.sparta.teamssc.rabbitmq;

import com.rabbitmq.client.Channel;
import com.sparta.teamssc.domain.chat.entity.Message;
import com.sparta.teamssc.domain.chat.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.google.cloud.firestore.BulkWriter.MAX_RETRY_ATTEMPTS;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageListener {

    private final SimpMessagingTemplate messagingTemplate;
    private final MessageRepository messageRepository;

    private static final int MAX_FAILURE_THRESHOLD = 3; // NACK 최대 허용 횟수
    private static final long CIRCUIT_BREAKER_TIMEOUT = 15000; // 15초 동안 Consumer 차단

    // 개별 Consumer의 실패 횟수 추적하는 맵
    private final Map<String, Integer> consumerFailureCount = new ConcurrentHashMap<>();
    private final Map<String, Long> consumerBlockTime = new ConcurrentHashMap<>();

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME, ackMode = "MANUAL")
    public void handleMessage(Message message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag,
                              @Header(value = "x-death", required = false) Map<String, Object> xDeathHeader) throws IOException {
        log.debug(" 받은 RabbitMQ 메시지: {}", message);

        // 기존 메시지 재시도 횟수 확인
        int retryCount = getRetryCount(xDeathHeader);
        log.info("메시지 재시도 횟수: {}", retryCount);

        try {
            if (message.getContent() == null || message.getContent().isEmpty()) {
                log.error("널값 메시지 감지: {}", message);
                channel.basicNack(tag, false, false); // 즉시 DLQ로 이동
                return;
            }

            // 메시지를 저장 후 WebSocket 전송
            messageRepository.save(message);
            String destination = "/app/chat/" + message.getRoomType().name().toLowerCase() + "/" + message.getRoomId();
            messagingTemplate.convertAndSend(destination, message);

            channel.basicAck(tag, false); // 메시지 정상 처리 후 삭제
        } catch (IOException e) {
            log.error(" 메시지 처리 실패 ({}회째): {}", retryCount + 1, message, e);

            if (retryCount >= MAX_RETRY_ATTEMPTS) {
                log.error(" 최대 재시도 횟수 초과 → 메시지를 DLQ로 이동: {}", message);
                channel.basicNack(tag, false, false); // DLQ로 이동
            } else {
                channel.basicNack(tag, false, true); // 같은 큐에서 재시도
            }
        }
    }

    /**
     * Consumer 메시지 처리 실패 시 NACK 횟수를 증가시키고 서킷 브레이커를 적용하는 메서드
     */
    private void handleFailure(String consumerTag, Channel channel, long tag, Message message) throws IOException {

        int failureCount = consumerFailureCount.getOrDefault(consumerTag, 0) + 1;
        consumerFailureCount.put(consumerTag, failureCount);

        if (failureCount >= MAX_FAILURE_THRESHOLD) {
            log.error("Consumer [{}]가 최대 실패 횟수 초과! 서킷 브레이커 활성화", consumerTag);

            consumerBlockTime.put(consumerTag, System.currentTimeMillis() + CIRCUIT_BREAKER_TIMEOUT);
            channel.basicNack(tag, false, false); // DLQ로 이동
        } else {
            log.warn("Consumer [{}] 메시지 재처리 시도 ({}회)", consumerTag, failureCount);

            channel.basicNack(tag, false, true); // 같은 큐에서 재시도
        }
    }

    /**
     * Consumer의 실패 횟수를 초기화하여 정상 상태로 되돌리는 메서드
     */
    private void resetConsumerFailure(String consumerTag) {
        consumerFailureCount.remove(consumerTag);
        consumerBlockTime.remove(consumerTag);
    }

    /**
     * 특정 Consumer가 서킷 브레이커로 차단된 상태인지 확인하는 메서드
     */
    private boolean isConsumerBlocked(String consumerTag) {
        Long blockEndTime = consumerBlockTime.get(consumerTag);
        if (blockEndTime == null) return false;

        if (System.currentTimeMillis() > blockEndTime) {
            log.info("Consumer [{}] 서킷 브레이커 상태 해제", consumerTag);
            resetConsumerFailure(consumerTag);
            return false;
        }
        return true;
    }

    /**
     * x-death 헤더에서 재시도 횟수 추출
     */
    private int getRetryCount(Map<String, Object> xDeathHeader) {
        if (xDeathHeader == null) return 0;

        try {
            List<Map<String, Object>> xDeathList = (List<Map<String, Object>>) xDeathHeader.get("x-death");
            if (xDeathList == null || xDeathList.isEmpty()) return 0;

            return (int) xDeathList.get(0).getOrDefault("count", 0);
        } catch (Exception e) {
            log.error("x-death 헤더 파싱 중 오류 발생", e);
            return 0;
        }
    }
}