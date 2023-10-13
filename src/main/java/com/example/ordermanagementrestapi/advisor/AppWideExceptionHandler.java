package com.example.ordermanagementrestapi.advisor;

import com.example.ordermanagementrestapi.exception.NotFoundException;
import com.example.ordermanagementrestapi.util.StandardResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppWideExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity handleNotFoundException(NotFoundException e){
        return new ResponseEntity(new StandardResponse(404,"Error", e.getMessage()), HttpStatus.NOT_FOUND);
    }
}
