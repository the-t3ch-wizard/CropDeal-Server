package com.ayush.crop_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CropServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CropServiceApplication.class, args);
	}

}
