package com.example.phr;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.example.phr.R;
import com.example.phr.enums.TrackerInputType;
import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.mobile.models.BloodPressure;
import com.example.phr.service.BloodPressureService;
import com.example.phr.serviceimpl.BloodPressureServiceImpl;

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
import android.widget.Spinner;
import android.widget.TextView;

public class NewStatusActivity extends Activity {
	
	ImageButton mBtnTagFriend;
	ImageButton mBtnCheckinLocation;
	ImageButton mBtnAddPhoto;
	ImageButton mBtnAddActions;
	ImageButton mBtnFb;
	NumberPicker systolicPicker;
	NumberPicker diastolicPicker;
	NumberPicker sugarPicker;
	Spinner sugarTypeSpinner;
	TextView txtSystolic;
	TextView txtDiastolic;
	TextView txtSugar;
	TextView sugarType;
	EditText bpStatus;
	EditText bsStatus;
	ScrollView bpTemplate;
	ScrollView bsTemplate;
	ScrollView notesTemplate;
	ScrollView weightTemplate;
	String currentTracker;
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
		currentTracker =TrackerInputType.NOTES;
		
		bsTemplate= (ScrollView) findViewById(R.id.bloodsugar_template);
		bpTemplate= (ScrollView) findViewById(R.id.bloodpressure_template);
		notesTemplate= (ScrollView) findViewById(R.id.notes_template);
		weightTemplate= (ScrollView) findViewById(R.id.weight_template);
		
		txtSystolic= (TextView) findViewById(R.id.systolic);
		txtDiastolic= (TextView) findViewById(R.id.diastolic);
		bpStatus= (EditText) findViewById(R.id.txtBPStatus);
		
