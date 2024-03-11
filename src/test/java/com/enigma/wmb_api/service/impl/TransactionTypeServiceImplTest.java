package com.enigma.wmb_api.service.impl;

import com.enigma.wmb_api.constant.TransactionTypeID;
import com.enigma.wmb_api.dto.request.transaction_type_request.NewTransactionType;
import com.enigma.wmb_api.dto.request.transaction_type_request.SearchTransactionTypeRequest;
import com.enigma.wmb_api.dto.request.transaction_type_request.UpdateTransactionTypeRequest;
import com.enigma.wmb_api.dto.response.TransactionTypeResponse;
import com.enigma.wmb_api.entity.Tables;
import com.enigma.wmb_api.entity.TransactionType;
import com.enigma.wmb_api.repository.TransactionTypeRepository;
import com.enigma.wmb_api.service.TransactionTypeService;
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

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TransactionTypeServiceImplTest {
    @Mock
    private TransactionTypeRepository transactionTypeRepository;

    @Mock
    private ValidationUtil validationUtil;
    TransactionTypeService transactionTypeService;

    @BeforeEach
    void setUp() {
        transactionTypeService=new TransactionTypeServiceImpl(transactionTypeRepository, validationUtil);
    }


    @Test
    void shouldReturnTransactionTypeWhenFindOneById() {
        TransactionTypeID transactionTypeID = TransactionTypeID.EI;
        TransactionType transactionType = TransactionType.builder().description("deskription").build();
        Mockito.when(transactionTypeRepository.findById(transactionTypeID))
                .thenReturn(Optional.of(transactionType));
        TransactionTypeResponse response = transactionTypeService.findOneById(transactionTypeID);
        assertEquals("deskription", response.getDescription());
    }

    @Test
    void shouldReturnTransactionTypeWhenUpdate() {
        UpdateTransactionTypeRequest updateTransactionTypeRequest = UpdateTransactionTypeRequest.builder().id(TransactionTypeID.EI).description("decription").build();
        TransactionType transactionType = TransactionType.builder().description("deskription").build();
        Mockito.doNothing().when(validationUtil).validate(updateTransactionTypeRequest);
        Mockito.when(transactionTypeRepository.findById(TransactionTypeID.EI))
                .thenReturn(Optional.of(transactionType));
        Mockito.when(transactionTypeRepository.saveAndFlush(Mockito.any(TransactionType.class)))
                .thenReturn(transactionType);
        TransactionTypeResponse update = transactionTypeService.update(updateTransactionTypeRequest);
        assertNotNull(update);
    }

    @Test
    void shouldReturnTransactionTypeWhenDelete() {
        TransactionTypeID transactionTypeID = TransactionTypeID.EI;
        TransactionType transactionType = TransactionType.builder().description("deskription").build();
        Mockito.when(transactionTypeRepository.findById(Mockito.any()))
                .thenReturn(Optional.of(transactionType));
        Mockito.doNothing().when(transactionTypeRepository).deleteById(TransactionTypeID.EI);
        transactionTypeService.delete(transactionTypeID.name());
        Mockito.verify(transactionTypeRepository, Mockito.times(1)).deleteById(TransactionTypeID.EI);
    }

    @Test
    void shouldReturnTransactionTypeWhenFindAll() {
        SearchTransactionTypeRequest searchTransactionTypeRequest = SearchTransactionTypeRequest.builder()
                .description("decription").page(1).size(10).direction("asc").sortBy("id").build();
        List<TransactionType> transactionTypeList= List.of(TransactionType.builder().description("deskription").build());
        Page<TransactionType> transactionTypePage=new PageImpl<>(transactionTypeList);
        Mockito.when(transactionTypeRepository.findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class)))
                .thenReturn(transactionTypePage);
        Page<TransactionType> transactionTypeServiceAll = transactionTypeService.findAll(searchTransactionTypeRequest);
        assertNotNull(transactionTypeServiceAll);
    }

    @Test
    void shouldReturnTransactionTypeWhenCreate() {
        NewTransactionType newTransactionType = NewTransactionType.builder().description("description").id(TransactionTypeID.EI).build();
        TransactionType transactionType = TransactionType.builder().description("description").build();
        Mockito.doNothing().when(validationUtil).validate(newTransactionType);
        Mockito.when(transactionTypeRepository.saveAndFlush(Mockito.any(TransactionType.class)))
                .thenReturn(transactionType);
        TransactionTypeResponse response = transactionTypeService.create(newTransactionType);
        assertNotNull(response);
        assertEquals("description",response.getDescription());
    }
}