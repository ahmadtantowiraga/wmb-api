package com.enigma.wmb_api.controller;

import com.enigma.wmb_api.constant.APIUrl;


import com.enigma.wmb_api.dto.request.table_request.NewTableRequest;
import com.enigma.wmb_api.dto.request.table_request.SearchTableRequest;
import com.enigma.wmb_api.dto.request.table_request.UpdateTableRequest;
import com.enigma.wmb_api.dto.response.CommonResponse;
import com.enigma.wmb_api.dto.response.PagingResponse;
import com.enigma.wmb_api.dto.response.TableResponse;
import com.enigma.wmb_api.entity.Tables;

import com.enigma.wmb_api.entity.TransactionType;
import com.enigma.wmb_api.service.TableService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping(path = APIUrl.TABLE_API)
@RequiredArgsConstructor
public class TableController {
    private final TableService tableService;

    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<TableResponse>> create(@RequestBody NewTableRequest request){
        TableResponse tables=tableService.create(request);
        CommonResponse<TableResponse> response=CommonResponse.<TableResponse>builder()
                .message("Success Create Table")
                .statusCode(HttpStatus.CREATED.value())
                .data(tables)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<TableResponse>> findById(@PathVariable String id) {
        TableResponse tables = tableService.findOneById(id);
        CommonResponse<TableResponse> response = CommonResponse.<TableResponse>builder()
                .data(tables)
                .statusCode(HttpStatus.OK.value())
                .message("Success Get Table")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @PutMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<TableResponse>> update(@RequestBody UpdateTableRequest request){
        TableResponse table=tableService.update(request);
        CommonResponse<TableResponse> response=CommonResponse.<TableResponse>builder()
                .data(table)
                .statusCode(HttpStatus.OK.value())
                .message("Success Update Tables")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<String>> deleteById(@PathVariable String id){
        tableService.delete(id);
        CommonResponse<String> response=CommonResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Success Delete Table")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<List<TableResponse>>> findAll(
            @RequestParam(name="page", defaultValue = "1") Integer page,
            @RequestParam(name="size", defaultValue = "10") Integer size,
            @RequestParam(name="direction", defaultValue = "asc") String direction,
            @RequestParam(name="sortBy", defaultValue = "tableName") String sortBy,
            @RequestParam(name="tableName", required = false) String tableName) {

        SearchTableRequest request= SearchTableRequest.builder()
                .direction(direction)
                .tableName(tableName)
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .build();
        Page<Tables> tables=tableService.findAll(request);
        PagingResponse pagingResponse=PagingResponse.builder()
                .page(tables.getPageable().getPageNumber()+1)
                .totalElement(tables.getTotalElements())
                .size(tables.getSize())
                .hasPrevious(tables.hasPrevious())
                .totalPages(tables.getTotalPages())
                .hasNext(tables.hasNext())
                .build();
        CommonResponse<List<TableResponse>> response=CommonResponse.<List<TableResponse>>builder()
                .message("Success Get Tables")
                .statusCode(HttpStatus.OK.value())
                .data(tables.getContent().stream().map(this::convertTableToTableResponse).toList())
                .pagingResponse(pagingResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    private TableResponse convertTableToTableResponse(Tables table) {
        return TableResponse.builder()
                .id(table.getId())
                .tableName(table.getTableName())
                .build();
    }
}
