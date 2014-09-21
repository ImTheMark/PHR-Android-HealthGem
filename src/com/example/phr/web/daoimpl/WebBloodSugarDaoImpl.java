package com.example.phr.web.daoimpl;

import java.util.List;

import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.WebServerException;
import com.example.phr.model.BloodSugar;
import com.example.phr.web.dao.WebBloodSugarDao;

public class WebBloodSugarDaoImpl extends BasicDaoImpl<BloodSugar> implements
		WebBloodSugarDao {

	@Override
	public int add_ReturnEntryIdInWeb(BloodSugar bloodSugar)
			throws WebServerException, OutdatedAccessTokenException {
		String command = "tracker/addBloodSugar";
		String fieldName = "bloodSugar";
		return add_ReturnIDToWebUsingHttp(command, fieldName, bloodSugar);
	}

	@Override
	public void edit(BloodSugar bloodSugar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(BloodSugar bloodSugar) {
		// TODO Auto-generated method stub

	}

	@Override
	public BloodSugar get(int entryID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BloodSugar> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
