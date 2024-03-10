package com.enigma.wmb_api.dto.response;

import com.enigma.wmb_api.entity.UserAccount;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerResponse {
    private String id;
    private String customerName;
    private String mobilePhoneNo;
    private Boolean status;
    private ImageResponse imageResponse;
}
