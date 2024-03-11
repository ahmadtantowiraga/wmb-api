package com.enigma.wmb_api.service.impl;

import com.enigma.wmb_api.constant.TransactionTypeID;
import com.enigma.wmb_api.constant.UserRole;
import com.enigma.wmb_api.dto.request.Transaction_detail_request.NewTransactionDetailRequest;
import com.enigma.wmb_api.dto.request.transaction_request.NewTransactionsRequest;
import com.enigma.wmb_api.dto.request.transaction_request.SearchTransactionRequest;
import com.enigma.wmb_api.dto.request.transaction_request.UpdateTransactionStatusRequest;
import com.enigma.wmb_api.dto.response.TransactionResponse;
import com.enigma.wmb_api.entity.*;
import com.enigma.wmb_api.repository.TransactionRepository;
import com.enigma.wmb_api.service.*;
import com.enigma.wmb_api.util.ValidationUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private ValidationUtil validationUtil;
    @Mock
    private CustomerService customerService;
    @Mock
    private MenuService menuService;
    @Mock
    private TableService tableService;
    @Mock
    private TransactionTypeService transactionTypeService;
    @Mock
    private TransactionDetailService transactionDetailService;
    @Mock
    private PaymentService paymentService;
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        transactionService=new TransactionServiceImpl(transactionRepository,
                validationUtil, customerService, menuService, tableService,
                transactionTypeService, transactionDetailService, paymentService);
    }

    @Test
    void shouldReturnTransactionWhenCreate() {
        NewTransactionDetailRequest newTransactionDetailRequest = NewTransactionDetailRequest.builder().qty(20)
                .menuId("id").build();
        List<NewTransactionDetailRequest> newTransactionDetailRequestList=List.of(newTransactionDetailRequest);
        NewTransactionsRequest request = NewTransactionsRequest.builder().transactionTypeID(TransactionTypeID.EI).tableId("id")
                .customerId("id").transactionDetail(newTransactionDetailRequestList).build();
        Customer customer = Customer.builder().status(true).mobilePhoneNo("0987").id("id").customerName("ahmad")
                .userAccount(UserAccount.builder().password("pas").username("ahmad")
                        .isEnable(true).role(List.of(Role.builder().role(UserRole.ROLE_CUSTOMER).build()))
                        .id("id").build()).build();
        Tables tables = Tables.builder().tableName("table").id("id").build();
        TransactionType transactionType = TransactionType.builder().description("description").id(TransactionTypeID.EI).build();
        Transaction transaction = Transaction.builder().transactionType(transactionType).date(new Date()).tables(tables)
                .customer(customer).id("id").build();
        Menu menu = Menu.builder().menuName("menu").price(2000L).id("id").build();
        TransactionDetail transactionDetail = TransactionDetail.builder().menu(menu).transaction(transaction).price(2000L)
                .qty(20).id("id").build();
        List<TransactionDetail> transactionDetails=List.of(transactionDetail);
        Payment payment = Payment.builder().transactionStatus("ordered").transaction(transaction).redirectUrl("url")
                .token("token").id("id").build();

        validationUtil.validate(request);
        Mockito.when(customerService.findById(Mockito.any()))
                .thenReturn(customer);
        Mockito.when(tableService.findById(Mockito.any()))
                .thenReturn(tables);
        Mockito.when(transactionTypeService.findById(Mockito.any()))
                .thenReturn(transactionType);
        Mockito.when(transactionRepository.saveAndFlush(Mockito.any(Transaction.class)))
                .thenReturn(transaction);
        Mockito.when(menuService.findById(Mockito.any()))
                .thenReturn(menu);
        Mockito.when(transactionDetailService.createBulk(Mockito.any()))
                .thenReturn(transactionDetails);
        Mockito.when(paymentService.createPayment(Mockito.any()))
                .thenReturn(payment);

        TransactionResponse transactionResponse = transactionService.create(request);

        assertNotNull(transactionResponse);


    }

    @Test
    void shouldReturnTransactionFindById() {


    }

    @Test
    void shouldReturnTransactionFindOneById() {
        String id="id";
        Tables tables = Tables.builder().tableName("table").id("id").build();
        TransactionType transactionType = TransactionType.builder().description("description").id(TransactionTypeID.EI).build();
        Customer customer = Customer.builder().status(true).mobilePhoneNo("0987").id("id").customerName("ahmad")
                .userAccount(UserAccount.builder().password("pas").username("ahmad")
                        .isEnable(true).role(List.of(Role.builder().role(UserRole.ROLE_CUSTOMER).build()))
                        .id("id").build()).build();
        Menu menu = Menu.builder().menuName("menu").price(2000L).id("id").build();
        Transaction transaction1 = Transaction.builder().id("id").build();
        TransactionDetail transactionDetail = TransactionDetail.builder().menu(menu).price(2000L)
                .qty(20).transaction(transaction1).id("id").build();
        List<TransactionDetail> transactionDetails=List.of(transactionDetail);
        Transaction transaction = Transaction.builder().transactionType(transactionType).date(new Date()).tables(tables)
                .customer(customer).id("id").transactionDetail(transactionDetails).build();
        Mockito.when(transactionRepository.findById(Mockito.any()))
                .thenReturn(Optional.of(transaction));
        TransactionResponse response = transactionService.findOneById(id);
        assertNotNull(response);
    }

    @Test
    void shouldReturnTransactionFindAll() {
        SearchTransactionRequest request = SearchTransactionRequest.builder().TransactionTypeId(TransactionTypeID.TA).tableId("id").customerId("id")
                .page(1).direction("asc").size(12).sortBy("id").build();
        Tables tables = Tables.builder().tableName("table").id("id").build();
        TransactionType transactionType = TransactionType.builder().description("description").id(TransactionTypeID.EI).build();
        Customer customer = Customer.builder().status(true).mobilePhoneNo("0987").id("id").customerName("ahmad")
                .userAccount(UserAccount.builder().password("pas").username("ahmad")
                        .isEnable(true).role(List.of(Role.builder().role(UserRole.ROLE_CUSTOMER).build()))
                        .id("id").build()).build();
        Menu menu = Menu.builder().menuName("menu").price(2000L).id("id").build();
        Transaction transaction1 = Transaction.builder().id("id").build();
        TransactionDetail transactionDetail = TransactionDetail.builder().menu(menu).price(2000L)
                .qty(20).transaction(transaction1).id("id").build();
        List<TransactionDetail> transactionDetails=List.of(transactionDetail);
        Transaction transaction = Transaction.builder().transactionType(transactionType).date(new Date()).tables(tables)
                .customer(customer).id("id").transactionDetail(transactionDetails).build();
        List<Transaction> transactions=List.of(transaction);
        Page<Transaction> transactionPage =new PageImpl<>(transactions);
        Mockito.when(transactionRepository.findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class)))
                .thenReturn(transactionPage);
        Page<Transaction> transactionServiceAll = transactionService.findAll(request);
        assertNotNull(transactionServiceAll);
    }

    @Test
    void ReturnTransactionUpdateStatus() {
        Payment payment = Payment.builder().transactionStatus("status").token("token").id("id").build();
        UpdateTransactionStatusRequest request = UpdateTransactionStatusRequest.builder().transactionStatus("status")
                .orderId("id").build();
        Transaction transaction = Transaction.builder().id("id").date(new Date()).payment(payment).build();
        Mockito.when(transactionRepository.findById(Mockito.any()))
                .thenReturn(Optional.of(transaction));
        transactionService.updateStatus(request);
        Mockito.verify(transactionRepository, Mockito.times(1)).findById(Mockito.any());
    }
}