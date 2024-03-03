package com.enigma.wmb_api.controller;

import com.enigma.wmb_api.constant.APIUrl;
import com.enigma.wmb_api.dto.request.customer_request.UpdateCustomerRequest;
import com.enigma.wmb_api.dto.request.customer_request.SearchCustomerRequest;
import com.enigma.wmb_api.dto.response.CommonResponse;
import com.enigma.wmb_api.dto.response.PagingResponse;
import com.enigma.wmb_api.entity.Customer;
import com.enigma.wmb_api.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path= APIUrl.CUSTOMER_API)
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
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
                .page(customers.getPageable().getPageNumber()+1)
                .hasNext(customers.hasNext())
                .hasPrevious(customers.hasPrevious())
                .size(customers.getSize())
                .totalElement(customers.getTotalElements())
                .build();
        CommonResponse<List<Customer>> response=CommonResponse.<List<Customer>>builder()
                .message("Success Get All Product")
                .statusCode(HttpStatus.OK.value())
                .pagingResponse(pagingResponse)
                .data(customers.getContent())
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<Customer>> updateCustomer(@RequestBody UpdateCustomerRequest request){
        Customer customer=customerService.update(request);
        CommonResponse<Customer> commonResponse=CommonResponse.<Customer>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully Update Customer")
                .data(customer)
                .build();
        return ResponseEntity.ok(commonResponse);
    }

    @GetMapping(value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<Customer>> findById(@PathVariable(name="id") String id){
        Customer customer=customerService.findById(id);
        CommonResponse<Customer> response=CommonResponse.<Customer>builder()
                .data(customer)
                .message("Success Get Customer")
                .statusCode(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<String>> deleteById(@PathVariable String id){
        customerService.deleteById(id);
        CommonResponse<String> response=CommonResponse.<String>builder()
                .data("Ok")
                .message("Success delete customer")
                .statusCode(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(response);
    }
}
