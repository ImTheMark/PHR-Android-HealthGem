package com.example.phr;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ListView;
import android.widget.Toast;

import com.example.phr.adapter.UnverifiedStatusAdapter;
import com.example.phr.application.HealthGem;
import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.service.VerificationService;
import com.example.phr.serviceimpl.VerificationServiceImpl;

@SuppressLint("NewApi")
public class VerificationActivity extends Activity {

	ListView vList;
	VerificationService vService;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();

		StrictMode.setThreadPolicy(policy);
		setContentView(R.layout.activity_verification);
		vList = (ListView) findViewById(R.id.verification_listview);
		vService = new VerificationServiceImpl();

		try {
			UnverifiedStatusAdapter adapter = new UnverifiedStatusAdapter(
					HealthGem.getContext(), vService.getAll());
			vList.setAdapter(adapter);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			this.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(HealthGem.getContext(),
							"Hello, Pls connect to the wifi!",
							Toast.LENGTH_SHORT).show();
				}
			});
			e.printStackTrace();
		} catch (OutdatedAccessTokenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
