package com.sparta.teamssc.domain.chat.service;

import com.sparta.teamssc.domain.chat.dto.MessageRequestDto;
import com.sparta.teamssc.domain.chat.dto.MessageResponseDto;
import com.sparta.teamssc.domain.chat.entity.Message;
import com.sparta.teamssc.domain.user.user.entity.User;
import org.springframework.messaging.MessageChannel;

import java.security.Principal;
import java.util.List;

public interface MessageService {
    void sendTeamMessage(Long teamId, String content);
     void sendPeriodMessage(Long periodId, String content, org.springframework.messaging.Message<?> message);

    List<Message> getMessagesForTeam(Long teamId);
    List<Message> getMessagesForPeriod(Long periodId);
    void deleteOldMessages();
}
