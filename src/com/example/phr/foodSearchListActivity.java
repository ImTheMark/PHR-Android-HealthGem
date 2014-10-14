package com.example.phr;

import java.util.ArrayList;
import java.util.List;

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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.phr.adapter.SingleFoodAdapter;
import com.example.phr.mobile.models.Food;

public class foodSearchListActivity extends Activity {

	EditText searchWord;
	ImageButton searchButton;
	ListView searchList;
	SingleFoodAdapter foodsingleAdapter;
	List<Food> list;

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
				list = new ArrayList<Food>();
				Food data1 = new Food("Sinigang", 2.0, 3.0, 5.3, 10.4, "cup",
						1.0, 1, true);
				Food data2 = new Food("Bacon", 3.0, 5.0, 2.3, 9.4, "slice",
						1.0, 1, true);
				Food data3 = new Food("Hashbrown", 1.0, 5.0, 8.3, 9.4, "piece",
						1.0, 1, true);

				list.add(data3);
				list.add(data2);
				list.add(data1);

				foodsingleAdapter = new SingleFoodAdapter(
						getApplicationContext(), list);
				searchList.setAdapter(foodsingleAdapter);
			}
		});

		searchList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Log.e("foodsingle", list.get(arg2).getName());
				Food chosenItem = list.get(arg2);
				Intent intent = new Intent();
				intent.putExtra("food chosen", chosenItem);
				setResult(4, intent);
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
			Intent i = new Intent(getApplicationContext(),
					AddNewFoodActivity.class);
			startActivity(i);

		default:
			return super.onOptionsItemSelected(item);
		}

	}

}
