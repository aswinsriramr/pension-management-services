package com.pension.authorizationserver.service;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;

import com.pension.authorizationserver.entity.Role;
import com.pension.authorizationserver.entity.Users;
import com.pension.authorizationserver.exception.UserAlreadyExistsException;
import com.pension.authorizationserver.repository.RoleRepository;
import com.pension.authorizationserver.repository.UserRepository;

@SpringBootTest
public class UserServiceTest {
	
	@InjectMocks
	UserService userService;

	@Mock
	UserRepository userRepository;
	@Mock
	RoleRepository roleRepository;
	@Mock
	PasswordEncoder passwordEncoder;
	
	
	Users getUser(){
		Users testuser = new Users();
		testuser.setUsername("TestUser");
		testuser.setPassword("testpassword");
		Set<Role> userRole = new HashSet<>();
		Role role = new Role(null,"ROLE_USER");
		userRole.add(role);
		testuser.setRoles(userRole);
		return testuser;
	}
	
	
	
	
	@Test
	@DirtiesContext
	void addUserTest() throws UserAlreadyExistsException {
		Users user = getUser();
		Optional<Role> role = Optional.of(new Role(null,"ROLE_USER"));
		when(roleRepository.findById(2L)).thenReturn(role);
		ResponseEntity<String> response = userService.saveUser(user.getUsername(), user.getPassword());
		assertEquals("User added successfully",response.getBody());
	}
	
	@Test
	@DirtiesContext
	void cannotAddUserTest() throws UserAlreadyExistsException {
		Users testuser = getUser();
		String username = testuser.getUsername();
		when(userRepository.findByUsername(username)).thenReturn(testuser);
		assertThatExceptionOfType(UserAlreadyExistsException.class).isThrownBy(() -> {
			userService.saveUser(testuser.getUsername(), testuser.getPassword());
		});	}
	
	@Test
	@DirtiesContext
	void LoadByUsernameTest() {
		Users testuser = getUser();
		String username = testuser.getUsername();
		when(userRepository.findByUsername(username)).thenReturn(testuser);
		UserDetails user = userService.loadUserByUsername(testuser.getUsername());
		assertEquals(testuser.getUsername(),user.getUsername());
	}
	
	
	@Test
	@DirtiesContext
	void LoadByNonExistingUsernameTest() {
		Users testuser = getUser();
		String username = testuser.getUsername();
		when(userRepository.findByUsername(username)).thenReturn(null);
		assertThatExceptionOfType(UsernameNotFoundException.class).isThrownBy(() -> {
			userService.loadUserByUsername(username);
		});
	}
	
	
}
