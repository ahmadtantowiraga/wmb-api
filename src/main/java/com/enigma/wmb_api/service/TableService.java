package com.enigma.wmb_api.service;


import com.enigma.wmb_api.dto.request.table_request.NewTableRequest;
import com.enigma.wmb_api.dto.request.table_request.UpdateTableRequest;
import com.enigma.wmb_api.entity.Tables;
import org.springframework.data.domain.Page;

public interface TableService {
    Tables create(NewTableRequest request);
    Tables findById(String id);
    Tables update(UpdateTableRequest request);
    void delete(String id);
    Page<Tables> findAll(SearchTableRequest request);
}
