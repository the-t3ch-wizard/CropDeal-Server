package com.ayush.dealer_service.dto;

import com.ayush.dealer_service.model.Status;

import lombok.Data;

@Data
public class DealerCreationDto {

	private Status status = Status.ACTIVE;
	
}
