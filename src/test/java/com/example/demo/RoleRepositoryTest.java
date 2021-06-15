package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class RoleRepositoryTest {

	@Autowired
	RoleRepository roleRepo;

	@Autowired
	UserRepository userRepo;

	@Autowired
	TestEntityManager entity;

	@Test
	public void testCreateRole() {
		Role user = new Role();
		user.setName("HAHA");

		roleRepo.save(user);

		List<Role> list = roleRepo.findAll();
		assertThat(list.size()).isEqualTo(1);

	}

	@Test
	public void testAddRoleToUser() {
		User user = new User();
		user.setEnable(true);
		user.setUsername("lolo");
		user.setPassword("123");

		Role roleUser = roleRepo.findByName("HAHA");

		user.addRole(roleUser);

		User saved = userRepo.save(user);

		assertThat(saved.getRoles().size()).isEqualTo(1);

	}

	@Test
	public void testAddRoleToExistUser() {
		User user = userRepo.findById(1L).get();

		Role roleUser = roleRepo.findByName("USER");

		user.addRole(roleUser);

//		Role roleAdmin = new Role();
//		user.addRole(roleAdmin);

		User saved = userRepo.save(user);

		assertThat(saved.getRoles().size()).isEqualTo(1);
	}

	@Test
	public void testUpdateFailedAttempt() {
		String username = "tuan";
		int failedAttempt = 2;

		userRepo.updateFailedAttempt(failedAttempt, username);

		User user = entity.find(User.class, 3);
		
		assertThat(failedAttempt).isEqualTo(user.getFailedAttempt());

	}
	
	
}
