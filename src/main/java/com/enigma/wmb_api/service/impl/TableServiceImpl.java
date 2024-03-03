package com.enigma.wmb_api.service.impl;

import com.enigma.wmb_api.dto.request.table_request.NewTableRequest;
import com.enigma.wmb_api.dto.request.table_request.SearchTableRequest;
import com.enigma.wmb_api.dto.request.table_request.UpdateTableRequest;
import com.enigma.wmb_api.entity.Tables;
import com.enigma.wmb_api.service.TableService;
import org.springframework.data.domain.Page;

public class TableServiceImpl implements TableService {
    @Override
    public Tables create(NewTableRequest request) {
        return null;
    }

    @Override
    public Tables findById(String id) {
        return null;
    }

    @Override
    public Tables update(UpdateTableRequest request) {
        return null;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public Page<Tables> findAll(SearchTableRequest request) {
        return null;
    }
}
