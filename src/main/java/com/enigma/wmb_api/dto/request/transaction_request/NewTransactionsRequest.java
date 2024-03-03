package com.enigma.wmb_api.dto.request.transaction_request;

import com.enigma.wmb_api.constant.TransactionTypeID;
import com.enigma.wmb_api.dto.request.Transaction_detail_request.NewTransactionDetailRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class NewTransactionsRequest {
    @NotBlank(message = "customerId is required")
    private String customerId;
    @NotBlank(message = "tableId is required")
    private String tableId;
    @NotNull(message = "transactionTypeId is required")
    private TransactionTypeID transactionTypeID;
    @NotNull(message = "transaction detail is required")
    private List<NewTransactionDetailRequest> transactionDetail;
}
