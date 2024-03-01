package com.enigma.wmb_api.service;

import com.enigma.wmb_api.dto.request.NewMenuRequest;
import com.enigma.wmb_api.dto.request.SearchMenuRequest;
import com.enigma.wmb_api.dto.request.UpdateMenuRequest;
import com.enigma.wmb_api.entity.Menu;

import java.util.List;

public interface MenuService {
    Menu create(NewMenuRequest requst);
    Menu findById(String id);
    Menu update(UpdateMenuRequest request);
    void deleteById(String id);
    List<Menu> findAll(SearchMenuRequest request);
}
