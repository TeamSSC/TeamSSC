package com.sparta.teamssc.domain.chat.controller;

import com.sparta.teamssc.domain.chat.dto.MessageRequestDto;
import com.sparta.teamssc.domain.chat.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @MessageMapping("/chat/team/{teamId}")
    public void sendTeamMessage(@Payload MessageRequestDto messageRequestDto,
                                @DestinationVariable Long teamId,
                                @Header("Authorization") String token) {
        messageService.sendTeamMessage(teamId, messageRequestDto.getContent(),token);
    }

    @MessageMapping("/chat/period/{periodId}")
    public void sendPeriodMessage(@Payload MessageRequestDto messageRequestDto,
                                  @DestinationVariable Long periodId,
                                  @Header("Authorization") String token) {
        messageService.sendPeriodMessage(periodId, messageRequestDto.getContent(),token);
    }
}