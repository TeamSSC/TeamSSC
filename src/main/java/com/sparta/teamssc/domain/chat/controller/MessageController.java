package com.sparta.teamssc.domain.chat.controller;

import com.sparta.teamssc.domain.chat.dto.MessageRequestDto;
import com.sparta.teamssc.domain.chat.entity.Message;
import com.sparta.teamssc.domain.chat.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @MessageMapping("/chat/team/{teamId}")
    public void sendTeamMessage(@Payload MessageRequestDto messageRequestDto,
                                @DestinationVariable Long teamId){

        messageService.sendTeamMessage(teamId, messageRequestDto.getContent());
    }

    @MessageMapping("/chat/period/{periodId}")
    public void sendPeriodMessage(@Payload MessageRequestDto messageRequestDto,
                                  @DestinationVariable Long periodId) {

        messageService.sendPeriodMessage(periodId, messageRequestDto.getContent());
    }

    // 새로운 API: 특정 팀의 모든 메시지를 불러오기
    @GetMapping("/api/messages/team/{teamId}")
    public List<Message> getTeamMessages(@PathVariable Long teamId) {
        return messageService.getMessagesForTeam(teamId);
    }

    // 새로운 API: 특정 기간의 모든 메시지를 불러오기
    @GetMapping("/api/messages/period/{periodId}")
    public List<Message> getPeriodMessages(@PathVariable Long periodId) {
        return messageService.getMessagesForPeriod(periodId);
    }
}
