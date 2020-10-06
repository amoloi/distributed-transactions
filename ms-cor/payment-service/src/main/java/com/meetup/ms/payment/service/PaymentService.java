package com.meetup.ms.payment.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.meetup.ms.contract.payment.PaymentRequest;
import com.meetup.ms.contract.payment.PaymentResponse;
import com.meetup.ms.contract.payment.TransactionUpdate;

import java.util.UUID;

public interface PaymentService {

    void createTransaction(PaymentRequest paymentRequest) throws JsonProcessingException;

    void updateTransaction (TransactionUpdate transactionUpdate);

    PaymentResponse getTransactionDetails(UUID transactionId);
}
