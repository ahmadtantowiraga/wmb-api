package com.enigma.wmb_api.controller;

import com.enigma.wmb_api.constant.APIUrl;
import com.enigma.wmb_api.constant.TransactionTypeID;
import com.enigma.wmb_api.dto.request.transaction_type_request.NewTransactionType;
import com.enigma.wmb_api.dto.request.transaction_type_request.UpdateTransactionTypeRequest;
import com.enigma.wmb_api.dto.request.transaction_type_request.SearchTransactionTypeRequest;
import com.enigma.wmb_api.dto.response.CommonResponse;
import com.enigma.wmb_api.dto.response.PagingResponse;
import com.enigma.wmb_api.entity.Tables;
import com.enigma.wmb_api.entity.TransactionType;
import com.enigma.wmb_api.service.TransactionTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = APIUrl.TRANSACTION_TYPE_API)
public class TransactionTypeController {
    private final TransactionTypeService transactionTypeService;

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<TransactionType>> findById(@PathVariable String id) {
        TransactionType transactionType = transactionTypeService.findById(TransactionTypeID.valueOf(id));
        CommonResponse<TransactionType> response = CommonResponse.<TransactionType>builder()
                .data(transactionType)
                .statusCode(HttpStatus.OK.value())
                .message("Success Get Table")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<TransactionType>> update(@RequestBody UpdateTransactionTypeRequest request){
        TransactionType transactionType=transactionTypeService.update(request);
        CommonResponse<TransactionType> response=CommonResponse.<TransactionType>builder()
                .data(transactionType)
                .statusCode(HttpStatus.OK.value())
                .message("Success Update TransactionType")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<String>> deleteById(@PathVariable String id){
        transactionTypeService.delete(id);
        CommonResponse<String> response=CommonResponse.<String>builder()
                .message("Success Delete TransactionType")
                .statusCode(HttpStatus.OK.value())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<List<TransactionType>>> findAll(
            @RequestParam(name="page", defaultValue = "1") Integer page,
            @RequestParam(name="size", defaultValue = "10") Integer size,
            @RequestParam(name="direction", defaultValue = "asc") String direction,
            @RequestParam(name="sortBy", defaultValue = "id") String sortBy,
            @RequestParam(name="description", required = false) String description) {

        SearchTransactionTypeRequest request= SearchTransactionTypeRequest.builder()
                .direction(direction)
                .description(description)
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .build();
        Page<TransactionType> transactionType=transactionTypeService.findAll(request);
        PagingResponse pagingResponse=PagingResponse.builder()
                .page(transactionType.getPageable().getPageNumber()+1)
                .totalElement(transactionType.getTotalElements())
                .size(transactionType.getSize())
                .hasPrevious(transactionType.hasPrevious())
                .totalPages(transactionType.getTotalPages())
                .hasNext(transactionType.hasNext())
                .build();
        CommonResponse<List<TransactionType>> response=CommonResponse.<List<TransactionType>>builder()
                .message("Success Get TransactionType")
                .statusCode(HttpStatus.OK.value())
                .data(transactionType.getContent())
                .pagingResponse(pagingResponse)
                .build();
        return ResponseEntity.ok(response);
    }
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<TransactionType>> create(@RequestBody NewTransactionType request){
        TransactionType transactionType=transactionTypeService.create(request);
        CommonResponse<TransactionType> response=CommonResponse.<TransactionType>builder()
                .message("Success Create TransactionType")
                .statusCode(HttpStatus.CREATED.value())
                .data(transactionType)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
