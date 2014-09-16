package com.example.phr;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.mobile.daoimpl.MobileBloodPressureDaoImpl;
import com.example.phr.mobile.models.MobileBloodPressure;
import com.example.phr.service.BloodPressureService;
import com.example.phr.serviceimpl.BloodPressureServiceImpl;
import com.example.phr.tools.DateTimeParser;

public class BloodPressurePostActivity extends Activity {

	private ImageButton mBtnAddPhoto;
	private TextView textViewBloodPressureCalendar;
	private TextView textViewBloodPressureClock;
	private TextView textViewbloodpressureStatus;
	private NumberPicker systolicPicker;
	private NumberPicker diastolicPicker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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

		DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
		Calendar calobj = Calendar.getInstance();
		textViewBloodPressureCalendar.setText(dateFormat.format(calobj
				.getTime()));
		dateFormat = new SimpleDateFormat("HH:mm:ss");
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
			onBackPressed();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	private void addBloodPressureToDatabase() throws ServiceException,
			OutdatedAccessTokenException {

		try {
			DateFormat fmt = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");
			System.out.println(textViewBloodPressureCalendar.getText());
			System.out.println(textViewBloodPressureClock.getText());
			java.util.Date dateAdded = fmt.parse(textViewBloodPressureCalendar
					.getText().toString()
					+ " "
					+ textViewBloodPressureClock.getText().toString());

			MobileBloodPressure bp = new MobileBloodPressure(
					DateTimeParser.getSQLDate(dateAdded),
					textViewbloodpressureStatus.getText().toString(),
					"test-image", systolicPicker.getCurrent(),
					diastolicPicker.getCurrent());

			// WEB SERVER INSERT
			BloodPressureService bpService = new BloodPressureServiceImpl();
			bpService.addBloodPressure(bp);

			// LOCAL DB INSERT
			MobileBloodPressureDaoImpl bpDaoImpl = new MobileBloodPressureDaoImpl();
			bpDaoImpl.add(bp);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
