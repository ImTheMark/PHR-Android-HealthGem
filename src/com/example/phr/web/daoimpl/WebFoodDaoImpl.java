package com.example.phr.web.daoimpl;

import java.lang.reflect.Type;
import java.util.List;

import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.WebServerException;
import com.example.phr.mobile.models.Food;
import com.example.phr.web.dao.WebFoodDao;
import com.google.gson.reflect.TypeToken;

public class WebFoodDaoImpl extends GenericListsFetcherDaoImpl<Food> implements
		WebFoodDao {

	@Override
	public int addReturnEntryId(Food food) throws WebServerException,
			OutdatedAccessTokenException {
		String command = "foodlist/add";
		return addReturnEntryIdUsingHttp(command, food);
	}

	@Override
	public List<Food> getAll() throws WebServerException,
			OutdatedAccessTokenException {
		String command = "/foodlist/getAll";
		Type type = new TypeToken<List<Food>>() {
		}.getType();
		return getAllUsingHttp(command, type);
	}

	@Override
	public List<Food> search(String query) {
		// TODO Auto-generated method stub
		return null;
	}

}
