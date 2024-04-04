package com.denilson.TesteTecnico.Exceptions;

public class InvalidFieldException extends RuntimeException{
    public InvalidFieldException(String message){
        super(message);
    }
}
