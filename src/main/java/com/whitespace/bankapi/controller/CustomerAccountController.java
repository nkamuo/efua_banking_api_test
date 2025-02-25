package com.whitespace.bankapi.controller;

import com.whitespace.bankapi.model.Account;
import com.whitespace.bankapi.model.Customer;
import com.whitespace.bankapi.repository.AccountRepository;
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
public class CustomerAccountController {

    private final AccountRepository accountRepository;

    public CustomerAccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // Show all Accounts
    @GetMapping("/accounts")
    public ResponseEntity<Page<Account>> getAllCustomerAccounts(
            @PathVariable Customer customer,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer perPage
    ) {
        if (perPage > 100){
            perPage = 100;
        }
        if(page > 0){
            page--;
        }
        var accounts = accountRepository.findAll(new Specification<Account>() {
            @Override
            public Predicate toPredicate(Root<Account> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.and(
                        criteriaBuilder.equal(
                                root.<Customer>get("customer"),
                                customer
                        )
                );
            }
        }, Pageable.ofSize(perPage).withPage(page));
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }
}
