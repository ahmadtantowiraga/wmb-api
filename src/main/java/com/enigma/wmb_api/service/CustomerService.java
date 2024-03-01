package com.enigma.wmb_api.service;


import com.enigma.wmb_api.dto.request.SearchCustomerRequest;
import com.enigma.wmb_api.dto.request.UpdateCustomerRequest;
import com.enigma.wmb_api.entity.Customer;
import org.springframework.data.domain.Page;

public interface CustomerService {
    Customer update(UpdateCustomerRequest updateCustomerRequest);
    Customer findById(String id);
    void deleteById(String id);
    Page<Customer> findAll(SearchCustomerRequest request);

}
