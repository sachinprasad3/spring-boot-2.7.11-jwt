package com.jwt.dto;

import lombok.Data;

@Data
public class UserResponse {
	
	private String token;
	private String message;
	
	
	public UserResponse() {
	}
	
	public UserResponse(String token, String message) {
		super();
		this.token = token;
		this.message = message;
	}

}
