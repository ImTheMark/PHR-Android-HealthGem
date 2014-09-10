package com.example.phr.exceptions;

public class OutdatedAccessTokenException extends Exception {

	public OutdatedAccessTokenException(String message) {
		super(message);
	}
}
