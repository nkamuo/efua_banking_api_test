package com.whitespace.bankapi.exception;

public class AccountNotFoundException extends IllegalArgumentException{

    public AccountNotFoundException(String message){
        super(message);
    }
}
