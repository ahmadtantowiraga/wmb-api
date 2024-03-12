package com.enigma.wmb_api.service;

import com.enigma.wmb_api.dto.request.transaction_request.NewTransactionsRequest;
import com.enigma.wmb_api.dto.request.transaction_request.SearchTransactionRequest;
import com.enigma.wmb_api.dto.request.transaction_request.UpdateTransactionStatusRequest;
import com.enigma.wmb_api.dto.response.TransactionResponse;
import com.enigma.wmb_api.entity.Transaction;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TransactionService {
    TransactionResponse create(NewTransactionsRequest request);
    Transaction findById(String id);
    TransactionResponse findOneById(String id);
    Page<Transaction> findAll(SearchTransactionRequest request);
    void updateStatus(UpdateTransactionStatusRequest request);
    List<Transaction> findAllList();
}
