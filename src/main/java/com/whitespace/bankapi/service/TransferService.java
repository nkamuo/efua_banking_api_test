package com.whitespace.bankapi.service;

import org.springframework.transaction.annotation.Transactional;

public interface TransferService {
    @Transactional
    void transferMoney(Long sourceAccountId, Long destinationAccountId, Long amount);
}
