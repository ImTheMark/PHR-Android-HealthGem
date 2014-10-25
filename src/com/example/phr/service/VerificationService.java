package com.example.phr.service;

import java.util.List;

import com.example.phr.exceptions.EntryNotFoundException;
import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.mobile.models.UnverifiedActivityEntry;
import com.example.phr.mobile.models.UnverifiedFoodEntry;
import com.example.phr.mobile.models.UnverifiedRestaurantEntry;
import com.example.phr.mobile.models.UnverifiedSportsEstablishmentEntry;

public interface VerificationService {
	public void updateListOfUnverifiedPosts() throws ServiceException,
			OutdatedAccessTokenException;

	public List<UnverifiedFoodEntry> getAllUnverifiedFoodPosts()
			throws ServiceException, OutdatedAccessTokenException;

	public List<UnverifiedActivityEntry> getAllUnverifiedActivityPosts()
			throws ServiceException, OutdatedAccessTokenException;

	public List<UnverifiedRestaurantEntry> getAllUnverifiedRestaurantPosts()
			throws ServiceException, OutdatedAccessTokenException;

	public List<UnverifiedSportsEstablishmentEntry> getAllUnverifiedSportsEstablishmentPosts()
			throws ServiceException, OutdatedAccessTokenException;

	public void delete(UnverifiedFoodEntry entry)
			throws EntryNotFoundException, ServiceException,
			OutdatedAccessTokenException;

	public void delete(UnverifiedActivityEntry entry)
			throws EntryNotFoundException, ServiceException,
			OutdatedAccessTokenException;

	public void delete(UnverifiedRestaurantEntry entry)
			throws EntryNotFoundException, ServiceException,
			OutdatedAccessTokenException;

	public void delete(UnverifiedSportsEstablishmentEntry entry)
			throws EntryNotFoundException, ServiceException,
			OutdatedAccessTokenException;
}
