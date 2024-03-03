package com.enigma.wmb_api.dto.request.table_request;

import jakarta.validation.constraints.NotBlank;

public class NewTableRequest {
    @NotBlank(message = "Table Name Is Required")
    private String tableName;
}
