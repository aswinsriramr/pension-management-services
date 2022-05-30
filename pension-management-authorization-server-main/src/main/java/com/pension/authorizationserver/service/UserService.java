package com.pension.authorizationserver.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pension.authorizationserver.entity.Role;
import com.pension.authorizationserver.entity.Users;
import com.pension.authorizationserver.exception.UserAlreadyExistsException;
import com.pension.authorizationserver.repository.RoleRepository;
import com.pension.authorizationserver.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService implements UserDetailsService {
	
	
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("Inside load user service ..");
		Users user = userRepository.findByUsername(username);
		if(user==null) {
			log.error("User not found so throwing exception");
			throw new UsernameNotFoundException("User not found in the database");
		}else {
		log.info("User found so logging in");
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		user.getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		});
		return new User(user.getUsername(),user.getPassword(),authorities);}
	}
	
	

	public ResponseEntity<String> saveUser(String username,String password) throws UserAlreadyExistsException {
		log.info("Inside save user service ..");
		Users user = new Users();
		if(userRepository.findByUsername(username)!=null) {
			log.info("User name already exists so throwing Exception ..");
			throw new UserAlreadyExistsException("Username already exists");
		}else {
			log.info("No users found with same name so saving ...");
		user.setUsername(username);
		user.setPassword(passwordEncoder.encode(password));
		Optional<Role> userRole = roleRepository.findById(2L);
		Role role = userRole.get();
		log.info("adding default role {} to the user", role);
		Set<Role> userRoles = new HashSet<>();
		userRoles.add(role);
		user.setRoles(userRoles);
		userRepository.save(user);
		return new ResponseEntity<>("User added successfully", HttpStatus.CREATED);
		}
	}

}

