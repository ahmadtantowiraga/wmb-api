package com.enigma.wmb_api.controller;

import com.enigma.wmb_api.dto.request.table_request.NewTableRequest;
import com.enigma.wmb_api.dto.request.table_request.UpdateTableRequest;
import com.enigma.wmb_api.dto.response.CommonResponse;
import com.enigma.wmb_api.dto.response.CustomerResponse;
import com.enigma.wmb_api.dto.response.TableResponse;
import com.enigma.wmb_api.entity.Customer;
import com.enigma.wmb_api.entity.Tables;
import com.enigma.wmb_api.entity.UserAccount;
import com.enigma.wmb_api.service.TableService;
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
class TableControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private TableService tableService;
    @Test
    @WithMockUser(username = "USER", roles = "SUPER_ADMIN")
    void shouldHave201StatusAndReturnCommonResponseCreate() throws Exception {
        NewTableRequest request= NewTableRequest.builder().tableName("table").build();
        TableResponse tableResponse = TableResponse.builder().tableName("table").id("id").build();
                Mockito.when(tableService.create(Mockito.any()))
                .thenReturn(tableResponse);

                String stringJson=objectMapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/tables")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(stringJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andDo(result -> {
                    CommonResponse<TableResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    assertEquals(201, response.getStatusCode());
                    assertNotNull(response.getData());
                });
    }

    @Test
    @WithMockUser(username = "USER", roles = "SUPER_ADMIN")
    void shouldHave200StatusAndReturnCommonResponseFindById() throws Exception {
        TableResponse tableResponse = TableResponse.builder().tableName("table").id("id").build();
        Mockito.when(tableService.findOneById(Mockito.any()))
                .thenReturn(tableResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/tables/id"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andDo(result -> {
                    CommonResponse<TableResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    assertEquals(200, response.getStatusCode());
                    assertNotNull(response.getData());
                });
    }

    @Test
    @WithMockUser(username = "USER", roles = "SUPER_ADMIN")
    void shouldHave200StatusAndReturnCommonResponseUpdate() throws Exception {
        UpdateTableRequest request= UpdateTableRequest.builder().tableName("table").build();
        TableResponse tableResponse = TableResponse.builder().tableName("table").id("id").build();
        Mockito.when(tableService.update(Mockito.any()))
                .thenReturn(tableResponse);

        String stringJson=objectMapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/tables")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(stringJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andDo(result -> {
                    CommonResponse<TableResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    assertEquals(200, response.getStatusCode());
                    assertNotNull(response.getData());
                });
    }

    @Test
    @WithMockUser(username = "USER", roles = "SUPER_ADMIN")
    void shouldHave200StatusAndReturnCommonResponseDeleteById() throws Exception {
        String id="id";
        Mockito.doNothing().when(tableService).delete(id);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/tables/id"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andDo(result -> {
                    CommonResponse<TableResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    assertEquals(200, response.getStatusCode());
                    assertNotNull(response.getMessage());
                });
    }

    @Test
    @WithMockUser(username = "USER", roles = "SUPER_ADMIN")
    void shouldHave200StatusAndReturnCommonResponseFindAll() throws Exception {
        List<Tables> tableList=List.of(Tables.builder().tableName("ahmad").id("id").build());
        Pageable pageable= PageRequest.of(1, 10);
        Page<Tables> tablePage=new PageImpl<>(tableList, pageable, tableList.size());
        Mockito.when(tableService.findAll(Mockito.any()))
                .thenReturn(tablePage);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/customers"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andDo(result -> {
                    CommonResponse<List<TableResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    assertEquals(200, response.getStatusCode());
                    assertNotNull(response.getData());
                });

    }
}