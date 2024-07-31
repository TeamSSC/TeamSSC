package com.sparta.teamssc.domain.chat.service;

import com.sparta.teamssc.domain.chat.dto.MessageRequestDto;
import com.sparta.teamssc.domain.chat.dto.MessageResponseDto;
import com.sparta.teamssc.domain.chat.entity.Message;
import com.sparta.teamssc.domain.chat.entity.MessageReceiver;
import com.sparta.teamssc.domain.chat.repository.MessageRepository;
import com.sparta.teamssc.domain.team.service.TeamService;
import com.sparta.teamssc.domain.user.user.entity.User;
import com.sparta.teamssc.domain.user.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final UserService userService;
    private final TeamService teamService;

    @Transactional
    @Override
    public MessageResponseDto sendMessageToTeam(Long teamId, MessageRequestDto messageRequestDto) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String currentUsername = userDetails.getUsername();

        User sender = userService.getUserByEmail(currentUsername);

        if (!teamService.isSameTeam(sender.getId(), teamId)) {
            throw new IllegalArgumentException("팀에 속한 사용자만 메시지를 보낼 수 있습니다.");
        }

        Message message = Message.builder()
                .sender(sender)
                .content(messageRequestDto.getContent())
                .sentTime(LocalDateTime.now())
                .build();

        message = messageRepository.save(message);

        return new MessageResponseDto(message.getId(), sender.getId(), sender.getUsername(), message.getContent(), message.getSentTime());
    }

    @Transactional
    @Override
    public MessageResponseDto sendMessageToPeriod(Long periodId, MessageRequestDto messageRequestDto) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currentUsername = userDetails.getUsername();
        User sender = userService.getUserByEmail(currentUsername);

        if (!sender.getPeriod().getId().equals(periodId)) {
            throw new IllegalArgumentException("같은 기수의 사용자만 메시지를 보낼 수 있습니다.");
        }

        Message message = Message.builder()
                .sender(sender)
                .content(messageRequestDto.getContent())
                .sentTime(LocalDateTime.now())
                .build();

        message = messageRepository.save(message);

        return new MessageResponseDto(message.getId(), sender.getId(), sender.getUsername(), message.getContent(), message.getSentTime());
    }
}
