package com.example.phr;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.phr.adapter.DailyFoodAdapter;
import com.example.phr.enums.TrackerInputType;
import com.example.phr.exceptions.DataAccessException;
import com.example.phr.exceptions.EntryNotFoundException;
import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.mobile.dao.MobileFoodTrackerDao;
import com.example.phr.mobile.daoimpl.MobileFoodTrackerDaoImpl;
import com.example.phr.mobile.models.FoodTrackerEntry;
import com.example.phr.mobile.models.GroupedFood;
import com.example.phr.mobile.models.User;
import com.example.phr.mobile.models.Weight;
import com.example.phr.service.FoodTrackerService;
import com.example.phr.service.UserService;
import com.example.phr.service.WeightTrackerService;
import com.example.phr.serviceimpl.FoodTrackerServiceImpl;
import com.example.phr.serviceimpl.UserServiceImpl;
import com.example.phr.serviceimpl.WeightTrackerServiceImpl;
import com.example.phr.tools.DateTimeParser;

public class FoodTrackerDailyActivity extends Activity {

	ListView mFoodSingleList;
	DailyFoodAdapter foodsingleAdapter;
	ImageView mBtnFoodSinglePost;
	MobileFoodTrackerDao foodDao;
	Timestamp timestamp;
	GroupedFood groupedFood;
	AlertDialog.Builder alertDialog;
	ArrayList<String> names;
	String mode;
	FoodTrackerService foodServiceImpl;
	AlertDialog alertD;
	FoodTrackerEntry chosenItem;

