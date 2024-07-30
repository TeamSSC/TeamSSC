package com.sparta.teamssc.domain.board.like.service;

import com.sparta.teamssc.domain.board.board.entity.Board;
import com.sparta.teamssc.domain.board.board.service.BoardService;
import com.sparta.teamssc.domain.board.like.dto.LikeResponseDto;
import com.sparta.teamssc.domain.board.like.entity.Like;
import com.sparta.teamssc.domain.board.like.repository.LikeRepository;
import com.sparta.teamssc.domain.user.user.entity.User;
import com.sparta.teamssc.domain.user.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final BoardService boardService;
    private final UserService userService;

    // 게시물 좋아요
    @Override
    public void toggleLikeBoard(Long boardId, String email) {

        Board board = boardService.findBoardByBoardId(boardId);
        User user = userService.getUserByEmail(email);

        // 해당 게시물에 대한 사용자의 좋아요 여부를 확인
        Optional<Like> existingLike = likeRepository.findByBoardIdAndUserId(boardId, user.getId());

        if (existingLike.isPresent()) {
            // 좋아요가 이미 존재하면 삭제
            likeRepository.delete(existingLike.get());
        } else {
            // 좋아요가 존재하지 않으면 새로 추가
            likeRepository.save(Like.builder()
                    .board(board)
                    .user(user)
                    .build());
        }
    }

    // 특정 게시글의 좋아요 수
    @Override
    public LikeResponseDto countBoardLikes(Long boardId) {
        // 게시글이 없을 경우 검증
        boardService.findBoardByBoardId(boardId);

        return LikeResponseDto.builder()
                .boardId(boardId)
                .likeCount(likeRepository.findByBoardId(boardId).size())
                .build();
    }
}
