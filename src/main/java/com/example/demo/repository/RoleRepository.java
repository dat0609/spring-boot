package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	
	@Query(value = "Select r from Role r WHERE r.name = ?1")
	public Role findByName(String name);
}
