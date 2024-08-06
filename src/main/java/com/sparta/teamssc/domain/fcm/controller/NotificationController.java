//package com.sparta.teamssc.domain.fcm.controller;
//
//import com.sparta.teamssc.domain.fcm.service.NotificationService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/notifications")
//@RequiredArgsConstructor
//public class NotificationController {
//    private final NotificationService notificationService;
//
//    // 게시글 좋아요 알림 전송
//    @PostMapping("/board-like/{boardId}")
//    public void sendPostLikeNotification(@PathVariable Long boardId) {
//        notificationService.sendPostLikeNotification(boardId);
//
//    }
//
//    // 게시글 댓글 알림 전송
//    @PostMapping("/board-comment/{boardId}")
//    public void sendPostCommentNotification(@PathVariable Long boardId) {
//        notificationService.sendPostCommentNotification(boardId);
//    }
//
//    // 공지 알림 전송
//    @PostMapping("/announcement/period/{periodId}")
//    public void sendAnnouncementNotification(@PathVariable Long periodId) {
//        notificationService.sendAnnouncementNotification(periodId);
//    }
//
//    // 메시지 알림 전송
//    @PreAuthorize("hasRole('ROLE_MANAGER')")
//    @PostMapping("/message/period/{periodId}")
//    public void sendMessageNotification(@PathVariable Long periodId, @RequestBody String messageContent) {
//        notificationService.sendMessageNotification(periodId, messageContent);
//    }
//}
