package com.enigma.wmb_api.dto.request.payment_request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentItemDetailRequest {
    private String id;
    private long price;
    private Integer quantity;
    private String name;
}
