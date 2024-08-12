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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MessageController {
    private final MessageService messageService;

    @MessageMapping("/chat.team.{teamId}")
    public void sendTeamMessage(@Payload MessageRequestDto messageRequestDto,
                                @DestinationVariable Long teamId){


        messageService.sendTeamMessage(teamId, messageRequestDto.getContent());
    }

    @MessageMapping("/chat.period.{periodId}")
    public void sendPeriodMessage(@Payload MessageRequestDto messageRequestDto,
                                  @DestinationVariable Long periodId,
                                  Principal principal) {
        if (principal == null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                principal = (Principal) authentication.getPrincipal();
                log.info("Fallback으로 SecurityContext에서 Principal을 가져옴: {}", principal.getName());
            } else {
                throw new IllegalStateException("Principal이 null이고 SecurityContext에서도 인증 정보를 찾을 수 없습니다.");
            }
        }

        messageService.sendPeriodMessage(periodId, messageRequestDto.getContent(),principal);
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
