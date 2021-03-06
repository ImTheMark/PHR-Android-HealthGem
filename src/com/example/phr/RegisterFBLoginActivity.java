package com.example.phr;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phr.application.HealthGem;
import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.exceptions.UserAlreadyExistsException;
import com.example.phr.local_db.SPreference;
import com.example.phr.mobile.dao.MobileSettingsDao;
import com.example.phr.mobile.daoimpl.MobileSettingsDaoImpl;
import com.example.phr.mobile.models.User;
import com.example.phr.mobile.models.Weight;
import com.example.phr.service.UserService;
import com.example.phr.service.WeightTrackerService;
import com.example.phr.serviceimpl.UserServiceImpl;
import com.example.phr.serviceimpl.WeightTrackerServiceImpl;
import com.example.phr.tools.DateTimeParser;
import com.example.phr.tools.ImageHandler;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.LoginButton.UserInfoChangedCallback;

@SuppressLint("NewApi")
public class RegisterFBLoginActivity extends Activity {

	private LoginButton btnLogin;
	private UiLifecycleHelper uiHelper;
	private static final List<String> PERMISSIONS = Arrays.asList(
			"user_birthday", "email", "read_stream", "user_photos",
			"user_status", "user_actions:instapp");
	private static final List<String> PUBLISH_PERMISSIONS = Arrays
			.asList("publish_actions");
	private TextView userName;
	private ImageView profilePicture;
	public static GraphUser user;
	public static String userID;
	private UserService userService;
	private WeightTrackerService weightService;

	private boolean pendingPublishReauthorization = false;

	private MobileSettingsDao settingDao;
	private Boolean isRegister = true;
	Activity activity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		userService = new UserServiceImpl();
		activity = this;

		StrictMode.setThreadPolicy(policy);
		getActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#030203")));
		weightService = new WeightTrackerServiceImpl();

		try {
			PackageInfo info = getPackageManager().getPackageInfo(
					"com.example.phr", PackageManager.GET_SIGNATURES);
			for (Signature signature : info.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				Log.e("KeyHash:",
						Base64.encodeToString(md.digest(), Base64.DEFAULT));
			}
		} catch (NameNotFoundException e) {

		} catch (NoSuchAlgorithmException e) {

		}

		uiHelper = new UiLifecycleHelper(this, statusCallback);
		uiHelper.onCreate(savedInstanceState);

		setContentView(R.layout.activity_registration_fblogin);

		userName = (TextView) findViewById(R.id.textViewRegistrationFBTitle);
		btnLogin = (LoginButton) findViewById(R.id.fb_login_button);
		profilePicture = (ImageView) findViewById(R.id.registerFBLogin_picture);

		btnLogin.setReadPermissions(PERMISSIONS);

		// btnLogin.setPublishPermissions(PUBLISH_PERMISSIONS);
		btnLogin.setUserInfoChangedCallback(new UserInfoChangedCallback() {
			@Override
			public void onUserInfoFetched(GraphUser user) {
				RegisterFBLoginActivity.this.user = user;
				if (user != null) {
					userName.setText("Hello, " + user.getName());
					userID = user.getUsername();
					Session s = Session.getActiveSession();
					HealthGem.getSharedPreferences().savePreferences(
							SPreference.REGISTER_FBACCESSTOKEN,
							s.getAccessToken());
					new LoadFbImage(profilePicture).execute(user.getId());
				} else {
					Log.e("onUserInfoFetched", "user is null");
					userName.setText("You are not logged right now");
				}
			}
		});

		Intent in = getIntent();
		Bundle extras = getIntent().getExtras();

