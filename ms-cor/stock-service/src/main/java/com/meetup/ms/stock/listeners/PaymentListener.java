package com.meetup.ms.stock.listeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meetup.ms.contract.payment.PaymentRequest;
import com.meetup.ms.stock.service.StockService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentListener {

    private final StockService stockService;
    private final ObjectMapper objectMapper;

    public PaymentListener(StockService stockService){
        this.stockService = stockService;
        this.objectMapper = new ObjectMapper();
    }

    @KafkaListener(topics = "modifyStockTopic")
    public void processModifyMessage(String content) throws JsonProcessingException {

        var stockModification = objectMapper.readValue(content, PaymentRequest.class);

        try{
            stockService.modifyStock(stockModification);
        }catch (Exception e){
            stockService.stockFail(stockModification);
        }
    }

    @KafkaListener(topics = "revertStockTopic")
    public void processRevertMessage(String content) throws JsonProcessingException {
        var stockModification = objectMapper.readValue(content, PaymentRequest.class);
        stockService.revertStock(stockModification);
    }
}
