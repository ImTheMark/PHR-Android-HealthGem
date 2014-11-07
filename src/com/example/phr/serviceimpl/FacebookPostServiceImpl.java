package com.example.phr.serviceimpl;

import com.example.phr.application.HealthGem;
import com.example.phr.local_db.SPreference;
import com.example.phr.service.FacebookPostService;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.auth.AccessToken;

public class FacebookPostServiceImpl implements FacebookPostService {
	private static final String appID = "458502710946706";
	private static final String appSecret = "c41ccfbd5ff58c87342f4df5911d2d88";

	Facebook facebook;

	@Override
	public String publish(String message) throws FacebookException {
		facebook = new FacebookFactory().getInstance();
		facebook.setOAuthAppId(appID, appSecret);
		String permissions = "publish_actions";
		facebook.setOAuthPermissions(permissions);

		facebook.setOAuthAccessToken(new AccessToken(HealthGem
				.getSharedPreferences().loadPreferences(
						SPreference.FBACCESSTOKEN), null));
		// Media arg1 = null;
		// facebook.postPhoto(message, arg1);
		return facebook.postStatusMessage(message);
	}
}
