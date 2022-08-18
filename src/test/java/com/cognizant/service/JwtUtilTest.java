package com.cognizant.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

//import org.junit.Test;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.cognizant.repository.ManagerRepository;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest @Slf4j
public class JwtUtilTest {
	/**
	 * This method will test the token based on the given parameter userDetails
	 * 
	
	 */
	UserDetails userdetails;
	
	
	@Autowired
	JwtUtil jwtUtil;
	
	@Mock
	ManagerRepository managerrepositary;

	
	/**
	 * This method is used to test the token based on the given token and
	 * userDetails as parameter. First from the token we will extract the username
	 * and then will check in the database whether the token extracted username and
	 * the user residing in database is same or not and also will check whether the
	 * token has been expired or not
	 * 
	
	 */
	@Test
	public void validateTokenTest() {
		
		userdetails = new User("admin", "admin", new ArrayList<>());
		String generateToken = jwtUtil.generateToken(userdetails);
		Boolean validateToken = jwtUtil.validateToken(generateToken);
		assertEquals(true, validateToken);
		

	}
	
	@Test
	public void validateTokenWithNameFalseTest() {
		
		userdetails = new User("admin", "admin", new ArrayList<>());
		UserDetails user1 = new User("admin1", "admin1", new ArrayList<>());
		
		String generateToken = jwtUtil.generateToken(userdetails);
		Boolean validateToken = jwtUtil.validateToken(generateToken, user1);
		assertEquals(false, validateToken);
		
	}

}
