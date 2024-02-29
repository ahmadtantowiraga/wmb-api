package com.enigma.wmb_api.controller;

import com.enigma.wmb_api.dto.response.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ErrorController {
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> responseExceptionHandler(ResponseStatusException exception){
        CommonResponse<?> commonResponse=CommonResponse.builder()
                .statusCode(exception.getStatusCode().value())
                .message(exception.getReason())
                .build();
        return ResponseEntity.status(exception.getStatusCode())
                .body(commonResponse);
    }

}
