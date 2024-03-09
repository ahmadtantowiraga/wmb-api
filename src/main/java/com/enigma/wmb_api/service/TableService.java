package com.enigma.wmb_api.service;


import com.enigma.wmb_api.dto.request.table_request.NewTableRequest;
import com.enigma.wmb_api.dto.request.table_request.SearchTableRequest;
import com.enigma.wmb_api.dto.request.table_request.UpdateTableRequest;
import com.enigma.wmb_api.dto.response.TableResponse;
import com.enigma.wmb_api.entity.Tables;
import org.springframework.data.domain.Page;

public interface TableService {
    TableResponse create(NewTableRequest request);
    Tables findById(String id);
    TableResponse findOneById(String id);
    TableResponse update(UpdateTableRequest request);
    void delete(String id);
    Page<Tables> findAll(SearchTableRequest request);
}