		txtSugar= (TextView) findViewById(R.id.sugar);
		bsStatus= (EditText) findViewById(R.id.txtBSStatus);
		sugarType= (TextView) findViewById(R.id.txtSugarType);
		
		
		mBtnAddPhoto = (ImageButton)findViewById(R.id.btnAddPhoto);
		mBtnAddPhoto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
				startActivity(intent);
			}
		});
		mBtnFb = (ImageButton)findViewById(R.id.btnFb);
		mBtnFb.setTag(new Boolean(false)); // wasn't clicked
		mBtnFb.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if( ((Boolean)mBtnFb.getTag())==false ){
						mBtnFb.setImageResource(R.drawable.activitynewstatus_fb_click);
						mBtnFb.setTag(new Boolean(true));
		          }
				else{
						mBtnFb.setImageResource(R.drawable.activitynewstatus_fb);
						mBtnFb.setTag(new Boolean(false));
				}
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
                          String item = data.getStringExtra("itemValue");                       
                          Log.e("itemValue = ", item);
                          if(item.equals(TrackerInputType.BLOOD_PRESSURE)){
                        	  currentTracker = TrackerInputType.BLOOD_PRESSURE;
                        	  callBloodPressureInput();
                          }
                          else if(item.equals(TrackerInputType.BLOOD_SUGAR)){
                        	  currentTracker = TrackerInputType.BLOOD_SUGAR;
                        	  callBloodSugarInput();
                          }
                          else if(item.equals(TrackerInputType.NOTES)){
                        	  currentTracker = TrackerInputType.NOTES;
                        	  callNotesInput();
                          }
                       }  
   
     }
	 
	 
	
	private void callNotesInput() {
		// TODO Auto-generated method stub
		setAllTemplateGone();
		notesTemplate.setVisibility(View.VISIBLE);
	}

	private void callWeightInput() {
		// TODO Auto-generated method stub
		setAllTemplateGone();
		weightTemplate.setVisibility(View.VISIBLE);
	}
	
	private void callBloodSugarInput() {
		// TODO Auto-generated method stub
		LayoutInflater layoutInflater = LayoutInflater.from(context);

		View bsView = layoutInflater.inflate(R.layout.item_bloodsugar_input, null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		alertDialogBuilder.setView(bsView);
		sugarPicker = (NumberPicker) bsView.findViewById(R.id.sugarPicker);
		sugarTypeSpinner= (Spinner) bsView.findViewById(R.id.sugarTypeSpinner);
		sugarPicker.setCurrent(120);
		alertDialogBuilder
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
	
								setAllTemplateGone();
								bsTemplate.setVisibility(View.VISIBLE);
								Log.e("sugar",Integer.toString(sugarPicker.getCurrent()));
								txtSugar.setText(Integer.toString(sugarPicker.getCurrent()));
								sugarType.setText(String.valueOf(sugarTypeSpinner.getSelectedItem()));
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

	private void callBloodPressureInput() {
		// TODO Auto-generated method stub
		
		LayoutInflater layoutInflater = LayoutInflater.from(context);

		View bpView = layoutInflater.inflate(R.layout.item_bloodpressure_input, null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		alertDialogBuilder.setView(bpView);
		systolicPicker = (NumberPicker) bpView.findViewById(R.id.systolicPicker);
		diastolicPicker = (NumberPicker) bpView.findViewById(R.id.diastolicPicker);
		systolicPicker.setCurrent(120);
		diastolicPicker.setCurrent(80);
		alertDialogBuilder
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
	
								setAllTemplateGone();
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
	
	

	private void addBloodPressureToDatabase() throws ServiceException,
			OutdatedAccessTokenException {

		try {
			DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy",
					Locale.ENGLISH);
			DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
			DateFormat fmt = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss",
					Locale.ENGLISH);
			Calendar calobj = Calendar.getInstance();
			Date date = fmt.parse(dateFormat.format(calobj
					.getTime())
					+ " "
					+ timeFormat.format(calobj.getTime()));
			Timestamp timestamp = new Timestamp(date.getTime());
			System.out.println(timestamp);
			Log.e(bpStatus.getText().toString(),Integer.toString(systolicPicker.getCurrent()));
			BloodPressure bp = new BloodPressure(timestamp,
					bpStatus.getText().toString(),
					"test-image", systolicPicker.getCurrent(),
					diastolicPicker.getCurrent());
			Log.e("added","pp");
			// WEB SERVER INSERT
			//BloodPressureService bpService = new BloodPressureServiceImpl();
			//bpService.add(bp);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void addBloodSugarToDatabase() throws ServiceException,
			OutdatedAccessTokenException {

		try {
			DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy",
					Locale.ENGLISH);
			DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
			DateFormat fmt = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss",
					Locale.ENGLISH);
			Calendar calobj = Calendar.getInstance();
			Date date = fmt.parse(dateFormat.format(calobj
					.getTime())
					+ " "
					+ timeFormat.format(calobj.getTime()));
			Timestamp timestamp = new Timestamp(date.getTime());
			System.out.println(timestamp);
		
		
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

}
	
	private void setAllTemplateGone()
	{
		bsTemplate.setVisibility(View.GONE);
		bpTemplate.setVisibility(View.GONE);
		notesTemplate.setVisibility(View.GONE);
		weightTemplate.setVisibility(View.GONE);
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
			 if(currentTracker.equals(TrackerInputType.BLOOD_PRESSURE)){
				try {
					addBloodPressureToDatabase();
				} catch (ServiceException e) {
					// output error message or something
					System.out.println(e.getMessage());
				} catch (OutdatedAccessTokenException e) {
					// Message - > Log user out
					e.printStackTrace();
				}
				// onBackPressed();
				Intent intent = new Intent(getApplicationContext(),
						BloodPressureTrackerActivity.class);
				startActivity(intent);
			 }
			 
			 else if(currentTracker.equals(TrackerInputType.BLOOD_SUGAR)){
					try {
						addBloodSugarToDatabase();
					} catch (ServiceException e) {
						// output error message or something
						System.out.println(e.getMessage());
					} catch (OutdatedAccessTokenException e) {
						// Message - > Log user out
						e.printStackTrace();
					}
					// onBackPressed();
					Intent intent = new Intent(getApplicationContext(),
							BloodSugarTrackerActivity.class);
					startActivity(intent);
				 }
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}
	
	

}
