package com.enigma.wmb_api.service.impl;

import com.enigma.wmb_api.dto.request.Transaction_detail_request.SearchTransactionDetailRequest;
import com.enigma.wmb_api.dto.request.table_request.SearchTableRequest;
import com.enigma.wmb_api.dto.response.TableResponse;
import com.enigma.wmb_api.dto.response.TransactionDetailResponse;
import com.enigma.wmb_api.entity.Menu;
import com.enigma.wmb_api.entity.Tables;
import com.enigma.wmb_api.entity.Transaction;
import com.enigma.wmb_api.entity.TransactionDetail;
import com.enigma.wmb_api.repository.TransactionDetailRepository;
import com.enigma.wmb_api.service.TransactionDetailService;
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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TransactionDetailServiceImplTest {
    @Mock
    private TransactionDetailRepository transactionDetailRepository;
    @Mock
    private ValidationUtil validationUtil;
    private TransactionDetailService transactionDetailService;

    @BeforeEach
    void setUp() {
        transactionDetailService=new TransactionDetailServiceImpl(transactionDetailRepository, validationUtil);
    }

    @Test
    void shouldReturnTransactionDetailWhenCreateBulk() {
        TransactionDetail transactionDetail = TransactionDetail.builder().id("id").qty(20)
                .transaction(Transaction.builder().id("id").build()).menu(Menu.builder().menuName("menu").build()).build();
        List<TransactionDetail> transactionDetailList= List.of(transactionDetail);
        Mockito.doNothing().when(validationUtil).validate(transactionDetailList);
        Mockito.when(transactionDetailRepository.saveAllAndFlush(Mockito.any()))
                .thenReturn(transactionDetailList);
        List<TransactionDetail> transactionDetails = transactionDetailService.createBulk(transactionDetailList);
        assertNotNull(transactionDetails);
    }


    @Test
    void findOneById() {
        String id="id";
        TransactionDetail transactionDetail = TransactionDetail.builder().id("id").qty(20)
                .transaction(Transaction.builder().id("id").build()).menu(Menu.builder().menuName("menu").build()).build();
        Mockito.when(transactionDetailRepository.findById(id))
                .thenReturn(Optional.of(transactionDetail));
        TransactionDetailResponse response = transactionDetailService.findOneById(id);
        assertEquals(20, response.getQty());
    }

    @Test
    void findAll() {
        SearchTransactionDetailRequest request = SearchTransactionDetailRequest.builder().page(1).size(10).direction("asc").sortBy("id").build();

        List<TransactionDetail> transactionDetailList=List.of(TransactionDetail.builder().id("id")
                .transaction(Transaction.builder().id("id").build()).menu(Menu.builder().menuName("name").build()).build());
        Page<TransactionDetail> transactionDetailsPage=new PageImpl<>(transactionDetailList);
        Mockito.when(transactionDetailRepository.findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class)))
                .thenReturn(transactionDetailsPage);
        Page<TransactionDetail> transactionDetailPage = transactionDetailService.findAll(request);

        assertNotNull(transactionDetailPage);
    }
}