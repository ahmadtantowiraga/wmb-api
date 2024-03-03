package com.enigma.wmb_api.service;

import com.enigma.wmb_api.constant.TransactionTypeID;
import com.enigma.wmb_api.dto.request.transaction_type_request.NewTransactionTypeRequest;
import com.enigma.wmb_api.dto.request.transaction_type_request.SearchTransactionTypeRequest;
import com.enigma.wmb_api.entity.TransactionType;
import org.springframework.data.domain.Page;

public interface TransactionTypeService {
    TransactionType findById(String id);
    TransactionType update(NewTransactionTypeRequest request);
    void delete(String id);
    Page<TransactionType> findAll(SearchTransactionTypeRequest request);

}
