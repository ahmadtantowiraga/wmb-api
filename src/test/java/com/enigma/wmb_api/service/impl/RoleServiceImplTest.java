package com.enigma.wmb_api.service.impl;

import com.enigma.wmb_api.constant.UserRole;
import com.enigma.wmb_api.entity.Role;
import com.enigma.wmb_api.repository.RoleRepository;
import com.enigma.wmb_api.service.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {
    @Mock
    private RoleRepository roleRepository;

    private RoleService roleService;

    @BeforeEach
    void setUp() {
        roleService=new RoleServiceImpl(roleRepository);
    }

    @Test
    void shouldReturnCustomerWhenGetOrSave() {
        UserRole role=UserRole.ROLE_CUSTOMER;
        Role newRole = Role.builder().role(UserRole.ROLE_CUSTOMER).build();
        Mockito.when(roleRepository.findByRole(role))
                .thenReturn(Optional.of(newRole));
        Role saveRole=roleService.getOrSave(role);
        assertNotNull(saveRole);
    }
}