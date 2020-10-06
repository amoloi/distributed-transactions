package com.meetup.ms.payment.entity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "transaction", schema = "public")
public class TransactionEntity {

    @Id
    private UUID id;

    @ManyToOne
    private PaymentEntity payment;

    private UUID stockId;

    private String status;

    private UUID customerId;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public PaymentEntity getPayment() {
        return payment;
    }

    public void setPayment(PaymentEntity payment) {
        this.payment = payment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UUID getStockId() {
        return stockId;
    }

    public void setStockId(UUID stockId) {
        this.stockId = stockId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }
}
