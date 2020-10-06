package com.meetup.monolith.service;

import com.meetup.monolith.contract.PaymentRequest;
import com.meetup.monolith.contract.PaymentResponse;

public interface TransactionService {

    PaymentResponse processTransaction(PaymentRequest paymentRequest);
}
