package com.usersmanagement.demo.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.usersmanagement.demo.models.entities.User;
import com.usersmanagement.demo.models.entities.UserDTO;
import com.usersmanagement.demo.models.repositories.UserRepository;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping
	public ResponseEntity<?> createUser(User user) {
		List<User> getByMail = userRepository.getByMail(user.getEmail());
		System.out.println(getByMail);
		if (getByMail.isEmpty()) {
			String rawPassword = user.getPassword();
			String encriptedPass = user.encryptPassword(rawPassword);
			user.setPassword(encriptedPass);
			userRepository.save(user);
			user.setPassword("***********");
			return new ResponseEntity<>(user, HttpStatus.CREATED);
			
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
		}
	}
	
	@GetMapping
	public List<UserDTO> getUsers() {
		List<UserDTO> user = userRepository.findAllUser();
		return user;
	}
	
	@ResponseBody
	@GetMapping(path="/{id}")
	public List<UserDTO> getUserById(@PathVariable int id) {
		List<UserDTO> user = userRepository.getUserById(id);
		return user;
	}
	
	@DeleteMapping(path="/{id}")
	public void deleteUser(@PathVariable int id) {
		userRepository.deleteById(id);
	}

}
