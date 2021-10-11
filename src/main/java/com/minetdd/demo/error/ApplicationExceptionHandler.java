package com.minetdd.demo.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(WeatherNotFoundException.class)
    public ResponseEntity handleException(WeatherNotFoundException e, HttpServletRequest request){
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND.toString(), e.getLocalizedMessage(), request.getServletPath());
        return new ResponseEntity(apiError, HttpStatus.NOT_FOUND);
    }



}
