package com.sparta.teamssc.domain.board.board.controller;

import com.sparta.teamssc.common.dto.ResponseDto;
import com.sparta.teamssc.domain.board.board.dto.request.BoardRequestDto;
import com.sparta.teamssc.domain.board.board.dto.response.BoardListResponseDto;
import com.sparta.teamssc.domain.board.board.dto.response.BoardResponseDto;
import com.sparta.teamssc.domain.board.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class NoticeController {

    private final BoardService boardService;

    // 공지 사항 작성
    @PostMapping("/notices")
    public ResponseEntity<ResponseDto<String>> createNotice(@ModelAttribute BoardRequestDto requestDto,
                                                            @AuthenticationPrincipal UserDetails userDetails) {
        boardService.createNotice(requestDto, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.<String>builder()
                        .message("공지사항 생성 성공했습니다")
                        .build());
    }

    // 공지 사항 조회
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @GetMapping("/notices")
    public ResponseEntity<ResponseDto<Page<BoardListResponseDto>>> getNotices(@RequestParam(value = "page", defaultValue = "1") int page,
                                                                              @AuthenticationPrincipal UserDetails userDetails) {

        Page<BoardListResponseDto> responseDto = boardService.getNotices(page - 1, userDetails.getUsername());

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.<Page<BoardListResponseDto>>builder()
                        .message("공지사항 조회를 성공했습니다.")
                        .data(responseDto)
                        .build());
    }
}
