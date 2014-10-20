package com.example.phr.serviceimpl;

import java.util.List;

import com.example.phr.exceptions.DataAccessException;
import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.exceptions.WebServerException;
import com.example.phr.mobile.dao.MobileFoodDao;
import com.example.phr.mobile.daoimpl.MobileFoodDaoImpl;
import com.example.phr.mobile.models.Food;
import com.example.phr.service.FoodService;
import com.example.phr.web.dao.WebFoodDao;
import com.example.phr.web.daoimpl.WebFoodDaoImpl;

public class FoodServiceImpl implements FoodService {

	WebFoodDao webFoodDao;
	MobileFoodDao mobileFoodDao;

	public FoodServiceImpl() {
		webFoodDao = new WebFoodDaoImpl();
		mobileFoodDao = new MobileFoodDaoImpl();
	}

	@Override
	public int add(Food food) throws ServiceException {
		try {
			int id = webFoodDao.addReturnEntryId(food);
			food.setEntryID(id);
			mobileFoodDao.add(food);
			return id;
		} catch (WebServerException e) {
			e.printStackTrace();
			throw new ServiceException("Error", e);
		} catch (OutdatedAccessTokenException e) {
			e.printStackTrace();
			throw new ServiceException("Error", e);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new ServiceException("Error", e);
		}
	}

	@Override
	public List<Food> getAll() {
		try {
			return webFoodDao.getAll();
		} catch (WebServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OutdatedAccessTokenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Food> search(String query) throws ServiceException,
			OutdatedAccessTokenException {
		try {
			return webFoodDao.search(query);
		} catch (WebServerException e) {
			throw new ServiceException("Error", e);
		}
	}

}
