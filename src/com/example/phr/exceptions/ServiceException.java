package com.example.phr.exceptions;

import android.widget.Toast;

import com.example.phr.application.HealthGem;

public class ServiceException extends Exception {

	public ServiceException(String message, Exception e) {
		super(message, e);
		Toast.makeText(HealthGem.getContext(), "Error!", Toast.LENGTH_LONG)
				.show();
	}

	public ServiceException(String message) {
		super(message);
	}
}
