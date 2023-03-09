package com.example.celog.exception;

import com.example.celog.common.ApiResponseDto;
import com.example.celog.common.ErrorResponse;
import com.example.celog.common.ResponseUtils;
import com.example.celog.exception.enumclass.Error;
import org.springframework.http.HttpStatus;

public class GlobalExceptionHandling {

    public static ApiResponseDto<ErrorResponse> responseException(Error error) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(error.getStatus())
                .message(error.getMessage())
                .build();
        return ResponseUtils.error(errorResponse);
    }

    public static ApiResponseDto<ErrorResponse> responseException(HttpStatus status, String message) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(status.toString())
                .message(message)
                .build();
        return ResponseUtils.error(errorResponse);
    }
}
