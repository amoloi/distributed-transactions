package com.meetup.ms.payment.service.impl;

import com.meetup.ms.contract.payment.PaymentRequest;
import com.meetup.ms.contract.payment.PaymentResponse;
import com.meetup.ms.payment.entity.PaymentEntity;
import com.meetup.ms.payment.entity.TransactionEntity;
import com.meetup.ms.payment.repository.PaymentRepository;
import com.meetup.ms.payment.repository.TransactionRepository;
import com.meetup.ms.payment.service.PaymentService;
import com.meetup.ms.payment.service.TransactionService;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {


    private final TransactionRepository transactionRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentService paymentService;

    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  PaymentRepository paymentRepository,
                                  PaymentService paymentService){
        this.transactionRepository = transactionRepository;
        this.paymentRepository = paymentRepository;
        this.paymentService = paymentService;
    }


    @Override
    public PaymentResponse processTransaction(PaymentRequest paymentRequest) {

        var newPayment = new PaymentEntity();
        newPayment.setAmount(paymentRequest.getAmount());
        newPayment.setItemAmount(paymentRequest.getItemAmount());
        newPayment.setCustomerId(paymentRequest.getCustomerId());
        var payment = paymentRepository.saveAndFlush(newPayment);

        var transaction = new TransactionEntity();
        transaction.setPayment(payment);
        transaction.setStockId(paymentRequest.getItem());
        transaction.setStatus("PENDING");
        transactionRepository.saveAndFlush(transaction);

        try{
            paymentService.processPayment(paymentRequest);
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
