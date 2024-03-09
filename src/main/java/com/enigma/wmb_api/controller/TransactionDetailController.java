package com.enigma.wmb_api.controller;

import com.enigma.wmb_api.constant.APIUrl;
import com.enigma.wmb_api.dto.request.Transaction_detail_request.SearchTransactionDetailRequest;
import com.enigma.wmb_api.dto.response.CommonResponse;
import com.enigma.wmb_api.dto.response.PagingResponse;
import com.enigma.wmb_api.dto.response.TransactionDetailResponse;
import com.enigma.wmb_api.entity.TransactionDetail;
import com.enigma.wmb_api.service.TransactionDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.TRANSACTION_DETAIL_API)
public class TransactionDetailController {
    private final TransactionDetailService transactionDetailService;

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<TransactionDetailResponse>> findById(@PathVariable String id) {
        TransactionDetailResponse transactionDetail = transactionDetailService.findOneById(id);
        CommonResponse<TransactionDetailResponse> response = CommonResponse.<TransactionDetailResponse>builder()
                .data(transactionDetail)
                .statusCode(HttpStatus.OK.value())
                .message("Success Get TransactionDetail")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<List<TransactionDetailResponse>>> findAll(
            @RequestParam(name="page", defaultValue = "1") Integer page,
            @RequestParam(name="size", defaultValue = "10") Integer size,
            @RequestParam(name="direction", defaultValue = "asc") String direction,
            @RequestParam(name="sortBy", defaultValue = "id") String sortBy,
            @RequestParam(name="transaction", required = false) String transactionId,
            @RequestParam(name="qty", required = false) Integer qty,
            @RequestParam(name="price", required = false) Long price) {

        SearchTransactionDetailRequest request= SearchTransactionDetailRequest.builder()
                .direction(direction)
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .transactionId(transactionId)
                .qty(qty)
                .price(price)
                .build();
        Page<TransactionDetail> transactionDetails=transactionDetailService.findAll(request);
        PagingResponse pagingResponse=PagingResponse.builder()
                .page(transactionDetails.getPageable().getPageNumber()+1)
                .totalElement(transactionDetails.getTotalElements())
                .size(transactionDetails.getSize())
                .hasPrevious(transactionDetails.hasPrevious())
                .totalPages(transactionDetails.getTotalPages())
                .hasNext(transactionDetails.hasNext())
                .build();
        CommonResponse<List<TransactionDetailResponse>> response=CommonResponse.<List<TransactionDetailResponse>>builder()
                .message("Success Get Transaction Detail")
                .statusCode(HttpStatus.OK.value())
                .data(transactionDetails.getContent().stream()
                        .map(this::convertTransacionDetailToTransactionDetailResponse).toList())
                .pagingResponse(pagingResponse)
                .build();
        return ResponseEntity.ok(response);
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
