package com.example.phr.serviceimpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.example.phr.application.HealthGem;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.local_db.SPreference;
import com.example.phr.mobile.models.ActivityTrackerEntry;
import com.example.phr.mobile.models.CalorieTrackerEntry;
import com.example.phr.mobile.models.FoodTrackerEntry;
import com.example.phr.mobile.models.TrackerEntry;
import com.example.phr.mobile.models.Weight;
import com.example.phr.service.ActivityTrackerService;
import com.example.phr.service.CalorieTrackerService;
import com.example.phr.service.FoodTrackerService;
import com.example.phr.tools.DateTimeParser;

public class CalorieTrackerEntryServiceImpl implements CalorieTrackerService {
	
	public static final double HOUR = 0.000277778;

	@Override
	public List<CalorieTrackerEntry> getAll() throws ServiceException {
		List<CalorieTrackerEntry> calorieList = new ArrayList<CalorieTrackerEntry>();
		
		List<TrackerEntry> list = new ArrayList<TrackerEntry>();
		
		ActivityTrackerService actService = new ActivityTrackerServiceImpl();
		FoodTrackerService foodService = new FoodTrackerServiceImpl();
		
		list.addAll(actService.getAll());
		list.addAll(foodService.getAll());
		
		Collections.sort(list, new Comparator<TrackerEntry>() {
			@Override public int compare(TrackerEntry e1, TrackerEntry e2) {
				return e1.getTimestamp().compareTo(e2.getTimestamp());
			}
		});
		
		Collections.reverse(list);
		
		while (list.size() != 0) {
			CalorieTrackerEntry calorieEntry = new CalorieTrackerEntry();
			calorieEntry.setDate(list.get(0)
					.getTimestamp());
			double totalCalBurned = 0.0;
			double totalCalIntake = 0.0;
			
			
			// NOT AVAILABLE
			double requireCal = 0.0;
			
			
			
			
			double weightInPounds = Double.parseDouble(HealthGem.getSharedPreferences().loadPreferences(SPreference.WEIGHT));
			
			List<TrackerEntry> trackerListPerDay = new ArrayList<TrackerEntry>();
			String monthDay = DateTimeParser.getMonthDay(list.get(0)
					.getTimestamp());
			

			do {
				TrackerEntry entry = list.remove(0);
				if(entry.getClass().equals(FoodTrackerEntry.class))
					totalCalIntake += ((FoodTrackerEntry)entry).getFood().getCalorie() * ((FoodTrackerEntry)entry).getServingCount();
				else if(entry.getClass().equals(ActivityTrackerEntry.class))
						totalCalIntake += ((ActivityTrackerEntry)entry).getActivity().getMET() * (((ActivityTrackerEntry)entry).getDurationInSeconds() * HOUR) * weightInPounds;
				trackerListPerDay.add(entry);
			} while (list.size() != 0
					&& monthDay.equals(DateTimeParser.getMonthDay(list.get(0).getTimestamp())));

			calorieEntry.setList(trackerListPerDay);
			calorieEntry.setRequireCal(requireCal);
			calorieEntry.setTotalCalBurned(totalCalBurned);
			calorieEntry.setTotalCalIntake(totalCalIntake);
			
			calorieList.add(calorieEntry);
		}
		
		
		return calorieList;
	}

}
