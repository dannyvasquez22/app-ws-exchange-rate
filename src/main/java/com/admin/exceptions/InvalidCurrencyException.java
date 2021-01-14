package com.admin.exceptions;

@SuppressWarnings("serial")
public class InvalidCurrencyException extends RuntimeException {

	public InvalidCurrencyException(String message, Throwable cause) {
		super(message, cause);
	}
}
