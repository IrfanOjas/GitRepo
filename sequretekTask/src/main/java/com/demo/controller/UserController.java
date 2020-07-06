package com.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.entity.UserRegister;
import com.demo.service.UserService;
import com.demo.utility.ErrorResponse;


@RestController
@RequestMapping(value = "/registration")
public class UserController {

	ErrorResponse error = new ErrorResponse();
	
	@Autowired
	UserService otpService;


	@PostMapping(value = "/userRegister")
	public ResponseEntity<Object> saveUser(@RequestBody UserRegister register) {
		if (register != null) {
			return otpService.register(register);
		} else {
			error.setMessage(error.getMessage());
			return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	@GetMapping(value = "/validateOtp/{otp}")
	public ResponseEntity<Object> validateOtp(@PathVariable String otp) {
		if (otp != null) {
			return  otpService.validateOtp(otp);
		} else {
			error.setMessage("Invalid Request");
			return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

}
