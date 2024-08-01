package com.sparta.teamssc.domain.chat.service;

import com.sparta.teamssc.domain.chat.entity.Message;
import com.sparta.teamssc.domain.chat.repository.MessageRepository;
import com.sparta.teamssc.domain.period.service.PeriodService;
import com.sparta.teamssc.domain.team.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final TeamService teamService;
    private final PeriodService periodService;
    @Transactional
    public void sendTeamMessage(Long teamId, String content) {
        String sender = "박다미";
       // UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //User user = userService.getUserByEmail(userDetails.getUsername());

        // 팀 ID와 사용자가 해당 팀에 속해 있는지 검증
      //  if (!teamService.isUserInTeam(user.getId(), teamId)) {
        if (!teamService.isUserInTeam(3l, teamId)) {
            throw new IllegalArgumentException("사용자가 해당 팀에 속해 있지 않습니다.");
        }
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
       // UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       // User user = userService.getUserByEmail(userDetails.getUsername());
        String sender = "박다미";
        // 기간 ID와 사용자가 해당 기간에 속해 있는지 검증
        if (!periodService.isUserInPeriod(3l, periodId)) {
            throw new IllegalArgumentException("사용자가 해당 기간에 속해 있지 않습니다.");
        }

        Message message = Message.builder()
                .content(content)
                .sender(sender)
                .roomId(periodId) // periodId
                .build();
        messageRepository.save(message);
        messagingTemplate.convertAndSend("/topic/period/" + periodId, message);
    }
}
