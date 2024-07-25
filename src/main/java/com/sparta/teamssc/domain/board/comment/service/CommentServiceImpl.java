package com.sparta.teamssc.domain.board.comment.service;

import com.sparta.teamssc.domain.board.board.entity.Board;
import com.sparta.teamssc.domain.board.board.service.BoardService;
import com.sparta.teamssc.domain.board.comment.dto.request.CommentRequestDto;
import com.sparta.teamssc.domain.board.comment.entity.Comment;
import com.sparta.teamssc.domain.board.comment.repository.CommentRepository;
import com.sparta.teamssc.domain.user.user.entity.User;
import com.sparta.teamssc.domain.user.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BoardService boardService;
    private final UserService userService;

    @Override
    public void createComment(Long boardId, CommentRequestDto commentRequestDto, String username) {
        try {
            Board board = boardService.findBoardByBoardId(boardId);
            User user = userService.findByUsername(username);

            Comment comment = Comment.builder()
                    .content(commentRequestDto.getContent())
                    .parentCommentId(commentRequestDto.getParentCommentId())
                    .user(user)
                    .board(board)
                    .build();

            commentRepository.save(comment);
        } catch (Exception e) {
            throw new IllegalArgumentException("댓글 생성을 실패했습니다.");
        }
    }

}
