package com.sparta.teamssc.domain.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponseDto {
    private Long messageId;
    private Long senderId;
    private String senderUsername;
    private String content;
    private LocalDateTime sentAt;
}
