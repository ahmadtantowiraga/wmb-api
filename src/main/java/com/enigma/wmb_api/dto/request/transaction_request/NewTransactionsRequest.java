package com.enigma.wmb_api.dto.request.transaction_request;

import com.enigma.wmb_api.constant.TransactionTypeID;
import com.enigma.wmb_api.dto.request.Transaction_detail_request.NewTransactionDetailRequest;

import java.util.List;

public class NewTransactionsRequest {
    private String customerId;
    private String tableId;
    private TransactionTypeID transactionTypeID;
    private List<NewTransactionDetailRequest> transactionDetail;
}
