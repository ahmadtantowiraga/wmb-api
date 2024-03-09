package com.enigma.wmb_api.service;


import com.enigma.wmb_api.dto.request.customer_request.SearchCustomerRequest;
import com.enigma.wmb_api.dto.request.customer_request.UpdateCustomerRequest;
import com.enigma.wmb_api.dto.response.CustomerResponse;
import com.enigma.wmb_api.entity.Customer;
import org.springframework.data.domain.Page;

public interface CustomerService {
    Customer create(Customer customer);
    CustomerResponse update(UpdateCustomerRequest updateCustomerRequest);
    CustomerResponse findOneById(String id);
    Customer findById(String id);
    void deleteById(String id);
    Page<Customer> findAll(SearchCustomerRequest request);

}
