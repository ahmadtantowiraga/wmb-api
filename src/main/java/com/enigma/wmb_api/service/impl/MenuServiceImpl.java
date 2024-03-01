package com.enigma.wmb_api.service.impl;

import com.enigma.wmb_api.dto.request.menu_request.NewMenuRequest;
import com.enigma.wmb_api.dto.request.menu_request.SearchMenuRequest;
import com.enigma.wmb_api.dto.request.menu_request.UpdateMenuRequest;
import com.enigma.wmb_api.entity.Menu;
import com.enigma.wmb_api.repository.MenuRepository;
import com.enigma.wmb_api.service.MenuService;
import com.enigma.wmb_api.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
    private MenuRepository menuRepository;
    private ValidationUtil validationUtil;

    @Override
    public Menu create(NewMenuRequest request) {
        validationUtil.validate(request);
        return null;
    }

    @Override
    public Menu findById(String id) {
        return null;
    }

    @Override
    public Menu update(UpdateMenuRequest request) {
        return null;
    }

    @Override
    public void deleteById(String id) {

    }

    @Override
    public List<Menu> findAll(SearchMenuRequest request) {
        return null;
    }
}
