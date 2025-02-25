package com.whitespace.bankapi.exception;

import com.whitespace.bankapi.model.Account;

public class InsufficientFundException extends RuntimeException{

    public InsufficientFundException(Long amount, Account account){
        var message = "Cannot transfer "+  amount+" Insufficient funds in source account.";
        super(message);
    }
}
