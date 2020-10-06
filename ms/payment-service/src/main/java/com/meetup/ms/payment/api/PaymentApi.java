package com.meetup.ms.payment.api;

import com.meetup.ms.contract.payment.PaymentRequest;
import com.meetup.ms.contract.payment.PaymentResponse;
import com.meetup.ms.payment.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentApi {

    private final TransactionService transactionService;

    public PaymentApi(TransactionService paymentService){
        this.transactionService = paymentService;
    }

    @PostMapping
    public ResponseEntity<PaymentResponse> paymentMade(@RequestBody PaymentRequest paymentRequest){
        return ResponseEntity.ok(transactionService.processTransaction(paymentRequest));
    }
}
