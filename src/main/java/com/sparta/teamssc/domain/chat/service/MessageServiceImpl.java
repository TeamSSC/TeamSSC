package com.sparta.teamssc.domain.chat.service;

import com.sparta.teamssc.domain.chat.entity.Message;
import com.sparta.teamssc.domain.chat.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Transactional
    public void sendTeamMessage(Long teamId, String content) {
       // User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String sender = "박다미";
        Message message = Message.builder()
                .content(content)
                .sender(sender)
                .roomId(teamId) // teamId로
                .build();
        messageRepository.save(message);
        messagingTemplate.convertAndSend("/topic/team/" + teamId, message);
    }

    @Transactional
    public void sendPeriodMessage(Long periodId, String content) {
        // User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String sender = "박다미";
        Message message = Message.builder()
                .content(content)
                .sender(sender)
                .roomId(periodId) // periodId
                .build();
        messageRepository.save(message);
        messagingTemplate.convertAndSend("/topic/period/" + periodId, message);
    }
}
