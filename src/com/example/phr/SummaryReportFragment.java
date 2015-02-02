package com.example.phr;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phr.application.HealthGem;
import com.example.phr.enums.TrackerInputType;
import com.example.phr.exceptions.DataAccessException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.mobile.dao.MobileActivityTrackerDao;
import com.example.phr.mobile.dao.MobileFoodTrackerDao;
import com.example.phr.mobile.dao.MobileSettingsDao;
import com.example.phr.mobile.daoimpl.MobileActivityTrackerDaoImpl;
import com.example.phr.mobile.daoimpl.MobileFoodTrackerDaoImpl;
import com.example.phr.mobile.daoimpl.MobileSettingsDaoImpl;
import com.example.phr.mobile.models.BloodPressure;
import com.example.phr.mobile.models.BloodSugar;
import com.example.phr.mobile.models.CalorieTrackerEntry;
import com.example.phr.mobile.models.GroupedActivity;
import com.example.phr.mobile.models.GroupedFood;
import com.example.phr.mobile.models.User;
import com.example.phr.mobile.models.Weight;
import com.example.phr.service.BloodPressureTrackerService;
import com.example.phr.service.BloodSugarTrackerService;
import com.example.phr.service.UserService;
import com.example.phr.service.WeightTrackerService;
import com.example.phr.serviceimpl.BloodPressureTrackerServiceImpl;
import com.example.phr.serviceimpl.BloodSugarTrackerServiceImpl;
import com.example.phr.serviceimpl.UserServiceImpl;
import com.example.phr.serviceimpl.WeightTrackerServiceImpl;
import com.example.phr.tools.DateTimeParser;

public class SummaryReportFragment extends Fragment {

	ProgressBar cProgress;

	Button mBtnRetrieve;
	Button mBtnWrite;
	LinearLayout postHolder;
	TextView txtBsDate;
	TextView txtBsTime;
	TextView txtBsSugarLvl;
	TextView txtBsType;
	ImageView imgBs;
	TextView txtBpDate;
	TextView txtBpTime;
	TextView txtBpSystolic;
	TextView txtBpDiastolic;
	ImageView imgBp;
	TextView txtWeight;
	TextView txtBmi;
	TextView txtWeightUnit;
	TextView txtWeightStatus;
	TextView txtWeightTime;
	TextView txtWeightDate;
	ImageView imgWeight;
	BloodSugar bs;
	BloodPressure bp;
	Weight weight;
	LinearLayout weightHomeRecordHolder;
	LinearLayout bsHomeRecordHolder;
	LinearLayout bsHomeRecordHolder2;
	LinearLayout bsHomeRecordHolderNull;
	LinearLayout bsHomeStatus;
	LinearLayout bpHomeRecordHolder;
	LinearLayout bpHomeRecordHolder2;
	LinearLayout bpHomeRecordHolderNull;
	LinearLayout bpHomeStatus;
	LinearLayout calorieRecordHolder;
	LinearLayout dailyContainer;
	LinearLayout weightStatus;
	TextView txtBigTotalCalRequire;
	TextView txtSmallTotalCalRequire;
	TextView txtTotalFoodCal;
	TextView txtTotalActivityCal;
	TextView txtTotalCal;
	TextView txtUserName;
	GroupedFood groupedFood;
	GroupedActivity groupedActivity;
	DateFormat dateFormat;
	DateFormat timeFormat;
	DateFormat fmt;
	Calendar calobj;
	Timestamp timestamp;
	MobileSettingsDao setting;
	XYMultipleSeriesDataset calorieListDataset;
	XYMultipleSeriesRenderer calorieListMultiRenderer;
	View calorieChart;
	View rootView;
	List<CalorieTrackerEntry> calorieTrackerList;

