package com.whitespace.bankapi.service;

import com.whitespace.bankapi.exception.AccountNotFound;
import com.whitespace.bankapi.exception.InsufficientFundException;
import com.whitespace.bankapi.exception.SelfTransferException;
import com.whitespace.bankapi.model.Account;
import com.whitespace.bankapi.model.Transfer;
import com.whitespace.bankapi.repository.AccountRepository;
import com.whitespace.bankapi.repository.TransferRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransferService {

    private final AccountRepository accountRepository;
    private final TransferRepository transferRepository;

    public TransferService(AccountRepository accountRepository, TransferRepository transferRepository) {
        this.accountRepository = accountRepository;
        this.transferRepository = transferRepository;
    }

    @Transactional
    public void transferMoney(Long sourceAccountId, Long destinationAccountId, Long amount) {
        // Load accounts
        Account source = accountRepository.findById(sourceAccountId)
                .orElseThrow(() -> new AccountNotFound("Source account not found"));
        Account destination = accountRepository.findById(destinationAccountId)
                .orElseThrow(() -> new AccountNotFound("Destination account not found"));

        if(source.equals(destination)){
            throw new SelfTransferException(source);
        }

        // Validate funds
        if (source.getBalance() < amount) {
            throw new InsufficientFundException(amount,source);
        }

        // Perform transfer
        source.setBalance(source.getBalance() - amount);
        destination.setBalance(destination.getBalance() + amount);
        accountRepository.save(source);
        accountRepository.save(destination);

        // Record transfer history
        Transfer transfer = new Transfer(sourceAccountId, destinationAccountId, amount);
        transferRepository.save(transfer);
    }
}
