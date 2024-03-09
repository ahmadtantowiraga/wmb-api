package com.enigma.wmb_api.service.impl;

import com.enigma.wmb_api.constant.UserRole;
import com.enigma.wmb_api.dto.request.AuthRequest;
import com.enigma.wmb_api.dto.response.LoginResponse;
import com.enigma.wmb_api.dto.response.RegisterResponse;
import com.enigma.wmb_api.entity.Customer;
import com.enigma.wmb_api.entity.Role;
import com.enigma.wmb_api.entity.UserAccount;
import com.enigma.wmb_api.repository.UserAccountRepository;
import com.enigma.wmb_api.service.AuthService;
import com.enigma.wmb_api.service.CustomerService;
import com.enigma.wmb_api.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final UserAccountRepository userAccountRepository;
    private final CustomerService customerService;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public RegisterResponse register(AuthRequest request) {
        Role role=roleService.getOrSave(UserRole.ROLE_CUSTOMER);
        String hashPassword= passwordEncoder.encode(request.getPassword());
        UserAccount userAccount=UserAccount.builder()
                .role(List.of(role))
                .isEnable(true)
                .username(request.getUsername())
                .password(hashPassword)
                .build();
        userAccountRepository.saveAndFlush(userAccount);
        Customer customer=Customer.builder()
                .status(true)
                .userAccount(userAccount)
                .build();
        customerService.create(customer);
        return RegisterResponse.builder()
                .username(userAccount.getUsername())
                .role(userAccount.getRole().stream().map(role1 -> role1.getRole().name()).toList())
                .build();
    }

    @Override
    public RegisterResponse registerAdmin(AuthRequest request) {
        Role roleUser=roleService.getOrSave(UserRole.ROLE_CUSTOMER);
        Role roleAdmin = roleService.getOrSave(UserRole.ROLE_ADMIN);
        String hashPassword= passwordEncoder.encode(request.getPassword());
        UserAccount userAccount=UserAccount.builder()
                .role(List.of(roleAdmin, roleUser))
                .isEnable(true)
                .username(request.getUsername())
                .password(hashPassword)
                .build();
        userAccountRepository.saveAndFlush(userAccount);
        Customer customer=Customer.builder()
                .status(true)
                .userAccount(userAccount)
                .build();
        customerService.create(customer);
        return RegisterResponse.builder()
                .username(userAccount.getUsername())
                .role(userAccount.getRole().stream().map(role1 -> role1.getRole().name()).toList())
                .build();
    }

    @Override
    public LoginResponse login(AuthRequest request) {
        return null;
    }
}
