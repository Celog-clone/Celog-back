package com.example.celog.exceptionhandling;

import com.example.celog.common.ApiResponseDto;
import com.example.celog.common.ErrorResponse;
import com.example.celog.common.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionHandling {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponseDto<ErrorResponse> methodValidException(MethodArgumentNotValidException e) {
        ErrorResponse error = errorResponse(e.getMessage());
        log.error(e.getMessage());
        return ResponseUtils.error(error);
    }

    @ExceptionHandler(RuntimeException.class)
    public ApiResponseDto<ErrorResponse> commonException(RuntimeException e) {
        ErrorResponse error = errorResponse(e.getMessage());
        log.error(e.getMessage());
        return ResponseUtils.error(error);
    }

    private ErrorResponse errorResponse(BindingResult bindingResult) {
        String message = "";

        if (bindingResult.hasErrors()) {
            message = bindingResult.getAllErrors().get(0).getDefaultMessage();
        }

        return ErrorResponse.of(HttpStatus.BAD_REQUEST.value(), message);
    }

    private ErrorResponse errorResponse(String message) {
        return ErrorResponse.of(HttpStatus.BAD_REQUEST.value(), message);
    }
}
