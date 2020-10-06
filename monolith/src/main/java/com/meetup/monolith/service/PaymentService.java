package com.meetup.monolith.service;

import com.meetup.monolith.contract.PaymentRequest;
import com.meetup.monolith.entity.CustomerEntity;
import com.meetup.monolith.entity.StockEntity;

public interface PaymentService {

    void processPayment(CustomerEntity customerEntity, StockEntity stockEntity, PaymentRequest paymentRequest);
}
