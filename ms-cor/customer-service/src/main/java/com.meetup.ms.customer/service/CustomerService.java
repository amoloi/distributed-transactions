package com.meetup.ms.customer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.meetup.ms.contract.payment.PaymentRequest;

public interface CustomerService {

    void modifyBalance(PaymentRequest amount) throws JsonProcessingException;
    void sendFailTransaction(PaymentRequest paymentRequest) throws JsonProcessingException;
}
