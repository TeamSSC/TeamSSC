package com.sparta.teamssc.domain.chat.controller;

import com.sparta.teamssc.domain.chat.dto.MessageResponse;
import com.sparta.teamssc.domain.chat.entity.Message;
import com.sparta.teamssc.domain.user.user.entity.User;
import com.sparta.teamssc.domain.user.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final UserService userService;

    @MessageMapping("/example") // 클라이언트가 /app/example로 메시지 전송
    @SendTo("/topic/messages")  // 서버가 /topic/messages로 메시지 전달
    public String example(String str) {
        return "success";
    }

    // 팀 채팅 메시지 처리
    @MessageMapping("/sendTeamMessage/{teamId}")
    @SendTo("/topic/team/{teamId}")
    public Message sendTeamMessage(@AuthenticationPrincipal UserDetails userDetails,
                                   @DestinationVariable String teamId,
                                   Message message) {
        message.updateSender(userDetails.getUsername());
        return message;
    }

    // 사용자가 속한 기수의 채팅 메시지 처리
    @MessageMapping("/sendMyTrackMessage")
    @SendTo("/topic/track/{trackId}")
    public Message sendTrackMessage(@AuthenticationPrincipal UserDetails userDetails,
                                    Message message) {
        User user = userService.getUserByEmail(userDetails.getUsername());
        Long trackId = user.getPeriod().getId();
        message.updateSender(userDetails.getUsername());
        return new MessageResponse(message, trackId).getMessage();
    }
}
