package com.example.phr.dummyservices;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.phr.exceptions.ServiceException;
import com.example.phr.mobile.models.Activity;
import com.example.phr.mobile.models.ActivityTrackerEntry;
import com.example.phr.mobile.models.BloodPressure;
import com.example.phr.mobile.models.BloodSugar;
import com.example.phr.mobile.models.PHRImage;
import com.example.phr.mobile.models.TrackerEntry;
import com.example.phr.mobile.models.Weight;
import com.example.phr.service.TimelineService;

public class TimelineServiceDummyImpl implements TimelineService {

	@Override
	public List getAll() throws ServiceException {
		List<TrackerEntry> list = new ArrayList<TrackerEntry>();

		
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getDate());
		
		Activity activity = new Activity("Jogging", 23.0);
		
		ActivityTrackerEntry e1 = new ActivityTrackerEntry(242332, "234234",
				timestamp, "Jogging in the morning!", null,
				activity, 21.0,
				32423);
		
		BloodPressure bp = new BloodPressure(timestamp, "Ate too much", null, 120, 170);
		
		BloodSugar bs = new BloodSugar(timestamp, "I think I need to eat less sugar", null, 12, "After Meal");
		
		Weight w = new Weight(timestamp, "Need to lose some pounds", null);
		
		list.add(e1);
		list.add(bp);
		list.add(bs);
		list.add(w);
		
		return list;
	}

}
