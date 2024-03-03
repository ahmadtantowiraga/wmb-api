package com.enigma.wmb_api.dto.request.transaction_type_request;

import com.enigma.wmb_api.constant.TransactionTypeID;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateTransactionTypeRequest {
    @NotNull(message = "Transaction Type Id is Required")
    private TransactionTypeID id;
    @NotBlank(message = "Description is Required")
    private String description;
}
