package com.whitespace.bankapi.service;

import com.whitespace.bankapi.exception.AccountNotFoundException;
import com.whitespace.bankapi.exception.InsufficientFundException;
import org.springframework.transaction.annotation.Transactional;

public interface TransferService {
    /**
     * Transfers Funds from one account to another
     * @param sourceAccountId ID of account to subtract from
     * @param destinationAccountId ID of Account to Add to
     * @param amount
     */
    @Transactional
    void transferMoney(Long sourceAccountId, Long destinationAccountId, Long amount) throws InsufficientFundException, AccountNotFoundException;
}
