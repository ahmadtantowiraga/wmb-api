package com.enigma.wmb_api.dto.request.table_request;

import jakarta.validation.constraints.NotBlank;

public class UpdateTableRequest {
    @NotBlank(message = "Table Name is Required")
    private String tableName;
}
