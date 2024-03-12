package com.enigma.wmb_api.controller;

import com.enigma.wmb_api.constant.TransactionTypeID;
import com.enigma.wmb_api.dto.request.table_request.NewTableRequest;
import com.enigma.wmb_api.dto.request.table_request.UpdateTableRequest;
import com.enigma.wmb_api.dto.request.transaction_type_request.NewTransactionType;
import com.enigma.wmb_api.dto.request.transaction_type_request.UpdateTransactionTypeRequest;
import com.enigma.wmb_api.dto.response.CommonResponse;
import com.enigma.wmb_api.dto.response.TableResponse;
import com.enigma.wmb_api.dto.response.TransactionTypeResponse;
import com.enigma.wmb_api.entity.Tables;
import com.enigma.wmb_api.entity.TransactionType;
import com.enigma.wmb_api.service.TransactionTypeService;
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
class TransactionTypeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private TransactionTypeService transactionTypeService;
    @Test
    @WithMockUser(username = "USER", roles = "SUPER_ADMIN")
    void shouldHave200StatusAndReturnCommonResponseFindById() throws Exception {
        TransactionTypeID transactionTypeID = TransactionTypeID.EI;
        TransactionTypeResponse transactionTypeResponse = TransactionTypeResponse.builder().id(TransactionTypeID.EI).description("description").build();
        Mockito.when(transactionTypeService.findOneById(transactionTypeID))
                .thenReturn(transactionTypeResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/transactionTypes/EI"
                ))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andDo(result -> {
                    CommonResponse<TransactionTypeResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    assertEquals(200, response.getStatusCode());
                    assertNotNull(response.getData());
                });
    }

    @Test
    @WithMockUser(username = "USER", roles = "SUPER_ADMIN")
    void shouldHave200StatusAndReturnCommonResponseWhenUpdate() throws Exception {
        UpdateTransactionTypeRequest request = UpdateTransactionTypeRequest.builder().description("description").id(TransactionTypeID.EI).build();
        TransactionTypeResponse transactionTypeResponse = TransactionTypeResponse.builder().id(TransactionTypeID.EI).description("description").build();
        Mockito.when(transactionTypeService.update(Mockito.any()))
                .thenReturn(transactionTypeResponse);

        String stringJson=objectMapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/transactionTypes")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(stringJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andDo(result -> {
                    CommonResponse<TransactionTypeResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    assertEquals(200, response.getStatusCode());
                    assertNotNull(response.getData());
                });
    }

    @Test
    @WithMockUser(username = "USER", roles = "SUPER_ADMIN")
    void shouldHave200StatusAndReturnCommonResponseDeleteById() throws Exception {
        Mockito.doNothing().when(transactionTypeService).delete(Mockito.any());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/transactionTypes/EI"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andDo(result -> {
                    CommonResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    assertEquals(200, response.getStatusCode());
                    assertNotNull(response.getMessage());
                });
    }

    @Test
    @WithMockUser(username = "USER", roles = "SUPER_ADMIN")
    void shouldHave200StatusAndReturnCommonResponseFindAll() throws Exception {
        List<TransactionType> transactionTypeList=List.of(TransactionType.builder().id(TransactionTypeID.EI).build());
        Pageable pageable= PageRequest.of(1, 10);
        Page<TransactionType> transactionTypesPage=new PageImpl<>(transactionTypeList, pageable, transactionTypeList.size());
        Mockito.when(transactionTypeService.findAll(Mockito.any()))
                .thenReturn(transactionTypesPage);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/transactionTypes"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andDo(result -> {
                    CommonResponse<List<TransactionType>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    assertEquals(200, response.getStatusCode());
                    assertNotNull(response.getData());
                });
    }

    @Test
    @WithMockUser(username = "USER", roles = "SUPER_ADMIN")
    void shouldHave201StatusAndReturnCommonResponseCreate() throws Exception {
        NewTransactionType newTransactionType = NewTransactionType.builder().description("description").id(TransactionTypeID.EI).build();
        TransactionTypeResponse transactionTypeResponse = TransactionTypeResponse.builder().description("description").id(TransactionTypeID.EI).build();
        Mockito.when(transactionTypeService.create(Mockito.any()))
                .thenReturn(transactionTypeResponse);

        String stringJson=objectMapper.writeValueAsString(newTransactionType);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/transactionTypes")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(stringJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andDo(result -> {
                    CommonResponse<TransactionTypeResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    assertEquals(201, response.getStatusCode());
                    assertNotNull(response.getData());
                });
    }
}