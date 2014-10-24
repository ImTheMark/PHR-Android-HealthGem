package com.example.phr.service;

import java.util.List;

import com.example.phr.exceptions.EntryNotFoundException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.mobile.models.UnverifiedActivityEntry;
import com.example.phr.mobile.models.UnverifiedFoodEntry;
import com.example.phr.mobile.models.UnverifiedRestaurantEntry;
import com.example.phr.mobile.models.UnverifiedSportsEstablishmentEntry;

public interface VerificationService {
	public void updateListOfUnverifiedPosts() throws ServiceException;

	public List<UnverifiedFoodEntry> getAllUnverifiedFoodPosts()
			throws ServiceException;

	public List<UnverifiedActivityEntry> getAllUnverifiedActivityPosts()
			throws ServiceException;

	public List<UnverifiedRestaurantEntry> getAllUnverifiedRestaurantPosts()
			throws ServiceException;

	public List<UnverifiedSportsEstablishmentEntry> getAllUnverifiedSportsEstablishmentPosts()
			throws ServiceException;

	public void delete(UnverifiedFoodEntry entry)
			throws EntryNotFoundException, ServiceException;

	public void delete(UnverifiedActivityEntry entry)
			throws EntryNotFoundException, ServiceException;

	public void delete(UnverifiedRestaurantEntry entry)
			throws EntryNotFoundException, ServiceException;

	public void delete(UnverifiedSportsEstablishmentEntry entry)
			throws EntryNotFoundException, ServiceException;
}
