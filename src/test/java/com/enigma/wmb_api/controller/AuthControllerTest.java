package com.enigma.wmb_api.controller;

import com.enigma.wmb_api.dto.request.auth_request.AuthRequest;
import com.enigma.wmb_api.dto.response.CommonResponse;
import com.enigma.wmb_api.dto.response.LoginResponse;
import com.enigma.wmb_api.dto.response.RegisterResponse;
import com.enigma.wmb_api.entity.Tables;
import com.enigma.wmb_api.service.AuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
class AuthControllerTest {
    @MockBean
    private AuthService authService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "USER", roles = "SUPER_ADMIN")
    void shouldHave201StatusAndReturnCommonResponseWhenRegisterAdmin() throws Exception {
        AuthRequest authRequest = AuthRequest.builder().username("user").password("password").build();
        RegisterResponse response = RegisterResponse.builder().role(List.of("CUSTOMER")).username("user").build();
        Mockito.when(authService.registerAdmin(Mockito.any()))
                .thenReturn(response);

        String stringJson=objectMapper.writeValueAsString(authRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/register/admin")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(stringJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andDo(result -> {
                    CommonResponse<RegisterResponse> registerResponse = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    assertEquals(201, registerResponse.getStatusCode());
                    assertNotNull(registerResponse.getData());
                });
    }


    @Test
    @WithMockUser(username = "USER", roles = "SUPER_ADMIN")
    void shouldHave201StatusAndReturnCommonResponseWhenRegister() throws Exception {
        AuthRequest authRequest = AuthRequest.builder().username("user").password("password").build();
        RegisterResponse response = RegisterResponse.builder().role(List.of("CUSTOMER")).username("user").build();
        String stringJson=objectMapper.writeValueAsString(authRequest);
        Mockito.when(authService.register(Mockito.any()))
                .thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(stringJson))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andDo(result -> {
                    CommonResponse<RegisterResponse> registerResponse = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    assertEquals(201, registerResponse.getStatusCode());
                    assertNotNull(registerResponse.getData());
                });
    }

    @Test
    @WithMockUser(username = "USER", roles = "SUPER_ADMIN")
    void shouldHave201StatusAndReturnCommonResponseWhenLogin() throws Exception {
        AuthRequest authRequest = AuthRequest.builder().username("user").password("password").build();
        LoginResponse response = LoginResponse.builder().token("token").role(List.of("CUSTOMER")).username("user").build();
        String stringJson=objectMapper.writeValueAsString(authRequest);
        Mockito.when(authService.login(Mockito.any()))
                .thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(stringJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andDo(result -> {
                    CommonResponse<LoginResponse> loginResponse = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    assertEquals(200, loginResponse.getStatusCode());
                    assertNotNull(loginResponse.getData());
                });
    }
}