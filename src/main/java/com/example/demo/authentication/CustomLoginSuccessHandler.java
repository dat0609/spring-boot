package com.example.demo.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;

@Component
public class CustomLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{

	@Autowired
	UserService service;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		MyUserDetails detail = (MyUserDetails) authentication.getPrincipal();
		User user = detail.getUser();
		
		if(user.getFailedAttempt() > 0) {
			service.resetFailedAttempt(user.getUsername());
		}
		
		super.onAuthenticationSuccess(request, response, authentication);
	}
	
	

	
}
