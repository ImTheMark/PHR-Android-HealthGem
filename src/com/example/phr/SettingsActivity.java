package com.example.phr;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SettingsActivity extends Activity {
	
	private RadioGroup radioGroupWeight;
	private RadioGroup radioGroupHeight;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		setTitle("Settings");
		radioGroupHeight = (RadioGroup) findViewById(R.id.radioGroupSettingHeight);
		radioGroupWeight = (RadioGroup) findViewById(R.id.radioGroupSettingWeight);
		setTitle("Settings");
	}

	private String getItemSelectedRadioGroupWeight() {
		int selected = radioGroupWeight.getCheckedRadioButtonId();
        Button radioBtn = (RadioButton) findViewById(selected);
        return radioBtn.getText().toString();
	}

	private String getItemSelectedRadioGroupHeight() {
		int selected = radioGroupHeight.getCheckedRadioButtonId();
        Button radioBtn = (RadioButton) findViewById(selected);
        return radioBtn.getText().toString();
	}

}