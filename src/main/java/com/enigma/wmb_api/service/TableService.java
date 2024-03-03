package com.enigma.wmb_api.service;


import com.enigma.wmb_api.dto.request.table_request.NewTableRequest;
import com.enigma.wmb_api.entity.Tables;

public interface TableService {
    Tables create(NewTableRequest request);
}