	// --------------------------------------------------------

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_food_tracker_daily);
		setTitle("Daily Food Tracker");
		getActionBar().setDisplayHomeAsUpEnabled(true);

		Intent in = getIntent();
		String txtDate = in.getExtras().getString("date");
		Log.e("dailyFoodtracker", txtDate);
		try {
			timestamp = DateTimeParser.getTimestamp(txtDate);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		mFoodSingleList = (ListView) findViewById(R.id.listView_food_single);

		// FAKE DATA
		/*
		 * Food food1 = new Food("Sinigang", 2.0, 3.0, 5.3, 10.4, "cup", 1.0, 1,
		 * true); Food food2 = new Food("Bacon", 3.0, 5.0, 2.3, 9.4, "slice",
		 * 1.0, 1, true); Food food3 = new Food("Hashbrown", 1.0, 5.0, 8.3, 9.4,
		 * "piece", 1.0, 1, true);
		 * 
		 * List<FoodTrackerEntry> list = new ArrayList<FoodTrackerEntry>();
		 * FoodTrackerEntry data1 = new FoodTrackerEntry(
		 * Timestamp.valueOf("2011-10-02 18:48:05.123456"), "status", null,
		 * food1, 1.0);
		 * 
		 * FoodTrackerEntry data2 = new FoodTrackerEntry(
		 * Timestamp.valueOf("2011-10-02 12:48:05.123456"), "status", null,
		 * food2, 1.0);
		 * 
		 * FoodTrackerEntry data3 = new FoodTrackerEntry(
		 * Timestamp.valueOf("2011-10-4 15:48:05.123456"), "status", null,
		 * food3, 1.0);
		 * 
		 * list.add(data3); list.add(data2); list.add(data1);
		 */
		List<FoodTrackerEntry> list = new ArrayList<FoodTrackerEntry>();
		foodDao = new MobileFoodTrackerDaoImpl();
		foodServiceImpl = new FoodTrackerServiceImpl();
		try {
			list = foodDao.getAllFromDate(timestamp);
			groupedFood = foodDao.getFromDateCalculated(timestamp);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Log.e(String.valueOf(list.size()), "size");

		foodsingleAdapter = new DailyFoodAdapter(getApplicationContext(), list);
		mFoodSingleList.setAdapter(foodsingleAdapter);
		mFoodSingleList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Log.e("foodsingle", "CLICKED!");
				chosenItem = (FoodTrackerEntry) arg0.getAdapter().getItem(arg2);
				mode = "";
				names = new ArrayList<String>();
				names.add("Edit");
				names.add("Delete");
				alertDialog = new AlertDialog.Builder(
						FoodTrackerDailyActivity.this);
				LayoutInflater inflater = getLayoutInflater();
				View convertView = inflater.inflate(R.layout.item_dialogbox,
						null);
				alertDialog.setView(convertView);
				alertDialog.setTitle("What to do?");
				ListView lv = (ListView) convertView
						.findViewById(R.id.dialogList);
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						getApplicationContext(), R.layout.item_custom_listview,
						names);
				lv.setAdapter(adapter);

				lv.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						mode = names.get(arg2);
						alertD.dismiss();
						Log.e("mode", names.get(arg2));

						if (mode.equals("Edit")) {

							Intent i = new Intent(getApplicationContext(),
									NewStatusActivity.class);
							i.putExtra("edit", TrackerInputType.FOOD);
							i.putExtra("object", chosenItem);
							startActivity(i);
						} else if (mode.equals("Delete")) {

							try {
								Log.e("fooddel", "start");
								foodServiceImpl.delete(chosenItem);
								Log.e("fooddel", "done");
							} catch (OutdatedAccessTokenException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (EntryNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (ServiceException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							Intent i = new Intent(getApplicationContext(),
									FoodTrackerDailyActivity.class);
							startActivity(i);
						}
					}

				});
				alertD = alertDialog.create();
				alertD.show();
				Log.e("in", "in");

			}

		});

		mBtnFoodSinglePost = (ImageView) findViewById(R.id.foodBanner);
		mBtnFoodSinglePost.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),
						NewStatusActivity.class);
				i.putExtra("tracker", TrackerInputType.FOOD);
				startActivity(i);
			}
		});

		// --------------------------------
		View dailyChart;

		WeightTrackerService weightService = new WeightTrackerServiceImpl();
		Weight weight = null;
		try {
			weight = weightService.getLatest();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		UserService userService = new UserServiceImpl();
		User user = userService.getUser();

		Timestamp bdaytimestamp = user.getDateOfBirth();

		int age = Integer.parseInt(DateTimeParser.getYear(timestamp))
				- Integer.parseInt(DateTimeParser.getYear(bdaytimestamp));
		double bmr = 0;
		// int age = 40;
		if (user.getGender().equals("F"))
			bmr = 655 + (4.35 * weight.getWeightInPounds())
					+ (4.7 * user.getHeight()) - (4.7 * age);
		else if (user.getGender().equals("M"))
			bmr = 66 + (6.23 * weight.getWeightInPounds())
					+ (12.7 * user.getHeight()) - (6.8 * age);

		double recommendFats = (50 / 2000) * bmr;
		double recommendCarbs = (300 / 2000) * bmr;
		double recommendProtein = (65 / 2000) * bmr;

		int[] x = { 0, 1, 2 };
		double[] intake = { groupedFood.getProtein(), groupedFood.getFat(),
				groupedFood.getCarbohydrates() };
		double[] recommended = { recommendProtein, recommendFats,
				recommendCarbs };

		String[] mMonth = new String[] { "Protein", "Fats", "Carbohydrates" };

		// Creating an XYSeries for Income
		// CategorySeries incomeSeries = new CategorySeries("Income");
		XYSeries incomeSeries = new XYSeries("Intake");
		// Creating an XYSeries for Income
		XYSeries expenseSeries = new XYSeries("Recommended");
		// Adding data to Income and Expense Series
		for (int i = 0; i < x.length; i++) {
			incomeSeries.add(i, intake[i]);
			expenseSeries.add(i, recommended[i]);
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
		multiRenderer.setChartTitle("Nutritional Value Chart Per Day \n\n\n");
		multiRenderer.setAxisTitleTextSize(20);
		multiRenderer.setXTitle("\n\n\n June 20, 2014");
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
		dailyChart = ChartFactory.getBarChartView(getBaseContext(), dataset,
				multiRenderer, Type.DEFAULT);

		LinearLayout dailyContainer = (LinearLayout) findViewById(R.id.foodGraph);

		dailyContainer.addView(dailyChart);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(getApplicationContext(), MainActivity.class);
		intent.putExtra("backToMenu", 2);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}

}
