package com.meetup.ms.payment.service.impl;

import com.meetup.ms.contract.customer.CustomerDetails;
import com.meetup.ms.contract.payment.PaymentRequest;
import com.meetup.ms.contract.stock.StockDetails;
import com.meetup.ms.payment.service.PaymentService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final RestTemplate restTemplate;

    public PaymentServiceImpl(){
        this.restTemplate = new RestTemplate();
    }

    @Override
    public void processPayment(PaymentRequest paymentRequest) {

        var stockDetails = new StockDetails();
        stockDetails.setStockId(paymentRequest.getItem());
        stockDetails.setAmount(paymentRequest.getItemAmount());

        var customerDetails = new CustomerDetails();
        customerDetails.setCustomerId(paymentRequest.getCustomerId());
        customerDetails.setBalance(paymentRequest.getAmount());

        restTemplate.postForEntity("http://localhost:8083/stock", stockDetails, StockDetails.class).getBody();

        try {
            restTemplate.postForEntity("http://localhost:8081/customer", customerDetails, CustomerDetails.class).getBody();
        }catch (Exception e){
            restTemplate.postForEntity("http://localhost:8083/stock/revert", stockDetails, StockDetails.class).getBody();
            throw e;
        }
    }
}
