package com.meetup.ms.customer.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meetup.ms.contract.payment.PaymentRequest;
import com.meetup.ms.contract.payment.TransactionUpdate;
import com.meetup.ms.customer.repository.CustomerRepository;
import com.meetup.ms.customer.service.CustomerService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ObjectMapper objectMapper;
    private final KafkaTemplate kafkaTemplate;

    public CustomerServiceImpl(CustomerRepository customerRepository, KafkaTemplate kafkaTemplate){
        this.customerRepository = customerRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void modifyBalance(PaymentRequest paymentRequest) throws JsonProcessingException {

        var customer = customerRepository.findById(paymentRequest.getCustomerId()).get();
        customer.setBalance(customer.getBalance() - paymentRequest.getAmount());
        customerRepository.saveAndFlush(customer);

        var transactionUpdate = new TransactionUpdate(paymentRequest.getTransactionId(),
                "CUSTOMER",paymentRequest.getCustomerId(),"COMPLETE");

        kafkaTemplate.send("transactionUpdateTopic", objectMapper.writeValueAsString(transactionUpdate));

    }

    @Override
    public void sendFailTransaction(PaymentRequest paymentRequest) throws JsonProcessingException {
        kafkaTemplate.send("revertStockTopic", objectMapper.writeValueAsString(paymentRequest));
    }
}
