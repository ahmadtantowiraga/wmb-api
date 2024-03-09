package com.enigma.wmb_api.service;

import com.enigma.wmb_api.dto.response.JwtClaims;
import com.enigma.wmb_api.entity.UserAccount;

public interface JwtService {
    String generateToken();
    boolean verifyJwtToken(String token);
    JwtClaims getClaimsByToken(String token);
}

