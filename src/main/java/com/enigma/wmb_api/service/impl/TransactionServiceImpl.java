package com.enigma.wmb_api.service.impl;

import com.enigma.wmb_api.dto.request.transaction_request.NewTransactionsRequest;
import com.enigma.wmb_api.dto.request.transaction_request.SearchTransactionRequest;
import com.enigma.wmb_api.entity.*;
import com.enigma.wmb_api.repository.TransactionDetailRepository;
import com.enigma.wmb_api.repository.TransactionRepository;
import com.enigma.wmb_api.service.*;
import com.enigma.wmb_api.spesification.TableSpesification;
import com.enigma.wmb_api.spesification.TransactionSpesification;
import com.enigma.wmb_api.util.ValidationUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final ValidationUtil validationUtil;
    private final CustomerService customerService;
    private final MenuService menuService;
    private final TableService tableService;
    private final TransactionTypeService transactionTypeService;
    private final TransactionDetailService transactionDetailService;
    @Override
    @Transactional
    public Transaction create(NewTransactionsRequest request) {
        validationUtil.validate(request);
        Customer customer=customerService.findById(request.getCustomerId());
        Tables tables=tableService.findById(request.getTableId());
        TransactionType transactionType=transactionTypeService.findById(request.getTransactionTypeID());
        Transaction transaction=Transaction.builder()
                .customer(customer)
                .tables(tables)
                .date(new Date())
                .transactionType(transactionType)
                .build();
        Transaction transaction1=transactionRepository.saveAndFlush(transaction);

        List<TransactionDetail> transactionDetail=request.getTransactionDetail().stream()
                .map(transDetail -> {
                    Menu menu=menuService.findById(transDetail.getMenuId());
                    return TransactionDetail.builder()
                            .qty(transDetail.getQty())
                            .price(menu.getPrice())
                            .transaction(transaction1)
                            .menu(menu)
                            .build();
                }).toList();
        List<TransactionDetail> transactionDetails=transactionDetailService.createBulk(transactionDetail);
        transaction1.setTransactionDetail(transactionDetails);
        return transaction1;
    }

    @Override
    public Transaction findById(String id) {
        return transactionRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST, "Transaction is not found"));
    }

    @Override
    public Page<Transaction> findAll(SearchTransactionRequest request) {
        if (request.getPage() < 1) request.setPage(1);
        Sort sort=Sort.by(Sort.Direction.fromString(request.getDirection()), request.getSortBy());
        Pageable pageable= PageRequest.of(request.getPage()-1, request.getSize(), sort);
        Specification<Transaction> specification= TransactionSpesification.getSpesification(request);
        return transactionRepository.findAll(specification, pageable);
    }
}
