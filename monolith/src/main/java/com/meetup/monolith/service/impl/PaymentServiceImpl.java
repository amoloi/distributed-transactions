package com.meetup.monolith.service.impl;

import com.meetup.monolith.contract.PaymentRequest;
import com.meetup.monolith.entity.CustomerEntity;
import com.meetup.monolith.entity.StockEntity;
import com.meetup.monolith.repository.CustomerRepository;
import com.meetup.monolith.repository.StockRepository;
import com.meetup.monolith.service.PaymentService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final CustomerRepository customerRepository;
    private final StockRepository stockRepository;

    public PaymentServiceImpl(CustomerRepository customerRepository,
                              StockRepository stockRepository){

        this.customerRepository = customerRepository;
        this.stockRepository = stockRepository;
    }

    @Transactional
    @Override
    public void processPayment(CustomerEntity customerEntity, StockEntity stockEntity, PaymentRequest paymentRequest) {

        customerEntity.setBalance(customerEntity.getBalance() - paymentRequest.getAmount());
        stockEntity.setAmount(stockEntity.getAmount() - paymentRequest.getItemAmount());

        stockRepository.save(stockEntity);
        customerRepository.save(customerEntity);
    }
}
