package com.sparta.teamssc.domain.board.board.controller;

import com.sparta.teamssc.domain.board.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

}
