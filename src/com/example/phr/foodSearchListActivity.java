package com.example.phr;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
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
import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.mobile.models.Food;
import com.example.phr.service.FoodService;
import com.example.phr.serviceimpl.FoodServiceImpl;

public class FoodSearchListActivity extends Activity {

	EditText searchWord;
	ImageButton searchButton;
	ListView searchList;
	SingleFoodAdapter foodsingleAdapter;
	List<Food> list;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();

		StrictMode.setThreadPolicy(policy);
		setContentView(R.layout.activity_search_list);
		setTitle("Food Search List");
		getActionBar().setDisplayHomeAsUpEnabled(true);
		searchList = (ListView) findViewById(R.id.searchList);
		searchWord = (EditText) findViewById(R.id.searchWord);
		searchButton = (ImageButton) findViewById(R.id.searchButton);

		searchButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				FoodService service = new FoodServiceImpl();
				Log.e("search word", searchWord.getText().toString());
				list = new ArrayList<Food>();
				try {
					list = service.search(searchWord.getText().toString());
				} catch (ServiceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (OutdatedAccessTokenException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (list != null) {
					foodsingleAdapter = new SingleFoodAdapter(
							getApplicationContext(), list);
					searchList.setAdapter(foodsingleAdapter);
				}
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
