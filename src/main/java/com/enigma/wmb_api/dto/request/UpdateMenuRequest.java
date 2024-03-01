package com.enigma.wmb_api.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateMenuRequest {
    @NotBlank(message = "Id is required")
    private String id;

    @NotBlank(message = "Menu is Required")
    private String menuName;

    @NotNull(message = "Price is Required")
    @Min(value = 0)
    private Long price;
}
