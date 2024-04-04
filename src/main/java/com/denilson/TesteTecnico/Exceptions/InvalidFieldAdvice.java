package com.denilson.TesteTecnico.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class InvalidFieldAdvice {
    @ResponseBody
    @ExceptionHandler(InvalidFieldException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<String> invalidFieldAdviceHandler( InvalidFieldException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
