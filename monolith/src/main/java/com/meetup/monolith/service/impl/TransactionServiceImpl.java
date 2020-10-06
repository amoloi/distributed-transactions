package com.meetup.monolith.service.impl;

import com.meetup.monolith.contract.PaymentRequest;
import com.meetup.monolith.contract.PaymentResponse;
import com.meetup.monolith.entity.PaymentEntity;
import com.meetup.monolith.entity.StockEntity;
import com.meetup.monolith.entity.TransactionEntity;
import com.meetup.monolith.repository.CustomerRepository;
import com.meetup.monolith.repository.PaymentRepository;
import com.meetup.monolith.repository.StockRepository;
import com.meetup.monolith.repository.TransactionRepository;
import com.meetup.monolith.service.PaymentService;
import com.meetup.monolith.service.TransactionService;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final CustomerRepository customerRepository;
    private final StockRepository stockRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentService paymentService;

    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  PaymentRepository paymentRepository,
                                  PaymentService paymentService,
                                  CustomerRepository customerRepository,
                                  StockRepository stockRepository){
        this.transactionRepository = transactionRepository;
        this.paymentRepository = paymentRepository;
        this.customerRepository = customerRepository;
        this.paymentService = paymentService;
        this.stockRepository = stockRepository;
    }


    @Override
    public PaymentResponse processTransaction(PaymentRequest paymentRequest) {

        var customer = customerRepository.findById(paymentRequest.getCustomerId()).get();
        var item = stockRepository.findById(paymentRequest.getItem()).get();

        var newPayment = new PaymentEntity();
        newPayment.setAmount(paymentRequest.getAmount());
        newPayment.setItemAmount(paymentRequest.getItemAmount());
        newPayment.setCustomer(customer);
        var payment = paymentRepository.saveAndFlush(newPayment);

        var transaction = new TransactionEntity();
        transaction.setPayment(payment);
        transaction.setStock(item);
        transaction.setStatus("PENDING");
        transactionRepository.saveAndFlush(transaction);

        try{
            paymentService.processPayment(customer,item,paymentRequest);
            transaction.setStatus("SUCCESS");
        }catch (Exception e){
            transaction.setStatus("FAILURE");
        }

        transactionRepository.saveAndFlush(transaction);

        var response = new PaymentResponse();
        response.setStatus(transaction.getStatus());
        response.setTransactionId(transaction.getId());

        return response;
    }
}
