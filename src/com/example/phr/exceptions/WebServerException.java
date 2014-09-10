package com.example.phr.exceptions;

public class WebServerException extends Exception {

	public WebServerException(String message, Exception e) {
		super(message, e);
	}

	public WebServerException(String message) {
		super(message);
	}
}
