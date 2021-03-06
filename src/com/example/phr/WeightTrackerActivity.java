package com.example.phr;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phr.adapter.WeightAdapter;
import com.example.phr.application.HealthGem;
import com.example.phr.enums.TrackerInputType;
import com.example.phr.exceptions.DataAccessException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.mobile.dao.MobileSettingsDao;
import com.example.phr.mobile.dao.MobileWeightTrackerDao;
import com.example.phr.mobile.daoimpl.MobileSettingsDaoImpl;
import com.example.phr.mobile.daoimpl.MobileWeightTrackerDaoImpl;
import com.example.phr.mobile.models.Weight;
import com.example.phr.service.WeightTrackerService;
import com.example.phr.serviceimpl.WeightTrackerServiceImpl;
import com.example.phr.tools.DateTimeParser;

public class WeightTrackerActivity extends Activity {

	WeightAdapter weightAdapter;
	ListView mWeightList;
	ImageView mBtnAddWeight;
	List<Weight> list;
	MobileSettingsDao setting;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();

		StrictMode.setThreadPolicy(policy);
		setContentView(R.layout.activity_weight_tracker);
		setTitle("Weight Tracker");
		mWeightList = (ListView) findViewById(R.id.listViewWeightTracker);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		getActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#030203")));
		MobileWeightTrackerDao daoImpl = new MobileWeightTrackerDaoImpl();
		WeightTrackerService weightService = new WeightTrackerServiceImpl();
		setting = new MobileSettingsDaoImpl();
		List<List<Weight>> weightList = new ArrayList<List<Weight>>();
		;
		try {
			weightList = daoImpl.getAllGroupedByDate();
			list = daoImpl.getAllReversed();
		} catch (DataAccessException e) {
			Log.e("weightracker", "ERROR IN WEIGHT TRACKER GET ALL LIST");
		}

		weightAdapter = new WeightAdapter(getApplicationContext(), weightList);
		mWeightList.setAdapter(weightAdapter);

		Intent in = getIntent();
		Bundle extras = getIntent().getExtras();
		if (extras != null && extras.getString("mode").equals("add")) {
			String feedback = null;
			try {
				feedback = weightService.getFeedback();
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				Toast.makeText(HealthGem.getContext(),
						"No Internet Connection !", Toast.LENGTH_LONG).show();
				e.printStackTrace();
			}

			displayFeedback(feedback);
		}

