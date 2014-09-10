package com.example.phr.adapter;

import com.example.phr.AboutMeFragment;
import com.example.phr.JournalFragment;
import com.example.phr.HealthTrackerFragment;
import com.example.phr.SummaryReportFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			// Top Rated fragment activity
			return new SummaryReportFragment();
		case 1:
			// Games fragment activity
			return new JournalFragment();
		case 2:
			// Movies fragment activity
			return new HealthTrackerFragment();
		case 3:
			// Movies fragment activity
			return new AboutMeFragment();
		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 4;
	}

}
