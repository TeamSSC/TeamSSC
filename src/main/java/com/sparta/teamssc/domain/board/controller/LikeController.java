package com.sparta.teamssc.domain.board.controller;

import com.sparta.teamssc.common.dto.ResponseDto;
import com.sparta.teamssc.domain.board.dto.response.LikeResponseDto;
import com.sparta.teamssc.domain.board.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    // 게시글 좋아요
    @PostMapping("/boards/{boardId}/like")
    public ResponseEntity<ResponseDto<String>> likeBoard(@PathVariable Long boardId,
                                                         @AuthenticationPrincipal UserDetails userDetails) {

        likeService.toggleLikeBoard(boardId,userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.<String>builder()
                        .message("게시물에 좋아요 상태 변경을 성공했습니다")
                        .build());
    }

    // 특정 게시글의 좋아요 수
    @GetMapping("/boards/{boardId}/like")
    public ResponseEntity<ResponseDto<LikeResponseDto>> countBoardLikes(@PathVariable Long boardId) {

        LikeResponseDto likeResponseDto = likeService.countBoardLikes(boardId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.<LikeResponseDto>builder()
                        .message("게시물의 좋아요 수 조회를 성공했습니다.")
                        .data(likeResponseDto)
                        .build());
    }
}
