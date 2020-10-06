package com.meetup.ms.gateway.api;

import com.meetup.ms.contract.payment.PaymentRequest;
import com.meetup.ms.contract.payment.PaymentResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@RestController
@RequestMapping("/payment")
public class PaymentApi {

    private final RestTemplate restTemplate;

    public PaymentApi(KafkaTemplate kafkaTemplate){
        this.restTemplate = new RestTemplate();
    }

    @PostMapping
    public ResponseEntity<PaymentResponse> paymentMade(@RequestBody PaymentRequest paymentRequest){
        return ResponseEntity
                .ok(restTemplate.postForEntity("http://localhost:8082/payment",paymentRequest,PaymentResponse.class).getBody());
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<PaymentResponse> paymentDetails(@PathVariable("transactionId")UUID transactionId){
        return ResponseEntity.ok(new PaymentResponse());
    }
}
