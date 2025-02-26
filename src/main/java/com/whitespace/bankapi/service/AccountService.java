package com.whitespace.bankapi.service;

import com.whitespace.bankapi.model.Account;
import com.whitespace.bankapi.model.Transfer;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AccountService {
    @Transactional
    Account createAccount(Long customerId, Long initialDeposit);

    Long getBalance(Long accountId);

    List<Transfer> getTransferHistory(Long accountId);
}
