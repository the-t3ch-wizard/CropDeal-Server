package com.ayush.auth_service.dto;

import com.ayush.auth_service.model.Role;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDto {
	
	@NotBlank(message = "Name is mandatory")
	@Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
	private String name;
	
	@NotBlank(message = "Email is mandatory")
    @Email(message = "Email must be valid")
    @Column(unique = true)
    private String email;
	
	@NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;
	
	private Role role;

}
