package com.enigma.wmb_api.dto.request.menu_request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchMenuRequest {
    private Integer size;
    private Integer page;
    private String sortBy;
    private String direction;
    private String name;
    private Long price;
}
