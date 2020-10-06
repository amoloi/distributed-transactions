package com.meetup.ms.stock.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.meetup.ms.contract.payment.PaymentRequest;

public interface StockService {

    void modifyStock(PaymentRequest paymentRequest) throws JsonProcessingException;
    void revertStock(PaymentRequest paymentRequest) throws JsonProcessingException;
    void stockFail(PaymentRequest paymentRequest) throws JsonProcessingException;
}
