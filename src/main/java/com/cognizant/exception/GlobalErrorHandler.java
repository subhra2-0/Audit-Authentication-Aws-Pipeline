package com.cognizant.exception;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cognizant.model.CustomErrorResponse;

import lombok.extern.slf4j.Slf4j;


@RestControllerAdvice @Slf4j
public class GlobalErrorHandler {
	
	
	
	@ExceptionHandler(LoginFailedException.class)
	public ResponseEntity<CustomErrorResponse> handleIdNotFoundexception(LoginFailedException ex)
	{
		log.info("start:");
		CustomErrorResponse response=new CustomErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND,
				"Username or Password Incorrect", ex.getMessage());
		log.info("end");
		return new ResponseEntity<CustomErrorResponse>(response,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(TokenExpiredException.class)
	public ResponseEntity<CustomErrorResponse> handleTokenNotFoundexception(TokenExpiredException ex)
	{
		log.info("start:");
		CustomErrorResponse response=new CustomErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND,
				"Username or Password Incorrect", ex.getMessage());
		log.info("end");
		return new ResponseEntity<CustomErrorResponse>(response,HttpStatus.NOT_FOUND);
	}
	
	
	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<CustomErrorResponse> handleUserNotFoundexception(UsernameNotFoundException ex)
	{
		log.info("start");
		CustomErrorResponse response=new CustomErrorResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND,
				"Username or Password Incorrect", ex.getMessage());
		log.info("end");
		return new ResponseEntity<CustomErrorResponse>(response,HttpStatus.NOT_FOUND);
	}
}
