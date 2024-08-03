package com.sparta.teamssc.domain.chat.scheduler;

import com.sparta.teamssc.domain.chat.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageCleanupScheduler {

    private final MessageService messageService;

    //    @Scheduled(cron = "0 0 0 * * *") // 매일 자정에 실행
    @Scheduled(cron = "0 55 9 * * *")
    public void cleanOldMessages() {
        messageService.deleteOldMessages();
    }
}
