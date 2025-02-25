package com.whitespace.bankapi.controller;

import com.whitespace.bankapi.dto.CreateAccountRequest;
import com.whitespace.bankapi.model.Account;
import com.whitespace.bankapi.model.Customer;
import com.whitespace.bankapi.model.Transfer;
import com.whitespace.bankapi.repository.AccountRepository;
import com.whitespace.bankapi.service.AccountService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;
    private final AccountRepository accountRepository;

    public AccountController(AccountService accountService, AccountRepository accountRepository) {
        this.accountService = accountService;
        this.accountRepository = accountRepository;
    }

    // Show all Accounts
    @GetMapping
    public ResponseEntity<Page<Account>> getAllAccounts(
        @RequestParam(required = false, defaultValue = "1") Integer page,
        @RequestParam(required = false, defaultValue = "10") Integer perPage
    ) {
        if (perPage > 100){
            perPage = 100;
        }
        if(page > 0){
            page--;
        }
        var accounts = accountRepository.findAll(Pageable.ofSize(perPage).withPage(page));
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }


    // Create a new bank account
    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody CreateAccountRequest request) {
        Account account = accountService.createAccount(request.getCustomerId(), request.getInitialDeposit());
        return new ResponseEntity<>(account, HttpStatus.CREATED);
    }

    // Retrieve account
    @GetMapping("/{accountId}")
    public ResponseEntity<Account> getAccount(@PathVariable Long accountId) {
//        Long balance = accountService.getBalance(accountId);
        var account = accountRepository.findById(accountId).orElseThrow();
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    // Retrieve transfer history
    @GetMapping("/{accountId}/transfers")
    public ResponseEntity<List<Transfer>> getTransferHistory(@PathVariable Long accountId) {
        List<Transfer> history = accountService.getTransferHistory(accountId);
        return new ResponseEntity<>(history, HttpStatus.OK);
    }
}
