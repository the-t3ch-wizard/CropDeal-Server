package com.ayush.auth_service.dto;

import com.ayush.auth_service.model.Role;
import lombok.Data;

@Data
public class WhoAmIDTO {
    private Long id;
    private String name;
    private String email;
    private Role role;
}
