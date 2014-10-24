package com.example.phr.serviceimpl;

import java.util.List;

import com.example.phr.exceptions.EntryNotFoundException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.mobile.models.UnverifiedActivityEntry;
import com.example.phr.mobile.models.UnverifiedFoodEntry;
import com.example.phr.mobile.models.UnverifiedRestaurantEntry;
import com.example.phr.mobile.models.UnverifiedSportsEstablishmentEntry;
import com.example.phr.service.VerificationService;

public class VerificationServiceImpl implements VerificationService {

	@Override
	public void updateListOfUnverifiedPosts() throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<UnverifiedFoodEntry> getAllUnverifiedFoodPosts()
			throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UnverifiedActivityEntry> getAllUnverifiedActivityPosts()
			throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UnverifiedRestaurantEntry> getAllUnverifiedRestaurantPosts()
			throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UnverifiedSportsEstablishmentEntry> getAllUnverifiedSportsEstablishmentPosts()
			throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(UnverifiedFoodEntry entry)
			throws EntryNotFoundException, ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(UnverifiedActivityEntry entry)
			throws EntryNotFoundException, ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(UnverifiedRestaurantEntry entry)
			throws EntryNotFoundException, ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(UnverifiedSportsEstablishmentEntry entry)
			throws EntryNotFoundException, ServiceException {
		// TODO Auto-generated method stub

	}

}
