//package com.sparta.teamssc.domain.fcm.service;
//
//import com.sparta.teamssc.domain.board.board.entity.Board;
//import com.sparta.teamssc.domain.board.board.service.BoardService;
//import com.sparta.teamssc.domain.fcm.dto.TokenNotificationRequestDto;
//import com.sparta.teamssc.domain.user.user.entity.User;
//import com.sparta.teamssc.domain.user.user.service.UserService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class NotificationServiceImpl implements NotificationService {
//
//    private final FCMService fcmService;
//    private final UserService userService;
//    private final BoardService boardService;
//
//    @Override
//
//    public void sendPostLikeNotification(Long postId) {
//        //게시글찾아서 -> 작성자 찾기
//        Board board = boardService.findBoardByBoardId(postId);
//        User boardAuthor = board.getUser();
//        String fcmToken = boardAuthor.getFcmToken();
//
//        log.info("게시글 좋아요 알림 - 보드 ID: {}, 작성자 ID: {}, FCM Token: {}", postId, boardAuthor.getId(), fcmToken);
//
//        if (isInvalidFcmToken(fcmToken)) {
//            log.warn("FCM 토큰 정보가 유효하지 않다. userID: {}", boardAuthor.getId());
//            return;
//        }
//
//        TokenNotificationRequestDto requestDto = createRequestDto(fcmToken, "게시글 좋아요 알림", "게시글 '" + board.getTitle() + "'에 좋아요가 눌렸습니다.");
//        fcmService.sendByToken(requestDto);
//    }
//
//    @Override
//    public void sendPostCommentNotification(Long postId) {
//        Board board = boardService.findBoardByBoardId(postId);
//        User boardAuthor = board.getUser();
//        String fcmToken = boardAuthor.getFcmToken();
//
//        log.info("게시글 댓글 알림 - 보드 ID: {}, 작성자 ID: {}, FCM Token: {}", postId, boardAuthor.getId(), fcmToken);
//
//        if (isInvalidFcmToken(fcmToken)) {
//            log.warn("FCM 토큰 정보가 유효하지 않다. userID: {}", boardAuthor.getId());
//            return;
//        }
//
//        TokenNotificationRequestDto requestDto = createRequestDto(fcmToken, "게시글 댓글 알림", "게시글 '" + board.getTitle() + "'에 댓글이 달렸습니다.");
//        fcmService.sendByToken(requestDto);
//    }
//
//    @Override
//    public void sendAnnouncementNotification(Long periodId) {
//        // periodId를 기반으로 사용자 목록을 가져오기
//        List<User> users = userService.getUsersByPeriodId(periodId);
//        for (User user : users) {
//
//            String fcmToken = user.getFcmToken();
//
//            log.info("알림  - User ID: {}, FCM Token: {}", user.getId(), fcmToken);
//
//            if (isInvalidFcmToken(fcmToken)) {
//                log.warn("FCM 토큰 정보가 유효하지 않다. userID: {}", user.getId());
//                continue;
//            }
//
//            // 각 사용자에게 알림을 전송
//            TokenNotificationRequestDto requestDto = createRequestDto(fcmToken, "공지 알림", "새로운 공지가 도착했습니다.");
//
//            fcmService.sendByToken(requestDto);
//        }
//    }
//
//    @Override
//    public void sendMessageNotification(Long periodId, String messageContent) {
//        List<User> users = userService.getUsersByPeriodId(periodId);
//        for (User user : users) {
//            String fcmToken = user.getFcmToken();
//
//            log.info("메시지 - User ID: {}, FCM Token: {}", user.getId(), fcmToken);
//            if (isInvalidFcmToken(fcmToken)) {
//                log.warn("FCM 토큰 정보가 유효하지 않다. userID: {}", user.getId());
//                continue;
//            }
//
//            TokenNotificationRequestDto requestDto = createRequestDto(fcmToken, "메시지 알림", messageContent);
//            fcmService.sendByToken(requestDto);
//        }
//    }
//
//    private boolean isInvalidFcmToken(String fcmToken) {
//        return fcmToken == null || fcmToken.isEmpty();
//    }
//
//    private TokenNotificationRequestDto createRequestDto(String fcmToken, String title, String message) {
//        TokenNotificationRequestDto requestDto = new TokenNotificationRequestDto();
//        requestDto.setTargetToken(fcmToken);
//        requestDto.setTitle(title);
//        requestDto.setMessage(message);
//        return requestDto;
//    }
//}