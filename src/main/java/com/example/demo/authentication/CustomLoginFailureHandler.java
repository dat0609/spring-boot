package com.example.demo.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;

@Component
public class CustomLoginFailureHandler  extends SimpleUrlAuthenticationFailureHandler{

	@Autowired
	UserService service;
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		
		String username = request.getParameter("username");
		User user = service.getByUsername(username);
		
		if (user != null) {
			if(user.isEnable() && user.isAccountNonLocked()) {
				if(user.getFailedAttempt() < UserService.MAX_FAILED_ATTEMPT - 1) {
					service.increaseFailedAttempt(user);
				}else {
					service.lock(user);
					exception =  new LockedException("Your account has been locked	due to 3 failed attempts.It will be unclock"
							+ "after 24h");
				}
			}else if(!user.isAccountNonLocked()) {
				if(service.unclock(user)) {
					exception = new LockedException("Your account has been unclock,Please try again!");
				}
			}
		}
		
		super.setDefaultFailureUrl("/login?error");
		super.onAuthenticationFailure(request, response, exception);
	}

}
