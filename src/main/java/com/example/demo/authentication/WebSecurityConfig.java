package com.example.demo.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Bean
	public UserDetailsService userDetailsService() {
		 return new UserDetailServiceImp();
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		
		authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
		authenticationProvider.setUserDetailsService(userDetailsService());
		
		return authenticationProvider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/delete/**","/admin","/edit/**","/new").hasAuthority("ADMIN")
			.antMatchers("/login","/register","/process_register","/oauth2/**").permitAll()
			.anyRequest().authenticated()
			.and()
				.formLogin().loginPage("/login").failureHandler(loginFailureHandler).successHandler(loginSuccessHandler).permitAll()
			.and()
				.logout().permitAll();
			
	}
	
	@Autowired
	private CustomLoginFailureHandler loginFailureHandler;
		
	@Autowired
	private CustomLoginSuccessHandler loginSuccessHandler;
	
	
	
}
