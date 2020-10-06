package com.meetup.ms.payment.service;

import com.meetup.ms.contract.payment.PaymentRequest;
import com.meetup.ms.contract.payment.PaymentResponse;

public interface TransactionService {

    PaymentResponse processTransaction(PaymentRequest paymentRequest);
}
