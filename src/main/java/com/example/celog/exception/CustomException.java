package com.example.celog.exception;

import com.example.celog.exception.enumclass.Error;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException {

    private final Error error;

}