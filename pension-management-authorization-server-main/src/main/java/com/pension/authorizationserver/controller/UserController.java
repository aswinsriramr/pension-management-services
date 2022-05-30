package com.pension.authorizationserver.controller;



import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pension.authorizationserver.entity.Users;
import com.pension.authorizationserver.exception.UserAlreadyExistsException;
import com.pension.authorizationserver.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Validated
@CrossOrigin
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class UserController {

	private final UserService userService;
	
	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody Users user) throws UserAlreadyExistsException {
		log.info("Inside sign up controller ..");
		return userService.saveUser(user.getUsername(),user.getPassword());
	}

}
