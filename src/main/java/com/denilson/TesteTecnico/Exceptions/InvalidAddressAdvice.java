package com.denilson.TesteTecnico.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class InvalidAddressAdvice {
    @ResponseBody
    @ExceptionHandler(InvalidAddressExcetion.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String nvalidAddressAdviceHandler( InvalidFieldException e){
        return e.getMessage();
    }
}
