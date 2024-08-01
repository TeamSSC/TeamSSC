package com.sparta.teamssc.domain.fcm.service;

import com.sparta.teamssc.domain.board.board.entity.Board;
import com.sparta.teamssc.domain.board.board.service.BoardService;
import com.sparta.teamssc.domain.fcm.dto.TokenNotificationRequestDto;
import com.sparta.teamssc.domain.user.user.entity.User;
import com.sparta.teamssc.domain.user.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final FCMService fcmService;
    private final UserService userService;
    private final BoardService boardService;

    @Override
    public void sendPostLikeNotification(Long postId) {
        //게시글찾아서 -> 작성자 찾기
        Board post = boardService.findBoardByBoardId(postId);

        User postAuthor = post.getUser();

        // 알림 요청
        TokenNotificationRequestDto requestDto = new TokenNotificationRequestDto();
        requestDto.setTargetToken(postAuthor.getFcmToken());
        requestDto.setTitle("게시글 좋아요 알림");
        requestDto.setMessage("게시글 '" + post.getTitle() + "'에 좋아요가 눌렸습니다.");

        // 알림 전송
        fcmService.sendByToken(requestDto);
    }

    @Override
    public void sendPostCommentNotification(Long postId) {
        Board post = boardService.findBoardByBoardId(postId);
        User postAuthor = post.getUser();

        TokenNotificationRequestDto requestDto = new TokenNotificationRequestDto();
        requestDto.setTargetToken(postAuthor.getFcmToken());
        requestDto.setTitle("게시글 댓글 알림");
        requestDto.setMessage("게시글 '" + post.getTitle() + "'에 댓글이 달렸습니다.");

        fcmService.sendByToken(requestDto);
    }

    @Override
    public void sendAnnouncementNotification(Long periodId) {
        // periodId를 기반으로 사용자 목록을 가져오기
        List<User> users = userService.getUsersByPeriodId(periodId);
        for (User user : users) {

            // 각 사용자에게 알림을 전송
            TokenNotificationRequestDto requestDto = new TokenNotificationRequestDto();
            requestDto.setTargetToken(user.getFcmToken());
            requestDto.setTitle("공지 알림");
            requestDto.setMessage("새로운 공지가 도착했습니다.");
            fcmService.sendByToken(requestDto);
        }
    }

    @Override
    public void sendMessageNotification(Long periodId, String messageContent) {

        List<User> users = userService.getUsersByPeriodId(periodId);
        for (User user : users) {

            TokenNotificationRequestDto requestDto = new TokenNotificationRequestDto();
            requestDto.setTargetToken(user.getFcmToken());
            requestDto.setTitle("메시지 알림");
            requestDto.setMessage(messageContent);
            fcmService.sendByToken(requestDto);
        }
    }
}
