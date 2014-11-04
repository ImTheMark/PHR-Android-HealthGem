package com.example.phr;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.mobile.models.BloodPressure;
import com.example.phr.mobile.models.PHRImage;
import com.example.phr.mobile.models.PHRImageType;
import com.example.phr.service.BloodPressureTrackerService;
import com.example.phr.serviceimpl.BloodPressureTrackerServiceImpl;

public class BloodPressurePostActivity extends Activity {

	private ImageButton mBtnAddPhoto;
	private TextView textViewBloodPressureCalendar;
	private TextView textViewBloodPressureClock;
	private TextView textViewbloodpressureStatus;
	private NumberPicker systolicPicker;
	private NumberPicker diastolicPicker;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();

		StrictMode.setThreadPolicy(policy);
		setContentView(R.layout.activity_bloodpressure_post);
		setTitle("Blood Pressure");

		mBtnAddPhoto = (ImageButton) findViewById(R.id.btnAddImageBloodPressure);
		mBtnAddPhoto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
				startActivity(intent);
			}
		});

		systolicPicker = (NumberPicker) findViewById(R.id.systolicPicker);
		diastolicPicker = (NumberPicker) findViewById(R.id.diastolicPicker);
		textViewbloodpressureStatus = (TextView) findViewById(R.id.textViewbloodpressureStatus);
		textViewBloodPressureCalendar = (TextView) findViewById(R.id.textViewBloodPressureCalendar);
		textViewBloodPressureClock = (TextView) findViewById(R.id.textViewBloodPressureClock);

		systolicPicker.setCurrent(100);
		diastolicPicker.setCurrent(150);
		DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy",
				Locale.ENGLISH);
		Calendar calobj = Calendar.getInstance();
		textViewBloodPressureCalendar.setText(dateFormat.format(calobj
				.getTime()));
		dateFormat = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
		textViewBloodPressureClock.setText(dateFormat.format(calobj.getTime()));
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
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	private void addBloodPressureToDatabase() throws ServiceException,
			OutdatedAccessTokenException {

		try {
			DateFormat fmt = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss",
					Locale.ENGLISH);
			Date date = fmt.parse(textViewBloodPressureCalendar.getText()
					.toString()
					+ " "
					+ textViewBloodPressureClock.getText().toString());
			Timestamp timestamp = new Timestamp(date.getTime());
			System.out.println(timestamp);
			PHRImage testImage = new PHRImage("testImage", PHRImageType.IMAGE);
			BloodPressure bp = new BloodPressure(timestamp,
					textViewbloodpressureStatus.getText().toString(),
					testImage, systolicPicker.getCurrent(),
					diastolicPicker.getCurrent());

			// WEB SERVER INSERT
			BloodPressureTrackerService bpService = new BloodPressureTrackerServiceImpl();
			bpService.add(bp);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
