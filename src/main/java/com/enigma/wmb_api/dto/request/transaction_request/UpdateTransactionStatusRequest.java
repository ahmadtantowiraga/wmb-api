package com.enigma.wmb_api.dto.request.transaction_request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateTransactionStatusRequest {
    private String orderId;
    private String transactionStatus;
}