package com.denilson.TesteTecnico.Exceptions;

public class InvalidAddressExcetion extends RuntimeException{
    public InvalidAddressExcetion(String message){
        super(message);
    }
}
