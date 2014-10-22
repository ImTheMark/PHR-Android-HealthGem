package com.example.phr;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.phr.application.HealthGem;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.LoginButton.UserInfoChangedCallback;

public class RegisterFBLoginActivity extends Activity {

	private LoginButton btnLogin;
	private UiLifecycleHelper uiHelper;
	private static final List<String> PERMISSIONS = Arrays.asList(
			"user_birthday", "email", "read_stream", "publish_actions");
	private TextView userName;
	public static GraphUser user;
	public static String userID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

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
		btnLogin.setUserInfoChangedCallback(new UserInfoChangedCallback() {
			@Override
			public void onUserInfoFetched(GraphUser user) {
				RegisterFBLoginActivity.this.user = user;
				if (user != null) {
					userName.setText("Hello, " + user.getName());
					userID = user.getUsername();

					Session s = Session.getActiveSession();
/*					try {
						facebookRequest(user.getId(), s.getAccessToken());
					} catch (ClientProtocolException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/

					// LoadFbProfilePicture(profilePictureView).execute(user.getId());
				} else {
					Log.e("onUserInfoFetched", "user is null");
					userName.setText("You are not logged right now");
				}
			}
		});

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
							"publish_actions"));
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
	}

	@Override
	public void onSaveInstanceState(Bundle savedState) {
		super.onSaveInstanceState(savedState);
		uiHelper.onSaveInstanceState(savedState);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_registration_next, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_item_next:
			Intent intent = new Intent(getApplicationContext(),
					MainActivity.class);
			HealthGem.getSharedPreferences().registerUser();
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	// Request to our java server.
	private void facebookRequest(String userId, String token)
			throws ClientProtocolException, IOException {
		String address = "http://10.0.2.2:8080/JSONTestServer/FacebookRequestServlet";
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(address);

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		nameValuePairs.add(new BasicNameValuePair("userId", userId));
		nameValuePairs.add(new BasicNameValuePair("accessToken", token));
		post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

		HttpResponse response = client.execute(post);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		response.getEntity().writeTo(out);
		out.close();
		String responseString = out.toString();
		System.out.println(responseString);
	}

	private class LoadFbProfilePicture extends AsyncTask<String, Void, Bitmap> {
		ImageView bmImage;

		public LoadFbProfilePicture(ImageView bmImage) {
			this.bmImage = bmImage;
		}

		@Override
		protected Bitmap doInBackground(String... urls) {
			URL img_value = null;
			Log.e("LoginAct", urls[0]);
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
		}
	}
}
