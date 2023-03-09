package com.example.celog.exception;

import com.example.celog.exception.enumclass.Error;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public class ExceptionDto {

    private Boolean success;

    private String response;

    private Map<String,String> error = new HashMap<>();
    private Map<String,Integer> error2 = new HashMap<>();


    public ExceptionDto(Boolean success, String response, Error error, Error error2){
        this.success = success;
        this.response = response;
        this.error.put("status", error2.getStatus());
        this.error.put("message", error.getMessage());
    }
}