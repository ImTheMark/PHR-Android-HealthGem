package com.example.phr.serviceimpl;

import java.util.List;

import com.example.phr.enums.WeightType;
import com.example.phr.exceptions.DataAccessException;
import com.example.phr.exceptions.EntryNotFoundException;
import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.exceptions.WebServerException;
import com.example.phr.mobile.dao.MobileWeightTrackerDao;
import com.example.phr.mobile.daoimpl.MobileWeightTrackerDaoImpl;
import com.example.phr.mobile.models.User;
import com.example.phr.mobile.models.Weight;
import com.example.phr.service.UserService;
import com.example.phr.service.WeightTrackerService;
import com.example.phr.web.dao.WebWeightTrackerDao;
import com.example.phr.web.daoimpl.WebWeightTrackerDaoImpl;

public class WeightTrackerServiceImpl implements WeightTrackerService {

	WebWeightTrackerDao webWeightTrackerDao;
	MobileWeightTrackerDao mobileWeightTrackerDao;
	UserService userService;

	public WeightTrackerServiceImpl() {
		webWeightTrackerDao = new WebWeightTrackerDaoImpl();
		mobileWeightTrackerDao = new MobileWeightTrackerDaoImpl();
		userService = new UserServiceImpl();
	}

	@Override
	public void add(Weight weight) throws ServiceException,
			OutdatedAccessTokenException {
		int entryID;
		try {
			entryID = webWeightTrackerDao.add_ReturnEntryIdInWeb(weight);
			weight.setEntryID(entryID);
			mobileWeightTrackerDao.add(weight);
		} catch (WebServerException e) {
			throw new ServiceException(
					"An error occured while trying to add weight to web", e);
		} catch (DataAccessException e) {
			throw new ServiceException(
					"An error occured while trying to add weight to web", e);
		}
	}

	@Override
	public void edit(Weight weight) throws OutdatedAccessTokenException,
			EntryNotFoundException, ServiceException {
		try {
			webWeightTrackerDao.edit(weight);
			mobileWeightTrackerDao.edit(weight);
		} catch (DataAccessException e) {
			throw new ServiceException(
					"An error occured while trying to edit weight to web", e);
		} catch (WebServerException e) {
			throw new ServiceException(
					"An error occured while trying to edit weight to web", e);
		}
	}

	@Override
	public void delete(Weight weight) throws ServiceException,
			OutdatedAccessTokenException, EntryNotFoundException {
		try {
			webWeightTrackerDao.delete(weight);
			mobileWeightTrackerDao.delete(weight);
		} catch (WebServerException e) {
			throw new ServiceException(
					"An error occured while trying to delete weight to web", e);
		} catch (DataAccessException e) {
			throw new ServiceException(
					"An error occured while trying to delete weight to web", e);
		}
	}

	@Override
	public List<Weight> getAll() throws ServiceException {
		try {
			return mobileWeightTrackerDao.getAll();
		} catch (DataAccessException e) {
			throw new ServiceException(
					"An error occured while trying to retrieve", e);
		}
	}

