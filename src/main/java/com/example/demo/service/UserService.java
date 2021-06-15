package com.example.demo.service;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;

@Service
@Transactional
public class UserService {

	@Autowired
	UserRepository userRepo;
	@Autowired
	RoleRepository roleRepo;

	public static final int MAX_FAILED_ATTEMPT = 3;
	public static final long LOCK_TIME_DURATION = 10 * 60 * 1000;// ms 15p
	
	public void saveUserWithDefaultRole(User user) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		String encode = bCryptPasswordEncoder.encode(user.getPassword());

		user.setAccountNonLocked(true);
		user.setEnable(true);
		
		user.setPassword(encode);
		Role roleUser = roleRepo.findByName("USER");

		user.addRole(roleUser);

		userRepo.save(user);
	}

	public User getByUsername(String username) {
		return userRepo.getByUsername(username);
	}

	public void increaseFailedAttempt(User user) {
		int newFailedAttempt = user.getFailedAttempt() + 1;
		userRepo.updateFailedAttempt(newFailedAttempt, user.getUsername());
	}

	public void lock(User user) {
		user.setAccountNonLocked(false);
		user.setLockTime(new Date());

		userRepo.save(user);
	}

	public boolean unclock(User user) {
		long lockTime = user.getLockTime().getTime();
		long currentTime = System.currentTimeMillis();

		if (lockTime + LOCK_TIME_DURATION < currentTime) {
			user.setAccountNonLocked(true);
			user.setLockTime(null);
			user.setFailedAttempt(0);
			
			userRepo.save(user);

			return true;
		}
		return false;
	}

	public void resetFailedAttempt(String username) {
		userRepo.updateFailedAttempt(0, username);
		
	}
}
