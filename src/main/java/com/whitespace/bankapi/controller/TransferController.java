package com.whitespace.bankapi.controller;

import com.whitespace.bankapi.dto.TransferRequest;
import com.whitespace.bankapi.model.Account;
import com.whitespace.bankapi.model.Transfer;
import com.whitespace.bankapi.repository.TransferRepository;
import com.whitespace.bankapi.service.TransferService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transfers")
public class TransferController {

    private final TransferService transferService;
    private final TransferRepository accountRepository;

    public TransferController(TransferService transferService, TransferRepository accountRepository) {
        this.transferService = transferService;
        this.accountRepository = accountRepository;
    }



    // Show all Accounts
    @GetMapping
    public ResponseEntity<Page<Transfer>> getAllTransfers(
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


    // Endpoint to transfer money between accounts
    @PostMapping
    public ResponseEntity<String> transferMoney(@RequestBody TransferRequest request) {
        try {
            transferService.transferMoney(request.sourceAccountId(), request.destinationAccountId(), request.amount());
            return new ResponseEntity<>("Transfer successful", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Transfer Failed", HttpStatus.BAD_REQUEST);
        }
    }
}
