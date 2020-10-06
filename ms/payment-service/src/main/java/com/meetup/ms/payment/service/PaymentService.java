package com.meetup.ms.payment.service;

import com.meetup.ms.contract.payment.PaymentRequest;

public interface PaymentService {

    void processPayment(PaymentRequest paymentRequest);
}
