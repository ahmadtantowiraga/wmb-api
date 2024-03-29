package com.enigma.wmb_api.controller;

import com.enigma.wmb_api.constant.APIUrl;
import com.enigma.wmb_api.dto.request.customer_request.UpdateCustomerRequest;
import com.enigma.wmb_api.dto.request.customer_request.SearchCustomerRequest;
import com.enigma.wmb_api.dto.response.CommonResponse;
import com.enigma.wmb_api.dto.response.CustomerResponse;
import com.enigma.wmb_api.dto.response.PagingResponse;
import com.enigma.wmb_api.entity.Customer;
import com.enigma.wmb_api.service.CustomerService;
import com.enigma.wmb_api.service.UserService;
import com.enigma.wmb_api.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path= APIUrl.CUSTOMER_API)
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;


    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<List<CustomerResponse>>> getAllCustomer(
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
        CommonResponse<List<CustomerResponse>> response=CommonResponse.<List<CustomerResponse>>builder()
                .message("Success Get All Customer")
                .statusCode(HttpStatus.OK.value())
                .pagingResponse(pagingResponse)
                .data(customers.getContent().stream().map(customer -> CustomerResponse.builder()
                        .id(customer.getId())
                        .status(customer.getStatus())
                        .mobilePhoneNo(customer.getMobilePhoneNo())
                        .customerName(customer.getCustomerName()).build()).toList())
                .build();
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN') OR @userServiceImpl.hasSameIdRequest(#request)")
    @PutMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<CustomerResponse>> updateCustomer(@RequestBody UpdateCustomerRequest request){
        CustomerResponse customer=customerService.update(request);
        CommonResponse<CustomerResponse> commonResponse=CommonResponse.<CustomerResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Successfully Update Customer")
                .data(customer)
                .build();
        return ResponseEntity.ok(commonResponse);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN') OR @userServiceImpl.hasSameId(#id)")
    @GetMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<CustomerResponse>> findById(@PathVariable(name="id") String id){
        CustomerResponse customer=customerService.findOneById(id);
        CommonResponse<CustomerResponse> response=CommonResponse.<CustomerResponse>builder()
                .data(customer)
                .message("Success Get Customer")
                .statusCode(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN') OR @userServiceImpl.hasSameId(#id)")
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
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
