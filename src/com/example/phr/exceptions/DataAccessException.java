package com.example.phr.exceptions;


public class DataAccessException extends Exception {

	public DataAccessException(String message, Exception e) {
		super(message, e);

	}
}