		if (extras != null && in.hasExtra("mode")) {
			isRegister = in.getExtras().getBoolean("mode");
		}
	}

	private final Session.StatusCallback statusCallback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state,
				Exception exception) {
			if (state.isOpened()) {

				Log.d("FB", "Facebook session opened");

			} else if (state.isClosed()) {
				Log.d("FB", "Facebook session closed");
			}
		}
	};

	public boolean checkPermissions() {
		Session s = Session.getActiveSession();
		if (s != null) {
			return s.getPermissions().contains(
					Arrays.asList("user_birthday", "email", "read_stream",
							"publish_actions", "user_photos"));
		} else
			return false;
	}

	public void requestPermissions() {
		Session s = Session.getActiveSession();
		if (s != null)
			s.requestNewPublishPermissions(new Session.NewPermissionsRequest(
					this, PERMISSIONS));
	}

	@Override
	public void onResume() {
		super.onResume();
		uiHelper.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);

		Session session = Session.getActiveSession();

		if (session != null) {

			// Check for publish permissions
			List<String> permissions = session.getPermissions();
			if (!isSubsetOf(PUBLISH_PERMISSIONS, permissions)) {
				pendingPublishReauthorization = true;
				Session.NewPermissionsRequest newPermissionsRequest = new Session.NewPermissionsRequest(
						this, PUBLISH_PERMISSIONS);
				session.requestNewPublishPermissions(newPermissionsRequest);
				return;
			}
		}
	}

	private boolean isSubsetOf(Collection<String> subset,
			Collection<String> superset) {
		for (String string : subset) {
			if (!superset.contains(string)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void onSaveInstanceState(Bundle savedState) {
		super.onSaveInstanceState(savedState);
		uiHelper.onSaveInstanceState(savedState);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (isRegister)
			getMenuInflater().inflate(R.menu.menu_registration_next, menu);
		else
			getMenuInflater().inflate(R.menu.menu_registration_save, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		final ProgressDialog progressDialog = new ProgressDialog(activity);
		progressDialog.setCancelable(false);
		progressDialog.setMessage("Loading...");

		switch (item.getItemId()) {
		case R.id.menu_item_next:

			progressDialog.show();
			new AsyncTask<Void, Void, Void>() {
				@Override
				protected Void doInBackground(Void... params) {

					Intent intent = new Intent(getApplicationContext(),
							MainActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
							| Intent.FLAG_ACTIVITY_CLEAR_TOP);
					try {
						User newUser = new User();
						newUser.setAllergies(HealthGem
								.getSharedPreferences()
								.loadPreferences(SPreference.REGISTER_ALLERGIES));
						newUser.setContactNumber(HealthGem
								.getSharedPreferences().loadPreferences(
										SPreference.REGISTER_CONTACTNUMBER));
						try {
							newUser.setDateOfBirth(DateTimeParser
									.getTimestamp(HealthGem
											.getSharedPreferences()
											.loadPreferences(
													SPreference.REGISTER_BIRTHDATE)));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						newUser.setEmail(HealthGem.getSharedPreferences()
								.loadPreferences(SPreference.REGISTER_EMAIL));
						newUser.setEmergencyContactNumber(HealthGem
								.getSharedPreferences()
								.loadPreferences(
										SPreference.REGISTER_CONTACTPERSONNUMBER));
						newUser.setEmergencyPerson(HealthGem
								.getSharedPreferences().loadPreferences(
										SPreference.REGISTER_CONTACTPERSON));
						newUser.setGender(HealthGem.getSharedPreferences()
								.loadPreferences(SPreference.REGISTER_GENDER));
						newUser.setHeight(Double.parseDouble(HealthGem
								.getSharedPreferences().loadPreferences(
										SPreference.REGISTER_HEIGHTINCHES)));
						newUser.setKnownHealthProblems(HealthGem
								.getSharedPreferences()
								.loadPreferences(
										SPreference.REGISTER_KNOWNHEALTHPROBLEMS));
						newUser.setName(HealthGem.getSharedPreferences()
								.loadPreferences(SPreference.REGISTER_NAME));
						newUser.setPassword(HealthGem.getSharedPreferences()
								.loadPreferences(SPreference.REGISTER_PASSWORD));
						newUser.setUsername(HealthGem.getSharedPreferences()
								.loadPreferences(SPreference.REGISTER_USERNAME));
						newUser.setWeight(Double.parseDouble(HealthGem
								.getSharedPreferences().loadPreferences(
										SPreference.REGISTER_WEIGHTPOUNDS)));

						if (user != null)
							newUser.setFbAccessToken(HealthGem
									.getSharedPreferences().loadPreferences(
											SPreference.REGISTER_FBACCESSTOKEN));
						userService.registerUser(newUser);
						Date date = new Date();
						Timestamp timestamp = new Timestamp(date.getTime());
						Weight weight = new Weight(timestamp, "", null,
								newUser.getWeight());
						try {
							weightService.add(weight);
						} catch (OutdatedAccessTokenException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						settingDao = new MobileSettingsDaoImpl();
						settingDao.initializeSettings();
						Intent intent2 = new Intent(getApplicationContext(),
								HelpActivity.class);
						intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
								| Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent2);
					} catch (ServiceException e) {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								Toast.makeText(HealthGem.getContext(),
										"No Internet Connection !",
										Toast.LENGTH_LONG).show();
							}
						});
					} catch (UserAlreadyExistsException e) {
						e.printStackTrace();
					}
					return null;
				}

				@Override
				protected void onPostExecute(Void result2) {

					if (progressDialog.isShowing())
						progressDialog.dismiss();
				};
			}.execute();
			return true;
		case R.id.menu_item_save:

			progressDialog.show();
			new AsyncTask<Void, Void, Void>() {
				@Override
				protected Void doInBackground(Void... params) {

					if (user != null) {
						User editedUser = userService.getUser();
						editedUser.setFbAccessToken(HealthGem
								.getSharedPreferences().loadPreferences(
										SPreference.REGISTER_FBACCESSTOKEN));
						try {
							userService.edit(editedUser);
						} catch (ServiceException e) {
							// TODO Auto-generated catch block
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									Toast.makeText(HealthGem.getContext(),
											"No Internet Connection !",
											Toast.LENGTH_LONG).show();
								}
							});
							e.printStackTrace();
						} catch (OutdatedAccessTokenException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					return null;
				}

				@Override
				protected void onPostExecute(Void result2) {

					if (progressDialog.isShowing())
						progressDialog.dismiss();

					onBackPressed();
				};
			}.execute();

			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	private class LoadFbImage extends AsyncTask<String, Void, Bitmap> {
		ImageView bmImage;

		public LoadFbImage(ImageView bmImage) {
			this.bmImage = bmImage;
		}

		@Override
		protected Bitmap doInBackground(String... urls) {
			URL img_value = null;
			try {
				img_value = new URL("https://graph.facebook.com/" + urls[0]
						+ "/picture?type=large");
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Bitmap mIcon1 = null;
			try {
				mIcon1 = BitmapFactory.decodeStream(img_value.openConnection()
						.getInputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return mIcon1;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			bmImage.setImageBitmap(result);
			String dir = ImageHandler.saveImage(result);
			HealthGem.getSharedPreferences().savePreferences(
					SPreference.PROFILE_PICTURE, dir);
			Log.e("FB LOGIN",
					HealthGem.getSharedPreferences().loadPreferences(
							SPreference.PROFILE_PICTURE));
		}
	}
}
