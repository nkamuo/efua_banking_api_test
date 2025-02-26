package com.whitespace.bankapi.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Transfer fund by withdrawing from one account and depositing into another
 * @param sourceAccountId The id of the account to withdraw the fund from
 * @param destinationAccountId The id of the account to deposit the fund to
 * @param amount
 */
public record TransferRequest (
        @NotNull
         Long sourceAccountId,
                @NotNull
                 Long destinationAccountId,
                @NotNull
                @Positive()
 Long amount){}