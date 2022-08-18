package com.cognizant.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.model.AuthResponse;
import com.cognizant.model.ProjectManager;
import com.cognizant.model.ProjectManager;
import com.cognizant.model.UserCredentials;
import com.cognizant.service.JwtUtil;
import com.cognizant.service.ManagerDetailsService;
import com.cognizant.exception.LoginFailedException;

import lombok.extern.slf4j.Slf4j;

/**
 * This class is having all the end points related to authorization purpose. For
 * getting the token and validating the token this class will be used.
 *
 */
@RestController
@Slf4j
public class AuthController {
	
	// Logger log= LoggerFactory.getLogger(AuthController.class);

	@Autowired
	private JwtUtil jwtutil;
	/**
	 * This is a private field which is used to fetch the user credentials from the
	 * database
	 */
	@Autowired
	private ManagerDetailsService managerDetailsService;

	/**
	 * This is used to encrypt password
	 */

	@GetMapping(path = "/health")
	public ResponseEntity<?> healthCheckup() {
		log.info("Health Check");
		return new ResponseEntity<>("Authenticated successfully", HttpStatus.OK);
	}

	/**
	 * This method is used to check the credentials whether the provided credentials
	 * are correct or not. For this we will call authenticate method. If user
	 * credentials are correct then we will fetch the data from database based on
	 * userid and return it to the user with a token
	 * 
	 * 
	 */

	@PostMapping(value = "/authenticate")
	public Map<String, String> getToken(@RequestBody UserCredentials userLoginCredentials) throws LoginFailedException {
		Map<String, String> tokenMap = new HashMap<>();
		log.info("start authentications");
		log.debug("User credentials", userLoginCredentials.toString());
		final UserDetails userdetails = managerDetailsService.loadUserByUsername(userLoginCredentials.getUserId());
		if (userdetails.getPassword().equals(userLoginCredentials.getPassword())) {
			String token = jwtutil.generateToken(userdetails);
			tokenMap.put("token", token);
			return tokenMap;
		} else {
			log.error("Access denied");
			log.info("Access denied");
			throw new LoginFailedException("Username or Password incorrect");

		}

	}

	@PostMapping(value = "/login")
	public ResponseEntity<?> login(@RequestBody UserCredentials userLoginCredentials) throws LoginFailedException {
		log.info("start login");
		log.debug(userLoginCredentials.toString());

		final UserDetails userdetails = managerDetailsService.loadUserByUsername(userLoginCredentials.getUserId());

		if (userdetails.getPassword().equals(userLoginCredentials.getPassword())) {
			String token = jwtutil.generateToken(userdetails);
			ProjectManager projectManager = new ProjectManager(userLoginCredentials.getUserId(),
					userLoginCredentials.getPassword(), token);
			managerDetailsService.saveUser(projectManager);
			log.info("end");
			return new ResponseEntity<>(projectManager, HttpStatus.OK);
		} else {
			log.error("access denied");
			log.info("access.denied");
			throw new LoginFailedException("Username or Password incorrect");

		}
	}

	/**
	 * validation
	 */
	@GetMapping(value = "/validate")
	public ResponseEntity<?> getValidity(@RequestHeader("Authorization") String token) {
		token = token.substring(7);
		AuthResponse res = new AuthResponse();
		ResponseEntity<?> response = null;
		log.info("start validate");
		log.debug("auth.token", token);
		try {
			if (jwtutil.validateToken(token)) {

				res.setUid(jwtutil.extractUsername(token));
				res.setValid(true);

			}
		} catch (Exception e) {
			res.setValid(false);
			log.info("end");
			if (e.getMessage().contains("the token is expired and not valid anymore"))
				response = new ResponseEntity<String>("the token is expired and not valid anymore",
						HttpStatus.FORBIDDEN);
			if (e.getMessage().contains("Authentication failed"))
				response = new ResponseEntity<String>("Authentication failed", HttpStatus.FORBIDDEN);
			response = new ResponseEntity<>(res, HttpStatus.FORBIDDEN);
			return response;
		}
		log.info("end validation");
		response = new ResponseEntity<AuthResponse>(res, HttpStatus.OK);
		return response;

	}

}
