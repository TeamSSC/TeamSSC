package com.sparta.teamssc.domain.chat.dto;

import com.sparta.teamssc.domain.chat.entity.Message;
import lombok.Getter;

@Getter
public class MessageResponse {
    private final Message message;
    private final Long trackId;

    public MessageResponse(Message message, Long trackId) {
        this.message = message;
        this.trackId = trackId;
    }
}
