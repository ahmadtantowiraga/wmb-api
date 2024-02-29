package com.enigma.wmb_api.service.impl;

import com.enigma.wmb_api.dto.request.SearchCustomerRequest;
import com.enigma.wmb_api.entity.Customer;
import com.enigma.wmb_api.repository.CustomerRepository;
import com.enigma.wmb_api.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    @Override
    public Customer create(Customer customer) {
        return customerRepository.saveAndFlush(customer);
    }

    @Override
    public Customer findById(String id) {
        return customerRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer not Found"));
    }

    @Override
    public void deleteById(String id) {
        Customer customer=findById(id);
        customerRepository.delete(customer);
    }

    @Override
    public Page<Customer> findAll(SearchCustomerRequest request) {
        return null;
    }

    @Override
    public Customer update(Customer customer) {
        Customer customerUpdate=findById(customer.getId());
        return customerRepository.saveAndFlush(customerUpdate);
    }
}
