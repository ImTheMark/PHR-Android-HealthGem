package com.example.phr.service;

import java.util.List;

import com.example.phr.exceptions.ServiceException;

public interface TimelineService<T> {

	public List<T> getAll() throws ServiceException;

}
