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
				HealthGem.getSharedPreferences().SETTINGS_NOTIF_BLOODPRESSURE, "1");
	}

	@Override
	public void setBloodPressureNotificationOff() {
		HealthGem.getSharedPreferences().savePreferences(
				HealthGem.getSharedPreferences().SETTINGS_NOTIF_BLOODPRESSURE, "0");
	}

	@Override
	public Boolean getBloodSugarNotificationSetting() {
		return Boolean.getBoolean(HealthGem.getSharedPreferences().loadPreferences(HealthGem.getSharedPreferences().SETTINGS_NOTIF_BLOODPRESSURE));
	}

	@Override
	public void setBloodSugarNotificationOn() {
		HealthGem.getSharedPreferences().savePreferences(
				HealthGem.getSharedPreferences().SETTINGS_NOTIF_BLOODSUGAR, "1");
	}

	@Override
	public void setBloodSugarNotificationOff() {
		HealthGem.getSharedPreferences().savePreferences(
				HealthGem.getSharedPreferences().SETTINGS_NOTIF_BLOODSUGAR, "0");
	}

	@Override
	public Boolean getBloodPressureNotificationSetting() {
		return Boolean.getBoolean(HealthGem.getSharedPreferences().loadPreferences(HealthGem.getSharedPreferences().SETTINGS_NOTIF_BLOODSUGAR));
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
		return Boolean.getBoolean(HealthGem.getSharedPreferences().loadPreferences(HealthGem.getSharedPreferences().SETTINGS_WEIGHTUNIT));
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
		return Boolean.getBoolean(HealthGem.getSharedPreferences().loadPreferences(HealthGem.getSharedPreferences().SETTINGS_HEIGHTUNIT));
	}

}
