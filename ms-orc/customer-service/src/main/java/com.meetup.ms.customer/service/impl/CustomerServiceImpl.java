package com.meetup.ms.customer.service.impl;

import com.meetup.ms.contract.customer.CustomerDetails;
import com.meetup.ms.customer.repository.CustomerRepository;
import com.meetup.ms.customer.service.CustomerService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public CustomerDetails modifyBalance(CustomerDetails customerDetails) {

        var customer = customerRepository.findById(customerDetails.getCustomerId()).get();
        customer.setBalance(customer.getBalance() - customerDetails.getBalance());

        var modifiedCustomer = customerRepository.save(customer);

        var modifiedDetails = new CustomerDetails();
        modifiedDetails.setBalance(modifiedCustomer.getBalance());
        modifiedDetails.setCustomerId(modifiedCustomer.getId());

        return modifiedDetails;
    }
}
