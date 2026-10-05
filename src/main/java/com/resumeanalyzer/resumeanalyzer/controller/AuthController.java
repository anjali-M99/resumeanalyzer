package com.resumeanalyzer.resumeanalyzer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.resumeanalyzer.resumeanalyzer.jwt.JWTHelper;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

	private static Logger Log =LoggerFactory.getLogger(AuthController.class);
	
	private JWTHelper jwtUtil;
	
	@PostMapping("/login")
	public String login(@RequestParam String username, @RequestParam String password) {
		//skiping DB creation for the moment
		if ("admin".equals(username) && "admin-access".equals(password)) {
			Log.info("Inside Login");
			return jwtUtil.generateToken(username);
		}
		return "Invalid credentials";
	}

}