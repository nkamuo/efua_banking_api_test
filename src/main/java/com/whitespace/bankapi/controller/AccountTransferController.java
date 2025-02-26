package com.whitespace.bankapi.controller;

import com.whitespace.bankapi.model.Account;
import com.whitespace.bankapi.model.Transfer;
import com.whitespace.bankapi.model.Customer;
import com.whitespace.bankapi.repository.TransferRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

@RestController
@RequestMapping("/api/customers/{customer}")
public class CustomerTransferController {

    private final TransferRepository transferRepository;

    public CustomerTransferController(TransferRepository transferRepository) {
        this.transferRepository = transferRepository;
    }

    // Show all Transfers

    @Operation(summary = "Show all Transfers by or to this Customer",
            parameters = {
            @Parameter(
                    name = "customer"
            )
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Request Successful",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Transfer.class))),
                    @ApiResponse(responseCode = "404", description = "Customer not found")
            })
    @GetMapping("/transfers")
    public ResponseEntity<Page<Transfer>> getAllCustomerTransfers(
            @PathVariable Account account,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer perPage
    ) {
        if (perPage > 100){
            perPage = 100;
        }
        if(page > 0){
            page--;
        }
        var transfers = transferRepository.findAll(new Specification<Transfer>() {
            @Override
            public Predicate toPredicate(Root<Transfer> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.or(
                        criteriaBuilder.equal(
                                root.get("sourceAccountId"),
                                account.getId()
                        ),
                        criteriaBuilder.equal(
                                root.get("destinationAccountId"),
                                account.getId()
                        )
                );
            }
        }, Pageable.ofSize(perPage).withPage(page));
        return new ResponseEntity<>(transfers, HttpStatus.OK);
    }
}
