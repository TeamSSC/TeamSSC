package com.sparta.teamssc.domain.board.board.controller;

import com.sparta.teamssc.common.dto.ResponseDto;
import com.sparta.teamssc.domain.board.board.dto.request.BoardRequestDto;
import com.sparta.teamssc.domain.board.board.dto.response.BoardListResponseDto;
import com.sparta.teamssc.domain.board.board.dto.response.BoardResponseDto;
import com.sparta.teamssc.domain.board.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // 게시글 생성
    @PostMapping("/boards")
    public ResponseEntity<ResponseDto<String>> createBoard(@ModelAttribute BoardRequestDto requestDto,
                                                           @AuthenticationPrincipal UserDetails userDetails) {

        boardService.createBoard(requestDto, userDetails.getUsername());

        return ResponseEntity.status(200)
                .body(ResponseDto.<String>builder()
                        .message("게시글 생성 성공")
                        .build());
    }

    // 특장 게시글 조회
    @GetMapping("/boards/{boardId}")
    public ResponseEntity<ResponseDto<BoardResponseDto>> getBoard(@PathVariable Long boardId) {

        BoardResponseDto responseDto = boardService.getBoard(boardId);

        return ResponseEntity.status(200)
                .body(ResponseDto.<BoardResponseDto>builder()
                        .message("게시글 조회 성공")
                        .data(responseDto)
                        .build());
    }

    // 게시글 전체 조회
    @GetMapping("/boards")
    public ResponseEntity<ResponseDto<Page<BoardListResponseDto>>> getBoards(@RequestParam(value = "page", defaultValue = "1") int page) {

        Page<BoardListResponseDto> responseDto = boardService.getBoards(page - 1);

        return ResponseEntity.status(200)
                .body(ResponseDto.<Page<BoardListResponseDto>>builder()
                        .message("게시글 조회 성공")
                        .data(responseDto)
                        .build());
    }
}
