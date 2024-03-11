package com.enigma.wmb_api.service.impl;

import com.enigma.wmb_api.dto.request.customer_request.SearchCustomerRequest;
import com.enigma.wmb_api.dto.request.customer_request.UpdateCustomerRequest;
import com.enigma.wmb_api.dto.response.CustomerResponse;
import com.enigma.wmb_api.entity.Customer;
import com.enigma.wmb_api.entity.UserAccount;
import com.enigma.wmb_api.repository.CustomerRepository;
import com.enigma.wmb_api.service.CustomerService;
import com.enigma.wmb_api.service.UserAccountService;
import com.enigma.wmb_api.spesification.CustomerSpesification;
import com.enigma.wmb_api.util.ValidationUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private ValidationUtil validationUtil;
    @Mock
    private  UserAccountService userAccountService;
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        customerService=new CustomerServiceImpl(customerRepository, validationUtil,userAccountService);
    }

    @Test
    void shouldReturnCustomerWhenCreate() {
        Customer customer = Customer.builder().status(true).customerName("ahmad").id("id")
                .userAccount(UserAccount.builder().build()).build();
        Mockito.when(customerRepository.saveAndFlush(customer))
                .thenReturn(customer);
        Customer customer1 = customerService.create(customer);
        assertNotNull(customer1);
    }

    @Test
    void shouldReturnCustomerWhenUpdate() {
        UpdateCustomerRequest updateCustomerRequest = UpdateCustomerRequest.builder().id("1").customerName("ahmad").mobilePhoneNo("0876")
                .build();
        Customer customer = Customer.builder().status(true).customerName("ahmad").id("id")
                .userAccount(UserAccount.builder().build()).build();
        Customer customerNew = Customer.builder().status(true).customerName("ahmad").id("1").mobilePhoneNo("0876")
                .userAccount(UserAccount.builder().build()).build();
        Mockito.doNothing().when(validationUtil).validate(updateCustomerRequest);
        Mockito.when(customerRepository.findById(Mockito.any()))
                .thenReturn(Optional.of(customer));
        Mockito.when(customerRepository.saveAndFlush(Mockito.any()))
                .thenReturn(customerNew);
        CustomerResponse response=customerService.update(updateCustomerRequest);
        assertNotNull(response);
        assertEquals("ahmad", response.getCustomerName());
    }

    @Test
    void shouldReturnCustomerWhenFindById() {
        String id="id";
        Customer customer = Customer.builder().status(true).customerName("ahmad").id("id")
                .userAccount(UserAccount.builder().build()).build();
        Mockito.when(customerRepository.findById(Mockito.any()))
                .thenReturn(Optional.of(customer));
        Customer customer1=customerService.findById(id);
        assertNotNull(customer1);
    }

    @Test
    void shouldReturnCustomerWhenFindOneById() {
        String id="id";
        Customer customer = Customer.builder().status(true).customerName("ahmad").id("id")
                .userAccount(UserAccount.builder().build()).build();
        Mockito.when(customerRepository.findById(Mockito.any()))
                .thenReturn(Optional.of(customer));
        CustomerResponse response=customerService.findOneById(id);
        assertNotNull(response);
        assertEquals("ahmad", response.getCustomerName());
    }

    @Test
    void CustomerResponseWhenDeleteById() {
        String id="id";
        Customer customer = Customer.builder().status(true).customerName("ahmad").id("id")
                .userAccount(UserAccount.builder().build()).build();
        Mockito.when(customerRepository.findById(Mockito.any()))
                .thenReturn(Optional.of(customer));
        Mockito.doNothing().when(customerRepository).delete(Optional.of(customer).get());
        Mockito.doNothing().when(userAccountService).deleteById(Mockito.any());
        customerService.deleteById(id);
        Mockito.verify(customerRepository, Mockito.times(1)).delete(customer);
    }

    @Test
    void shouldReturnAllCustomerWhenFindAll() {
        SearchCustomerRequest request = SearchCustomerRequest.builder()
                .customerName("ahmad").mobilePhoneNo("0876").page(1).size(10).direction("asc").sortBy("customerName")
                .build();

        List<Customer> customerList=List.of(Customer.builder().status(true).customerName("ahmad").id("id")
                .status(true).userAccount(UserAccount.builder().build()).build());
        Page<Customer> customerPage=new PageImpl<>(customerList);

        if (request.getPage() <= 0) request.setPage(1);
        Sort sort=Sort.by(Sort.Direction.fromString(request.getDirection()), request.getSortBy());
        Mockito.when(customerRepository.findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class)))
                .thenReturn(customerPage);
        Page<Customer> customers = customerService.findAll(request);
        assertNotNull(customers);
    }
}