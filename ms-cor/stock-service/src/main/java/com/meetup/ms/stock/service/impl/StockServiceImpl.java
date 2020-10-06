package com.meetup.ms.stock.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meetup.ms.contract.payment.PaymentRequest;
import com.meetup.ms.contract.payment.TransactionUpdate;
import com.meetup.ms.stock.repository.StockRepository;
import com.meetup.ms.stock.service.StockService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;
    private final KafkaTemplate kafkaTemplate;
    private final ObjectMapper objectMapper;

    public StockServiceImpl(StockRepository stockRepository, KafkaTemplate kafkaTemplate){
        this.stockRepository = stockRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void modifyStock(PaymentRequest paymentRequest) throws JsonProcessingException {
        var item = stockRepository.findById(paymentRequest.getItem()).get();
        item.setAmount(item.getAmount() - paymentRequest.getItemAmount());
        stockRepository.saveAndFlush(item);

        kafkaTemplate.send("balanceModifyTopic", objectMapper.writeValueAsString(paymentRequest));

    }

    @Override
    public void revertStock(PaymentRequest paymentRequest) throws JsonProcessingException {
        var item = stockRepository.findById(paymentRequest.getItem()).get();
        item.setAmount(item.getAmount() + paymentRequest.getItemAmount());
        stockRepository.saveAndFlush(item);

        stockFail(paymentRequest);
    }

    @Override
    public void stockFail(PaymentRequest paymentRequest) throws JsonProcessingException {

        var transactionUpdate = new TransactionUpdate(paymentRequest.getTransactionId(),"STOCK",
                paymentRequest.getItem(),"FAILURE");

        kafkaTemplate.send("transactionUpdateTopic", objectMapper.writeValueAsString(transactionUpdate));
    }
}
