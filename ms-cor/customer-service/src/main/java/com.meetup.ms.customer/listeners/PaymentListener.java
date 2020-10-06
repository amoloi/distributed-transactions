package com.meetup.ms.customer.listeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meetup.ms.contract.payment.PaymentRequest;
import com.meetup.ms.customer.service.CustomerService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentListener {

    private final CustomerService customerService;
    private final ObjectMapper objectMapper;

    public PaymentListener(CustomerService customerService){
        this.customerService = customerService;
        this.objectMapper = new ObjectMapper();
    }

    @KafkaListener(topics = "balanceModifyTopic")
    public void processModifyMessage(String content) throws JsonProcessingException {
        var balanceModification = objectMapper.readValue(content, PaymentRequest.class);
        try {
            customerService.modifyBalance(balanceModification);
        } catch (Exception e){
            customerService.sendFailTransaction(balanceModification);
        }
    }
}
