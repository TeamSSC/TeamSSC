package com.sparta.teamssc.domain.board.like.service;

import com.sparta.teamssc.domain.board.board.entity.Board;
import com.sparta.teamssc.domain.board.board.service.BoardService;
import com.sparta.teamssc.domain.board.like.entity.Like;
import com.sparta.teamssc.domain.board.like.repository.LikeRepository;
import com.sparta.teamssc.domain.user.user.entity.User;
import com.sparta.teamssc.domain.user.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final BoardService boardService;
    private final UserService userService;

    @Override
    public void likeBoard(Long boardId, String username) {
        try {
            Board board = boardService.findBoardByBoardId(boardId);
            User user = userService.findByUsername(username);

            if (likeRepository.findByBoardIdAndUserId(boardId, user.getId()).isPresent()) {
                throw new IllegalArgumentException("중복 좋아요는 할 수 없습니다.");
            } else {
                likeRepository.save(Like.builder()
                        .board(board)
                        .user(user)
                        .build());
            }

        } catch (Exception e) {
            throw new IllegalArgumentException("좋아요 실패");
        }
    }

}
