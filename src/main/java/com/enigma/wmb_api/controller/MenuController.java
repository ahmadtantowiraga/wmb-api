package com.enigma.wmb_api.controller;

import com.enigma.wmb_api.constant.APIUrl;
import com.enigma.wmb_api.dto.request.menu_request.NewMenuRequest;
import com.enigma.wmb_api.dto.request.menu_request.SearchMenuRequest;
import com.enigma.wmb_api.dto.request.menu_request.UpdateMenuRequest;
import com.enigma.wmb_api.dto.response.CommonResponse;
import com.enigma.wmb_api.dto.response.PagingResponse;
import com.enigma.wmb_api.entity.Menu;
import com.enigma.wmb_api.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.MENU_API)
public class MenuController {
    private final MenuService menuService;
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<Menu>> create(@RequestBody NewMenuRequest request){
        Menu menu=menuService.create(request);
        CommonResponse<Menu> response=CommonResponse.<Menu>builder()
                .message("Success Create Menu")
                .statusCode(HttpStatus.CREATED.value())
                .data(menu)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<Menu>> findById(@PathVariable String id){
        Menu menu=menuService.findById(id);
        CommonResponse<Menu> response=CommonResponse.<Menu>builder()
                .data(menu)
                .statusCode(HttpStatus.OK.value())
                .message("Success Get Menu")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<Menu>> update(@RequestBody UpdateMenuRequest request){
        Menu menu=menuService.update(request);
        CommonResponse<Menu> response=CommonResponse.<Menu>builder()
                .data(menu)
                .statusCode(HttpStatus.OK.value())
                .message("Success Update Menu")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse<String>> deleteById(@PathVariable String id){
        menuService.deleteById(id);
        CommonResponse<String> response=CommonResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Success Delete Menu")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @GetMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<List<Menu>>> findAll(
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
        CommonResponse<List<Menu>> response=CommonResponse.<List<Menu>>builder()
                .message("Success Get Menu")
                .statusCode(HttpStatus.OK.value())
                .data(menu.getContent())
                .pagingResponse(pagingResponse)
                .build();
        return ResponseEntity.ok(response);
    }


}
