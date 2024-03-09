package com.enigma.wmb_api.service.impl;

import com.enigma.wmb_api.dto.request.customer_request.UpdateCustomerRequest;
import com.enigma.wmb_api.entity.Customer;
import com.enigma.wmb_api.entity.UserAccount;
import com.enigma.wmb_api.repository.UserAccountRepository;
import com.enigma.wmb_api.service.CustomerService;
import com.enigma.wmb_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserAccountRepository userAccountRepository;
    private final CustomerService customerService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userAccountRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("Username not found"));
    }

    @Override
    public UserAccount getById(String id) {
        return userAccountRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST, "user not found"));
    }

    @Override
    public UserAccount getByContext() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userAccountRepository.findByUsername(principal.toString()).orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST, "user not found"));
    }

    public Boolean hasSameIdRequest(UpdateCustomerRequest request){
        Customer customer=customerService.findById(request.getId());
        return customer.getUserAccount().getId().equals(getByContext().getId());
    }
    public Boolean hasSameId(String id){
        Customer customer=customerService.findById(id);
        return customer.getUserAccount().getId().equals(getByContext().getId());
    }
}
