package com.enigma.wmb_api.dto.request.table_request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateTableRequest {
    @NotBlank(message = "id is required")
    private String id;
    @NotBlank(message = "Table Name is Required")
    private String tableName;
}
