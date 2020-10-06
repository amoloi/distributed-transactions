package com.meetup.monolith.entity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "stock", schema = "public")
public class StockEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;

    @Min(0)
    @Column(columnDefinition = "numeric CHECK (amount > -1)")
    private Integer amount;

    @OneToMany(mappedBy = "stock")
    private Set<TransactionEntity> transactions;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Set<TransactionEntity> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<TransactionEntity> transactions) {
        this.transactions = transactions;
    }
}
