package com.hq.myfirtsapi.controllers;

import com.hq.myfirtsapi.apiusers.AuthError;
import com.hq.myfirtsapi.models.ApiExceptionModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    // Mensaje para falta de argumento nulos.
    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<Object> handlerArgumentException(IllegalArgumentException ex){
        ApiExceptionModel apiExceptionModel = ApiExceptionModel.builder()
                .message(ex.getMessage())
                //.throwable(ex)
                .httpStatus(HttpStatus.BAD_REQUEST)
                .zonedDateTime(ZonedDateTime.now(ZoneId.of("Z")))
                .build();

        return new ResponseEntity<>(apiExceptionModel, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<Object> handlerRentimeException(RuntimeException ex){
        ApiExceptionModel apiExceptionModel = ApiExceptionModel.builder()
                .message(ex.getMessage())
                //.throwable(ex)
                .httpStatus(HttpStatus.BAD_REQUEST)
                .zonedDateTime(ZonedDateTime.now(ZoneId.of("Z")))
                .build();

        return new ResponseEntity<>(apiExceptionModel, HttpStatus.BAD_REQUEST);
    }

}
