package com.meetup.ms.contract.stock;

import java.util.UUID;

public class StockModification {

    private UUID stockId;

    private Integer amount;

    public UUID getStockId() {
        return stockId;
    }

    public void setStockId(UUID stockId) {
        this.stockId = stockId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
