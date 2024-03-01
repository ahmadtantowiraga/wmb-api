package com.enigma.wmb_api.dto.request.customer_request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateCustomerRequest {

    @NotBlank(message = "id is required")
    private String id;

    @NotBlank(message ="Name is Required")
    private String customerName;

    @NotBlank(message ="mobilePhoneNo is Required")
    @Pattern(regexp = "^(\\+62|62|0)8[1-9][0-9]{6,9}$", message = "format phone not valid")
    private String mobilePhoneNo;
}
