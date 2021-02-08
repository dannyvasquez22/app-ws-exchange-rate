package com.admin.utils;

public class Constants {

	private Constants() {
		throw new IllegalStateException("Constants class");
	}

	public static final String AUTHORIZATION = "Authorization";
	public static final String BEARER = "Bearer ";
	public static final String MY_SECRET_KEY = "mySecretKey";
	public static final String AUTHORITIES = "authorities";
	public static final String URL_LOGIN = "/api/v1/login";	

	public static final long TOKEN_EXPIRATION_TIME = 864_000_000; // 10 day
}
