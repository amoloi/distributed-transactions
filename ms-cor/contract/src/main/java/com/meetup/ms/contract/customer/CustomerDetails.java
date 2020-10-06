package com.meetup.ms.contract.customer;

import java.util.UUID;

public class CustomerDetails {

    private UUID customerId;
    private Double balance;

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
