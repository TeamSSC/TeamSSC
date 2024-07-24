package com.sparta.teamssc.domain.user.user.managerService;

import com.sparta.teamssc.domain.user.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManagerServiceImpl implements ManagerService{

    private final UserRepository userRepository;
}
