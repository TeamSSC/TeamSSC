package com.sparta.teamssc.domain.board.comment.controller;


import com.sparta.teamssc.domain.board.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

}
