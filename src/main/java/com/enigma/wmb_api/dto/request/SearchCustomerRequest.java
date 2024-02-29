package com.enigma.wmb_api.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchCustomerRequest {
    private String customerName;
    private String mobilePhoneNo;
    private Integer page;
    private Integer size;
}
