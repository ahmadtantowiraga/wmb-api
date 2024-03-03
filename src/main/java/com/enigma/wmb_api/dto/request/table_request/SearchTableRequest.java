package com.enigma.wmb_api.dto.request.table_request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchTableRequest {
    private Integer page;
    private Integer size;
    private String direction;
    private String sortBy;
    private String tableName;
}
