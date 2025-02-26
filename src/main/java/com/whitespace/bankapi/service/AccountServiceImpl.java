package com.whitespace.bankapi.service;

import com.whitespace.bankapi.exception.AccountNotFoundException;
import com.whitespace.bankapi.model.Account;
import com.whitespace.bankapi.model.Customer;
import com.whitespace.bankapi.model.Transfer;
import com.whitespace.bankapi.repository.AccountRepository;
import com.whitespace.bankapi.repository.CustomerRepository;
import com.whitespace.bankapi.repository.TransferRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final TransferRepository transferRepository;

    public AccountServiceImpl(AccountRepository accountRepository, CustomerRepository customerRepository, TransferRepository transferRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
        this.transferRepository = transferRepository;
    }

    @Transactional
    @Override
    public Account createAccount(Long customerId, Long initialDeposit) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        Account account = new Account(customer, initialDeposit);
        return accountRepository.save(account);
    }

    @Override
    public Long getBalance(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
        return account.getBalance();
    }

    @Override
    public List<Transfer> getTransferHistory(Long accountId) {
        return transferRepository.findBySourceAccountIdOrDestinationAccountId(accountId, accountId);
    }
}