	@Override
	public Weight get(int entryID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Weight getLatest() throws ServiceException {
		try {
			return mobileWeightTrackerDao.getLatest();
		} catch (DataAccessException e) {
			throw new ServiceException(
					"An error occured while trying to get latest weight from local db",
					e);
		}
	}

	@Override
	public void initializeMobileDatabase() throws ServiceException {
		try {
			List<Weight> list = webWeightTrackerDao.getAll();
			for (Weight entry : list) {
				mobileWeightTrackerDao.add(entry);
			}
		} catch (WebServerException e) {
			throw new ServiceException(
					"An error occured while trying to get latest weight from local db",
					e);
		} catch (OutdatedAccessTokenException e) {
			throw new ServiceException(
					"An error occured while trying to get latest weight from local db",
					e);
		} catch (DataAccessException e) {
			throw new ServiceException(
					"An error occured while trying to get latest weight from local db",
					e);
		}
	}

	private WeightType getWeightType(Weight weight, double heightInMeter) {
		double bmi = weight.getWeightInKilograms()
				/ (heightInMeter * heightInMeter);
		if (bmi < 18.5)
			return WeightType.UNDERWEIGHT;
		else if (bmi >= 18.5 && bmi < 24.9)
			return WeightType.NORMAL;
		else if (bmi >= 25 && bmi < 29.9)
			return WeightType.OVERWEIGHT;
		else
			return WeightType.OBESE;
	}

	@Override
	public String getFeedback() throws ServiceException {
		User user = userService.getUser();
		double heightInMeter = user.getHeightInMeter();

		try {
			List<Weight> lastTwoWeights = mobileWeightTrackerDao
					.getLastTwoEntries();
			WeightType latest = getWeightType(lastTwoWeights.get(0),
					heightInMeter);
			WeightType second = getWeightType(lastTwoWeights.get(1),
					heightInMeter);

			if (latest.equals(WeightType.OVERWEIGHT)
					&& second.equals(WeightType.OVERWEIGHT))
				return "	You have been overweight for a period of time. It is recommended for you to eat healthier and exercise.";
			else if (latest.equals(WeightType.UNDERWEIGHT)
					&& second.equals(WeightType.UNDERWEIGHT))
				return "	You have been underweight for a period of time. It is recommended for you to consult your doctor.";
			else if (latest.equals(WeightType.OBESE)
					&& second.equals(WeightType.OBESE))
				return "	You have been obese for a period of time.  It is recommended for you to eat healthier,exercise and consult a nutritionist.";
			else if (latest.equals(WeightType.UNDERWEIGHT)
					&& second.equals(WeightType.NORMAL))
				return "	You seem to drop from the recommended weight. It is recommended for you to eat more nutritious food and have exercise.";
			else if (latest.equals(WeightType.OVERWEIGHT)
					&& second.equals(WeightType.NORMAL))
				return "	You suddenly gained a few pounds. Take on a light diet to get you back to shape";
			else if (latest.equals(WeightType.OBESE)
					&& second.equals(WeightType.NORMAL))
				return "	You drastically gained a surprising amount of weight. Please fix your diet.";
			else if (latest.equals(WeightType.OBESE)
					&& second.equals(WeightType.OVERWEIGHT))
				return "	You suddenly gained a few pounds. It is recommended to to consult your nutritionist.";
			else if (latest.equals(WeightType.NORMAL)
					&& second.equals(WeightType.OVERWEIGHT))
				return "	Hurray for a healthy lifestyle!";
			else if (latest.equals(WeightType.UNDERWEIGHT)
					&& second.equals(WeightType.OVERWEIGHT))
				return "	Your weight has dropped significantly. Kindly consult your doctor.";
			else if (latest.equals(WeightType.OVERWEIGHT)
					&& second.equals(WeightType.OBESE))
				return "	Great job for losing some weight continue to eat healthy and exercise.";
			else if (latest.equals(WeightType.NORMAL)
					&& second.equals(WeightType.OBESE))
				return "	Great job for losing some weight and maintain your weight.";
			else if (latest.equals(WeightType.UNDERWEIGHT)
					&& second.equals(WeightType.OBESE))
				return "	Your weight has dropped significantly. Kindly consult your doctor.";
			else if (latest.equals(WeightType.NORMAL)
					&& second.equals(WeightType.UNDERWEIGHT))
				return "	Good job! Continue to eat healthy.";
			else if (latest.equals(WeightType.OVERWEIGHT)
					&& second.equals(WeightType.UNDERWEIGHT))
				return "	Your weight has increased significantly. Kindly consult your doctor.";
			else if (latest.equals(WeightType.OBESE)
					&& second.equals(WeightType.UNDERWEIGHT))
				return "	Your weight has increased significantly. Kindly consult your doctor.";
			else
				return "";
		} catch (DataAccessException e) {
			throw new ServiceException("Error while fetching last two posts", e);
		}

	}
}
