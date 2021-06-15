package com.example.demo.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.demo.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {
	
	@Query(value = "SELECT u FROM User u WHERE u.username = ?1")
	User getUserByUsername(String username);
	
	@Query(value = "update users set failed_attempt = ?1 where users.username = ?2", nativeQuery = true)
	@Modifying
	void updateFailedAttempt(int faildAttempt,String username);
	
	User getByUsername(String username);
}
	