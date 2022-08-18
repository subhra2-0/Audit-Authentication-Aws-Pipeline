package com.cognizant.exception;


import static org.junit.jupiter.api.Assertions.assertNotNull;


import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;

import lombok.extern.slf4j.Slf4j;

@ContextConfiguration @Slf4j
@SpringBootTest
public class LoginFailedExceptionTest {

	/**
	 * to test the LoginFailedException Exception
	 * 
	 */
	
	@Test
	public void testInvalidAuthorizationException() {
		
		LoginFailedException loginFailedException = new LoginFailedException("invalid credentials");
		assertNotNull(loginFailedException);
		
	}


}
