package com.enigma.wmb_api.service.impl;

import com.enigma.wmb_api.entity.TransactionDetail;
import com.enigma.wmb_api.repository.TransactionDetailRepository;
import com.enigma.wmb_api.service.TransactionDetailService;
import com.enigma.wmb_api.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class TransactionDetailServiceDetailImpl implements TransactionDetailService {
    private final TransactionDetailRepository transactionDetailRepository;
    private final ValidationUtil validationUtil;
    @Override
    public List<TransactionDetail> create(List<TransactionDetail> transactionDetails) {
        validationUtil.validate(transactionDetails);
        return transactionDetailRepository.saveAllAndFlush(transactionDetails);
    }
}
