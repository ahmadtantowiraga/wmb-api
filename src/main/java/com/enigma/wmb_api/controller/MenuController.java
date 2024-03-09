package com.enigma.wmb_api.controller;

import com.enigma.wmb_api.constant.APIUrl;
import com.enigma.wmb_api.dto.request.menu_request.NewMenuRequest;
import com.enigma.wmb_api.dto.request.menu_request.SearchMenuRequest;
import com.enigma.wmb_api.dto.request.menu_request.UpdateMenuRequest;
import com.enigma.wmb_api.dto.response.CommonResponse;
import com.enigma.wmb_api.dto.response.MenuResponse;
import com.enigma.wmb_api.dto.response.PagingResponse;
import com.enigma.wmb_api.entity.Menu;
import com.enigma.wmb_api.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.MENU_API)
public class MenuController {
    private final MenuService menuService;

    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<MenuResponse>> create(@RequestBody NewMenuRequest request){
        MenuResponse menu=menuService.create(request);
        CommonResponse<MenuResponse> response=CommonResponse.<MenuResponse>builder()
                .message("Success Create Menu")
                .statusCode(HttpStatus.CREATED.value())
                .data(menu)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<MenuResponse>> findById(@PathVariable String id){
        MenuResponse menu=menuService.findOneById(id);
        CommonResponse<MenuResponse> response=CommonResponse.<MenuResponse>builder()
                .data(menu)
                .statusCode(HttpStatus.OK.value())
                .message("Success Get Menu")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @PutMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<MenuResponse>> update(@RequestBody UpdateMenuRequest request){
        MenuResponse menu=menuService.update(request);
        CommonResponse<MenuResponse> response=CommonResponse.<MenuResponse>builder()
                .data(menu)
                .statusCode(HttpStatus.OK.value())
                .message("Success Update Menu")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<String>> deleteById(@PathVariable String id){
        menuService.deleteById(id);
        CommonResponse<String> response=CommonResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Success Delete Menu")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<List<MenuResponse>>> findAll(
            @RequestParam(name="page", defaultValue = "1") Integer page,
            @RequestParam(name="size", defaultValue = "10") Integer size,
            @RequestParam(name="direction", defaultValue = "asc") String direction,
            @RequestParam(name="sortBy", defaultValue = "menuName") String sortBy,
            @RequestParam(name="menuName", required = false) String menuName,
            @RequestParam(name="price", required = false) Long price){

        SearchMenuRequest request=SearchMenuRequest.builder()
                .direction(direction)
                .name(menuName)
                .page(page)
                .price(price)
                .size(size)
                .sortBy(sortBy)
                .build();
        Page<Menu> menu=menuService.findAll(request);
        PagingResponse pagingResponse=PagingResponse.builder()
                .page(menu.getPageable().getPageNumber()+1)
                .totalElement(menu.getTotalElements())
                .size(menu.getSize())
                .hasPrevious(menu.hasPrevious())
                .totalPages(menu.getTotalPages())
                .hasNext(menu.hasNext())
                .build();
        CommonResponse<List<MenuResponse>> response=CommonResponse.<List<MenuResponse>>builder()
                .message("Success Get Menu")
                .statusCode(HttpStatus.OK.value())
                .data(menu.getContent().stream().map(this::convertMenuToMenuResponse).toList())
                .pagingResponse(pagingResponse)
                .build();
        return ResponseEntity.ok(response);
    }
    private MenuResponse convertMenuToMenuResponse(Menu menu) {
        return MenuResponse.builder()
                .id(menu.getId())
                .menuName(menu.getMenuName())
                .price(menu.getPrice())
                .build();
    }


}
