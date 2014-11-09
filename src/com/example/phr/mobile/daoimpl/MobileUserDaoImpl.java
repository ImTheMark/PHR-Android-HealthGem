package com.example.phr.mobile.daoimpl;

import java.text.ParseException;

import android.preference.PreferenceManager;
import android.util.Log;

import com.example.phr.application.HealthGem;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.local_db.SPreference;
import com.example.phr.mobile.dao.MobileSettingsDao;
import com.example.phr.mobile.dao.MobileUserDao;
import com.example.phr.mobile.models.User;
import com.example.phr.mobile.models.Weight;
import com.example.phr.service.WeightTrackerService;
import com.example.phr.serviceimpl.WeightTrackerServiceImpl;
import com.example.phr.tools.DateTimeParser;

public class MobileUserDaoImpl implements MobileUserDao {

	@Override
	public void clearRegisterInformation() {
		HealthGem.getSharedPreferences().savePreferences(SPreference.REGISTER_ALLERGIES, "");
		HealthGem.getSharedPreferences().savePreferences(SPreference.REGISTER_BIRTHDATE, "");
		HealthGem.getSharedPreferences().savePreferences(SPreference.REGISTER_CONTACTPERSON, "");
		HealthGem.getSharedPreferences().savePreferences(SPreference.REGISTER_CONTACTPERSONNUMBER, "");
		HealthGem.getSharedPreferences().savePreferences(SPreference.REGISTER_FBACCESSTOKEN, "");
		HealthGem.getSharedPreferences().savePreferences(SPreference.REGISTER_FBUSERNAME, "");
		HealthGem.getSharedPreferences().savePreferences(SPreference.REGISTER_NAME, "");
		HealthGem.getSharedPreferences().savePreferences(SPreference.REGISTER_GENDER, "");
		HealthGem.getSharedPreferences().savePreferences(SPreference.REGISTER_HEIGHTINCHES, "");
		HealthGem.getSharedPreferences().savePreferences(SPreference.REGISTER_KNOWNHEALTHPROBLEMS, "");
		HealthGem.getSharedPreferences().savePreferences(SPreference.REGISTER_EMAIL, "");
		HealthGem.getSharedPreferences().savePreferences(SPreference.REGISTER_CONTACTNUMBER, "");
		HealthGem.getSharedPreferences().savePreferences(SPreference.REGISTER_USERNAME, "");
		HealthGem.getSharedPreferences().savePreferences(SPreference.REGISTER_PASSWORD, "");
		HealthGem.getSharedPreferences().savePreferences(SPreference.REGISTER_WEIGHTPOUNDS, "");
	}

	@Override
	public void registerUser() {
		HealthGem.getSharedPreferences().savePreferences(SPreference.ALLERGIES, HealthGem.getSharedPreferences().loadPreferences(SPreference.REGISTER_ALLERGIES));
		HealthGem.getSharedPreferences().savePreferences(SPreference.BIRTHDATE, HealthGem.getSharedPreferences().loadPreferences(SPreference.REGISTER_BIRTHDATE));
		HealthGem.getSharedPreferences().savePreferences(SPreference.CONTACTPERSON, HealthGem.getSharedPreferences().loadPreferences(SPreference.REGISTER_CONTACTPERSON));
		HealthGem.getSharedPreferences().savePreferences(SPreference.CONTACTPERSONNUMBER,
				HealthGem.getSharedPreferences().loadPreferences(SPreference.REGISTER_CONTACTPERSONNUMBER));
		HealthGem.getSharedPreferences().savePreferences(SPreference.FBACCESSTOKEN, HealthGem.getSharedPreferences().loadPreferences(SPreference.REGISTER_FBACCESSTOKEN));
		HealthGem.getSharedPreferences().savePreferences(SPreference.FBUSERNAME, HealthGem.getSharedPreferences().loadPreferences(SPreference.REGISTER_FBUSERNAME));
		HealthGem.getSharedPreferences().savePreferences(SPreference.NAME, HealthGem.getSharedPreferences().loadPreferences(SPreference.REGISTER_NAME));
		HealthGem.getSharedPreferences().savePreferences(SPreference.GENDER, HealthGem.getSharedPreferences().loadPreferences(SPreference.REGISTER_GENDER));
		HealthGem.getSharedPreferences().savePreferences(SPreference.HEIGHTINCHES, HealthGem.getSharedPreferences().loadPreferences(SPreference.REGISTER_HEIGHTINCHES));
		HealthGem.getSharedPreferences().savePreferences(SPreference.KNOWNHEALTHPROBLEMS,
				HealthGem.getSharedPreferences().loadPreferences(SPreference.REGISTER_KNOWNHEALTHPROBLEMS));
		HealthGem.getSharedPreferences().savePreferences(SPreference.EMAIL, HealthGem.getSharedPreferences().loadPreferences(SPreference.REGISTER_EMAIL));
		HealthGem.getSharedPreferences().savePreferences(SPreference.NUMBER, HealthGem.getSharedPreferences().loadPreferences(SPreference.REGISTER_CONTACTNUMBER));
		HealthGem.getSharedPreferences().savePreferences(SPreference.USERNAME, HealthGem.getSharedPreferences().loadPreferences(SPreference.REGISTER_USERNAME));

		clearRegisterInformation();
	}

