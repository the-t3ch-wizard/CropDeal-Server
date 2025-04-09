package com.ayush.auth_service.controller;

import com.ayush.auth_service.dto.AuthRequest;
import com.ayush.auth_service.dto.AuthResponse;
import com.ayush.auth_service.dto.ErrorResponse;
import com.ayush.auth_service.dto.UserDto;
import com.ayush.auth_service.dto.WhoAmIDTO;
import com.ayush.auth_service.model.User;
import com.ayush.auth_service.security.JwtTokenUtil;
import com.ayush.auth_service.security.CustomUserDetailsService;
import com.ayush.auth_service.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;

    // Removed AuthServiceApplication from constructor
    public AuthController(
    		AuthenticationManager authenticationManager,
    		CustomUserDetailsService userDetailsService,
    		JwtTokenUtil jwtTokenUtil,
    		UserService userService
    ) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDto userDto) {
    	try {
    		User registeredUser = userService.registerNewUser(userDto);
            return ResponseEntity.ok(registeredUser);
        } catch (BadCredentialsException e) {
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse(
                    HttpStatus.UNAUTHORIZED.value(),
                    "Invalid email or password",
                    System.currentTimeMillis()
                ));
        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Authentication failed: " + e.getMessage(),
                    System.currentTimeMillis()
                ));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    authRequest.getEmail(),
                    authRequest.getPassword()
                )
            );
            
            final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());
            final String token = jwtTokenUtil.generateToken(userDetails);
            
            return ResponseEntity.ok(new AuthResponse(token));
            
        } catch (BadCredentialsException e) {
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse(
                    HttpStatus.UNAUTHORIZED.value(),
                    "Invalid email or password",
                    System.currentTimeMillis()
                ));
        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Authentication failed: " + e.getMessage(),
                    System.currentTimeMillis()
                ));
        }
    }
    
    @GetMapping("/whoami")
    public ResponseEntity<WhoAmIDTO> whoAmI() {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("User not authenticated");
        }
        String email = ((UserDetails) authentication.getPrincipal()).getUsername();
        WhoAmIDTO userResponse = userService.getCurrentUserDetails(email);
        return ResponseEntity.ok(userResponse);
    }
    
}
