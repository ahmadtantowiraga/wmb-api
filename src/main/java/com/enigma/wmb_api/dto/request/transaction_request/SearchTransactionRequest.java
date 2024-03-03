package com.enigma.wmb_api.dto.request.transaction_request;

import com.enigma.wmb_api.constant.TransactionTypeID;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SearchTransactionRequest {
    private Integer page;
    private Integer size;
    private String direction;
    private String sortBy;
    private String customerId;
    private String tableId;
    private TransactionTypeID TransactionTypeId;
}
