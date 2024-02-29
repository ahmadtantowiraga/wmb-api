package com.enigma.wmb_api.controller;

import com.enigma.wmb_api.constant.APIUrl;
import com.enigma.wmb_api.dto.request.SearchCustomerRequest;
import com.enigma.wmb_api.dto.response.CommonResponse;
import com.enigma.wmb_api.dto.response.PagingResponse;
import com.enigma.wmb_api.entity.Customer;
import com.enigma.wmb_api.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.List;

@RestController
@RequestMapping(path= APIUrl.CUSTOMER_API)
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<CommonResponse<List<Customer>>> getAllProduct(
            @RequestParam(name="page", defaultValue = "1") Integer page,
            @RequestParam(name="size", defaultValue = "10") Integer size,
            @RequestParam(name="direction", defaultValue = "asc") String direction,
            @RequestParam(name="sortBy", defaultValue = "customerName") String sortBy,
            @RequestParam(name="customerName", required = false) String customerName,
            @RequestParam(name="mobilePhoneNo", required = false) String mobilePhoneNo
    ){
        SearchCustomerRequest request=SearchCustomerRequest.builder()
                .customerName(customerName)
                .direction(direction)
                .sortBy(sortBy)
                .mobilePhoneNo(mobilePhoneNo)
                .page(page)
                .size(size)
                .build();

        Page<Customer> customers=customerService.findAll(request);
        PagingResponse pagingResponse=PagingResponse.builder()
                .totalPages(customers.getTotalPages())
                .page(customers.getPageable().getPageNumber())
                .hasNext(customers.hasNext())
                .hasPrevious(customers.hasPrevious())
                .size(customers.getSize())
                .totalElement(customers.getTotalElements())
                .build();
        CommonResponse<List<Customer>> response=CommonResponse.<List<Customer>>builder()
                .message("Succec Get All Product")
                .statusCode(HttpStatus.OK.value())
                .pagingResponse(pagingResponse)
                .data(customers.getContent())
                .build();
        return ResponseEntity.ok(response);
    }
}
