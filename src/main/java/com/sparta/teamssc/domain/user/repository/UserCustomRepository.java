package com.sparta.teamssc.domain.user.repository;

import com.sparta.teamssc.domain.track.entity.Period;
import com.sparta.teamssc.domain.user.dto.response.PendSignupResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserCustomRepository {

    Page<PendSignupResponseDto> findPagedPendList(Pageable pageable, Period period);
}
