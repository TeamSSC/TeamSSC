package com.sparta.teamssc.exception;

import com.sparta.teamssc.common.dto.ResponseDto;
import com.sparta.teamssc.domain.team.exception.TeamNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 팀을 찾을 수 없을때
    @ExceptionHandler(TeamNotFoundException.class)
    public ResponseEntity<ResponseDto<Object>> handleTeamNotFoundException(TeamNotFoundException ex) {

        ResponseDto<Object> errorResponse = ResponseDto.<Object>builder()
                .message(ex.getMessage())
                .data(null)
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
