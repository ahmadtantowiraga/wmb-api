package com.enigma.wmb_api.controller;

import com.enigma.wmb_api.dto.request.customer_request.UpdateCustomerRequest;
import com.enigma.wmb_api.dto.request.menu_request.NewMenuRequest;
import com.enigma.wmb_api.dto.response.CommonResponse;
import com.enigma.wmb_api.dto.response.CustomerResponse;
import com.enigma.wmb_api.dto.response.ImageResponse;
import com.enigma.wmb_api.dto.response.MenuResponse;
import com.enigma.wmb_api.entity.Customer;
import com.enigma.wmb_api.entity.Menu;
import com.enigma.wmb_api.entity.UserAccount;
import com.enigma.wmb_api.service.MenuService;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class MenuControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private MenuService menuService;

    @Test
    @WithMockUser(username = "USER", roles = "SUPER_ADMIN")
    void shouldHave201StatusAndReturnCommonResponseWhenCreate() throws Exception {
//        MockMultipartFile image = new MockMultipartFile("image", "image.jpg", "image/jpeg", "Test file content".getBytes());
//        NewMenuRequest request = NewMenuRequest.builder().menuName("name").price(2000L).build();
//        MenuResponse menuResponse = MenuResponse.builder().menuName("menu").price(2000L).id("id").build();
//        Mockito.when(menuService.create(Mockito.any()))
//                .thenReturn(menuResponse);
//        String stringJson=objectMapper.writeValueAsString(request);
//
//        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/menus")
//                        .file(image)
//                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
//                        .param("menu", stringJson))
//                .andExpect(MockMvcResultMatchers.status().isCreated())
//                .andDo(MockMvcResultHandlers.print())
//                .andDo(result -> {
//                    CommonResponse<MenuResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
//                    });
//                    assertEquals(201, response.getStatusCode());
//                    assertNotNull(response.getData());
//                });
    }

    @Test
    @WithMockUser(username = "USER", roles = "SUPER_ADMIN")
    void shouldHave201StatusAndReturnCommonResponseWhenFindById() throws Exception {
        String id="id";
        MenuResponse menuResponse = MenuResponse.builder().menuName("menu").build();
        Mockito.when(menuService.findOneById(Mockito.any()))
                .thenReturn(menuResponse);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/menus/id"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andDo(result -> {
                    CommonResponse<MenuResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    assertEquals(200, response.getStatusCode());
                    assertNotNull(response.getData());
                });
    }

    @Test
    @WithMockUser(username = "USER", roles = "SUPER_ADMIN")
    void shouldHave201StatusAndReturnCommonResponseWhenUpdate() {
//        String id="id";
//        MenuResponse menuResponse = MenuResponse.builder().menuName("menu").build();
//        Mockito.when(menuService.findOneById(Mockito.any()))
//                .thenReturn(menuResponse);
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/menus/id"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andDo(MockMvcResultHandlers.print())
//                .andDo(result -> {
//                    CommonResponse<MenuResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
//                    });
//                    assertEquals(200, response.getStatusCode());
//                    assertNotNull(response.getData());
//                });
    }

    @Test
    @WithMockUser(username = "USER", roles = "SUPER_ADMIN")
    void shouldHave201StatusAndReturnCommonResponseDeleteById() throws Exception {
        Mockito.doNothing().when(menuService).deleteById(Mockito.any());
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/menus/id"))
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
    void shouldHave201StatusAndReturnCommonResponseFindAll() throws Exception {
        List<Menu> menuList=List.of(Menu.builder().menuName("ahmad").id("id").price(2000L).build());
        Pageable pageable= PageRequest.of(1, 10);
        Page<Menu> menuPage=new PageImpl<>(menuList, pageable, menuList.size());
        Mockito.when(menuService.findAll(Mockito.any()))
                .thenReturn(menuPage);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/customers"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andDo(result -> {
                    CommonResponse<List<MenuResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    assertEquals(200, response.getStatusCode());
                    assertNotNull(response.getData());
                });
    }
}