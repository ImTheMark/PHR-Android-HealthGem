package com.example.phr;

import com.example.phr.R;
import com.example.phr.enums.TrackerInputType;
import com.example.phr.mobile.models.BloodPressure;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

public class NewStatusActivity extends Activity {
	
	ImageButton mBtnTagFriend;
	ImageButton mBtnCheckinLocation;
	ImageButton mBtnAddPhoto;
	ImageButton mBtnAddActions;
	NumberPicker systolicPicker;
	NumberPicker diastolicPicker;
	TextView txtSystolic;
	TextView txtDiastolic;
	EditText bpStatus;
	final Context context = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("Post a Status");
		setContentView(R.layout.activity_new_status);
		/*
		mBtnTagFriend = (ImageButton)findViewById(R.id.btnTagFriend);
		mBtnTagFriend.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						TagFriendActivity.class);
				startActivity(intent);
			}
		});
		
		mBtnCheckinLocation = (ImageButton)findViewById(R.id.btnCheckinLocation);
		mBtnCheckinLocation.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						CheckinLocationActivity.class);
				startActivity(intent);
			}
		});
		*/
		txtSystolic= (TextView) findViewById(R.id.systolic);
		txtDiastolic= (TextView) findViewById(R.id.diastolic);
		bpStatus= (EditText) findViewById(R.id.txtBPStatus);
		mBtnAddPhoto = (ImageButton)findViewById(R.id.btnAddPhoto);
		mBtnAddPhoto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
				startActivity(intent);
			}
		});
		
		mBtnAddActions = (ImageButton)findViewById(R.id.btnAddActions);
		mBtnAddActions.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				 Intent intent=new Intent(NewStatusActivity.this,AddActionActivity.class);  
	                startActivityForResult(intent, 2);
			}
		});
	}

	 @Override  
     protected void onActivityResult(int requestCode, int resultCode, Intent data)  
     {  
               super.onActivityResult(requestCode, resultCode, data);  
                   
                // check if the request code is same as what is passed  here it is 2  
                 if(requestCode==2)  
                       {  
                          String message=data.getStringExtra("itemValue");                       
                          Log.e("itemValue = ", message);
                          if(message.equals(TrackerInputType.BLOOD_PRESSURE))
                        	  callBloodPressureInput();
                       }  
   
     }  
	
	private void callBloodPressureInput() {
		// TODO Auto-generated method stub
		//BloodPressure newEntry = new BloodPressure();
		LayoutInflater layoutInflater = LayoutInflater.from(context);

		View bpView = layoutInflater.inflate(R.layout.item_bloodpressure_input, null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		alertDialogBuilder.setView(bpView);
		systolicPicker = (NumberPicker) bpView.findViewById(R.id.systolicPicker);
		diastolicPicker = (NumberPicker) bpView.findViewById(R.id.diastolicPicker);
		systolicPicker.setCurrent(100);
		diastolicPicker.setCurrent(150);
		alertDialogBuilder
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// get user input and set it to result
								ScrollView bpTemplate= (ScrollView) findViewById(R.id.bloodpressure_template);
								bpTemplate.setVisibility(View.VISIBLE);
								Log.e("in","kkdks");
								Log.e("systolic",Integer.toString(systolicPicker.getCurrent()));
								txtSystolic.setText(Integer.toString(systolicPicker.getCurrent()));
								txtDiastolic.setText(Integer.toString(diastolicPicker.getCurrent()));
								
							}
						})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,	int id) {
								dialog.cancel();
							}
						});

		// create an alert dialog
		AlertDialog alertD = alertDialogBuilder.create();

		alertD.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_status_post, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_item_status_post:
			onBackPressed();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

}
