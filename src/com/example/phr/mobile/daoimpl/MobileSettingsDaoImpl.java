package com.example.phr.mobile.daoimpl;

import com.example.phr.application.HealthGem;
import com.example.phr.mobile.dao.MobileSettingsDao;

public class MobileSettingsDaoImpl implements MobileSettingsDao {

	@Override
	public void initializeSettings() {
		setBloodPressureNotificationOn();
		setBloodSugarNotificationOn();
		setHeightToFeet();
		setWeightToPounds();
	}

	@Override
	public void setBloodPressureNotificationOn() {
		HealthGem.getSharedPreferences().savePreferences(
				HealthGem.getSharedPreferences().SETTINGS_NOTIF_BLOODPRESSURE,
				"1");
	}

	@Override
	public void setBloodPressureNotificationOff() {
		HealthGem.getSharedPreferences().savePreferences(
				HealthGem.getSharedPreferences().SETTINGS_NOTIF_BLOODPRESSURE,
				"0");
	}

	@Override
	public Boolean getBloodPressureNotificationSetting() {
		int x = Integer
				.parseInt(HealthGem
						.getSharedPreferences()
						.loadPreferences(
								HealthGem.getSharedPreferences().SETTINGS_NOTIF_BLOODPRESSURE));
		if (x == 1)
			return true;
		else
			return false;
	}

	@Override
	public void setBloodSugarNotificationOn() {
		HealthGem
				.getSharedPreferences()
				.savePreferences(
						HealthGem.getSharedPreferences().SETTINGS_NOTIF_BLOODSUGAR,
						"1");
	}

	@Override
	public void setBloodSugarNotificationOff() {
		HealthGem
				.getSharedPreferences()
				.savePreferences(
						HealthGem.getSharedPreferences().SETTINGS_NOTIF_BLOODSUGAR,
						"0");
	}

	@Override
	public Boolean getBloodSugarNotificationSetting() {
		int x = Integer
				.parseInt(HealthGem
						.getSharedPreferences()
						.loadPreferences(
								HealthGem.getSharedPreferences().SETTINGS_NOTIF_BLOODSUGAR));
		if (x == 1)
			return true;
		else
			return false;
	}

	@Override
	public void setWeightToKilograms() {
		HealthGem.getSharedPreferences().savePreferences(
				HealthGem.getSharedPreferences().SETTINGS_WEIGHTUNIT, "1");
	}

	@Override
	public void setWeightToPounds() {
		HealthGem.getSharedPreferences().savePreferences(
				HealthGem.getSharedPreferences().SETTINGS_WEIGHTUNIT, "0");
	}

	@Override
	public Boolean isWeightSettingInPounds() {
		int x = Integer.parseInt(HealthGem.getSharedPreferences()
				.loadPreferences(
						HealthGem.getSharedPreferences().SETTINGS_WEIGHTUNIT));
		if (x == 1)
			return false;
		else
			return true;
	}

	@Override
	public void setHeightToFeet() {
		HealthGem.getSharedPreferences().savePreferences(
				HealthGem.getSharedPreferences().SETTINGS_HEIGHTUNIT, "1");
	}

	@Override
	public void setHeightToCentimeters() {
		HealthGem.getSharedPreferences().savePreferences(
				HealthGem.getSharedPreferences().SETTINGS_HEIGHTUNIT, "0");
	}

	@Override
	public Boolean isHeightSettingInFeet() {
		int x = Integer.parseInt(HealthGem.getSharedPreferences()
				.loadPreferences(
						HealthGem.getSharedPreferences().SETTINGS_HEIGHTUNIT));
		if (x == 1)
			return true;
		else
			return false;
	}
}
