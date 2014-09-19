package com.example.phr;

import java.util.ArrayList;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import com.example.phr.adapter.GroupedStatusAdapter;
import com.example.phr.enums.TrackerInputType;
import com.example.phr.model.GroupedStatus;
import com.example.phr.model.Status;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

public class WeightTrackerActivity extends Activity {
	
	GroupedStatusAdapter weightAdapter;
	ListView mWeightList;
	ImageView mBtnAddWeight;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weight_tracker);
		setTitle("Weight Tracker");
		mWeightList = (ListView) findViewById(R.id.listViewWeightTracker);
        getActionBar().setDisplayHomeAsUpEnabled(true);
		weightAdapter = new GroupedStatusAdapter(getApplicationContext(), generateData());
		mWeightList.setAdapter(weightAdapter);
		
		
		mBtnAddWeight = (ImageView) findViewById(R.id.btnAddWeight);
		mBtnAddWeight.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(getApplicationContext(), NewStatusActivity.class);
				i.putExtra("tracker",TrackerInputType.WEIGHT);
				startActivity(i);
			}
		});
		
		
		//Graph	
	//------------------------------
		View weightChart;

		String[] weightMonth = new String[] { "May 1", "May 8", "May 15", "May 20", "May 22","Jun 10", "Jul 12"};

		int[] weightx = { 1, 2, 3, 4, 5, 6, 7 };
		int[] bloodsugar = { 180, 178, 172, 176, 174, 178, 180};

		XYSeries weightSeries = new XYSeries("Weight");

		for (int i = 0; i < weightx.length; i++) {
			weightSeries.add(weightx[i], bloodsugar[i]);
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
		weightMultiRenderer.setXTitle("Year 2014");
		weightMultiRenderer.setYTitle("Weight in lbs");
		weightMultiRenderer.setZoomButtonsVisible(false);
		weightMultiRenderer.setAxisTitleTextSize(30);
		weightMultiRenderer.setChartTitleTextSize(30);
		weightMultiRenderer.setLegendTextSize(30);
		weightMultiRenderer.setPointSize(10);
		weightMultiRenderer.setXAxisMin(0);
		weightMultiRenderer.setXAxisMax(7);
		
		// margin --- top, left, bottom, right
		weightMultiRenderer.setMargins(new int[] { 90, 100, 120, 50 });
		weightMultiRenderer.setLegendHeight(60);

		for (int i = 0; i < weightx.length; i++) {
			weightMultiRenderer.addXTextLabel(i + 1, weightMonth[i]);
		}

		weightMultiRenderer.setApplyBackgroundColor(true);
		weightMultiRenderer.setBackgroundColor(Color.argb(0x00, 0x01, 0x01, 0x01));
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

	private List<GroupedStatus> generateData() {
		List<GroupedStatus> list = new ArrayList<GroupedStatus>();
		
		GroupedStatus day1 = new GroupedStatus();
		day1.setMonth("Jul");
		day1.setDay("12");
		day1.setAverage("180 lbs");
		
		ArrayList<Status> sList = new ArrayList<Status>();
		Status gain = new Status();
		gain.setActionHolder("weighting");
		gain.setActionName("181 lbs");
		gain.setDatettime("4:18 pm");
		sList.add(gain);
		Status loose = new Status();
		loose.setActionHolder("weighting");
		loose.setActionName("179 lbs");
		loose.setDatettime("6:30 am");
		sList.add(loose);
		
		day1.setWeightStatusList(sList);
		
		//----------------------
		

		GroupedStatus day2 = new GroupedStatus();
		day2.setMonth("Jul");
		day2.setDay("10");
		day2.setAverage("178 lbs");
		
		ArrayList<Status> sList2 = new ArrayList<Status>();
		Status gain21 = new Status();
		gain21.setActionHolder("weighting");
		gain21.setActionName("178 lbs");
		gain21.setDatettime("3:50 pm");
		sList2.add(gain21);
		
		day2.setWeightStatusList(sList2);
		
		//----------------------
		

		GroupedStatus day3 = new GroupedStatus();
		day3.setMonth("May");
		day3.setDay("22");
		day3.setAverage("174 lbs");
		
		ArrayList<Status> sList3 = new ArrayList<Status>();
		Status gain31 = new Status();
		gain31.setActionHolder("weighting");
		gain31.setActionName("174 lbs");
		gain31.setDatettime("12:30 pm");
		sList3.add(gain31);
		
		day3.setWeightStatusList(sList3);
		
		GroupedStatus day4 = new GroupedStatus();
		day4.setMonth("May");
		day4.setDay("20");
		day4.setAverage("176 lbs");
		
		ArrayList<Status> sList4 = new ArrayList<Status>();
		Status gain41 = new Status();
		gain41.setActionHolder("weighting");
		gain41.setActionName("176 lbs");
		gain41.setDatettime("12:30 pm");
		sList4.add(gain41);
		
		day4.setWeightStatusList(sList4);
		
		GroupedStatus day5 = new GroupedStatus();
		day5.setMonth("May");
		day5.setDay("15");
		day5.setAverage("172 lbs");
		
		ArrayList<Status> sList5 = new ArrayList<Status>();
		Status gain51 = new Status();
		gain51.setActionHolder("weighting");
		gain51.setActionName("172 lbs");
		gain51.setDatettime("12:30 pm");
		sList5.add(gain51);
		
		day5.setWeightStatusList(sList5);
		
		GroupedStatus day6 = new GroupedStatus();
		day6.setMonth("May");
		day6.setDay("08");
		day6.setAverage("178 lbs");
		
		ArrayList<Status> sList6 = new ArrayList<Status>();
		Status gain61 = new Status();
		gain61.setActionHolder("weighting");
		gain61.setActionName("178 lbs");
		gain61.setDatettime("1:30 pm");
		sList6.add(gain61);
		
		day6.setWeightStatusList(sList6);
		GroupedStatus day7 = new GroupedStatus();
		day7.setMonth("May");
		day7.setDay("01");
		day7.setAverage("180 lbs");
		
		ArrayList<Status> sList7 = new ArrayList<Status>();
		Status gain71 = new Status();
		gain71.setActionHolder("weighting");
		gain71.setActionName("180 lbs");
		gain71.setDatettime("12:30 pm");
		sList7.add(gain71);
		
		day7.setWeightStatusList(sList7);
		
		list.add(day1);
		list.add(day2);
		list.add(day3);
		list.add(day4);
		list.add(day5);
		list.add(day6);
		list.add(day7);
		
		return list;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu_tracker_help, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
        switch (item.getItemId()) 
        {
        case android.R.id.home: 
            onBackPressed();
            break;
        default:
            return super.onOptionsItemSelected(item);
        }
        return true;
    }
	

}
