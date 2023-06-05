package com.jwt.controller;

import java.util.HashMap;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.dto.UserRequest;
import com.jwt.dto.UserResponse;
import com.jwt.model.Users;
import com.jwt.service.UsresService;

@RestController
@RequestMapping("/api/v3/jwt/user")
public class AuthController {
	
	@Autowired
	private UsresService usresService;
	
	@Autowired
	private com.jwt.config.JwtUtil jwtUtil;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/signup")
	public ResponseEntity<?> create(@RequestBody Users user){
		Users usr = usresService.create(user);
		return new ResponseEntity<>(user, HttpStatus.CREATED);
		
	}
	
	
	//2. Validate user and generate token(login)
	@PostMapping(path = "/login")
	public ResponseEntity<UserResponse> loginUser(@RequestBody UserRequest request){
		
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		//generating token
		String token = jwtUtil.generateToken(request.getUsername());
		return new ResponseEntity<UserResponse>(new UserResponse(token, "Success! Generated by Silent"), HttpStatus.OK);
	}
	
	@GetMapping("/welcome")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Map<String, String>> welcome(){
		Map<String, String> map = new HashMap<>();
		map.put("msg", "GOTCHA");
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
}