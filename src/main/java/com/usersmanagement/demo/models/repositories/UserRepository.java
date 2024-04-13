package com.usersmanagement.demo.models.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.usersmanagement.demo.models.entities.User;
import com.usersmanagement.demo.models.entities.UserDTO;

@Repository
public interface UserRepository
	extends CrudRepository<User, Integer> {
		@Query(value = "SELECT * FROM user", nativeQuery = true)
	    List<UserDTO> findAllUser();
		@Query(value = "SELECT * FROM user where id = :id", nativeQuery = true)
	    List<UserDTO> getUserById(int id);
		@Query(value = "SELECT * FROM user where email = :email limit 1", nativeQuery = true)
		List<User> getByMail(String email);
	}
