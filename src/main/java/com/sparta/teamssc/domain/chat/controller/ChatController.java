package com.sparta.teamssc.domain.chat.controller;

import com.sparta.teamssc.domain.chat.dto.MessageRequestDto;
import com.sparta.teamssc.domain.chat.dto.MessageResponseDto;
import com.sparta.teamssc.domain.user.user.entity.User;
import com.sparta.teamssc.domain.user.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final UserService userService;

    @MessageMapping("/sendTeamMessage/{teamId}")
    @SendTo("/topic/team/{teamId}")
    public MessageResponseDto sendTeamMessage(MessageRequestDto messageRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new IllegalArgumentException("시큐리티 못찾음");
        }
        String username = authentication.getName();
        User sender = userService.getUserByEmail(username);

        return MessageResponseDto.builder()
                .messageId(System.currentTimeMillis())
                .senderId(sender.getId())
                .senderUsername(sender.getUsername())
                .content(messageRequestDto.getContent())
                .sentAt(LocalDateTime.now())
                .build();
    }

    @MessageMapping("/sendMyTrackMessage")
    @SendTo("/topic/track/{periodId}")
    public MessageResponseDto sendTrackMessage(MessageRequestDto messageRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new IllegalArgumentException("시큐리티 못찾음");
        }
        String username = authentication.getName();
        User sender = userService.getUserByEmail(username);
        Long periodId = sender.getPeriod().getId();

        return MessageResponseDto.builder()
                .messageId(System.currentTimeMillis())
                .senderId(sender.getId())
                .senderUsername(sender.getUsername())
                .content(messageRequestDto.getContent())
                .sentAt(LocalDateTime.now())
                .build();
    }
}
