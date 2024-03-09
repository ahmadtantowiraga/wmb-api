package com.enigma.wmb_api.service.impl;

import com.enigma.wmb_api.constant.UserRole;
import com.enigma.wmb_api.dto.request.auth_request.AuthRequest;
import com.enigma.wmb_api.dto.response.LoginResponse;
import com.enigma.wmb_api.dto.response.RegisterResponse;
import com.enigma.wmb_api.entity.Customer;
import com.enigma.wmb_api.entity.Role;
import com.enigma.wmb_api.entity.UserAccount;
import com.enigma.wmb_api.repository.UserAccountRepository;
import com.enigma.wmb_api.service.AuthService;
import com.enigma.wmb_api.service.CustomerService;
import com.enigma.wmb_api.service.RoleService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final UserAccountRepository userAccountRepository;
    private final CustomerService customerService;

    @Value("${USERNAME_SUPER_ADMIN:superadmin}")
    private String usernameSuperAdmin;

    @Value("${PASSWORD_SUPER_ADMIN:password}")
    private String passwordSuperAdmin;

    @Transactional(rollbackFor = Exception.class)
    @PostConstruct
    public void iniSuperAdmin(){
        Optional<UserAccount> superAdminAccount=userAccountRepository.findByUsername(usernameSuperAdmin);
        if (superAdminAccount.isPresent()) return;

        Role superAdmin = roleService.getOrSave(UserRole.ROLE_SUPER_ADMIN);
        Role admin = roleService.getOrSave(UserRole.ROLE_ADMIN);
        Role customer = roleService.getOrSave(UserRole.ROLE_CUSTOMER);
        UserAccount account = UserAccount.builder()
                .username(usernameSuperAdmin)
                .password(passwordEncoder.encode(passwordSuperAdmin))
                .role(List.of(superAdmin, admin, customer))
                .isEnable(true)
                .build();
        userAccountRepository.save(account);
    }
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
    @Transactional(rollbackFor = Exception.class)
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
