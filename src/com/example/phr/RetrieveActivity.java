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
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.phr.adapter.RetrieveFoodAdapter;
import com.example.phr.model.Retrieve_Food;
import com.example.phr.model.Retrieve_Other;

public class RetrieveActivity extends Activity{

	
	ListView mRetrieveFoodList;
	ListView mRetrieveActivityList;
	ListView mRetrieveOtherList;
	RetrieveFoodAdapter RetrieveAdapter;
	RetrieveFoodAdapter RetrieveActivityAdapter;
	//RetrieveOtherAdapter RetrieveOtherAdapter;
	LinearLayout mBtnRetrievePost;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_retrieve_post);
		setTitle("Retrieved Posts");
        getActionBar().setDisplayHomeAsUpEnabled(true);
		mRetrieveFoodList = (ListView) findViewById(R.id.listViewFood);
				
		// FAKE DATA
		List<Retrieve_Food> foodList = new ArrayList<Retrieve_Food>();
		Retrieve_Food data1 = new Retrieve_Food("July 20,2014","10:05 am","Sramble egg for breakfast yum yum yum ! :)","guess u ate :","scramble egg","1 g");
		Retrieve_Food data2 = new Retrieve_Food("July 18,2014","7:06 pm","woooow Pizz time ><","guess u ate :","pizza","1 slice");
		Retrieve_Food data3 = new Retrieve_Food("July 17,2014","11:09 pm","I really like this chocolate cake","guess u ate :","cake","1 slice");
		
		foodList.add(data1);
		foodList.add(data2);
		foodList.add(data3);

		RetrieveAdapter = new RetrieveFoodAdapter(getApplicationContext(), foodList);
		mRetrieveFoodList.setAdapter(RetrieveAdapter);
		
		
		mRetrieveActivityList = (ListView) findViewById(R.id.listViewActivity);
		
		// FAKE DATA
		List<Retrieve_Food> activityList = new ArrayList<Retrieve_Food>();
		Retrieve_Food data4 = new Retrieve_Food("July 20,2014","10:05 am","I like running","guess u did :","running","1 hr");
		Retrieve_Food data5 = new Retrieve_Food("July 18,2014","7:06 pm","Hiking with him was fun","guess u did :","hiking","1 hr");

		activityList.add(data4);
		activityList.add(data5);
		

		RetrieveActivityAdapter = new RetrieveFoodAdapter(getApplicationContext(), activityList);
		mRetrieveActivityList.setAdapter(RetrieveActivityAdapter);
		
		// FAKE DATA
		List<Retrieve_Other> otherList = new ArrayList<Retrieve_Other>();
		Retrieve_Other data6 = new Retrieve_Other("July 20,2014","10:05 am","I feel sick! :(");
		Retrieve_Other data7 = new Retrieve_Other("July 18,2014","7:06 pm","aaaah injection ><");

		otherList.add(data6);
		otherList.add(data7);
		

		//RetrieveOtherAdapter = new RetrieveOtherAdapter(getApplicationContext(), otherList);
		//mRetrieveOtherList.setAdapter(RetrieveOtherAdapter);
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
