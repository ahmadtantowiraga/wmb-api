package com.enigma.wmb_api.service;

import com.enigma.wmb_api.dto.request.NewMenuRequest;
import com.enigma.wmb_api.entity.Menu;

public interface MenuService {
    Menu create(NewMenuRequest requst);
    Menu update( );
}
