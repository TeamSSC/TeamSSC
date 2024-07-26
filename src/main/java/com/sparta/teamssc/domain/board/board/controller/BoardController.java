package com.sparta.teamssc.domain.board.board.controller;

import com.sparta.teamssc.common.dto.ResponseDto;
import com.sparta.teamssc.domain.board.board.dto.request.BoardRequestDto;
import com.sparta.teamssc.domain.board.board.dto.request.BoardUpdateRequestDto;
import com.sparta.teamssc.domain.board.board.dto.response.BoardListResponseDto;
import com.sparta.teamssc.domain.board.board.dto.response.BoardResponseDto;
import com.sparta.teamssc.domain.board.board.service.BoardService;
import com.sparta.teamssc.domain.user.role.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.<String>builder()
                        .message("게시글 생성 성공")
                        .build());
    }

    // 특장 게시글 조회
    @GetMapping("/boards/{boardId}")
    public ResponseEntity<ResponseDto<BoardResponseDto>> getBoard(@PathVariable Long boardId) {

        BoardResponseDto responseDto = boardService.getBoard(boardId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.<BoardResponseDto>builder()
                        .message("게시글 조회 성공")
                        .data(responseDto)
                        .build());
    }

    // 게시글 전체 조회
    @GetMapping("/boards")
    public ResponseEntity<ResponseDto<Page<BoardListResponseDto>>> getBoards(@RequestParam(value = "page", defaultValue = "1") int page,
                                                                             @AuthenticationPrincipal UserDetails userDetails) {

        Page<BoardListResponseDto> responseDto = boardService.getBoards(page - 1, userDetails.getUsername());

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.<Page<BoardListResponseDto>>builder()
                        .message("게시글 조회 성공")
                        .data(responseDto)
                        .build());
    }

    // 게시글 수정 기능
    @PatchMapping("/boards/{boardId}")
    public ResponseEntity<ResponseDto<String>> updateBoard(@PathVariable Long boardId,
                                                           @ModelAttribute BoardUpdateRequestDto requestDto,
                                                           @AuthenticationPrincipal UserDetails userDetails) {

        boardService.updateBoard(boardId, requestDto, userDetails.getUsername()/*, (List<Role>) userDetails.getAuthorities()*/);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.<String>builder()
                        .message("게시글 수정 성공")
                        .build());
    }

    // 게시글 삭제 기능
    @DeleteMapping("/boards/{boardId}")
    public ResponseEntity<ResponseDto<String>> deleteBoard(@PathVariable Long boardId,
                                                           @AuthenticationPrincipal UserDetails userDetails) {

        boardService.deleteBoard(boardId,userDetails.getUsername()/*, (List<Role>) userDetails.getAuthorities()*/);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.<String>builder()
                        .message("게시글 삭제 성공")
                        .build());
    }
}
