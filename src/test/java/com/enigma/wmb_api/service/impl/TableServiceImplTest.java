package com.enigma.wmb_api.service.impl;

import com.enigma.wmb_api.dto.request.table_request.NewTableRequest;
import com.enigma.wmb_api.dto.request.table_request.SearchTableRequest;
import com.enigma.wmb_api.dto.request.table_request.UpdateTableRequest;
import com.enigma.wmb_api.dto.response.TableResponse;
import com.enigma.wmb_api.entity.Customer;
import com.enigma.wmb_api.entity.Tables;
import com.enigma.wmb_api.repository.TableRepository;
import com.enigma.wmb_api.service.TableService;
import com.enigma.wmb_api.util.ValidationUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TableServiceImplTest {
    @Mock
    private TableRepository tableRepository;
    @Mock
    private  ValidationUtil validationUtil;
    private TableService tableService;

    @BeforeEach
    void setUp() {
        tableService=new TableServiceImpl(tableRepository, validationUtil);
    }

    @Test
    void shouldReturnMenuWhenCreate() {
        NewTableRequest table = NewTableRequest.builder().tableName("table").build();
        Tables tableNew = Tables.builder().id("id").tableName("table").build();
        Mockito.doNothing().when(validationUtil).validate(table);
        Mockito.when(tableRepository.saveAndFlush(Mockito.any(Tables.class)))
                .thenReturn(tableNew);
        TableResponse tableResponse = tableService.create(table);
        assertEquals("table", tableResponse.getTableName());
    }

    @Test
    void findOneById() {
        String id="id";
        Tables tableNew = Tables.builder().id("id").tableName("table").build();
        Mockito.when(tableRepository.findById(id))
                .thenReturn(Optional.of(tableNew));
        TableResponse tableResponse = tableService.findOneById(id);
        assertEquals("table", tableResponse.getTableName());
    }

    @Test
    void update() {
        UpdateTableRequest request = UpdateTableRequest.builder().tableName("table").id("id").build();
        Mockito.doNothing().when(validationUtil).validate(request);
        Tables tableNew = Tables.builder().id("id").tableName("table").build();
        Mockito.when(tableRepository.findById(tableNew.getId()))
                .thenReturn(Optional.of(tableNew));
        Mockito.when(tableRepository.saveAndFlush(Mockito.any(Tables.class)))
                .thenReturn(tableNew);
        TableResponse update = tableService.update(request);
        assertEquals("table", update.getTableName());
    }

    @Test
    void delete() {
        String id="id";
        Tables tableNew = Tables.builder().id("id").tableName("table").build();
        Mockito.when(tableRepository.findById(id))
                .thenReturn(Optional.of(tableNew));
        Mockito.doNothing().when(tableRepository).deleteById(id);
        tableService.delete(id);
        Mockito.verify(tableRepository, Mockito.times(1)).deleteById(id);
    }

    @Test
    void findAll() {
        SearchTableRequest request = SearchTableRequest.builder().tableName("table").page(1).size(10)
                .sortBy("id").direction("asc").build();
        List<Tables> tablesList=List.of(Tables.builder().id("id").tableName("table").build());
        Page<Tables> tablePage=new PageImpl<>(tablesList);
        Mockito.when(tableRepository.findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class)))
                .thenReturn(tablePage);
        Page<Tables> tablesPage = tableService.findAll(request);

        assertNotNull(tablesPage);

    }
}