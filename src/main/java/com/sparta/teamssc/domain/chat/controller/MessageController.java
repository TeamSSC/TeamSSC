package com.sparta.teamssc.domain.chat.controller;

import com.sparta.teamssc.domain.chat.dto.MessageRequestDto;
import com.sparta.teamssc.domain.chat.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @MessageMapping("/chat/team/{teamId}")
    public void sendTeamMessage(@Payload MessageRequestDto messageRequestDto, @DestinationVariable Long teamId) {
        messageService.sendTeamMessage(teamId, messageRequestDto.getContent());
    }

    @MessageMapping("/chat/period/{periodId}")
    public void sendPeriodMessage(@Payload MessageRequestDto messageRequestDto, @DestinationVariable Long periodId) {
        messageService.sendPeriodMessage(periodId, messageRequestDto.getContent());
    }
}