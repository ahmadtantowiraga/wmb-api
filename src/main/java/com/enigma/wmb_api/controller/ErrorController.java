package com.enigma.wmb_api.controller;

import com.enigma.wmb_api.dto.response.CommonResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
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

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<?> responseContraintViolationException(ConstraintViolationException exception){
        CommonResponse<?> commonResponse=CommonResponse.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(exception.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(commonResponse);
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<?> responeDataIntegrityViolationException(DataIntegrityViolationException e){
        CommonResponse.CommonResponseBuilder<?> builder=CommonResponse.builder();

        HttpStatus httpStatus;

        if (e.getMessage().contains("foreign constraint foreign")){
            builder.statusCode(HttpStatus.BAD_REQUEST.value());
            builder.message("Tidak dapat menghapus data karena ada referensi dari tabel lain");
            httpStatus=HttpStatus.BAD_REQUEST;
        }else if(e.getMessage().contains("unique constraint") || e.getMessage().contains("Duplicate entry")){
            builder.statusCode(HttpStatus.CONFLICT.value());
            builder.message("Data Alredy Exist");
            httpStatus=HttpStatus.CONFLICT;

        }else{
            builder.statusCode((HttpStatus.INTERNAL_SERVER_ERROR.value()));
            builder.message("Internal Server Error");
            httpStatus=HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(httpStatus).body(builder.build());
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<?> responseIllegalArgumentException(RuntimeException exception){
        CommonResponse<?> commonResponse=CommonResponse.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(exception.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(commonResponse);
    }


}
