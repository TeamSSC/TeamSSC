package com.sparta.teamssc.domain.fcm.controller;

import com.sparta.teamssc.domain.fcm.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    // 게시글 좋아요 알림 전송
    @PostMapping("/post-like/{postId}")
    public void sendPostLikeNotification(@PathVariable Long postId) {
        notificationService.sendPostLikeNotification(postId);

    }

    // 게시글 댓글 알림 전송
    @PostMapping("/post-comment/{postId}")
    public void sendPostCommentNotification(@PathVariable Long postId) {
        notificationService.sendPostCommentNotification(postId);
    }

    // 공지 알림 전송
    @PostMapping("/announcement/{periodId}")
    public void sendAnnouncementNotification(@PathVariable Long periodId) {
        notificationService.sendAnnouncementNotification(periodId);
    }

    // 메시지 알림 전송
    @PostMapping("/message/{periodId}")
    public void sendMessageNotification(@PathVariable Long periodId, @RequestBody String messageContent) {
        notificationService.sendMessageNotification(periodId, messageContent);
    }
}
