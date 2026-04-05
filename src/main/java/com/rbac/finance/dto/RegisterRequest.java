package com.rbac.finance.dto;

import com.rbac.finance.model.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private Role role; // Defaults to VIEWER if not provided
}
