package com.meetup.ms.payment.api;

import com.meetup.ms.contract.payment.PaymentResponse;
import com.meetup.ms.payment.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequestMapping("/payment")
@RestController
public class PaymentApi {

    private final PaymentService paymentService;

    public PaymentApi(PaymentService paymentService){
        this.paymentService = paymentService;
    }

    @GetMapping("{transactionId}")
    public ResponseEntity<PaymentResponse> getTransactionDetails(@PathVariable("transactionId") UUID transactionId){
        return ResponseEntity.ok(paymentService.getTransactionDetails(transactionId));
    }
}
