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

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

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
	Button mBtnStatus;
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
	ImageView imgWeight;
	BloodSugar bs;
	BloodPressure bp;
	Weight weight;
	LinearLayout weightHomeRecordHolder;
	LinearLayout bsHomeRecordHolder;
	LinearLayout bsHomeRecordHolderNull;
	LinearLayout bpHomeRecordHolder;
	LinearLayout bpHomeRecordHolderNull;
	TextView txtBigTotalCalRequire;
	TextView txtSmallTotalCalRequire;
	TextView txtTotalFoodCal;
	TextView txtTotalActivityCal;
	TextView txtTotalCal;
	GroupedFood groupedFood;
	GroupedActivity groupedActivity;
	DateFormat dateFormat;
	DateFormat timeFormat;
	DateFormat fmt;
	Calendar calobj;
	Timestamp timestamp;
	MobileSettingsDao setting;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_summary_report,
				container, false);
		setting = new MobileSettingsDaoImpl();
		DecimalFormat df = new DecimalFormat("#.00");

		// blood sugar
		txtBsDate = (TextView) rootView.findViewById(R.id.txtHomeBsDate);
		txtBsTime = (TextView) rootView.findViewById(R.id.txtHomeBsTime);
		txtBsSugarLvl = (TextView) rootView.findViewById(R.id.txtHomeBsNumber);
		txtBsType = (TextView) rootView.findViewById(R.id.txtHomeBsType);
		imgBs = (ImageView) rootView.findViewById(R.id.imgHomeBs);
		bsHomeRecordHolder = (LinearLayout) rootView
				.findViewById(R.id.bsHomeRecordHolder);
		bsHomeRecordHolderNull = (LinearLayout) rootView
				.findViewById(R.id.bsHomeRecordHolderNull);

		BloodSugarTrackerService bsService = new BloodSugarTrackerServiceImpl();
		try {
			bs = bsService.getLatest();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (bs != null) {
			Log.e("home bs", "not null");
			bsHomeRecordHolderNull.setVisibility(View.GONE);
			bsHomeRecordHolder.setVisibility(View.VISIBLE);
			txtBsDate.setText(String.valueOf(DateTimeParser.getDate(bs
					.getTimestamp())));
			txtBsTime.setText(String.valueOf(DateTimeParser.getTime(bs
					.getTimestamp())));
			txtBsSugarLvl.setText(String.valueOf(bs.getBloodSugar()));
			txtBsType.setText(bs.getType());
			if (bs.getType().equals("Before meal") && bs.getBloodSugar() >= 4.0
					&& bs.getBloodSugar() <= 5.9)
				imgBs.setImageResource(R.drawable.bloodsugar_normal);
			else if (bs.getType().equals("After meal")
					&& bs.getBloodSugar() < 7.8)
				imgBs.setImageResource(R.drawable.bloodsugar_normal);
			else
				imgBs.setImageResource(R.drawable.bloodsugar_warning);
		}

		bsHomeRecordHolderNull.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), NewStatusActivity.class);
				i.putExtra("tracker", TrackerInputType.BLOOD_SUGAR);
				startActivity(i);
			}
		});
		bsHomeRecordHolder.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), NewStatusActivity.class);
				i.putExtra("tracker", TrackerInputType.BLOOD_SUGAR);
				startActivity(i);
			}
		});
		// blood pressure
		txtBpDate = (TextView) rootView.findViewById(R.id.txtHomeBpDate);
		txtBpTime = (TextView) rootView.findViewById(R.id.txtHomeBpTime);
		txtBpDiastolic = (TextView) rootView
				.findViewById(R.id.txtHomeDiastolic);
		txtBpSystolic = (TextView) rootView
				.findViewById(R.id.txtHomeSystolicNum);
		imgBp = (ImageView) rootView.findViewById(R.id.imgHomeBp);
		bpHomeRecordHolder = (LinearLayout) rootView
				.findViewById(R.id.bpHomeRecordHolder);
		bpHomeRecordHolderNull = (LinearLayout) rootView
				.findViewById(R.id.bpHomeRecordHolderNull);

		BloodPressureTrackerService bpService = new BloodPressureTrackerServiceImpl();
		try {
			bp = bpService.getLatest();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (bp != null) {
			Log.e("home bp", "not null");
			bpHomeRecordHolderNull.setVisibility(View.GONE);
			bpHomeRecordHolder.setVisibility(View.VISIBLE);
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
				imgBp.setImageResource(R.drawable.bloodpressure_normal);
			else
				imgBp.setImageResource(R.drawable.bloodpressure_warning);
		}

		bpHomeRecordHolderNull.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), NewStatusActivity.class);
				i.putExtra("tracker", TrackerInputType.BLOOD_PRESSURE);
				startActivity(i);
			}
		});
		bpHomeRecordHolder.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), NewStatusActivity.class);
				i.putExtra("tracker", TrackerInputType.BLOOD_PRESSURE);
				startActivity(i);
			}
		});

		// Weight
		txtWeight = (TextView) rootView.findViewById(R.id.txtHomeWeight);
		txtBmi = (TextView) rootView.findViewById(R.id.txtHomeBmi);
		txtWeightUnit = (TextView) rootView
				.findViewById(R.id.txtHomeWeightUnit);
		txtWeightStatus = (TextView) rootView
				.findViewById(R.id.txtHomeWeightStatus);
		imgWeight = (ImageView) rootView.findViewById(R.id.imgHomeWeight);
		weightHomeRecordHolder = (LinearLayout) rootView
				.findViewById(R.id.weightRecordHolder);

		WeightTrackerService weightService = new WeightTrackerServiceImpl();
		try {
			weight = weightService.getLatest();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (weight != null) {
			Log.e("home weight", weight.getWeightInPounds() + "");
			if (setting.isWeightSettingInPounds()) {
				txtWeight.setText(df.format(weight.getWeightInPounds()));
				txtWeightUnit.setText("lbs");
			} else {
				txtWeight.setText(df.format(weight.getWeightInKilograms()));
				txtWeightUnit.setText("kgs");
			}
			double heightInMeter = 1.75;
			double bmi = weight.getWeightInKilograms()
					/ (heightInMeter * heightInMeter);

			String formattedBmi = df.format(bmi);
			txtBmi.setText(formattedBmi);

			String weightStatus;
			if (bmi < 18.5)
				weightStatus = "Underweight";
			else if (bmi >= 18.5 && bmi < 24.9)
				weightStatus = "Normal weight";
			else if (bmi >= 25 && bmi < 29.9)
				weightStatus = "Overweight";
			else
				weightStatus = "Obesity";
			txtWeightStatus.setText(weightStatus);

			if (weightStatus.equals("Overweight")
					|| weightStatus.equals("Obesity"))
				imgWeight.setImageResource(R.drawable.overweight);

			else if (weightStatus.equals("Normal weight"))
				imgWeight.setImageResource(R.drawable.normalweight);
			else if (weightStatus.equals("Underweight"))
				imgWeight.setImageResource(R.drawable.underweight);

		}
		weightHomeRecordHolder.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), NewStatusActivity.class);
				i.putExtra("tracker", TrackerInputType.WEIGHT);
				startActivity(i);
			}
		});

		// calorie

		cProgress = (ProgressBar) rootView.findViewById(R.id.progressBar2);
		Drawable draw = getResources()
				.getDrawable(R.drawable.customprogressbar);
		cProgress.setProgressDrawable(draw);
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

		txtTotalFoodCal.setText(String.valueOf(groupedFood.getCalorie()));
		txtTotalActivityCal.setText(String.valueOf(groupedActivity
				.getCalorieBurned()));
		double total = groupedFood.getCalorie()
				- groupedActivity.getCalorieBurned();
		txtTotalCal.setText(String.valueOf(total));
		double bmr = 1500; // compute
		// Women: BMR = 655 + ( 4.35 x weight in pounds ) + ( 4.7 x height in
		// inches ) - ( 4.7 x age in years )
		// Men: BMR = 66 + ( 6.23 x weight in pounds ) + ( 12.7 x height in
		// inches ) - ( 6.8 x age in year )

		UserService userService = new UserServiceImpl();
		User user = userService.getUser();

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
		if (cProgressStatus > 100)
			cProgressStatus = 100;
		cProgress.setProgress(cProgressStatus);
		cProgress.setMax(100);

		mBtnRetrieve = (Button) rootView.findViewById(R.id.btnSync);
		mBtnRetrieve.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getActivity()
						.getApplicationContext(), RetrieveActivity.class);
				startActivity(intent);
			}
		});

		mBtnWrite = (Button) rootView.findViewById(R.id.btnWrite);
		mBtnWrite.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				/*
				 * Intent intent = new
				 * Intent(getActivity().getApplicationContext(),
				 * NewStatusActivity.class); startActivity(intent);
				 */
			}
		});

		mBtnStatus = (Button) rootView.findViewById(R.id.btnStatus);
		mBtnStatus.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),
						NewStatusActivity.class);
				startActivity(intent);
			}
		});

		View dailyChart;
		// 300g carbohydrates, 50g of protein and 65g fat
		// RDI2K(protein) / 2000 = x / BMR
		double recommendFats = Double.parseDouble(df
				.format((50 / 2000.0) * bmr));
		double recommendCarbs = Double.parseDouble(df.format((300 / 2000.0)
				* bmr));
		double recommendProtein = Double.parseDouble(df.format((65 / 2000.0)
				* bmr));

		int[] x = { 0, 1, 2 };
		double[] intake = { groupedFood.getProtein(), groupedFood.getFat(),
				groupedFood.getCarbohydrates() };
		double[] recommeded = { recommendProtein, recommendFats, recommendCarbs };

		String[] mMonth = new String[] { "Protein", "Fats", "Carbohydrates" };

		XYSeries incomeSeries = new XYSeries("Current Intake");

		XYSeries expenseSeries = new XYSeries("Recommended Intake");

		for (int i = 0; i < x.length; i++) {
			incomeSeries.add(i, intake[i]);
			expenseSeries.add(i, recommeded[i]);
		}

		// Creating a dataset to hold each series
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		// Adding Income Series to the dataset
		dataset.addSeries(incomeSeries);
		// Adding Expense Series to dataset
		dataset.addSeries(expenseSeries);

		// Creating XYSeriesRenderer to customize incomeSeries
		XYSeriesRenderer incomeRenderer = new XYSeriesRenderer();
		incomeRenderer.setColor(Color.parseColor("#C177C9"));
		incomeRenderer.setFillPoints(true);
		incomeRenderer.setLineWidth(2);
		incomeRenderer.setDisplayChartValues(true);
		incomeRenderer.setChartValuesTextSize(20);

		// Creating XYSeriesRenderer to customize expenseSeries
		XYSeriesRenderer expenseRenderer = new XYSeriesRenderer();
		expenseRenderer.setColor(Color.parseColor("#5C77D1"));
		expenseRenderer.setFillPoints(true);
		expenseRenderer.setLineWidth(2);

		expenseRenderer.setDisplayChartValues(true);
		expenseRenderer.setChartValuesTextSize(20);

		// Creating a XYMultipleSeriesRenderer to customize the whole chart
		XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
		multiRenderer.setXLabels(0);
		multiRenderer.setChartTitle("My Daily Nutritional Value Chart \n\n\n");
		multiRenderer.setAxisTitleTextSize(20);
		multiRenderer.setXTitle("\n\n\n Year 2014");
		multiRenderer.setYTitle("");
		multiRenderer.setZoomButtonsVisible(false);
		multiRenderer.setZoomEnabled(false);
		multiRenderer.setMargins(new int[] { 60, 30, 15, 0 });
		multiRenderer.setXAxisMin(-1);
		multiRenderer.setXAxisMax(3);
		multiRenderer.setYAxisMin(0);
		// multiRenderer.setAxisTitleTextSize(30);
		multiRenderer.setChartTitleTextSize(25);
		multiRenderer.setLabelsTextSize(15);
		multiRenderer.setAxesColor(Color.BLACK);
		multiRenderer.setLabelsColor(Color.BLACK);
		multiRenderer.setXLabelsColor(Color.BLACK);
		multiRenderer.setYLabelsColor(0, Color.BLACK);
		// multiRenderer.setLegendTextSize(15);
		multiRenderer.setLabelsTextSize(20);
		// multiRenderer.setLegendHeight(20);
		multiRenderer.setLegendTextSize(20);
		multiRenderer.setApplyBackgroundColor(true);
		multiRenderer.setBackgroundColor(Color.argb(0x00, 0x01, 0x01, 0x01));
		multiRenderer.setMarginsColor(Color.argb(0x00, 0x01, 0x01, 0x01));

		for (int i = 0; i < x.length; i++) {
			multiRenderer.addXTextLabel(i, mMonth[i]);
		}

		// Adding incomeRenderer and expenseRenderer to multipleRenderer
		// Note: The order of adding dataseries to dataset and renderers to
		// multipleRenderer
		// should be same
		multiRenderer.addSeriesRenderer(incomeRenderer);
		multiRenderer.addSeriesRenderer(expenseRenderer);

		// Creating an intent to plot bar chart using dataset and
		// multipleRenderer
		dailyChart = ChartFactory.getBarChartView(getActivity()
				.getBaseContext(), dataset, multiRenderer, Type.DEFAULT);

		LinearLayout dailyContainer = (LinearLayout) rootView
				.findViewById(R.id.piegraph);

		dailyContainer.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(),
						FoodTrackerDailyActivity.class);
				SimpleDateFormat fmt = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
				String txtdate = fmt.format(timestamp);
				Log.e("home", txtdate);
				i.putExtra("date", txtdate);
				startActivity(i);
			}
		});

		dailyContainer.addView(dailyChart);

		return rootView;
	}

	protected void setChartSettings(XYMultipleSeriesRenderer renderer,
			String title, String xTitle, String yTitle, double xMin,
			double xMax, double yMin, double yMax, int axesColor,
			int labelsColor) {
		renderer.setChartTitle(title);
		renderer.setXTitle(xTitle);
		renderer.setYTitle(yTitle);
		renderer.setXAxisMin(xMin);
		renderer.setXAxisMax(xMax);
		renderer.setYAxisMin(yMin);
		renderer.setYAxisMax(yMax);
		renderer.setAxesColor(axesColor);
		renderer.setLabelsColor(labelsColor);
	}

	protected XYMultipleSeriesRenderer buildBarRenderer(int[] colors) {
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		renderer.setAxisTitleTextSize(16);
		renderer.setChartTitleTextSize(20);
		renderer.setLabelsTextSize(15);
		renderer.setLegendTextSize(15);
		int length = colors.length;
		for (int i = 0; i < length; i++) {
			SimpleSeriesRenderer r = new SimpleSeriesRenderer();
			r.setColor(colors[i]);
			renderer.addSeriesRenderer(r);
		}
		return renderer;
	}

	protected XYMultipleSeriesDataset buildBarDataset(String[] titles,
			List<double[]> values) {// The data source and the pie chart almost,
									// is by some key value pairs
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		int length = titles.length;
		for (int i = 0; i < length; i++) {
			CategorySeries series = new CategorySeries(titles[i]);
			double[] v = values.get(i);
			int seriesLength = v.length;
			for (int k = 0; k < seriesLength; k++) {
				series.add(v[k]);
			}
			dataset.addSeries(series.toXYSeries());
		}
		return dataset;
	}
}
