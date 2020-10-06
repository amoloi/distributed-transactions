package com.meetup.monolith.entity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "customer", schema = "public")
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Min(0)
    @Column(columnDefinition = "numeric CHECK (balance > -1)")
    private Double balance;

    @OneToMany(mappedBy = "customer")
    private Set<PaymentEntity> payments;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Set<PaymentEntity> getPayments() {
        return payments;
    }

    public void setPayments(Set<PaymentEntity> payments) {
        this.payments = payments;
    }
}
