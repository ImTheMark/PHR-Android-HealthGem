package com.example.phr;

import com.example.phr.adapter.UnverifiedStatusAdapter;
import com.example.phr.application.HealthGem;
import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.service.VerificationService;
import com.example.phr.serviceimpl.VerificationServiceImpl;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class VerificationActivity extends Activity {
	
	ListView vList;
	VerificationService vService;
	
	 @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_verification);
		vList = (ListView) findViewById(R.id.verification_listview);
		vService = new VerificationServiceImpl();
		
		try {
			UnverifiedStatusAdapter adapter = new UnverifiedStatusAdapter(HealthGem.getContext(), vService.getAll());
			vList.setAdapter(adapter);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OutdatedAccessTokenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
