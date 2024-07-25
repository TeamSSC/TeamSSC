package com.sparta.teamssc.domain.board.comment.controller;


import com.sparta.teamssc.common.dto.ResponseDto;
import com.sparta.teamssc.domain.board.comment.dto.request.CommentRequestDto;
import com.sparta.teamssc.domain.board.comment.dto.response.CommentResponseDto;
import com.sparta.teamssc.domain.board.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 생성
    @PostMapping("/boards/{boardId}/comment")
    public ResponseEntity<ResponseDto<String>> createComment(@PathVariable Long boardId,
                                                             @RequestBody CommentRequestDto commentRequestDto,
                                                             @AuthenticationPrincipal UserDetails userDetails) {

        commentService.createComment(boardId, commentRequestDto, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.<String>builder()
                        .message("댓글 작성을 성공했습니다.")
                        .build());
    }

    // 특정 게시글에 있는 댓글 조회 ( 대댓글은 조회 안함 )
    @GetMapping("/boards/{boardId}/comments")
    public ResponseEntity<ResponseDto<Page<CommentResponseDto>>> getCommentFromBoard(@PathVariable Long boardId,
                                                                                     @RequestParam(value = "page", defaultValue = "1") int page) {

        Page<CommentResponseDto> responseDto = commentService.getCommentFromBoard(boardId, page - 1);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.<Page<CommentResponseDto>>builder()
                        .message("댓글 조회를 성공했습니다.")
                        .data(responseDto)
                        .build());
    }
}
