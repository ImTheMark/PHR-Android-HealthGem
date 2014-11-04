package com.example.phr.service;

import facebook4j.FacebookException;

public interface FacebookPostService {

	public String publish(String message) throws FacebookException;
}
