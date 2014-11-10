package com.example.phr.exceptions;


public class ServiceException extends Exception {

	public ServiceException(String message, Exception e) {
		super(message, e);

	}

	public ServiceException(String message) {
		super(message);
	}
}
