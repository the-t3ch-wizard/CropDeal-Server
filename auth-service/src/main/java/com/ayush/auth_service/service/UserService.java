package com.ayush.auth_service.service;

import com.ayush.auth_service.dto.UserDto;
import com.ayush.auth_service.dto.WhoAmIDTO;
import com.ayush.auth_service.feign.DealerInterface;
import com.ayush.auth_service.feign.FarmerInterface;
import com.ayush.auth_service.model.Dealer;
import com.ayush.auth_service.model.Farmer;
import com.ayush.auth_service.model.Role;
import com.ayush.auth_service.model.User;
import com.ayush.auth_service.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Autowired
    DealerInterface dealerInterface;
    
    @Autowired
    FarmerInterface farmerInterface;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerNewUser(UserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(userDto.getRole());
        
        switch (userDto.getRole()) {
        	case Role.FARMER : {
        		Farmer newFarmer = farmerInterface.createFarmer(new Farmer()).getBody();
        		System.out.println("FARMER CREATED -" + newFarmer);
        		user.setUserId(newFarmer.getId());
        		break;
        	}
        	case DEALER: {
        		Dealer newDealer = dealerInterface.createDealer(new Dealer()).getBody();
        		System.out.println("DEALER CREATED -" + newDealer);
        		user.setUserId(newDealer.getId());
        		break;
        	}
        	case ADMIN: {
        		break;
        	}
        	default: {
        		break;
        	}
        }

        return userRepository.save(user);
    }
    
    public WhoAmIDTO getCurrentUserDetails(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        return mapToUserResponse(user);
    }

    private WhoAmIDTO mapToUserResponse(User user) {
    	WhoAmIDTO response = new WhoAmIDTO();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        return response;
    }
}
