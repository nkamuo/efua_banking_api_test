package com.whitespace.bankapi.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TransferRequest (
        @NotNull
         Long sourceAccountId,
                @NotNull
                 Long destinationAccountId,
                @NotNull
                @Positive()
 Long amount){}