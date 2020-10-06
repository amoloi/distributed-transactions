package com.meetup.ms.gateway.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final KafkaTemplate kafkaTemplate;
    private final ObjectMapper objectMapper;

    public PaymentApi(KafkaTemplate kafkaTemplate){
        this.restTemplate = new RestTemplate();
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = new ObjectMapper();
    }

    @PostMapping
    public ResponseEntity<PaymentResponse> paymentMadeCoreo(@RequestBody PaymentRequest paymentRequest) throws JsonProcessingException {

        paymentRequest.setTransactionId(UUID.randomUUID());

        kafkaTemplate.send("newPaymentTopic", objectMapper.writeValueAsString(paymentRequest));

        var response = new PaymentResponse();
        response.setTransactionId(paymentRequest.getTransactionId());
        response.setStatus("NEW");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<PaymentResponse> paymentDetails(@PathVariable("transactionId")UUID transactionId){
        return ResponseEntity
                .ok(restTemplate.getForEntity("http://localhost:8082/payment/" + transactionId,PaymentResponse.class).getBody());
    }
}
