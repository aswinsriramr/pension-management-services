package com.pension.authorizationserver.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pension.authorizationserver.entity.Role;
import com.pension.authorizationserver.entity.Users;
import com.pension.authorizationserver.exception.UserAlreadyExistsException;
import com.pension.authorizationserver.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

	@MockBean
	UserService userService;

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void signuptest() throws Exception {
		Users testuser = getUser();
		when(userService.saveUser(testuser.getUsername(),testuser.getPassword())).thenReturn(new ResponseEntity<>("User added successfully", HttpStatus.CREATED));
		this.mockMvc.perform(post("/api/signup").contentType(MediaType.APPLICATION_JSON).content(asJsonString(testuser)))
					.andExpect(status().isCreated())
					.andExpect(content().string("User added successfully"));
	}
	
	
	@Test
	void cantSignuptest() throws Exception {
		Users testuser = getUser();
		when(userService.saveUser(testuser.getUsername(),testuser.getPassword())).thenThrow(UserAlreadyExistsException.class);
		this.mockMvc.perform(post("/api/signup").contentType(MediaType.APPLICATION_JSON).content(asJsonString(testuser)))
					.andDo(print())
					.andExpect(status().isConflict())
					.andExpect((content().string("Username already exists")));
	}
	
	Users getUser(){
		Users testuser = new Users();
		testuser.setUsername("TestUser");
		testuser.setPassword("testpassword");
		Set<Role> userRole = new HashSet<>();
		Role role = new Role(1L,"User");
		userRole.add(role);
		testuser.setRoles(userRole);
		return testuser;
	}
	
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
	
	
	
}
	
	

