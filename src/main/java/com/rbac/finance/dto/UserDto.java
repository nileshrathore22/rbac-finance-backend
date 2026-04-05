package com.rbac.finance.dto;

import com.rbac.finance.model.Role;
import com.rbac.finance.model.Status;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserDto {
    private Long id;
    private String username;
    private Role role;
    private Status status;
    private LocalDateTime createdAt;
}
