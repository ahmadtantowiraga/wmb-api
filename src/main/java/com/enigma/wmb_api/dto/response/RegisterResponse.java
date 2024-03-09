package com.enigma.wmb_api.dto.response;

import com.enigma.wmb_api.constant.UserRole;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterResponse {
    private String username;
    private List<String> role;
}
