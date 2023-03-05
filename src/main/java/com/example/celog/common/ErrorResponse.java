package com.example.celog.common;

import com.example.celog.enumclass.ExceptionEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
    private int status;
    private String message;

    @Builder
    private ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public static ErrorResponse of(int status, String message){
        return ErrorResponse.builder()
                .status(status)
                .message(message)
                .build();
    }

    public static ErrorResponse of(ExceptionEnum exceptionEnum) {
        return ErrorResponse.builder()
                .status(exceptionEnum.getCode())
                .message(exceptionEnum.getMsg())
                .build();
    }
}