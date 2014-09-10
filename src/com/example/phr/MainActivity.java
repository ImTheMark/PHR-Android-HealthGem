package com.example.phr;

import com.example.phr.R;
import com.example.phr.adapter.TabsPagerAdapter;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

@SuppressLint("NewApi")
public class MainActivity extends FragmentActivity implements
		ActionBar.TabListener {

	private ViewPager viewPager;
	private TabsPagerAdapter mAdapter;
	private ActionBar actionBar;
	// Tab titles
	private String[] tabs = { "Summary Report", "Timeline", "Tracker",
			"About Me" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Initilization
		viewPager = (ViewPager) findViewById(R.id.pager);
		actionBar = getActionBar();
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

		viewPager.setAdapter(mAdapter);
		actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		//actionBar.setDisplayShowTitleEnabled(false);
		//actionBar.setDisplayShowHomeEnabled(false);
		// Adding Tabs
		/*
		 * for (String tab_name : tabs) {
		 * actionBar.addTab(actionBar.newTab().setText(tab_name)
		 * .setTabListener(this)); }
		 */
		for (int x = 0; x < tabs.length; x++) {
			switch (x) {
			case 0:
				actionBar.addTab(actionBar.newTab()
						.setIcon(R.drawable.activitymain_selector_summary_report)
						.setTabListener(this));
				break;
			case 1:
				actionBar.addTab(actionBar.newTab()
						.setIcon(R.drawable.activitymain_selector_journal)
						.setTabListener(this));
				break;
			case 2:
				actionBar.addTab(actionBar.newTab()
						.setIcon(R.drawable.activitymain_selector_health_tracker)
						.setTabListener(this));
				break;
			case 3:
				actionBar.addTab(actionBar.newTab()
						.setIcon(R.drawable.activitymain_selector_about_me)
						.setTabListener(this));
				break;
			}

		}

		/**
		 * on swiping the viewpager make respective tab selected
		 * */
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@SuppressLint("NewApi")
			@Override
			public void onPageSelected(int position) {
				// on changing the page
				// make respected tab selected
				actionBar.setSelectedNavigationItem(position);
				setTitle(tabs[position]);
				
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	@SuppressLint("NewApi")
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// on tab selected
		// show respected fragment view
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.activitymain_menu_settings, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_settings:
	        	Intent intent = new Intent(getApplicationContext(),
						SettingsActivity.class);
				startActivity(intent);
	            return true;
	        case R.id.action_notifications:
	        	Intent intent2 = new Intent(getApplicationContext(),
						RetrieveActivity.class);
				startActivity(intent2);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

}
