package com.sparta.teamssc.exception;

import com.amazonaws.services.kms.model.AlreadyExistsException;
import com.sparta.teamssc.common.dto.ResponseDto;
import com.sparta.teamssc.domain.team.exception.TeamCreationFailedException;
import com.sparta.teamssc.domain.team.exception.TeamNotFoundException;
import com.sparta.teamssc.domain.weekProgress.exception.InvalidWeekProgressException;
import jakarta.xml.bind.ValidationException;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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

    // 팀 생성에 실패했을 때
    @ExceptionHandler(TeamCreationFailedException.class)
    public ResponseEntity<ResponseDto<Object>> handleTeamCreationFailedException(TeamCreationFailedException ex) {
        ResponseDto<Object> errorResponse = ResponseDto.<Object>builder()
                .message(ex.getMessage())
                .data(null)
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    //팀생성시 에러
    @ExceptionHandler(InvalidWeekProgressException.class)
    public ResponseEntity<ResponseDto<Object>> handleInvalidRequestException(InvalidWeekProgressException ex) {
        ResponseDto<Object> errorResponse = ResponseDto.<Object>builder()
                .message(ex.getMessage())
                .data(null)
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // 존재여부에
    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ResponseDto<Object>> handleInvalidRequestException(AlreadyExistsException ex) {
        ResponseDto<Object> errorResponse = ResponseDto.<Object>builder()
                .message(ex.getMessage())
                .data(null)
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseDto<Object>> handleInvalidRequestException(IllegalArgumentException ex) {
        ResponseDto<Object> errorResponse = ResponseDto.<Object>builder()
                .message(ex.getMessage())
                .data(null)
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto<Object>> handleUserBadRequestException(MethodArgumentNotValidException ex) {
        StringBuilder errorMessage = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String message = ((FieldError) error).getDefaultMessage();
            errorMessage.append(message);
        });

        ResponseDto<Object> errorResponse = ResponseDto.<Object>builder()
                .message(errorMessage.toString())
                .data(null)
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
