package com.enigma.wmb_api.controller;

import com.enigma.wmb_api.dto.response.CommonResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.mapping.PropertyReferenceException;
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
    public ResponseEntity<?> responseConstraintViolationException(ConstraintViolationException exception){
        CommonResponse<?> commonResponse=CommonResponse.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(exception.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(commonResponse);
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<?> responseDataIntegrityViolationException(DataIntegrityViolationException e){
        CommonResponse.CommonResponseBuilder<?> builder=CommonResponse.builder();

        HttpStatus httpStatus;

        if (e.getMessage().contains("foreign constraint foreign")){
            builder.statusCode(HttpStatus.BAD_REQUEST.value());
            builder.message("Tidak dapat menghapus data karena ada referensi dari tabel lain");
            httpStatus=HttpStatus.BAD_REQUEST;
        }else if(e.getMessage().contains("unique constraint") || e.getMessage().contains("Duplicate entry")){
            builder.statusCode(HttpStatus.CONFLICT.value());
            builder.message("Data Already Exist");
            httpStatus=HttpStatus.CONFLICT;

        }else{
            builder.statusCode((HttpStatus.INTERNAL_SERVER_ERROR.value()));
            builder.message("Internal Server Error");
            httpStatus=HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(httpStatus).body(builder.build());
    }
    @ExceptionHandler({PropertyReferenceException.class})
    public ResponseEntity<?> getPropertyReferenceException(PropertyReferenceException exception){
        CommonResponse<?> response=CommonResponse.builder()
                .message("property does not exist")
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<?> responseIllegalArgumentException(IllegalArgumentException e){
        CommonResponse.CommonResponseBuilder<?> builder=CommonResponse.builder();

        HttpStatus httpStatus;

        if (e.getMessage().contains("'desc' or 'asc'")){
            builder.statusCode(HttpStatus.BAD_REQUEST.value());
            builder.message("Has to be either 'desc' or 'asc'");
            httpStatus=HttpStatus.BAD_REQUEST;
        }else{
            builder.statusCode((HttpStatus.INTERNAL_SERVER_ERROR.value()));
            builder.message("Internal Server Error");
            httpStatus=HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(httpStatus).body(builder.build());
    }

    


}
