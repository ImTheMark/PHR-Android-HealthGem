package com.example.phr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.phr.mobile.models.Food;

public class AddNewFoodActivity extends Activity {

	EditText newFood;
	EditText newFoodCal;
	EditText newFoodProtein;
	EditText newFoodCarbs;
	EditText newFoodFat;
	EditText newFoodServing;
	Spinner newServingUnit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("Add New Food");
		setContentView(R.layout.activity_add_new_food);

		newFood = (EditText) findViewById(R.id.txtNewFood);
		newFoodCal = (EditText) findViewById(R.id.txtFoodCal);
		newFoodProtein = (EditText) findViewById(R.id.txtFoodProtein);
		newFoodCarbs = (EditText) findViewById(R.id.txtFoodCarbs);
		newFoodFat = (EditText) findViewById(R.id.txtFoodFat);
		newFoodServing = (EditText) findViewById(R.id.txtNewServing);
		newServingUnit = (Spinner) findViewById(R.id.spinnerFoodUnit);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_add_activity, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_add_activity:

			Intent i = new Intent(getApplicationContext(),
					NewStatusActivity.class);
			i.putExtra("from", "new food");

			Food addFood = new Food(newFood.getText().toString(),
					Double.parseDouble(newFoodCal.getText().toString()),
					Double.parseDouble(newFoodProtein.getText().toString()),
					Double.parseDouble(newFoodFat.getText().toString()),
					Double.parseDouble(newFoodCarbs.getText().toString()),
					String.valueOf(newServingUnit.getSelectedItem()) + " " +
					newFoodServing.getText().toString(),
					null, true);
			i.putExtra("food added", addFood);
			startActivity(i);
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}

	}
}
