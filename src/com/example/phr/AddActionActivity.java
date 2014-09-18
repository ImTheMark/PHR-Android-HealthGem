package com.example.phr;

import java.util.ArrayList;
import java.util.List;

import com.example.phr.R;
import com.example.phr.adapter.ActionAdapter;
import com.example.phr.model.StatusAction;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class AddActionActivity extends Activity {
	
	ActionAdapter actionAdapter;
	List<StatusAction> list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("What are you doing?");
		setContentView(R.layout.activity_add_actions);
		ListView actionList = (ListView) findViewById(R.id.actionList);
		actionAdapter = new ActionAdapter(getApplicationContext(), generateData());
		actionList.setAdapter(actionAdapter);
		
		actionList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
		        Intent intent=new Intent();  
		        intent.putExtra("itemValue",list.get(arg2).getActionName());  
		        setResult(2,intent);  
		        finish();
			}

        });
	}

	private List<StatusAction> generateData() {
		list = new ArrayList<StatusAction>();
		
		
		StatusAction eat = new StatusAction();
		eat.setActionName("Eating a Food");
		eat.setImgUrl(getResources().getDrawable(R.drawable.icon_food));
		list.add(eat);
		
		StatusAction doing = new StatusAction();
		doing.setActionName("Doing an Activity");
		doing.setImgUrl(getResources().getDrawable(R.drawable.icon_activity));
		list.add(doing);
		
		StatusAction bloodpressure = new StatusAction();
		bloodpressure.setActionName("Measuring Blood Pressure");
		bloodpressure.setImgUrl(getResources().getDrawable(R.drawable.icon_blood_pressure));
		list.add(bloodpressure);
		
		StatusAction bloodsugar = new StatusAction();
		bloodsugar.setActionName("Measuring Blood Sugar");
		bloodsugar.setImgUrl(getResources().getDrawable(R.drawable.icon_blood_sugar));
		list.add(bloodsugar);
		
		StatusAction weight = new StatusAction();
		weight.setActionName("Measuring Weight");
		weight.setImgUrl(getResources().getDrawable(R.drawable.icon_weight));
		list.add(weight);
		
		StatusAction checkup = new StatusAction();
		checkup.setActionName("Record a Checkup");
		checkup.setImgUrl(getResources().getDrawable(R.drawable.icon_checkup));
		list.add(checkup);
		
		return list;
	}
	

	
}
