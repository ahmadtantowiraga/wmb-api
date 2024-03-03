package com.enigma.wmb_api.service;

import com.enigma.wmb_api.dto.request.Transaction_detail_request.SearchTransactionDetailRequest;
import com.enigma.wmb_api.entity.Transaction;
import com.enigma.wmb_api.entity.TransactionDetail;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TransactionDetailService {
    List<TransactionDetail> createBulk(List<TransactionDetail> request);
    TransactionDetail findById(String id);
    Page<TransactionDetail> findAll(SearchTransactionDetailRequest request);
}
