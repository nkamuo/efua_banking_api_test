package com.whitespace.bankapi.exception;

public class BankingException extends RuntimeException {
    public BankingException(String message) {
        super(message);
    }
}
