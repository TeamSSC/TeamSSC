package com.sparta.teamssc.domain.fcm.service;

import com.sparta.teamssc.domain.user.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final FCMService fcmService;
    private final UserService userService;


}
