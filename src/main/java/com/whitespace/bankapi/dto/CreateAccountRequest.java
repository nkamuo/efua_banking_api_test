package com.whitespace.bankapi.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 *
 * @param customerId the ID of the customer this new account should belong to
 * @param initialDeposit the initial account balance to be associated with this account
 */
public record CreateAccountRequest(
        @NotNull
        @Positive
         Long customerId,
        @NotNull
        @Positive
         Long initialDeposit
        ) {
}
