package com.example.celog.exceptionhandling;

import com.example.celog.common.ApiResponseDto;
import com.example.celog.common.ErrorResponse;
import com.example.celog.common.ResponseUtils;
import com.example.celog.enumclass.ExceptionEnum;
import org.springframework.http.HttpStatus;

public class GlobalExceptionHandling {

    public static ApiResponseDto<ErrorResponse> responseException(ExceptionEnum exceptionEnum) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(exceptionEnum.getCode())
                .message(exceptionEnum.getMsg())
                .build();
        return ResponseUtils.error(errorResponse);
    }

    public static ApiResponseDto<ErrorResponse> responseException(HttpStatus status, String message) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(status.value())
                .message(message)
                .build();
        return ResponseUtils.error(errorResponse);
    }
}
