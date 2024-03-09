package com.enigma.wmb_api.dto.response;

import com.enigma.wmb_api.constant.TransactionTypeID;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionTypeResponse {
    private TransactionTypeID id;
    private String description;
}
