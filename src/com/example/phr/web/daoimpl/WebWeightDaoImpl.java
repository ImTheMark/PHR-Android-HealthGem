package com.example.phr.web.daoimpl;

import java.util.List;

import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.WebServerException;
import com.example.phr.mobile.models.Weight;
import com.example.phr.web.dao.WebWeightDao;

public class WebWeightDaoImpl extends BasicDaoImpl<Weight> implements
		WebWeightDao {

	@Override
	public int add_ReturnEntryIdInWeb(Weight weight) throws WebServerException,
			OutdatedAccessTokenException {
		String command = "tracker/addWeight";
		String fieldName = "weight";
		return add_ReturnIDToWebUsingHttp(command, fieldName, weight);
	}

	@Override
	public void edit(Weight weight) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Weight weight) {
		// TODO Auto-generated method stub

	}

	@Override
	public Weight get(int entryID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Weight> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
