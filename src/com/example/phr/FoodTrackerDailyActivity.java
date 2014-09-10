package com.example.phr;

import java.util.ArrayList;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import com.example.phr.adapter.SingleFoodAdapter;
import com.example.phr.model.FoodSingle;

public class FoodTrackerDailyActivity extends Activity{
	
	ListView mFoodSingleList;
	SingleFoodAdapter foodsingleAdapter;
	ImageView mBtnFoodSinglePost;
	

	//--------------------------------------------------------
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_food_tracker_daily);
		setTitle("Daily Food Tracker");
        getActionBar().setDisplayHomeAsUpEnabled(true);
		
    	mFoodSingleList = (ListView) findViewById(R.id.listView_food_single);
		
		// FAKE DATA
		List<FoodSingle> list = new ArrayList<FoodSingle>();
		FoodSingle data1 = new FoodSingle("Sinigang","1 cup",10.8,222,17,16.56,"7:30 pm");
		
		FoodSingle data2 = new FoodSingle("Bacon","1 slice",2.09,27,0.07,1.85,"7:30 pm");

		FoodSingle data3 = new FoodSingle("Hash browns","1",12,210,26,2,"9:30 pm");

		list.add(data3);
		list.add(data2);
		list.add(data1);


		foodsingleAdapter = new SingleFoodAdapter(getApplicationContext(), list);
		mFoodSingleList.setAdapter(foodsingleAdapter);
		mFoodSingleList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Log.e("foodsingle", "CLICKED!");
			}
		});
		
		mBtnFoodSinglePost = (ImageView) findViewById(R.id.foodBanner);
		mBtnFoodSinglePost.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {/*
				Intent intent = new Intent(getApplicationContext(),
						FoodTrackerPostActivity.class);
				startActivity(intent);*/
			}
		});
	
        
        //--------------------------------
        View dailyChart;
		
		int[] x = { 0,1,2 };
    	double[] intake = { 20.41,24.89,43.07};
    	double[] recommended = {41.25, 53.63, 247.5};
    	
    	 String[] mMonth = new String[] {"Protein", "Fats" , "Carbohydrates"};

    	
    	// Creating an  XYSeries for Income
    	//CategorySeries incomeSeries = new CategorySeries("Income");
    	XYSeries incomeSeries = new XYSeries("Intake");
    	// Creating an  XYSeries for Income
    	XYSeries expenseSeries = new XYSeries("Recommended");
    	// Adding data to Income and Expense Series
    	for(int i=0;i<x.length;i++){    		
    		incomeSeries.add(i,intake[i]);
    		expenseSeries.add(i,recommended[i]);
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
    	multiRenderer.setMargins(new int[] {60, 30, 15, 0});
    	multiRenderer.setXAxisMin(-1);
    	multiRenderer.setXAxisMax(3);
    	multiRenderer.setYAxisMin(0);
    	//multiRenderer.setAxisTitleTextSize(30);
    	multiRenderer.setChartTitleTextSize(25);
    	multiRenderer.setLabelsTextSize(15);
    	multiRenderer.setAxesColor(Color.BLACK);
    	multiRenderer.setLabelsColor(Color.BLACK);
    	multiRenderer.setXLabelsColor(Color.BLACK);
    	multiRenderer.setYLabelsColor(0, Color.BLACK);
    	//multiRenderer.setLegendTextSize(15);
        multiRenderer.setLabelsTextSize(20);
       // multiRenderer.setLegendHeight(20);
        multiRenderer.setLegendTextSize(20);
    	multiRenderer.setApplyBackgroundColor(true);
    	multiRenderer.setBackgroundColor(Color.argb(0x00, 0x01, 0x01, 0x01));
    	multiRenderer.setMarginsColor(Color.argb(0x00, 0x01, 0x01, 0x01));
    	
    	for(int i=0; i< x.length;i++){
    		multiRenderer.addXTextLabel(i, mMonth[i]);    		
    	}    	
    	
    	
    	// Adding incomeRenderer and expenseRenderer to multipleRenderer
    	// Note: The order of adding dataseries to dataset and renderers to multipleRenderer
    	// should be same
    	multiRenderer.addSeriesRenderer(incomeRenderer);
    	multiRenderer.addSeriesRenderer(expenseRenderer);
    	
    	// Creating an intent to plot bar chart using dataset and multipleRenderer    	
    	dailyChart = ChartFactory.getBarChartView(getBaseContext(), dataset, multiRenderer, Type.DEFAULT);
	    
	    LinearLayout dailyContainer = (LinearLayout) findViewById(R.id.foodGraph);
	    
	    dailyContainer.addView(dailyChart);
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