		mBtnAddWeight = (ImageView) findViewById(R.id.btnAddWeight);
		mBtnAddWeight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(getApplicationContext(),
						NewStatusActivity.class);
				i.putExtra("tracker", TrackerInputType.WEIGHT);
				startActivity(i);
			}
		});

		// Graph
		// ------------------------------
		View weightChart;

		// String[] weightMonth = new String[] { "May 1", "May 8", "May 15",
		// "May 20", "May 22", "Jun 10", "Jul 12" };

		// int[] weightx = { 1, 2, 3, 4, 5, 6, 7 };
		// int[] weight = { 180, 178, 172, 176, 174, 178, 180 };

		ArrayList<Integer> weightx = getGraphElement();
		ArrayList<String> weightMonth = getLastSevenDateTime();
		ArrayList<Double> weight = getLastSevenWeight();
		XYSeries weightSeries = new XYSeries("Weight");

		// for (int i = 0; i < weightx.length; i++) {
		// weightSeries.add(weightx[i], weight[i]);
		// }

		for (int i = 0; i < weightx.size(); i++) {
			weightSeries.add(weightx.get(i), weight.get(i));
		}

		XYMultipleSeriesDataset bloodsugarDataset = new XYMultipleSeriesDataset();

		bloodsugarDataset.addSeries(weightSeries);

		XYSeriesRenderer mmolRenderer = new XYSeriesRenderer();
		mmolRenderer.setColor(Color.parseColor("#B559BA"));
		mmolRenderer.setPointStyle(PointStyle.CIRCLE);
		mmolRenderer.setFillPoints(true);
		mmolRenderer.setLineWidth(10);
		mmolRenderer.setDisplayChartValues(true);
		mmolRenderer.setChartValuesTextSize(25);
		mmolRenderer.setChartValuesSpacing(20);

		XYMultipleSeriesRenderer weightMultiRenderer = new

		XYMultipleSeriesRenderer();
		weightMultiRenderer.setXLabels(0);
		weightMultiRenderer.setChartTitle("Weight Graph");
		weightMultiRenderer.setXTitle("\n\n\n\n\n\n Year 2014");
		if (setting.isWeightSettingInPounds())
			weightMultiRenderer.setYTitle("Weight in lbs");
		else
			weightMultiRenderer.setYTitle("Weight in kgs");
		weightMultiRenderer.setZoomButtonsVisible(false);
		weightMultiRenderer.setAxisTitleTextSize(30);
		weightMultiRenderer.setChartTitleTextSize(30);
		weightMultiRenderer.setLegendTextSize(30);
		weightMultiRenderer.setPointSize(10);
		weightMultiRenderer.setXAxisMin(list.size() - 6);
		weightMultiRenderer.setXAxisMax(list.size());
		weightMultiRenderer.setPanEnabled(true, false);
		weightMultiRenderer.setZoomEnabled(false, false);
		weightMultiRenderer.setClickEnabled(false);
		weightMultiRenderer.setInScroll(true);

		// margin --- top, left, bottom, right
		weightMultiRenderer.setMargins(new int[] { 90, 80, 140, 50 });
		weightMultiRenderer.setLegendHeight(60);

		// for (int i = 0; i < weightx.length; i++) {
		// weightMultiRenderer.addXTextLabel(i + 1, weightMonth[i]);
		// }

		for (int i = 0; i < weightMonth.size(); i++) {
			weightMultiRenderer.addXTextLabel(i + 1, weightMonth.get(i));
		}

		weightMultiRenderer.setApplyBackgroundColor(true);
		weightMultiRenderer.setBackgroundColor(Color.argb(0x00, 0x01, 0x01,
				0x01));
		weightMultiRenderer.setMarginsColor(Color.argb(0x00, 0x01, 0x01, 0x01));
		weightMultiRenderer.setAxesColor(Color.BLACK);
		weightMultiRenderer.setLabelsColor(Color.BLACK);
		weightMultiRenderer.setXLabelsColor(Color.BLACK);
		weightMultiRenderer.setYLabelsColor(0, Color.BLACK);
		weightMultiRenderer.setAxisTitleTextSize(30);
		weightMultiRenderer.setLabelsTextSize(30);

		weightMultiRenderer.addSeriesRenderer(mmolRenderer);

		LinearLayout weightContainer = (LinearLayout) findViewById(R.id.weightGraph);

		weightChart = ChartFactory.getLineChartView(getBaseContext(),
				bloodsugarDataset, weightMultiRenderer);

		weightContainer.addView(weightChart);

	}

	public ArrayList<String> getLastSevenDateTime() {
		ArrayList<String> bloodPressureDate = new ArrayList<String>();

		for (int i = list.size() - 1; i >= 0; i--)
			bloodPressureDate.add(DateTimeParser.getMonthDay(list.get(i)
					.getTimestamp())
					+ " \n "
					+ DateTimeParser.getTime(list.get(i).getTimestamp()));

		return bloodPressureDate;

	}

	public ArrayList<Double> getLastSevenWeight() {
		ArrayList<Double> weight = new ArrayList<Double>();
		DecimalFormat df = new DecimalFormat("#.00");
		if (setting.isWeightSettingInPounds()) {
			for (int i = list.size() - 1; i >= 0; i--)
				weight.add(Double.parseDouble(df.format(list.get(i)
						.getWeightInPounds())));
		} else {
			for (int i = list.size() - 1; i >= 0; i--)
				weight.add(Double.parseDouble(df.format(list.get(i)
						.getWeightInKilograms())));
		}

		return weight;
	}

	public ArrayList<Integer> getGraphElement() {
		ArrayList<Integer> number = new ArrayList<Integer>();

		for (int i = 0; i < list.size(); i++)
			number.add(i + 1);

		return number;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_tracker_help, menu);
		return super.onCreateOptionsMenu(menu);
	}

	private void displayhelp() {

		Dialog dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.item_help);
		ImageView image = (ImageView) dialog.findViewById(R.id.help_imageview);
		image.setBackgroundResource(R.drawable.weighttracker_help);
		dialog.getWindow().setBackgroundDrawable(null);
		dialog.show();
	}

	private void displayFeedback(String feedback) {

		Dialog dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.item_weight_feedback);
		TextView txtFeedback = (TextView) dialog
				.findViewById(R.id.txtWeightFeedback);
		txtFeedback.setText(feedback);
		dialog.getWindow().setBackgroundDrawable(null);
		dialog.show();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			break;
		case R.id.action_help:
			displayhelp();
			return true;
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
