package com.example.phr.serviceimpl;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.example.phr.exceptions.ServiceException;
import com.example.phr.mobile.models.ActivityTrackerEntry;
import com.example.phr.mobile.models.CalorieTrackerEntry;
import com.example.phr.mobile.models.FoodTrackerEntry;
import com.example.phr.mobile.models.TrackerEntry;
import com.example.phr.mobile.models.User;
import com.example.phr.mobile.models.Weight;
import com.example.phr.service.ActivityTrackerService;
import com.example.phr.service.CalorieTrackerService;
import com.example.phr.service.FoodTrackerService;
import com.example.phr.service.UserService;
import com.example.phr.service.WeightTrackerService;
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
			@Override
			public int compare(TrackerEntry e1, TrackerEntry e2) {
				return e1.getTimestamp().compareTo(e2.getTimestamp());
			}
		});

		Collections.reverse(list);
		Weight weight = null;
		WeightTrackerService weightService = new WeightTrackerServiceImpl();
		try {
			weight = weightService.getLatest();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		UserService userService = new UserServiceImpl();
		User user = userService.getUser();
		Timestamp timestamp;
		// current date
		DateFormat dateFormat;
		DateFormat timeFormat;
		DateFormat fmt;
		Calendar calobj;
		dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
		timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
		fmt = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss", Locale.ENGLISH);
		calobj = Calendar.getInstance();
		Date date = null;

		try {
			date = fmt.parse(dateFormat.format(calobj.getTime()) + " "
					+ timeFormat.format(calobj.getTime()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		timestamp = new Timestamp(date.getTime());
		Timestamp bdaytimestamp = user.getDateOfBirth();
		int age = Integer.parseInt(DateTimeParser.getYear(timestamp))
				- Integer.parseInt(DateTimeParser.getYear(bdaytimestamp));

		double bmr = 0.0;
		// int age = 40;

		if (user.getGender().equals("F"))
			bmr = 655 + (4.35 * weight.getWeightInPounds())
					+ (4.7 * user.getHeight()) - (4.7 * age);
		else if (user.getGender().equals("M"))
			bmr = 66 + (6.23 * weight.getWeightInPounds())
					+ (12.7 * user.getHeight()) - (6.8 * age);

		while (list.size() != 0) {
			CalorieTrackerEntry calorieEntry = new CalorieTrackerEntry();
			calorieEntry.setDate(list.get(0).getTimestamp());
			double totalCalBurned = 0.0;
			double totalCalIntake = 0.0;

			List<TrackerEntry> trackerListPerDay = new ArrayList<TrackerEntry>();
			String monthDay = DateTimeParser.getMonthDay(list.get(0)
					.getTimestamp());

			do {
				TrackerEntry entry = list.remove(0);
				if (entry.getClass().equals(FoodTrackerEntry.class))
					totalCalIntake += ((FoodTrackerEntry) entry).getFood()
							.getCalorie()
							* ((FoodTrackerEntry) entry).getServingCount();
				else if (entry.getClass().equals(ActivityTrackerEntry.class))
					totalCalBurned += ((ActivityTrackerEntry) entry)
							.getActivity().getMET()
							* (((ActivityTrackerEntry) entry)
									.getDurationInSeconds() * HOUR)
							* weight.getWeightInKilograms();
				trackerListPerDay.add(entry);
			} while (list.size() != 0
					&& monthDay.equals(DateTimeParser.getMonthDay(list.get(0)
							.getTimestamp())));

			calorieEntry.setList(trackerListPerDay);
			calorieEntry.setRequireCal(bmr);
			calorieEntry.setTotalCalBurned(totalCalBurned);
			calorieEntry.setTotalCalIntake(totalCalIntake);

			calorieList.add(calorieEntry);
		}

		return calorieList;
	}

}
