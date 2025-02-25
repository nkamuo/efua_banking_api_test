package com.whitespace.bankapi.repository;

import com.whitespace.bankapi.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
