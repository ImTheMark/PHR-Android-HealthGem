package com.example.phr.exceptions;

public class EntryNotFoundException extends Exception {
	
	public EntryNotFoundException(String message, Exception e) {
		super(message, e);
	}

}
