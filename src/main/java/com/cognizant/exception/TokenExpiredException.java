package com.cognizant.exception;

import io.jsonwebtoken.ExpiredJwtException;

public class TokenExpiredException extends ExpiredJwtException{

	
	private static final long serialVersionUID = 1L;
	public TokenExpiredException(String message)
	{
		super(null, null, message);
	}
}
