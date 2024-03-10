package com.enigma.wmb_api.controller;

import com.enigma.wmb_api.constant.APIUrl;
import com.enigma.wmb_api.constant.TransactionTypeID;
import com.enigma.wmb_api.dto.request.table_request.NewTableRequest;
import com.enigma.wmb_api.dto.request.table_request.SearchTableRequest;
import com.enigma.wmb_api.dto.request.transaction_request.NewTransactionsRequest;
import com.enigma.wmb_api.dto.request.transaction_request.SearchTransactionRequest;
import com.enigma.wmb_api.dto.request.transaction_request.UpdateTransactionStatusRequest;
import com.enigma.wmb_api.dto.response.*;
import com.enigma.wmb_api.entity.Payment;
import com.enigma.wmb_api.entity.Transaction;
import com.enigma.wmb_api.entity.Transaction;
import com.enigma.wmb_api.entity.TransactionDetail;
import com.enigma.wmb_api.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(path= APIUrl.TRANSACTION_API)
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<TransactionResponse>> create(@RequestBody NewTransactionsRequest request){
        TransactionResponse transaction=transactionService.create(request);
        CommonResponse<TransactionResponse> response=CommonResponse.<TransactionResponse>builder()
                .message("Success Create Transaction")
                .statusCode(HttpStatus.CREATED.value())
                .data(transaction)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<TransactionResponse>> findById(@PathVariable String id) {
        TransactionResponse transaction = transactionService.findOneById(id);
        CommonResponse<TransactionResponse> response = CommonResponse.<TransactionResponse>builder()
                .data(transaction)
                .statusCode(HttpStatus.OK.value())
                .message("Success Get Transaction")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<List<TransactionResponse>>> findAll(
            @RequestParam(name="page", defaultValue = "1") Integer page,
            @RequestParam(name="size", defaultValue = "10") Integer size,
            @RequestParam(name="direction", defaultValue = "asc") String direction,
            @RequestParam(name="sortBy", defaultValue = "id") String sortBy,
            @RequestParam(name="customer", required = false) String customerId,
            @RequestParam(name="tables", required = false) String tableId,
            @RequestParam(name="transactionType", required = false) TransactionTypeID transactionTypeId) {

        SearchTransactionRequest request= SearchTransactionRequest.builder()
                .direction(direction)
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .TransactionTypeId(transactionTypeId)
                .customerId(customerId)
                .tableId(tableId)
                .build();
        Page<Transaction> transactions=transactionService.findAll(request);
        PagingResponse pagingResponse=PagingResponse.builder()
                .page(transactions.getPageable().getPageNumber()+1)
                .totalElement(transactions.getTotalElements())
                .size(transactions.getSize())
                .hasPrevious(transactions.hasPrevious())
                .totalPages(transactions.getTotalPages())
                .hasNext(transactions.hasNext())
                .build();
        CommonResponse<List<TransactionResponse>> response=CommonResponse.<List<TransactionResponse>>builder()
                .message("Success Get Transaction")
                .statusCode(HttpStatus.OK.value())
                .data(transactions.getContent().stream().map(this::convertTransacionToTransactionResponse).toList())
                .pagingResponse(pagingResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/status")
    public ResponseEntity<CommonResponse<?>> updateStatus(@RequestBody Map<String, Object> request){
        UpdateTransactionStatusRequest updateTransactionStatusRequest= UpdateTransactionStatusRequest.builder()
                .orderId(request.get("order_id").toString())
                .transactionStatus(request.get("transaction_status").toString())
                .build();
        transactionService.updateStatus(updateTransactionStatusRequest);
        return ResponseEntity.ok(CommonResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Update data is success")
                .build());
    }

    private TransactionResponse convertTransacionToTransactionResponse(Transaction transaction) {
        PaymentResponse paymentResponse;
        if (transaction.getPayment() == null) {
            paymentResponse=null;
        }else{
            Payment payment=transaction.getPayment();
            paymentResponse = PaymentResponse.builder()
                    .id(payment.getId())
                    .token(payment.getToken())
                    .redirectUrl(payment.getRedirectUrl())
                    .transactionStatus(payment.getTransactionStatus())
                    .build();
        }
        return TransactionResponse.builder()
                .transactionDetailResponse(transaction.getTransactionDetail()
                        .stream().map(this::convertTransacionDetailToTransactionDetailResponse).toList())
                .customerName(transaction.getCustomer().getCustomerName())
                .tableName(transaction.getTables().getTableName())
                .date(transaction.getDate())
                .paymentResponse(paymentResponse)
                .id(transaction.getId())
                .transactionType(transaction.getTransactionType().getId().name())
                .build();
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
