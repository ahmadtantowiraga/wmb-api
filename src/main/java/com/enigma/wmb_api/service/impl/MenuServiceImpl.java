package com.enigma.wmb_api.service.impl;

import com.enigma.wmb_api.dto.request.menu_request.NewMenuRequest;
import com.enigma.wmb_api.dto.request.menu_request.SearchMenuRequest;
import com.enigma.wmb_api.dto.request.menu_request.UpdateMenuRequest;
import com.enigma.wmb_api.dto.response.MenuResponse;
import com.enigma.wmb_api.entity.Menu;
import com.enigma.wmb_api.repository.MenuRepository;
import com.enigma.wmb_api.service.MenuService;
import com.enigma.wmb_api.spesification.MenuSpesification;
import com.enigma.wmb_api.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuRepository;
    private final ValidationUtil validationUtil;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MenuResponse create(NewMenuRequest request) {
        validationUtil.validate(request);
        Menu menu= Menu.builder()
                .menuName(request.getMenuName())
                .price(request.getPrice())
                .build();
        return convertMenuToMenuResponse(menuRepository.saveAndFlush(menu));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Menu findById(String id) {
        return menuRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id is not found"));
    }

    @Override
    public MenuResponse findOneById(String id) {
        return convertMenuToMenuResponse(findById(id));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MenuResponse update(UpdateMenuRequest request) {
        validationUtil.validate(request);
        findById(request.getId());
        Menu menu= Menu.builder()
                .id(request.getId())
                .menuName(request.getMenuName())
                .price(request.getPrice())
                .build();
        return convertMenuToMenuResponse(menuRepository.saveAndFlush(menu));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String id) {
        findById(id);
        menuRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Page<Menu> findAll(SearchMenuRequest request) {
        if (request.getPage() <= 0) request.setPage(1);
        Specification<Menu> specification= MenuSpesification.getSpesification(request);
        Sort sort=Sort.by(Sort.Direction.fromString(request.getDirection()), request.getSortBy());
        Pageable pageable= PageRequest.of(request.getPage()-1, request.getSize(), sort);
        return menuRepository.findAll(specification, pageable);
    }
    private MenuResponse convertMenuToMenuResponse(Menu menu) {
        return MenuResponse.builder()
                .id(menu.getId())
                .menuName(menu.getMenuName())
                .price(menu.getPrice())
                .build();
    }
}
