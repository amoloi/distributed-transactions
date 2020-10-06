package com.meetup.ms.contract.payment;

import java.util.UUID;

public class TransactionUpdate {

    private UUID transactionId;

    private String source;

    private UUID externalId;

    private String status;

    public TransactionUpdate(UUID transactionId, String source, UUID externalId, String status) {
        this.transactionId = transactionId;
        this.source = source;
        this.externalId = externalId;
        this.status = status;
    }

    public TransactionUpdate(){

    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public UUID getExternalId() {
        return externalId;
    }

    public void setExternalId(UUID externalId) {
        this.externalId = externalId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
