package com.example.phr.exceptions;

import android.widget.Toast;

import com.example.phr.application.HealthGem;

public class WebServerException extends Exception {

	public WebServerException(String message, Exception e) {
		super(message, e);
		Toast.makeText(HealthGem.getContext(), "No Internet Connection !",
				Toast.LENGTH_LONG).show();
	}

	public WebServerException(String message) {
		super(message);
	}

}
