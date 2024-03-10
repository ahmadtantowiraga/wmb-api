package com.enigma.wmb_api.service.impl;

import com.enigma.wmb_api.dto.request.customer_request.UpdateCustomerRequest;
import com.enigma.wmb_api.dto.request.customer_request.SearchCustomerRequest;
import com.enigma.wmb_api.dto.response.CustomerResponse;
import com.enigma.wmb_api.entity.Customer;
import com.enigma.wmb_api.repository.CustomerRepository;
import com.enigma.wmb_api.service.CustomerService;
import com.enigma.wmb_api.service.UserAccountService;
import com.enigma.wmb_api.spesification.CustomerSpesification;
import com.enigma.wmb_api.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final ValidationUtil validationUtil;
    private final UserAccountService userAccountService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Customer create(Customer customer) {
        return customerRepository.saveAndFlush(customer);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CustomerResponse update(UpdateCustomerRequest updateCustomer) {
        validationUtil.validate(updateCustomer);
        Customer currentCustomer=findById(updateCustomer.getId());
        Customer customer = Customer.builder()
                .id(updateCustomer.getId())
                .customerName(updateCustomer.getCustomerName())
                .mobilePhoneNo(updateCustomer.getMobilePhoneNo())
                .userAccount(currentCustomer.getUserAccount())
                .status(currentCustomer.getStatus())
                .build();
        Customer upadateCustomer = customerRepository.saveAndFlush(customer);
        return CustomerResponse.builder()
                .customerName(updateCustomer.getCustomerName())
                .id(updateCustomer.getId())
                .mobilePhoneNo(updateCustomer.getMobilePhoneNo())
                .status(upadateCustomer.getStatus())
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Customer findById(String id) {
        return customerRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer not Found"));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CustomerResponse findOneById(String id) {
        Customer customer= customerRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer not Found"));
        return CustomerResponse.builder()
                .customerName(customer.getCustomerName())
                .status(customer.getStatus())
                .mobilePhoneNo(customer.getMobilePhoneNo())
                .id(customer.getId())
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String id) {
        Customer customer= findById(id);
        customerRepository.delete(customer);
        userAccountService.deleteById(customer.getUserAccount().getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Page<Customer> findAll(SearchCustomerRequest request) {
        if (request.getPage() <= 0) request.setPage(1);
        Sort sort=Sort.by(Sort.Direction.fromString(request.getDirection()), request.getSortBy());
        Pageable pageable= PageRequest.of(request.getPage()-1, request.getSize(), sort);
        Specification<Customer> specification= CustomerSpesification.specificationCustomer(request);
        return customerRepository.findAll(specification, pageable);
    }


}
