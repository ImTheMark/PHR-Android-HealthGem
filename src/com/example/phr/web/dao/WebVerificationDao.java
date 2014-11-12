package com.example.phr.web.dao;

import java.util.List;

import com.example.phr.exceptions.EntryNotFoundException;
import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.exceptions.WebServerException;
import com.example.phr.mobile.models.UnverifiedActivityEntry;
import com.example.phr.mobile.models.UnverifiedFoodEntry;
import com.example.phr.mobile.models.UnverifiedRestaurantEntry;
import com.example.phr.mobile.models.UnverifiedSportsEstablishmentEntry;

public interface WebVerificationDao {
	public void updateListOfUnverifiedPosts() throws ServiceException,
			OutdatedAccessTokenException, WebServerException;

	public List<UnverifiedFoodEntry> getAllUnverifiedFoodPosts()
			throws WebServerException, OutdatedAccessTokenException;

	public List<UnverifiedActivityEntry> getAllUnverifiedActivityPosts()
			throws WebServerException, OutdatedAccessTokenException;

	public List<UnverifiedRestaurantEntry> getAllUnverifiedRestaurantPosts()
			throws WebServerException, OutdatedAccessTokenException;

	public List<UnverifiedSportsEstablishmentEntry> getAllUnverifiedSportsEstablishmentPosts()
			throws WebServerException, OutdatedAccessTokenException;

	public void delete(UnverifiedFoodEntry entry)
			throws EntryNotFoundException, WebServerException,
			OutdatedAccessTokenException;

	public void delete(UnverifiedActivityEntry entry)
			throws EntryNotFoundException, WebServerException,
			OutdatedAccessTokenException;

	public void delete(UnverifiedRestaurantEntry entry)
			throws EntryNotFoundException, WebServerException,
			OutdatedAccessTokenException;

	public void delete(UnverifiedSportsEstablishmentEntry entry)
			throws EntryNotFoundException, WebServerException,
			OutdatedAccessTokenException;

	public UnverifiedFoodEntry get(UnverifiedFoodEntry entry)
			throws EntryNotFoundException, WebServerException,
			OutdatedAccessTokenException;

	public UnverifiedActivityEntry get(UnverifiedActivityEntry entry)
			throws EntryNotFoundException, WebServerException,
			OutdatedAccessTokenException;

	public UnverifiedRestaurantEntry get(UnverifiedRestaurantEntry entry)
			throws EntryNotFoundException, WebServerException,
			OutdatedAccessTokenException;

	public UnverifiedSportsEstablishmentEntry get(
			UnverifiedSportsEstablishmentEntry entry)
			throws EntryNotFoundException, WebServerException,
			OutdatedAccessTokenException;
}
