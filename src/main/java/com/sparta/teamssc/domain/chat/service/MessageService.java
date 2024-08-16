package com.sparta.teamssc.domain.chat.service;

import com.sparta.teamssc.domain.chat.entity.Message;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;

import java.util.List;

public interface MessageService {
    void sendTeamMessage(Long teamId, String content,StompHeaderAccessor accessor);
     void sendPeriodMessage(Long periodId, String content, StompHeaderAccessor accessor);

    List<Message> getMessagesForTeam(Long teamId);
    List<Message> getMessagesForPeriod(Long periodId);
    void deleteOldMessages();
}
