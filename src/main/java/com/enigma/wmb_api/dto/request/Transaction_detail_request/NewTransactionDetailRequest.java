package com.enigma.wmb_api.dto.request.Transaction_detail_request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewTransactionDetailRequest {
    @NotBlank(message = "menu is required")
    private String menuId;
    @NotNull(message = "quantity is required")
    @Min(value = 0, message = "minimal  value is 0")
    private Integer qty;
}
