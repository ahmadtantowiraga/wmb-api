package com.enigma.wmb_api.service.impl;

import com.enigma.wmb_api.dto.request.transaction_type_request.NewTransactionTypeRequest;
import com.enigma.wmb_api.dto.request.transaction_type_request.SearchTransactionTypeRequest;
import com.enigma.wmb_api.entity.TransactionType;
import com.enigma.wmb_api.repository.TransactionTypeRepository;
import com.enigma.wmb_api.service.TransactionTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class TransactionTypeServiceImpl implements TransactionTypeService {
    private final TransactionTypeRepository transactionTypeRepository;
    @Override
    public TransactionType findById(String id) {
        return transactionTypeRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST, "TransactionType is not found"));
    }

    @Override
    public TransactionType update(NewTransactionTypeRequest request) {
        return null;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public Page<TransactionType> findAll(SearchTransactionTypeRequest request) {
        return null;
    }
}
