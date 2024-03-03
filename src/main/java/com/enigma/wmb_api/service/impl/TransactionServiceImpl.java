package com.enigma.wmb_api.service.impl;

import com.enigma.wmb_api.dto.request.transaction_request.NewTransactionsRequest;
import com.enigma.wmb_api.dto.request.transaction_request.SearchTransactionRequest;
import com.enigma.wmb_api.entity.Transaction;
import com.enigma.wmb_api.service.TransactionService;
import org.springframework.data.domain.Page;

public class TransactionServiceImpl implements TransactionService {
    @Override
    public Transaction create(NewTransactionsRequest request) {

        return null;
    }

    @Override
    public Transaction findById(String id) {
        return null;
    }

    @Override
    public Page<Transaction> findAll(SearchTransactionRequest request) {
        return null;
    }
}
