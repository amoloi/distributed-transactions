package com.meetup.ms.customer.service;

import com.meetup.ms.contract.customer.CustomerDetails;

public interface CustomerService {

    CustomerDetails modifyBalance(CustomerDetails amount);
}
