package com.sparta.teamssc.domain.board.like.controller;

import com.sparta.teamssc.domain.board.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

}
