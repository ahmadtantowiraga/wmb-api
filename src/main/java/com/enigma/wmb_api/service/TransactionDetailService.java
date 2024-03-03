package com.enigma.wmb_api.service;

import com.enigma.wmb_api.entity.Transaction;
import com.enigma.wmb_api.entity.TransactionDetail;

import java.util.List;

public interface TransactionDetailService {
    List<TransactionDetail> createBulk(List<TransactionDetail> request);
    TransactionDetail findById(String id);
    List<TransactionDetail> findAll()
}
