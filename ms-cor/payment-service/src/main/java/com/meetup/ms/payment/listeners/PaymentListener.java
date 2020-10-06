package com.meetup.ms.payment.listeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meetup.ms.contract.payment.PaymentRequest;
import com.meetup.ms.contract.payment.TransactionUpdate;
import com.meetup.ms.payment.service.PaymentService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentListener {

    private final PaymentService paymentService;
    private final ObjectMapper objectMapper;

    public PaymentListener(PaymentService paymentService){
        this.paymentService = paymentService;
        this.objectMapper = new ObjectMapper();
    }


    @KafkaListener(topics = "newPaymentTopic")
    public void processMessage(String content) throws JsonProcessingException {
        var paymentRequest = objectMapper.readValue(content, PaymentRequest.class);
        paymentService.createTransaction(paymentRequest);
    }

    @KafkaListener(topics = "transactionUpdateTopic")
    public void processUpdate(String content) throws JsonProcessingException {
        var transactionUpdate = objectMapper.readValue(content, TransactionUpdate.class);
        paymentService.updateTransaction(transactionUpdate);
    }
}
