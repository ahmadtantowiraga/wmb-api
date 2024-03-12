package com.enigma.wmb_api.controller;

import com.enigma.wmb_api.constant.TransactionTypeID;
import com.enigma.wmb_api.dto.response.CommonResponse;
import com.enigma.wmb_api.dto.response.TransactionDetailResponse;
import com.enigma.wmb_api.dto.response.TransactionTypeResponse;
import com.enigma.wmb_api.entity.Menu;
import com.enigma.wmb_api.entity.Transaction;
import com.enigma.wmb_api.entity.TransactionDetail;
import com.enigma.wmb_api.entity.TransactionType;
import com.enigma.wmb_api.service.TransactionDetailService;
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
class TransactionDetailControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private TransactionDetailService transactionDetailService;

    @Test
    @WithMockUser(username = "USER", roles = "SUPER_ADMIN")
    void shouldHave200StatusAndReturnCommonResponseFindById() throws Exception {
        String id="id";
        TransactionDetailResponse detailResponse = TransactionDetailResponse.builder().transactionId("id").menuName("name").qty(20).id("id").build();
        Mockito.when(transactionDetailService.findOneById(id))
                .thenReturn(detailResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/transactionDetails/id"
                ))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andDo(result -> {
                    CommonResponse<TransactionDetailResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    assertEquals(200, response.getStatusCode());
                    assertNotNull(response.getData());
                });
    }

    @Test
    @WithMockUser(username = "USER", roles = "SUPER_ADMIN")
    void shouldHave200StatusAndReturnCommonResponseFindAll() throws Exception {
        List<TransactionDetail> transactionDetailList=List.of(TransactionDetail.builder().id("id").transaction(Transaction.builder()
                .id("id").build()).menu(Menu.builder().menuName("menu").build()).build());
        Pageable pageable= PageRequest.of(1, 10);
        Page<TransactionDetail> transactionDetailsPage=new PageImpl<>(transactionDetailList, pageable, transactionDetailList.size());
        Mockito.when(transactionDetailService.findAll(Mockito.any()))
                .thenReturn(transactionDetailsPage);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/transactionDetails"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andDo(result -> {
                    CommonResponse<List<TransactionDetail>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    assertEquals(200, response.getStatusCode());
                    assertNotNull(response.getData());
                });
    }
}