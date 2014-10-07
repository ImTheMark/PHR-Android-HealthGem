package com.example.phr;

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
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.phr.adapter.BloodPressureAdapter;
import com.example.phr.enums.TrackerInputType;
import com.example.phr.exceptions.DataAccessException;
import com.example.phr.exceptions.EntryNotFoundException;
import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.mobile.daoimpl.MobileBloodPressureDaoImpl;
import com.example.phr.mobile.models.BloodPressure;
import com.example.phr.serviceimpl.BloodPressureServiceImpl;
import com.example.phr.tools.DateTimeParser;
import com.google.common.collect.Lists;

public class BloodPressureTrackerActivity extends Activity {

	ListView mBloodPressureList;
	BloodPressureAdapter bloodPressureAdapter;
	LinearLayout mBtnBloodPressurePost;
	List<BloodPressure> list;
	List<BloodPressure> reverselist;
	XYMultipleSeriesDataset bloodPressureDataset;
	View bloodPressureChart;
	LinearLayout bloodPressureContainer;
	AlertDialog.Builder alertDialog;
	ArrayList<String> names;
	String mode;
	BloodPressureServiceImpl bpServiceImpl;
	AlertDialog alertD;
	BloodPressure chosenItem;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bloodpressure_tracker);
		setTitle("Blood Pressure Tracker");
		getActionBar().setDisplayHomeAsUpEnabled(true);
		mBloodPressureList = (ListView) findViewById(R.id.listView_bloodpressure);

		// FAKE DATA
		list = new ArrayList<BloodPressure>();
		reverselist = new ArrayList<BloodPressure>();
		/*
		 * BloodPressure data7 = new
		 * BloodPressure(1,140,90,"May 31, 2014","3:40pm"
		 * ,null,getResources().getDrawable(R.drawable.bloodpressure_warning));
		 * 
		 * BloodPressure data6 = new
		 * BloodPressure(2,134,90,"Jun 07, 2014","1:40pm"
		 * ,null,getResources().getDrawable(R.drawable.bloodpressure_warning));
		 * 
		 * BloodPressure data5 = new
		 * BloodPressure(3,110,80,"Jun 14, 2014","2:40pm"
		 * ,null,getResources().getDrawable(R.drawable.bloodpressure_normal));
		 * 
		 * BloodPressure data4 = new
		 * BloodPressure(4,114,80,"Jun 21, 2014","2:50pm"
		 * ,null,getResources().getDrawable(R.drawable.bloodpressure_normal));
		 * 
		 * BloodPressure data3 = new
		 * BloodPressure(5,118,85,"Jun 28, 2014","1:40pm"
		 * ,null,getResources().getDrawable(R.drawable.bloodpressure_warning));
		 * 
		 * BloodPressure data2 = new
		 * BloodPressure(6,114,80,"Jul 05, 2014","2:40pm"
		 * ,null,getResources().getDrawable(R.drawable.bloodpressure_normal));
		 * 
		 * BloodPressure data1 = new
		 * BloodPressure(7,110,80,"Jul 12, 2014","2:50pm"
		 * ,null,getResources().getDrawable(R.drawable.bloodpressure_normal));
		 * list.add(data1); list.add(data2); list.add(data3); list.add(data4);
		 * list.add(data5); list.add(data6); list.add(data7);
		 */

		MobileBloodPressureDaoImpl bpDaoImpl = new MobileBloodPressureDaoImpl();
		try {
			list = bpDaoImpl.getAllReversed();
			reverselist = Lists.reverse(list);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (BloodPressure bp : list) {
			// bp.setImage(getResources().getDrawable(R.drawable.bloodpressure_warning));
		}

		bloodPressureAdapter = new BloodPressureAdapter(
				getApplicationContext(), reverselist);
		mBloodPressureList.setAdapter(bloodPressureAdapter);
		mBloodPressureList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Log.e("bloodpressure", "CLICKED!");
				chosenItem = (BloodPressure) arg0.getAdapter().getItem(arg2);
				mode = "";
				names = new ArrayList<String>();
				names.add("Edit");
				names.add("Delete");
				alertDialog = new AlertDialog.Builder(
						BloodPressureTrackerActivity.this);
				LayoutInflater inflater = getLayoutInflater();
				View convertView = inflater.inflate(R.layout.item_dialogbox,
						null);
				alertDialog.setView(convertView);
				alertDialog.setTitle("What to do?");
				ListView lv = (ListView) convertView
						.findViewById(R.id.dialogList);
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						getApplicationContext(),
						android.R.layout.simple_list_item_1, names);
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
							i.putExtra("edit", TrackerInputType.BLOOD_PRESSURE);
							i.putExtra("object", chosenItem);
							startActivity(i);
						} else if (mode.equals("Delete")) {

							try {
								Log.e("bloodpressure", "del");
								bpServiceImpl.delete(chosenItem);
								Log.e("bloodpressure", "del_done");
							} catch (ServiceException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (OutdatedAccessTokenException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (EntryNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							Intent i = new Intent(getApplicationContext(),
									BloodPressureTrackerActivity.class);
							startActivity(i);
						}
					}

				});
				alertD = alertDialog.create();
				alertD.show();
				Log.e("in", "in");

			}
		});

		mBtnBloodPressurePost = (LinearLayout) findViewById(R.id.bloodpressurePost);
		mBtnBloodPressurePost.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent i = new Intent(getApplicationContext(),
						NewStatusActivity.class);
				i.putExtra("tracker", TrackerInputType.BLOOD_PRESSURE);
				startActivity(i);

			}
		});

		createGraph();

	}

	public ArrayList<String> getLastSevenDateTime() {
		ArrayList<String> bloodPressureDate = new ArrayList<String>();

		if (list.size() >= 7)
			for (int i = list.size() - 7; i < list.size(); i++)
				bloodPressureDate.add(DateTimeParser.getMonthDay(list.get(i)
						.getTimestamp()));
		else
			for (int i = 0; i < list.size(); i++)
				bloodPressureDate.add(DateTimeParser.getMonthDay(list.get(i)
						.getTimestamp()));

		return bloodPressureDate;

	}

	public ArrayList<Integer> getLastSevenSystolic() {
		ArrayList<Integer> systolic = new ArrayList<Integer>();

		if (list.size() >= 7)
			for (int i = list.size() - 7; i < list.size(); i++)
				systolic.add(list.get(i).getSystolic());
		else
			for (int i = 0; i < list.size(); i++)
				systolic.add(list.get(i).getSystolic());

		return systolic;
	}

	public ArrayList<Integer> getLastSevenDiastolic() {
		ArrayList<Integer> diastolic = new ArrayList<Integer>();

		if (list.size() >= 7)
			for (int i = list.size() - 7; i < list.size(); i++)
				diastolic.add(list.get(i).getDiastolic());
		else
			for (int i = 0; i < list.size(); i++)
				diastolic.add(list.get(i).getDiastolic());

		return diastolic;
	}

	public ArrayList<Integer> getGraphElement() {
		ArrayList<Integer> number = new ArrayList<Integer>();

		if (list.size() >= 7)
			for (int i = 0; i < 7; i++)
				number.add(i + 1);
		else
			for (int i = 0; i < list.size(); i++)
				number.add(i + 1);

		return number;
	}

	public void createGraph() {

		// bloodPressureDataset.clear();
		// String[] bloodPressureMonth = new String[] { "","","May 31", "Jun 7",
		// "Jun 14", "Jun 21", "Jun 28",};
		// int[] bloodPressurex = { 1, 2, 3, 4, 5,6,7};
		// int[] systolic = { 0,0,140,134, 110, 114, 118};
		// int[] diastolic = { 0,0,90, 90, 80, 80, 85};

		ArrayList<String> bloodPressureMonth = getLastSevenDateTime();
		ArrayList<Integer> bloodPressurex = getGraphElement();
		ArrayList<Integer> systolic = getLastSevenSystolic();
		ArrayList<Integer> diastolic = getLastSevenDiastolic();

		XYSeries systolicSeries = new XYSeries("Systolic");
		XYSeries diastolicSeries = new XYSeries("Diastolic");

		for (int i = 0; i < bloodPressurex.size(); i++) {
			systolicSeries.add(bloodPressurex.get(i), systolic.get(i));
			diastolicSeries.add(bloodPressurex.get(i), diastolic.get(i));
		}

		XYMultipleSeriesDataset bloodPressureDataset = new XYMultipleSeriesDataset();

		bloodPressureDataset.addSeries(systolicSeries);
		bloodPressureDataset.addSeries(diastolicSeries);

		XYSeriesRenderer systolicRenderer = new XYSeriesRenderer();
		systolicRenderer.setColor(Color.parseColor("#B559BA"));
		systolicRenderer.setPointStyle(PointStyle.CIRCLE);
		systolicRenderer.setFillPoints(true);
		systolicRenderer.setLineWidth(10);
		systolicRenderer.setDisplayChartValues(true);
		systolicRenderer.setChartValuesTextSize(25);
		systolicRenderer.setChartValuesSpacing(20);

		XYSeriesRenderer diastolicRenderer = new XYSeriesRenderer();
		diastolicRenderer.setColor(Color.parseColor("#5C77D1"));
		diastolicRenderer.setPointStyle(PointStyle.CIRCLE);
		diastolicRenderer.setFillPoints(true);
		diastolicRenderer.setLineWidth(10);
		diastolicRenderer.setDisplayChartValues(true);
		diastolicRenderer.setChartValuesTextSize(25);
		diastolicRenderer.setChartValuesSpacing(20);

		XYMultipleSeriesRenderer bloodPressureMultiRenderer = new

		XYMultipleSeriesRenderer();
		bloodPressureMultiRenderer.setXLabels(0);
		bloodPressureMultiRenderer.setChartTitle("Blood Pressure Graph");
		bloodPressureMultiRenderer.setXTitle("Year 2014");
		bloodPressureMultiRenderer
				.setYTitle("Systolic/Diastolic Pressure (mm hg)");
		bloodPressureMultiRenderer.setZoomButtonsVisible(false);
		bloodPressureMultiRenderer.setAxisTitleTextSize(30);
		bloodPressureMultiRenderer.setChartTitleTextSize(30);
		bloodPressureMultiRenderer.setLegendTextSize(30);
		bloodPressureMultiRenderer.setPointSize(10);
		bloodPressureMultiRenderer.setXAxisMin(0);
		bloodPressureMultiRenderer.setXAxisMax(7);

		// margin --- top, left, bottom, right
		bloodPressureMultiRenderer.setMargins(new int[] { 90, 100, 120, 50 });
		bloodPressureMultiRenderer.setLegendHeight(60);

		// for (int i = 0; i < bloodPressurex.size(); i++) {
		for (int i = 0; i < bloodPressureMonth.size(); i++) {
			// bloodPressureMultiRenderer.addXTextLabel(i + 1,
			// bloodPressureMonth[i]);
			bloodPressureMultiRenderer.addXTextLabel(i + 1,
					bloodPressureMonth.get(i));
		}

		bloodPressureMultiRenderer.setApplyBackgroundColor(true);
		bloodPressureMultiRenderer.setBackgroundColor(Color.argb(0x00, 0x01,
				0x01, 0x01));
		bloodPressureMultiRenderer.setMarginsColor(Color.argb(0x00, 0x01, 0x01,
				0x01));
		bloodPressureMultiRenderer.setAxesColor(Color.BLACK);
		bloodPressureMultiRenderer.setLabelsColor(Color.BLACK);
		bloodPressureMultiRenderer.setXLabelsColor(Color.BLACK);
		bloodPressureMultiRenderer.setYLabelsColor(0, Color.BLACK);
		bloodPressureMultiRenderer.setAxisTitleTextSize(30);
		bloodPressureMultiRenderer.setLabelsTextSize(30);

		bloodPressureMultiRenderer.addSeriesRenderer(systolicRenderer);
		bloodPressureMultiRenderer.addSeriesRenderer(diastolicRenderer);

		LinearLayout bloodPressureContainer = (LinearLayout) findViewById(R.id.bloopressureGraph);

		bloodPressureChart = ChartFactory.getLineChartView(getBaseContext(),
				bloodPressureDataset, bloodPressureMultiRenderer);

		bloodPressureContainer.addView(bloodPressureChart);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_tracker_help, menu);
		Log.e("bp: ", "start ..");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	protected void onResume() {
		super.onResume();

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

}
