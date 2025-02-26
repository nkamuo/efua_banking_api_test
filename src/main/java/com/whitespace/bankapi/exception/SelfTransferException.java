package com.whitespace.bankapi.exception;

import com.whitespace.bankapi.model.Account;

public class SelfTransferException extends BankingException{

    public SelfTransferException(Account account){
//        var message = "Cannot transfer "+  amount+" Insufficient funds in source account.";
        super("Cannot transfer funds to and from the same account.");
    }
}