	@Override
	public void saveUser(User user) {
		HealthGem.getSharedPreferences().savePreferences(SPreference.ALLERGIES, user.getAllergies());
		HealthGem.getSharedPreferences().savePreferences(SPreference.BIRTHDATE, user.getDateOfBirth() + "");
		HealthGem.getSharedPreferences().savePreferences(SPreference.CONTACTPERSON, user.getEmergencyPerson());
		HealthGem.getSharedPreferences().savePreferences(SPreference.CONTACTPERSONNUMBER, user.getEmergencyContactNumber());
		HealthGem.getSharedPreferences().savePreferences(SPreference.FBACCESSTOKEN, user.getFbAccessToken());
		HealthGem.getSharedPreferences().savePreferences(SPreference.NAME, user.getName());
		HealthGem.getSharedPreferences().savePreferences(SPreference.GENDER, user.getGender());
		HealthGem.getSharedPreferences().savePreferences(SPreference.HEIGHTINCHES, user.getHeight() + "");
		HealthGem.getSharedPreferences().savePreferences(SPreference.KNOWNHEALTHPROBLEMS, user.getKnownHealthProblems());
		HealthGem.getSharedPreferences().savePreferences(SPreference.EMAIL, user.getEmail());
		HealthGem.getSharedPreferences().savePreferences(SPreference.NUMBER, user.getContactNumber());
		HealthGem.getSharedPreferences().savePreferences(SPreference.USERNAME, user.getUsername());
	}

	@Override
	public User getUser() {
		User user = new User();
		user.setAllergies(HealthGem.getSharedPreferences().loadPreferences(
				SPreference.ALLERGIES));
		user.setContactNumber(HealthGem.getSharedPreferences().loadPreferences(
				SPreference.NUMBER));
		try {
			user.setDateOfBirth(DateTimeParser.getTimestamp(HealthGem
					.getSharedPreferences().loadPreferences(
							SPreference.BIRTHDATE)));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		user.setEmail(HealthGem.getSharedPreferences().loadPreferences(
				SPreference.EMAIL));
		user.setEmergencyContactNumber(HealthGem.getSharedPreferences()
				.loadPreferences(SPreference.CONTACTPERSONNUMBER));
		user.setEmergencyPerson(HealthGem.getSharedPreferences()
				.loadPreferences(SPreference.CONTACTPERSON));
		user.setGender(HealthGem.getSharedPreferences().loadPreferences(
				SPreference.GENDER));
		user.setFbAccessToken(HealthGem.getSharedPreferences().loadPreferences(
				SPreference.FBACCESSTOKEN));
		user.setKnownHealthProblems(HealthGem.getSharedPreferences()
				.loadPreferences(SPreference.KNOWNHEALTHPROBLEMS));
		user.setName(HealthGem.getSharedPreferences().loadPreferences(
				SPreference.NAME));
		user.setUsername(HealthGem.getSharedPreferences().loadPreferences(
				SPreference.USERNAME));
		

		WeightTrackerService weightService = new WeightTrackerServiceImpl();
		MobileSettingsDao setting = new MobileSettingsDaoImpl();
		
		try {
			
			Weight weight = weightService.getLatest();

			if (weight != null) {
				Log.e("home weight", weight.getWeightInPounds() + "");
				if (setting.isWeightSettingInPounds()) {
					double lbs = weight.getWeightInPounds();
					user.setWeight(lbs);
				} else {
					double kgs = weight.getWeightInKilograms();
					user.setWeight(kgs);
				}
			}
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (setting.isHeightSettingInFeet()) {
			Double ft = Double.parseDouble(HealthGem.getSharedPreferences().loadPreferences(SPreference.HEIGHTINCHES)) * 0.39370;
			user.setHeight(ft);
		} else {
			double cm = Double.parseDouble(HealthGem.getSharedPreferences().loadPreferences(SPreference.HEIGHTINCHES));
			user.setHeight(cm);
		}
		
		return user;
	}

	@Override
	public void editUser(User user) {
		saveUser(user);
	}

}
