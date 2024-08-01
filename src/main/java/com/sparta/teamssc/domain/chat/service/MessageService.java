package com.sparta.teamssc.domain.chat.service;

import com.sparta.teamssc.domain.chat.dto.MessageRequestDto;
import com.sparta.teamssc.domain.chat.dto.MessageResponseDto;
import com.sparta.teamssc.domain.user.user.entity.User;

public interface MessageService {
    void sendTeamMessage(Long teamId, String content);
     void sendPeriodMessage(Long periodId, String content);

}
