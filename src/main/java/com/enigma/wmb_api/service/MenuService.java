package com.enigma.wmb_api.service;

import com.enigma.wmb_api.dto.request.menu_request.NewMenuRequest;
import com.enigma.wmb_api.dto.request.menu_request.SearchMenuRequest;
import com.enigma.wmb_api.dto.request.menu_request.UpdateMenuRequest;
import com.enigma.wmb_api.dto.response.MenuResponse;
import com.enigma.wmb_api.entity.Menu;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MenuService {
    MenuResponse create(NewMenuRequest requst);
    Menu findById(String id);
    MenuResponse findOneById(String id);
    MenuResponse update(UpdateMenuRequest request);
    void deleteById(String id);
    Page<Menu> findAll(SearchMenuRequest request);

}
