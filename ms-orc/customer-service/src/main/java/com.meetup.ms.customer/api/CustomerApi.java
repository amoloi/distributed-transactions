package com.meetup.ms.customer.api;

import com.meetup.ms.contract.customer.CustomerDetails;
import com.meetup.ms.customer.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerApi {

    private final CustomerService customerService;

    public CustomerApi(CustomerService customerService){
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<CustomerDetails> modifyBalance(@RequestBody CustomerDetails customerDetails){
        return ResponseEntity.ok(customerService.modifyBalance(customerDetails));
    }
}
