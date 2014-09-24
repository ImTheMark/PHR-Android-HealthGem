package com.example.phr;

import java.util.ArrayList;
import java.util.List;

import com.example.phr.adapter.SingleFoodAdapter;
import com.example.phr.model.FoodSingle;

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

public class foodSearchListActivity extends Activity{
	
	EditText searchWord;
	ImageButton searchButton;
	ListView searchList;
	SingleFoodAdapter foodsingleAdapter;
	List<FoodSingle> list;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_list);
		setTitle("Food Search List");
		getActionBar().setDisplayHomeAsUpEnabled(true);
		searchList = (ListView) findViewById(R.id.searchList);
		searchWord = (EditText) findViewById(R.id.searchWord);
		searchButton = (ImageButton) findViewById(R.id.searchButton);
		
		searchButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				// FAKE DATA
				list = new ArrayList<FoodSingle>();
				FoodSingle data1 = new FoodSingle("Sinigang","cup",10.8,222,17,16.56,"via fatsecret");
				FoodSingle data2 = new FoodSingle("Bacon","slice",2.09,27,0.07,1.85,"");
				FoodSingle data3 = new FoodSingle("Hash browns","piece",12,210,26,2,"");

				list.add(data3);
				list.add(data2);
				list.add(data1);
				
				foodsingleAdapter = new SingleFoodAdapter(getApplicationContext(), list);
				searchList.setAdapter(foodsingleAdapter);
			}
		});
		
		searchList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Log.e("foodsingle", list.get(arg2).getFood());
				Intent intent=new Intent();  
		        intent.putExtra("food chosen",list.get(arg2).getFood()); 
		        intent.putExtra("cal",list.get(arg2).getCal()); 
		        intent.putExtra("protein",list.get(arg2).getProtein()); 
		        intent.putExtra("fat",list.get(arg2).getFat()); 
		        intent.putExtra("carbs",list.get(arg2).getCarbs()); 
		        intent.putExtra("serving",list.get(arg2).getServing()); 
		        setResult(4,intent);  
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
			Intent i = new Intent(getApplicationContext(), AddNewFoodActivity.class);
			startActivity(i);
			
		default:
			return super.onOptionsItemSelected(item);
		}

	}


}
