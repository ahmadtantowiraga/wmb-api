package com.enigma.wmb_api.service.impl;

import com.enigma.wmb_api.dto.request.menu_request.NewMenuRequest;
import com.enigma.wmb_api.dto.request.menu_request.SearchMenuRequest;
import com.enigma.wmb_api.dto.request.menu_request.UpdateMenuRequest;
import com.enigma.wmb_api.dto.response.ImageResponse;
import com.enigma.wmb_api.dto.response.MenuResponse;
import com.enigma.wmb_api.entity.Customer;
import com.enigma.wmb_api.entity.Image;
import com.enigma.wmb_api.entity.Menu;
import com.enigma.wmb_api.entity.UserAccount;
import com.enigma.wmb_api.repository.MenuRepository;
import com.enigma.wmb_api.service.ImageService;
import com.enigma.wmb_api.service.MenuService;
import com.enigma.wmb_api.util.ValidationUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class MenuServiceImplTest {
    @Mock
    private MenuRepository menuRepository;
    @Mock
    private ValidationUtil validationUtil;
    @Mock
    private  ImageService imageService;
    private MenuService menuService;

    @BeforeEach
    void setUp() {
        menuService=new MenuServiceImpl(menuRepository, validationUtil, imageService);
    }

    @Test
    void shouldReturnMenuWhenCreate() {
        Menu menu=Menu.builder().menuName("ahmad").build();
        MultipartFile image = new MockMultipartFile("image", "image.jpg", "image/jpeg", "Test file content".getBytes());
        NewMenuRequest newMenu = NewMenuRequest.builder().image(image).build();
        Mockito.when(imageService.create(newMenu.getImage()))
                .thenReturn(Image.builder().build());
        Mockito.doNothing().when(validationUtil).validate(newMenu);
        Mockito.when(menuRepository.saveAndFlush(Mockito.any()))
                .thenReturn(menu);

        MenuResponse menuResponse = menuService.create(newMenu);

        assertNotNull(menuResponse);
        assertEquals("ahmad",menuResponse.getMenuName());

    }

    @Test
    void shouldReturnMenuWhenFindById() {
        String id="id";
        Menu menu = Menu.builder().menuName("ahmad").build();
        Mockito.when(menuRepository.findById(id))
                .thenReturn(Optional.of(menu));
        Menu menuNow=menuService.findById(id);
        assertNotNull(menuNow);
    }

    @Test
    void shouldReturnCustomerWhenFindOneById() {
        String id="id";
        Menu menu = Menu.builder().menuName("ahmad").build();
        Mockito.when(menuRepository.findById(id))
                .thenReturn(Optional.of(menu));
        MenuResponse response=menuService.findOneById(id);
        assertNotNull(response);
        assertEquals("ahmad", response.getMenuName());
    }

    @Test
    void shouldReturnCustomerWhenUpdate() {
        MultipartFile image = new MockMultipartFile("image", "image.jpg", "image/jpeg", "Test file content".getBytes());
        UpdateMenuRequest updateMenuRequest = UpdateMenuRequest.builder().menuName("ahmad").id("id").image(image).price(2000L).build();
        Menu menu = Menu.builder().menuName("ahmad").build();
        Image image1 = Image.builder().build();
        Mockito.when(menuRepository.findById(updateMenuRequest.getId()))
                .thenReturn(Optional.of(menu));
        Mockito.doNothing().when(validationUtil).validate(updateMenuRequest);
        Mockito.when(imageService.create(updateMenuRequest.getImage()))
                .thenReturn(image1);
        Mockito.when(menuRepository.saveAndFlush(Mockito.any()))
                .thenReturn(Mockito.any());
        MenuResponse update = menuService.update(updateMenuRequest);
        assertNotNull(update);

    }

    @Test
    void CustomerResponseWhenDeleteById() {
        String id="id";
        Menu menu = Menu.builder().price(2000L).menuName("ahmad").image(Image.builder().id("id").build()).build();
        Mockito.when(menuRepository.findById(id))
                .thenReturn(Optional.of(menu));
        Mockito.doNothing().when(menuRepository).deleteById(id);
        Mockito.doNothing().when(imageService).deleteById(Mockito.anyString());
        menuService.deleteById(id);
        Mockito.verify(menuRepository, Mockito.times(1)).deleteById(id);
    }

    @Test
    void findAll() {
        SearchMenuRequest request = SearchMenuRequest.builder().page(1).size(10).sortBy("id").direction("asc").build();
        List<Menu> menuList=List.of(Menu.builder().build());
        Page<Menu> menuPage=new PageImpl<>(menuList);
        Mockito.when(menuRepository.findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class)))
                .thenReturn(menuPage);
        Page<Menu> allMenu = menuService.findAll(request);
        assertNotNull(allMenu);
    }
}