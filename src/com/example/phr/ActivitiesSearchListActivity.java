package com.example.phr;

import java.util.ArrayList;
import java.util.List;

import com.example.phr.enums.TrackerInputType;
import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.ServiceException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ActivitiesSearchListActivity extends Activity{
	EditText searchWord;
	ListView searchList;
	ImageButton searchButton;
	ArrayAdapter<String> adapter;
	ArrayList<String> result;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_list);
		setTitle("Activity Search List");
		getActionBar().setDisplayHomeAsUpEnabled(true);
		searchList = (ListView) findViewById(R.id.searchList);
		searchWord = (EditText) findViewById(R.id.searchWord);
		searchButton = (ImageButton) findViewById(R.id.searchButton);
		
		searchButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				result = new ArrayList<String>();
				result.add("jogging");
				result.add("running");
				adapter = new ArrayAdapter<String>(getApplicationContext(),
		                R.layout.item_custom_listview, result);
				
				searchList.setAdapter(adapter);
			}
		});

		searchList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
		        Intent intent=new Intent();  
		        intent.putExtra("activity chosen",result.get(arg2)); 
		        Log.e("activity chosen ", result.get(arg2));
		        setResult(3,intent);  
		        finish();
			}

        });
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_add_new_activity, menu);
		return super.onCreateOptionsMenu(menu);
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_item_add_new_activity:
			Intent i = new Intent(getApplicationContext(), AddNewActivityActivity.class);
			startActivity(i);
			
		default:
			return super.onOptionsItemSelected(item);
		}

	}

}