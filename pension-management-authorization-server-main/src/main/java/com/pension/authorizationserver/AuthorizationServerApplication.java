package com.pension.authorizationserver;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.pension.authorizationserver.service.UserService;

@SpringBootApplication
public class AuthorizationServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthorizationServerApplication.class, args);
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run(UserService userService) {
		
		return args ->{
			userService.saveUser("John","abc@123");
			userService.saveUser("Joe","abc@123");
			userService.saveUser("Job","abc@123");
			userService.saveUser("Toast","abc@123");
			userService.saveUser("Jack","abc@123");
			userService.saveUser("Jill","abc@123");
			userService.saveUser("Jim","abc@123");
			userService.saveUser("Peter","abc@123");
			userService.saveUser("Bob","abc@123");
			userService.saveUser("Albert","abc@123");
			userService.saveUser("Rachel","abc@123");
			userService.saveUser("Julie","abc@123");
			
			
		};
	}
}