	UserService userService;
	User user;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_summary_report,
				container, false);
		setting = new MobileSettingsDaoImpl();
		DecimalFormat df = new DecimalFormat("#.00");

		userService = new UserServiceImpl();
		user = userService.getUser();

		txtUserName = (TextView) rootView.findViewById(R.id.txtUserName);
		txtUserName.setText(user.getName());

		WeightTrackerService weightService = new WeightTrackerServiceImpl();
		try {
			weight = weightService.getLatest();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			Toast.makeText(HealthGem.getContext(), "No Internet Connection !",
					Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}

		// calorie
		cProgress = (ProgressBar) rootView.findViewById(R.id.progressBar2);
		Drawable draw = getResources()
				.getDrawable(R.drawable.customprogressbar);
		Drawable drawRed = getResources().getDrawable(
				R.drawable.customprogressbarred);

		txtBigTotalCalRequire = (TextView) rootView
				.findViewById(R.id.txtHomeCalRequire);
		txtSmallTotalCalRequire = (TextView) rootView
				.findViewById(R.id.txtHomeTotalReqCal);
		txtTotalFoodCal = (TextView) rootView
				.findViewById(R.id.txtTotalFoodCal);
		txtTotalActivityCal = (TextView) rootView
				.findViewById(R.id.txtTotalActivityCal);
		txtTotalCal = (TextView) rootView.findViewById(R.id.txtHomeTotalCal);

		// current date
		dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
		timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
		fmt = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss", Locale.ENGLISH);
		calobj = Calendar.getInstance();
		Date date = null;
		try {
			date = fmt.parse(dateFormat.format(calobj.getTime()) + " "
					+ timeFormat.format(calobj.getTime()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		timestamp = new Timestamp(date.getTime());

		txtBigTotalCalRequire.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(),
						CalorieTrackerActivity.class);
				startActivity(i);
			}
		});

		MobileFoodTrackerDao foodDao = new MobileFoodTrackerDaoImpl();
		MobileActivityTrackerDao activityDao = new MobileActivityTrackerDaoImpl();
		try {
			groupedFood = foodDao.getFromDateCalculated(timestamp);
			groupedActivity = activityDao.getFromDateCalculated(timestamp);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// set calorie

		txtTotalFoodCal.setText(df.format(groupedFood.getCalorie()));
		txtTotalActivityCal.setText(df.format(groupedActivity
				.getCalorieBurned()));
		double total = groupedFood.getCalorie()
				- groupedActivity.getCalorieBurned();
		txtTotalCal.setText(df.format(total));
		double bmr = 1500; // compute
		// Women: BMR = 655 + ( 4.35 x weight in pounds ) + ( 4.7 x height in
		// inches ) - ( 4.7 x age in years )
		// Men: BMR = 66 + ( 6.23 x weight in pounds ) + ( 12.7 x height in
		// inches ) - ( 6.8 x age in year )

		Timestamp bdaytimestamp = user.getDateOfBirth();

		int age = Integer.parseInt(DateTimeParser.getYear(timestamp))
				- Integer.parseInt(DateTimeParser.getYear(bdaytimestamp));
		String gender = user.getGender();
		// String gender = "F";
		Log.e("bmr", weight.getWeightInPounds() + "");
		Log.e("bmr2", user.getHeight() + "");
		if (gender.equals("F"))
			bmr = 655 + (4.35 * weight.getWeightInPounds())
					+ (4.7 * user.getHeight()) - (4.7 * age);
		else if (gender.equals("M"))
			bmr = 66 + (6.23 * weight.getWeightInPounds())
					+ (12.7 * user.getHeight()) - (6.8 * age);

		txtBigTotalCalRequire.setText(df.format(bmr));
		txtSmallTotalCalRequire.setText(df.format(bmr));

		int cProgressStatus = (int) Math.round((total / bmr) * 100); // compute
		Log.e("progress status", cProgressStatus + "");
		cProgress.setProgressDrawable(draw);
		if (cProgressStatus > 100) {
			cProgressStatus = 100;
			cProgress.setProgressDrawable(drawRed);
		}
		cProgress.setProgress(cProgressStatus);
		cProgress.setMax(100);

		// blood pressure
		txtBpDate = (TextView) rootView.findViewById(R.id.txtHomeBpDate);
		txtBpTime = (TextView) rootView.findViewById(R.id.txtHomeBpTime);
		txtBpDiastolic = (TextView) rootView
				.findViewById(R.id.txtHomeDiastolic);
		txtBpSystolic = (TextView) rootView
				.findViewById(R.id.txtHomeSystolicNum);
		bpHomeStatus = (LinearLayout) rootView
				.findViewById(R.id.BpLinearLayoutStatus);
		bpHomeRecordHolder = (LinearLayout) rootView
				.findViewById(R.id.bpHomeRecordHolder);
		bpHomeRecordHolder2 = (LinearLayout) rootView
				.findViewById(R.id.bpHomeRecordHolder2);
		bpHomeRecordHolderNull = (LinearLayout) rootView
				.findViewById(R.id.bpHomeRecordHolderNull);

		BloodPressureTrackerService bpService = new BloodPressureTrackerServiceImpl();
		try {
			bp = bpService.getLatest();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			Toast.makeText(HealthGem.getContext(), "No Internet Connection !",
					Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
		if (bp != null) {
			Log.e("home bp", "not null");
			bpHomeRecordHolderNull.setVisibility(View.GONE);
			bpHomeRecordHolder2.setVisibility(View.VISIBLE);
			txtBpDate.setText(String.valueOf(DateTimeParser.getDate(bp
					.getTimestamp())));
			txtBpTime.setText(String.valueOf(DateTimeParser.getTime(bp
					.getTimestamp())));
			txtBpDiastolic.setText(String.valueOf(bp.getDiastolic()));
			Log.e("dia", "pass");
			txtBpSystolic.setText(String.valueOf((bp.getSystolic())));
			Log.e("sys", "pass");

			if (bp.getSystolic() > 90 && bp.getSystolic() < 120
					&& bp.getDiastolic() > 60 && bp.getDiastolic() < 80)
				bpHomeStatus.setBackgroundColor(Color.GREEN);
			else
				bpHomeStatus.setBackgroundColor(Color.RED);
		}

		bpHomeRecordHolder.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), NewStatusActivity.class);
				i.putExtra("tracker", TrackerInputType.BLOOD_PRESSURE);
				startActivity(i);
			}
		});
		bpHomeRecordHolderNull.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), NewStatusActivity.class);
				i.putExtra("tracker", TrackerInputType.BLOOD_PRESSURE);
				startActivity(i);
			}
		});

		// blood sugar
		txtBsDate = (TextView) rootView.findViewById(R.id.txtHomeBsDate);
		txtBsTime = (TextView) rootView.findViewById(R.id.txtHomeBsTime);
		txtBsSugarLvl = (TextView) rootView.findViewById(R.id.txtHomeBsNumber);
		txtBsType = (TextView) rootView.findViewById(R.id.txtHomeBsType);
		bsHomeRecordHolder = (LinearLayout) rootView
				.findViewById(R.id.bsHomeRecordHolder);
		bsHomeRecordHolder2 = (LinearLayout) rootView
				.findViewById(R.id.bsHomeRecord2);
		bsHomeStatus = (LinearLayout) rootView
				.findViewById(R.id.bsLinearLayoutStatus);
		bsHomeRecordHolderNull = (LinearLayout) rootView
				.findViewById(R.id.bsHomeRecordHolderNull);

		BloodSugarTrackerService bsService = new BloodSugarTrackerServiceImpl();
		try {
			bs = bsService.getLatest();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			Toast.makeText(HealthGem.getContext(), "No Internet Connection !",
					Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
		if (bs != null) {
			Log.e("home bs", "not null");
			bsHomeRecordHolderNull.setVisibility(View.GONE);
			bsHomeRecordHolder2.setVisibility(View.VISIBLE);
			txtBsDate.setText(String.valueOf(DateTimeParser.getDate(bs
					.getTimestamp())));
			txtBsTime.setText(String.valueOf(DateTimeParser.getTime(bs
					.getTimestamp())));
			txtBsSugarLvl.setText(String.valueOf(bs.getBloodSugar()));
			txtBsType.setText(bs.getType());
			if (bs.getType().equals("Before meal") && bs.getBloodSugar() >= 4.0
					&& bs.getBloodSugar() <= 5.9)
				bsHomeStatus.setBackgroundColor(Color.GREEN);
			else if (bs.getType().equals("After meal")
					&& bs.getBloodSugar() < 7.8)
				bsHomeStatus.setBackgroundColor(Color.GREEN);
			else
				bsHomeStatus.setBackgroundColor(Color.RED);
		}

		bsHomeRecordHolder.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), NewStatusActivity.class);
				i.putExtra("tracker", TrackerInputType.BLOOD_SUGAR);
				startActivity(i);
			}
		});

		bsHomeRecordHolderNull.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), NewStatusActivity.class);
				i.putExtra("tracker", TrackerInputType.BLOOD_SUGAR);
				startActivity(i);
			}
		});

		// weight

		txtWeightTime = (TextView) rootView.findViewById(R.id.txtBMIHomeTime);
		txtWeightDate = (TextView) rootView.findViewById(R.id.txtBMIHomeDate);
		txtWeight = (TextView) rootView.findViewById(R.id.txtWeightHome);
		txtBmi = (TextView) rootView.findViewById(R.id.txtBMIHome);
		txtWeightUnit = (TextView) rootView
				.findViewById(R.id.txtWeightHomeUnit);
		weightStatus = (LinearLayout) rootView
				.findViewById(R.id.weightLinearLayoutStatus);

		weightHomeRecordHolder = (LinearLayout) rootView
				.findViewById(R.id.weightRecordHolder);

		if (weight != null) {
			Log.e("home weight", weight.getWeightInPounds() + "");
			if (setting.isWeightSettingInPounds()) {
				txtWeight.setText(df.format(weight.getWeightInPounds()));
				txtWeightUnit.setText("lbs");
			} else {
				txtWeight.setText(df.format(weight.getWeightInKilograms()));
				txtWeightUnit.setText("kgs");
			}
			txtWeightDate.setText(String.valueOf(DateTimeParser.getDate(weight
					.getTimestamp())));
			txtWeightTime.setText(String.valueOf(DateTimeParser.getTime(weight
					.getTimestamp())));

			double heightInMeter = user.getHeightInMeter();

			double bmi = weight.getWeightInKilograms()
					/ (heightInMeter * heightInMeter);

			String formattedBmi = df.format(bmi);
			txtBmi.setText(formattedBmi);

			String txtweightStatus;
			if (bmi < 18.5)
				txtweightStatus = "Underweight";
			else if (bmi >= 18.5 && bmi < 24.9)
				txtweightStatus = "Normal weight";
			else if (bmi >= 25 && bmi < 29.9)
				txtweightStatus = "Overweight";
			else
				txtweightStatus = "Obesity";
			// txtWeightStatus.setText(weightStatus);

			if (txtweightStatus.equals("Overweight")
					|| txtweightStatus.equals("Obesity"))
				weightStatus.setBackgroundColor(Color.RED);

			else if (txtweightStatus.equals("Normal weight"))
				weightStatus.setBackgroundColor(Color.GREEN);
			else if (txtweightStatus.equals("Underweight"))
				weightStatus.setBackgroundColor(Color.YELLOW);

		}
		weightHomeRecordHolder.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), NewStatusActivity.class);
				i.putExtra("tracker", TrackerInputType.WEIGHT);
				startActivity(i);
			}
		});

		postHolder = (LinearLayout) rootView
				.findViewById(R.id.journalTabsPlaceholder);
		postHolder.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),
						NewStatusActivity.class);
				startActivity(intent);
			}
		});
		return rootView;
	}
}
