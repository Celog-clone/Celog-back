package com.example.celog.post.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//@RestControllerAdvice
//public class RestApiExceptionHandler {
//    @ExceptionHandler(value = { CustomException.class })
//    public ResponseEntity<ExceptionDto> handleApiRequestException(CustomException ex) {

//        return ResponseEntity.badRequest().body(new ExceptionDto(false, null, ex.getError()));

//    }
//}