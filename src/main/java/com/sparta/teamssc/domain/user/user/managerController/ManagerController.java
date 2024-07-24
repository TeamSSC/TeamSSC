package com.sparta.teamssc.domain.user.user.managerController;

import com.sparta.teamssc.domain.user.user.managerService.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class ManagerController {
    private final ManagerService managerService;
}
