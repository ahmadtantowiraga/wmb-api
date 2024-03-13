package com.enigma.wmb_api.service.impl;

import com.enigma.wmb_api.dto.request.Transaction_detail_request.SearchTransactionDetailRequest;
import com.enigma.wmb_api.dto.response.TransactionDetailResponse;
import com.enigma.wmb_api.entity.TransactionDetail;
import com.enigma.wmb_api.repository.TransactionDetailRepository;
import com.enigma.wmb_api.service.TransactionDetailService;
import com.enigma.wmb_api.spesification.TransactionDetailSpesification;
import com.enigma.wmb_api.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@Service
@RequiredArgsConstructor
public class TransactionDetailServiceImpl implements TransactionDetailService {
    private final TransactionDetailRepository transactionDetailRepository;
    private final ValidationUtil validationUtil;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<TransactionDetail> createBulk(List<TransactionDetail> transactionDetails) {
        validationUtil.validate(transactionDetails);
        return transactionDetailRepository.saveAllAndFlush(transactionDetails);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TransactionDetail findById(String id) {
        return transactionDetailRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST,"TransactionDetail Not Found"));
    }

    @Override
    public TransactionDetailResponse findOneById(String id) {
        return convertTransacionDetailToTransactionDetailResponse(findById(id));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Page<TransactionDetail> findAll(SearchTransactionDetailRequest request) {
        if (request.getPage() < 1) request.setPage(1);
        Sort sort=Sort.by(Sort.Direction.fromString(request.getDirection()), request.getSortBy());
        Pageable pageable= PageRequest.of(request.getPage()-1, request.getSize(), sort);
        Specification<TransactionDetail> specification= TransactionDetailSpesification.getSpesification(request);
        return transactionDetailRepository.findAll(specification, pageable);
    }
    private TransactionDetailResponse convertTransacionDetailToTransactionDetailResponse(TransactionDetail transactionDetail) {
        return TransactionDetailResponse.builder()
                .id(transactionDetail.getId())
                .transactionId(transactionDetail.getTransaction().getId())
                .qty(transactionDetail.getQty())
                .price(transactionDetail.getPrice())
                .menuName(transactionDetail.getMenu().getMenuName())
                .build();
    }
}
