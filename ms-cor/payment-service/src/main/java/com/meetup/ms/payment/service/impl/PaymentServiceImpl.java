package com.meetup.ms.payment.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meetup.ms.contract.payment.PaymentRequest;
import com.meetup.ms.contract.payment.PaymentResponse;
import com.meetup.ms.contract.payment.TransactionUpdate;
import com.meetup.ms.payment.entity.PaymentEntity;
import com.meetup.ms.payment.entity.TransactionEntity;
import com.meetup.ms.payment.repository.PaymentRepository;
import com.meetup.ms.payment.repository.TransactionRepository;
import com.meetup.ms.payment.service.PaymentService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final TransactionRepository transactionRepository;
    private final KafkaTemplate kafkaTemplate;
    private final ObjectMapper objectMapper;

    public PaymentServiceImpl(PaymentRepository paymentRepository,
                              TransactionRepository transactionRepository,
                              KafkaTemplate kafkaTemplate){

        this.paymentRepository = paymentRepository;
        this.transactionRepository = transactionRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = new ObjectMapper();
    }


    @Override
    @Transactional
    public void createTransaction(PaymentRequest paymentRequest) throws JsonProcessingException {
        var newPayment = new PaymentEntity();
        newPayment.setCustomerId(paymentRequest.getCustomerId());
        newPayment.setItemAmount(paymentRequest.getItemAmount());
        newPayment.setAmount(paymentRequest.getAmount());

        var paymentEntity = paymentRepository.save(newPayment);

        var transaction = new TransactionEntity();
        transaction.setId(paymentRequest.getTransactionId());
        transaction.setStockId(paymentRequest.getItem());
        transaction.setCustomerId(paymentRequest.getCustomerId());
        transaction.setPayment(paymentEntity);
        transaction.setStatus("PENDING");

        transactionRepository.save(transaction);

        kafkaTemplate.send("modifyStockTopic", objectMapper.writeValueAsString(paymentRequest));
    }

    @Override
    public void updateTransaction(TransactionUpdate transactionUpdate) {

        var transaction = transactionRepository.findById(transactionUpdate.getTransactionId()).get();
        transaction.setStatus(transactionUpdate.getStatus());
        transactionRepository.save(transaction);
    }

    @Override
    public PaymentResponse getTransactionDetails(UUID transactionId) {
        var transaction = transactionRepository.findById(transactionId).get();

        var response = new PaymentResponse();
        response.setTransactionId(transaction.getId());
        response.setStatus(transaction.getStatus());

        return response;
    }


}
