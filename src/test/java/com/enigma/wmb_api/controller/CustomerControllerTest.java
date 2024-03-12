package com.enigma.wmb_api.controller;

import com.enigma.wmb_api.dto.request.auth_request.AuthRequest;
import com.enigma.wmb_api.dto.request.customer_request.SearchCustomerRequest;
import com.enigma.wmb_api.dto.request.customer_request.UpdateCustomerRequest;
import com.enigma.wmb_api.dto.response.CommonResponse;
import com.enigma.wmb_api.dto.response.CustomerResponse;
import com.enigma.wmb_api.dto.response.RegisterResponse;
import com.enigma.wmb_api.entity.Customer;
import com.enigma.wmb_api.entity.UserAccount;
import com.enigma.wmb_api.service.AuthService;
import com.enigma.wmb_api.service.CustomerService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {
    @MockBean
    private CustomerService customerService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Test
    @WithMockUser(username = "USER", roles = "SUPER_ADMIN")
    void shouldHave201StatusAndReturnCommonResponseWhenFindByIdUpdateGetAllCustomer() throws Exception {
        SearchCustomerRequest searchCustomerRequest = SearchCustomerRequest.builder().build();
        List<Customer> customerList=List.of(Customer.builder().status(true).customerName("ahmad").id("id")
                .status(true).userAccount(UserAccount.builder().build()).build());
        Pageable pageable= PageRequest.of(1, 10);
        Page<Customer> customerPage=new PageImpl<>(customerList, pageable, customerList.size());

        Mockito.when(customerService.findAll(Mockito.any()))
                .thenReturn(customerPage);

        String stringJson=objectMapper.writeValueAsString(searchCustomerRequest);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(stringJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andDo(result -> {
                    CommonResponse<List<CustomerResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    assertEquals(200, response.getStatusCode());
                    assertNotNull(response.getData());
                });
    }

    @Test
    @WithMockUser(username = "USER", roles = "SUPER_ADMIN")
    void shouldHave201StatusAndReturnCommonResponseWhenFindByIdUpdateCustomer() throws Exception {
        CustomerResponse responseData = CustomerResponse.builder().id("id").customerName("name").build();
        UpdateCustomerRequest updateCustomerRequest = UpdateCustomerRequest.builder().build();
        Mockito.when(customerService.update(Mockito.any()))
                .thenReturn(responseData);

        String stringJson=objectMapper.writeValueAsString(updateCustomerRequest);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(stringJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andDo(result -> {
                    CommonResponse<CustomerResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    assertEquals(200, response.getStatusCode());
                    assertNotNull(response.getData());
                });
    }

    @Test
    @WithMockUser(username = "USER", roles = "SUPER_ADMIN")
    void shouldHave201StatusAndReturnCommonResponseWhenFindById() throws Exception {
        CustomerResponse responseData = CustomerResponse.builder().customerName("name").id("id").build();
        Mockito.when(customerService.findOneById(Mockito.any()))
                .thenReturn(responseData);


        mockMvc.perform(MockMvcRequestBuilders.get("/api/customers/id")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andDo(result -> {
                    CommonResponse<CustomerResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    assertEquals(200, response.getStatusCode());
                    assertNotNull(response.getData());
                });
    }

    @Test
    @WithMockUser(username = "USER", roles = "SUPER_ADMIN")
    void shouldHave201StatusAndReturnCommonResponseWhenDeleteById() throws Exception {
        String id="id";
        Mockito.doNothing().when(customerService).deleteById(id);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/customers/id")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andDo(result -> {
                    CommonResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    assertEquals(200, response.getStatusCode());
                    assertNotNull(response.getData());
                });
    }
}