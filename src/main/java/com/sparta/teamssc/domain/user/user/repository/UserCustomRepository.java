package com.sparta.teamssc.domain.user.user.repository;

import com.sparta.teamssc.domain.period.entity.Period;
import com.sparta.teamssc.domain.user.user.dto.response.PendSignupResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserCustomRepository {

    Page<PendSignupResponseDto> findPagedPendList(Pageable pageable, Period period);
}
