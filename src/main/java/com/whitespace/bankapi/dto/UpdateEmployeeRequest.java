package com.whitespace.bankapi.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

/**
 * Creates a new Employee account
 * @param username Employees Username
 * @param password Employees password
 */
public record CreateEmployeeRequest(
        @NotNull
                @Length(min = 3, max = 64)
         String username,
                @NotNull
                @Length(min = 6, max = 128)
                String password){}