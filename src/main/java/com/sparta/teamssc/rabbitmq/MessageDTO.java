package com.sparta.teamssc.rabbitmq;

import com.sparta.teamssc.domain.chat.entity.Message;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MessageDTO {
    private String content;
    private String sender;
    private Long roomId;
    private String roomType;
    private int retryCount; // DLQ에서 사용할 재시도 횟수
    private boolean isCircuitBreakerUsed; // 서킷 브레이커 적용 여부
    public MessageDTO(Message message, int retryCount, boolean isCircuitBreakerUsed) {
        this.content = message.getContent();
        this.sender = message.getSender();
        this.roomId = message.getRoomId();
        this.roomType = message.getRoomType().name();
        this.retryCount = retryCount;
        this.isCircuitBreakerUsed = isCircuitBreakerUsed;
    }
}
