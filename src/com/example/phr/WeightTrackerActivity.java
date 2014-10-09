package com.example.phr;

import java.util.ArrayList;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import com.example.phr.adapter.WeightAdapter;
import com.example.phr.enums.TrackerInputType;
import com.example.phr.exceptions.DataAccessException;
import com.example.phr.mobile.dao.MobileWeightDao;
import com.example.phr.mobile.daoimpl.MobileWeightDaoImpl;
import com.example.phr.mobile.models.Weight;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

public class WeightTrackerActivity extends Activity {
	
	WeightAdapter weightAdapter;
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
        
        
        MobileWeightDao daoImpl = new MobileWeightDaoImpl();
        List<List<Weight>> weightList = new ArrayList<List<Weight>>();;
		try {
			weightList = daoImpl.getAllGroupedByDate();
		} catch (DataAccessException e) {
			Log.e("weightracker", "ERROR IN WEIGHT TRACKER GET ALL LIST");
		}
        
		weightAdapter = new WeightAdapter(getApplicationContext(), weightList);
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
