package com.whitespace.bankapi.exception;

import com.whitespace.bankapi.model.Account;

public class AccountNotFound extends IllegalArgumentException{

    public AccountNotFound(String message){
        super(message);
    }
}
