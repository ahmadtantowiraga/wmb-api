package com.enigma.wmb_api.service;

import com.enigma.wmb_api.constant.TransactionTypeID;
import com.enigma.wmb_api.dto.request.transaction_type_request.NewTransactionType;
import com.enigma.wmb_api.dto.request.transaction_type_request.UpdateTransactionTypeRequest;
import com.enigma.wmb_api.dto.request.transaction_type_request.SearchTransactionTypeRequest;
import com.enigma.wmb_api.dto.response.TransactionTypeResponse;
import com.enigma.wmb_api.entity.TransactionType;
import org.springframework.data.domain.Page;

public interface TransactionTypeService {
    TransactionType findById(TransactionTypeID id);
    TransactionTypeResponse findOneById(TransactionTypeID id);
    TransactionTypeResponse update(UpdateTransactionTypeRequest request);
    void delete(String id);
    Page<TransactionType> findAll(SearchTransactionTypeRequest request);
    TransactionTypeResponse create(NewTransactionType newTransactionType);

}
