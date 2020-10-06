package com.meetup.ms.contract.payment;

import java.util.UUID;

public class PaymentResponse {

    private String status;
    private UUID transactionId;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }
}
