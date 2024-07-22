package com.sparta.teamssc.domain.board.board.controller;

import com.sparta.teamssc.common.dto.ResponseDto;
import com.sparta.teamssc.domain.board.board.dto.BoardRequestDto;
import com.sparta.teamssc.domain.board.board.entity.Board;
import com.sparta.teamssc.domain.board.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // 게시글 생성
    @PostMapping("/boards")
    public ResponseEntity<ResponseDto<?>> createBoard(@RequestBody BoardRequestDto requestDto,
                                                      @AuthenticationPrincipal UserDetails userDetails) {
        ResponseDto<?> responseDto = boardService.createBoard(requestDto, userDetails.getUsername());
        return ResponseEntity.status(200).body(responseDto);
    }

}