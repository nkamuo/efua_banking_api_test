package com.whitespace.bankapi.controller;

import com.whitespace.bankapi.dto.CreateAccountRequest;
import com.whitespace.bankapi.exception.AccountNotFoundException;
import com.whitespace.bankapi.model.Account;
import com.whitespace.bankapi.model.Transfer;
import com.whitespace.bankapi.repository.AccountRepository;
import com.whitespace.bankapi.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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


    @Operation(summary = "Get all accounts",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of accounts",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Account.class))))
            })
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

    @Operation(summary = "Create new account",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Account created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid Request Input"),

            })
    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody CreateAccountRequest request) {
        Account account = accountService.createAccount(request.customerId(), request.initialDeposit());
        return new ResponseEntity<>(account, HttpStatus.CREATED);
    }

    // Retrieve account

    @Operation(summary = "Get a single account resource",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Account found"),
                    @ApiResponse(responseCode = "404", description = "Account ID not found"),

            })
    @GetMapping("/{accountId}")
    public ResponseEntity<Account> getAccount(@PathVariable Long accountId) {
//        Long balance = accountService.getBalance(accountId);
        var account = accountRepository.findById(accountId).orElseThrow(
                () -> new AccountNotFoundException("Account not found")
        );
        return new ResponseEntity<>(account, HttpStatus.OK);
    }


    // Retrieve account Balance

    @Operation(summary = "Get a single account balance",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Account found"),
                    @ApiResponse(responseCode = "404", description = "Account ID not found"),

            })
    @GetMapping("/{accountId}/balance")
    public ResponseEntity<Long> getAccountBalance(@PathVariable Long accountId) {
//        Long balance = accountService.getBalance(accountId);
        var account = accountRepository.findById(accountId).orElseThrow(
                () -> new AccountNotFoundException("Account not found")
        );
        return new ResponseEntity<>(account.getBalance(), HttpStatus.OK);
    }


    // Retrieve transfer history
    @Operation(summary = "Show all Transfers by or to this Account",
            parameters = {
                    @Parameter(
                            name = "account",
                            schema = @Schema(implementation = Long.class )
                    )
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Request Successful",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Transfer.class))),
                    @ApiResponse(responseCode = "404", description = "Account not found")
            })
    @GetMapping("/{account}/transfers")
    public ResponseEntity<Page<Transfer>> getTransferHistory(
            @PathVariable Long account,
        @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer perPage
    ) {
        if (perPage > 100){
            perPage = 100;
        }
        if(page > 0){
            page--;
        }
        List<Transfer> history = accountService.getTransferHistory(account);

        if(accountRepository.findById(account).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        var transfers = new PageImpl<Transfer>(
                history,
                Pageable.ofSize(perPage).withPage(page),
                history.size());

        return new ResponseEntity<>(transfers, HttpStatus.OK);
    }
}
