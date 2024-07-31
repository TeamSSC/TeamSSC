package com.sparta.teamssc.domain.chat.service;

import com.sparta.teamssc.domain.chat.dto.MessageRequestDto;
import com.sparta.teamssc.domain.chat.dto.MessageResponseDto;
import com.sparta.teamssc.domain.user.user.entity.User;

public interface MessageService {
    MessageResponseDto sendMessageToTeam(Long teamId, MessageRequestDto messageRequestDto);

    MessageResponseDto sendMessageToPeriod(Long periodId, MessageRequestDto messageRequestDto);
}
