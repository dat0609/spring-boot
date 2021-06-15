package com.example.demo.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {

	@Id
	@Column(name ="user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String username;
	private String password;
	private boolean enable;
	
	@Column(name = "account_non_locked")
	private boolean accountNonLocked;
	
	@Column(name = "failed_attempt")
	private int failedAttempt;
	
	@Column(name = "lock_time")
	private Date lockTime;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "users_roles",
				joinColumns = @JoinColumn(name ="user_id"),
				inverseJoinColumns = @JoinColumn(name ="role_id"))
	Set<Role> roles = new HashSet<Role>();
	
	public void addRole(Role role) {
		this.roles.add(role);
	}
	
}
