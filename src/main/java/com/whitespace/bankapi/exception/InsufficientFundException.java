package com.whitespace.bankapi.exception;

import com.whitespace.bankapi.model.Account;

public class InsuficientFundException extends RuntimeException{

    public InsuficientFundException(Long amount, Account account){
        var message = "Cannot transfer "+  amount+" Insufficient funds in source account.";
        super(message);
    }
}
