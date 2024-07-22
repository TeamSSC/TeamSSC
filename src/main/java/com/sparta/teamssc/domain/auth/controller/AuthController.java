package com.sparta.teamssc.domain.auth.controller;

import com.sparta.teamssc.domain.auth.util.JwtUtil;
import com.sparta.teamssc.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final JwtUtil jwtUtil;


}