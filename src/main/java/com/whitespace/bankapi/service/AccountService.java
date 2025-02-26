package com.whitespace.bankapi.service;

import com.whitespace.bankapi.model.Account;
import com.whitespace.bankapi.model.Transfer;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AccountService {
    @Transactional
    Account createAccount(Long customerId, Long initialDeposit);

    /**
     * Gets the current balance of a specific account
     * @param accountId - ID of the account to retrieve the balance for
     * @return
     */
    Long getBalance(Long accountId);

    /**
     * Returns a list of all transactions(transfers) to or from the account with the given ID
     * @param accountId - ID of account to check history for
     * @return
     */
    List<Transfer> getTransferHistory(Long accountId);
}
