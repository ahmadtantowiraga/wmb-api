package com.enigma.wmb_api.service;

import com.enigma.wmb_api.dto.request.transaction_request.NewTransactionsRequest;
import com.enigma.wmb_api.dto.request.transaction_request.SearchTransactionRequest;
import com.enigma.wmb_api.entity.Transaction;
import org.springframework.data.domain.Page;

public interface TransactionService {
    Transaction create(NewTransactionsRequest request);
    Transaction findById(String id);
    Page<Transaction> findAll(SearchTransactionRequest request);
}
