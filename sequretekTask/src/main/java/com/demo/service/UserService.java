package com.demo.service;

import org.springframework.http.ResponseEntity;

import com.demo.entity.UserRegister;

public interface UserService {

	public ResponseEntity<Object> register(UserRegister register);

	public ResponseEntity<Object> validateOtp(String otp);

}
