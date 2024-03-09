package com.enigma.wmb_api.service.impl;

import com.enigma.wmb_api.repository.UserAccountRepository;
import com.enigma.wmb_api.service.UserAccountService;
import com.enigma.wmb_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {
    private final UserAccountRepository userAccountRepository;
    @Override
    public void deleteById(String id) {
        userAccountRepository.deleteById(id);
    }
}
