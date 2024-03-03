package com.enigma.wmb_api.dto.request.transaction_type_request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchTransactionTypeRequest {
    private Integer page;
    private Integer size;
    private String direction;
    private String sortBy;
    private String description;
}
