package com.enigma.wmb_api.dto.request.customer_request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchCustomerRequest {
    private Integer size;
    private Integer page;
    private String sortBy;
    private String direction;
    private String customerName;
    private String mobilePhoneNo;
}
