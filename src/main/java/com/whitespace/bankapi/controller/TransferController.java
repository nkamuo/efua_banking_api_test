package com.whitespace.bankapi.controller;

import com.whitespace.bankapi.dto.TransferRequest;
import com.whitespace.bankapi.exception.BankingException;
import com.whitespace.bankapi.model.Employee;
import com.whitespace.bankapi.model.Transfer;
import com.whitespace.bankapi.repository.TransferRepository;
import com.whitespace.bankapi.service.TransferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transfers")
public class TransferController {

    private final TransferService transferService;
    private final TransferRepository transferRepository;

    public TransferController(TransferService transferService, TransferRepository transferRepository) {
        this.transferService = transferService;
        this.transferRepository = transferRepository;
    }



    // Show all Transfers
    @Operation(summary = "Retrieve all Transfers", responses = {
            @ApiResponse(responseCode = "200", description = "List paginated list of transfers",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Employee.class))))
    })
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
        var transfers = transferRepository.findAll(Pageable.ofSize(perPage).withPage(page));
        return new ResponseEntity<>(transfers, HttpStatus.OK);
    }


    // Endpoint to transfer money between accounts
    @Operation(summary = "Transfer money between accounts", responses = {
            @ApiResponse(responseCode = "200", description = "Transfer successful",
                    content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "400", description = "Invalid transfer request")
    })
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
