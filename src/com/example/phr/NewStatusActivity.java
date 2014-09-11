package com.example.phr;

import com.example.phr.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class NewStatusActivity extends Activity {
	
	ImageButton mBtnTagFriend;
	ImageButton mBtnCheckinLocation;
	ImageButton mBtnAddPhoto;
	ImageButton mBtnAddActions;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("Post a Status");
		setContentView(R.layout.activity_new_status);
		/*
		mBtnTagFriend = (ImageButton)findViewById(R.id.btnTagFriend);
		mBtnTagFriend.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						TagFriendActivity.class);
				startActivity(intent);
			}
		});
		
		mBtnCheckinLocation = (ImageButton)findViewById(R.id.btnCheckinLocation);
		mBtnCheckinLocation.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						CheckinLocationActivity.class);
				startActivity(intent);
			}
		});
		*/
		
		mBtnAddPhoto = (ImageButton)findViewById(R.id.btnAddPhoto);
		mBtnAddPhoto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
				startActivity(intent);
			}
		});
		
		mBtnAddActions = (ImageButton)findViewById(R.id.btnAddActions);
		mBtnAddActions.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						AddActionActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_status_post, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_item_status_post:
			onBackPressed();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

}
