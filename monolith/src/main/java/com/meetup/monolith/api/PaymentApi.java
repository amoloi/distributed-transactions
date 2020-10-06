package com.meetup.monolith.api;

import com.meetup.monolith.contract.PaymentRequest;
import com.meetup.monolith.contract.PaymentResponse;
import com.meetup.monolith.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentApi {

    private final TransactionService transactionService;

    public PaymentApi(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<PaymentResponse> paymentMade(@RequestBody PaymentRequest paymentRequest){
        return ResponseEntity.ok(transactionService.processTransaction(paymentRequest));
    }
}
