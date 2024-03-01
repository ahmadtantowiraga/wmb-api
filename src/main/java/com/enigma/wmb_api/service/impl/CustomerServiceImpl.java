package com.enigma.wmb_api.service.impl;

import com.enigma.wmb_api.dto.request.customer_request.UpdateCustomerRequest;
import com.enigma.wmb_api.dto.request.customer_request.SearchCustomerRequest;
import com.enigma.wmb_api.entity.Customer;
import com.enigma.wmb_api.repository.CustomerRepository;
import com.enigma.wmb_api.service.CustomerService;
import com.enigma.wmb_api.spesification.CustomerSpesification;
import com.enigma.wmb_api.util.ValidationUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final ValidationUtil validationUtil;
    @Override
    @Transactional
    public Customer update(UpdateCustomerRequest updateCustomer) {
        validationUtil.validate(updateCustomer);
        customerRepository.findById(updateCustomer.getId());
        Customer customer=Customer.builder()
                .customerName(updateCustomer.getCustomerName())
                .mobilePhoneNo(updateCustomer.getMobilePhoneNo())
                .build();
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
        if (request.getPage() <= 0) request.setPage(1);
        Sort sort=Sort.by(Sort.Direction.fromString(request.getDirection()), request.getSortBy());
        Pageable pageable= PageRequest.of(request.getPage()-1, request.getSize(), sort);
        Specification<Customer> specification= CustomerSpesification.specificationCustomer(request);
        return customerRepository.findAll(specification, pageable);
    }

}
