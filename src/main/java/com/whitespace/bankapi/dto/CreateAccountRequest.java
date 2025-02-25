package com.whitespace.bankapi.dto;

public class CreateAccountRequest {
    private Long customerId;
    private Long initialDeposit;

    public CreateAccountRequest() { }

    // Getters & setters
    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getInitialDeposit() {
        return initialDeposit;
    }

    public void setInitialDeposit(Long initialDeposit) {
        this.initialDeposit = initialDeposit;
    }
}
