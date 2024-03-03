package com.enigma.wmb_api.dto.request.transaction_type_request;

import com.enigma.wmb_api.constant.TransactionTypeID;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewTransactionType {
    @NotNull(message = "Id is Required")
    private TransactionTypeID id;
    @NotBlank(message = "description is required")
    private String description;
}
