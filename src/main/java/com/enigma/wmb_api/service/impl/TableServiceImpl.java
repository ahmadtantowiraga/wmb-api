package com.enigma.wmb_api.service.impl;

import com.enigma.wmb_api.dto.request.table_request.NewTableRequest;
import com.enigma.wmb_api.dto.request.table_request.SearchTableRequest;
import com.enigma.wmb_api.dto.request.table_request.UpdateTableRequest;
import com.enigma.wmb_api.dto.response.MenuResponse;
import com.enigma.wmb_api.dto.response.TableResponse;
import com.enigma.wmb_api.entity.Menu;
import com.enigma.wmb_api.entity.Tables;
import com.enigma.wmb_api.repository.TableRepository;
import com.enigma.wmb_api.service.TableService;
import com.enigma.wmb_api.spesification.TableSpesification;
import com.enigma.wmb_api.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class TableServiceImpl implements TableService {
    private final TableRepository tableRepository;
    private final ValidationUtil validationUtil;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TableResponse create(NewTableRequest request) {
        validationUtil.validate(request);
        Tables tables=Tables.builder()
                .tableName(request.getTableName())
                .build();
        return convertTableToTableResponse(tableRepository.saveAndFlush(tables));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Tables findById(String id) {
        return tableRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST, "Table is not found"));
    }

    @Override
    public TableResponse findOneById(String id) {
        return convertTableToTableResponse(findById(id));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TableResponse update(UpdateTableRequest request) {
        validationUtil.validate(request);
        tableRepository.findById(request.getId());
        Tables tables=Tables.builder()
                .id(request.getId())
                .tableName(request.getTableName())
                .build();
        return convertTableToTableResponse(tableRepository.saveAndFlush(tables));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) {
    findById(id);
    tableRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Page<Tables> findAll(SearchTableRequest request) {
        if (request.getPage() < 1) request.setPage(1);
        Sort sort=Sort.by(Sort.Direction.fromString(request.getDirection()), request.getSortBy());
        Pageable pageable= PageRequest.of(request.getPage()-1, request.getSize(), sort);
        Specification<Tables> specification= TableSpesification.getSpesification(request);
        return tableRepository.findAll(specification, pageable);
    }
    private TableResponse convertTableToTableResponse(Tables table) {
        return TableResponse.builder()
                .id(table.getId())
                .tableName(table.getTableName())
                .build();
    }
}
