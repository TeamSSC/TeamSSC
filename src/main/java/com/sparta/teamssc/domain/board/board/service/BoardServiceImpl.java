package com.sparta.teamssc.domain.board.board.service;

import com.sparta.teamssc.common.dto.ResponseDto;
import com.sparta.teamssc.domain.board.board.dto.BoardRequestDto;
import com.sparta.teamssc.domain.board.board.entity.Board;
import com.sparta.teamssc.domain.board.board.repository.BoardRepository;
import com.sparta.teamssc.domain.user.user.entity.User;
import com.sparta.teamssc.domain.user.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final UserService userService;

    // 게시글 생성
    @Override
    public ResponseDto<?> createBoard(BoardRequestDto requestDto, String username) {

        User user = userService.findByUsername(username);
        Board board = Board.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .user(user)
                .build();

        boardRepository.save(board);

        return ResponseDto.builder()
                .message("게시글 생성 성공")
                .build();
    }



}
