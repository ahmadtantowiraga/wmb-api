package com.enigma.wmb_api.service.impl;

import com.enigma.wmb_api.constant.TransactionTypeID;
import com.enigma.wmb_api.dto.request.transaction_type_request.NewTransactionType;
import com.enigma.wmb_api.dto.request.transaction_type_request.UpdateTransactionTypeRequest;
import com.enigma.wmb_api.dto.request.transaction_type_request.SearchTransactionTypeRequest;
import com.enigma.wmb_api.entity.TransactionType;
import com.enigma.wmb_api.repository.TransactionTypeRepository;
import com.enigma.wmb_api.service.TransactionTypeService;
import com.enigma.wmb_api.spesification.TransactionTypeSpesification;
import com.enigma.wmb_api.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class TransactionTypeServiceImpl implements TransactionTypeService {
    private final TransactionTypeRepository transactionTypeRepository;
    private final ValidationUtil validationUtil;
    @Override
    public TransactionType findById(TransactionTypeID id) {
        return transactionTypeRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST, "TransactionType is not found"));
    }

    @Override
    public TransactionType update(UpdateTransactionTypeRequest request) {
        validationUtil.validate(request);
        findById(request.getId());
        TransactionType transactionType=TransactionType.builder()
                .description(request.getDescription())
                .id(request.getId())
                .build();
        return transactionTypeRepository.saveAndFlush(transactionType);
    }

    @Override
    public void delete(String id) {
        findById(TransactionTypeID.valueOf(id));
        transactionTypeRepository.deleteById(TransactionTypeID.valueOf(id));
    }

    @Override
    public Page<TransactionType> findAll(SearchTransactionTypeRequest request) {
        if (request.getPage()<1) request.setPage(1);
        Sort sort=Sort.by(Sort.Direction.fromString(request.getDirection()), request.getSortBy());
        Pageable pageable= PageRequest.of(request.getPage()-1, request.getSize(), sort);
        Specification<TransactionType> specification= TransactionTypeSpesification.getSpesification(request);
        return transactionTypeRepository.findAll(specification, pageable);
    }

    @Override
    public TransactionType create(NewTransactionType newTransactionType) {
        validationUtil.validate(newTransactionType);
        TransactionType transactionType=TransactionType.builder()
                .id(newTransactionType.getId())
                .description(newTransactionType.getDescription())
                .build();
        return transactionTypeRepository.saveAndFlush(transactionType);
    }
}
