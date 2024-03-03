package com.enigma.wmb_api.dto.request.Transaction_detail_request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchTransactionDetailRequest {
    private Integer page;
    private Integer size;
    private String direction;
    private String sortBy;
    private String transactionId;
    private String menuId;
    private Integer stock;
    private Long price;
}
