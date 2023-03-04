package com.example.celog.post.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
public class ExceptionDto {

    private Boolean success;

    private String response;

    private Map<String,String> error = new HashMap<>();


    public ExceptionDto(Boolean success, String response, Error error){
        this.success = success;
        this.response = response;
        this.error.put("status", error.getStatus());
        this.error.put("message", error.getMessage());
    }
}
