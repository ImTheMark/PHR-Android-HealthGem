package com.example.phr.serviceimpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.example.phr.exceptions.DataAccessException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.mobile.dao.MobileActivityTrackerDao;
import com.example.phr.mobile.dao.MobileBloodPressureTrackerDao;
import com.example.phr.mobile.dao.MobileBloodSugarTrackerDao;
import com.example.phr.mobile.dao.MobileCheckupTrackerDao;
import com.example.phr.mobile.dao.MobileFoodTrackerDao;
import com.example.phr.mobile.dao.MobileNoteTrackerDao;
import com.example.phr.mobile.dao.MobileWeightTrackerDao;
import com.example.phr.mobile.daoimpl.MobileActivityTrackerDaoImpl;
import com.example.phr.mobile.daoimpl.MobileBloodPressureTrackerDaoImpl;
import com.example.phr.mobile.daoimpl.MobileBloodSugarTrackerDaoImpl;
import com.example.phr.mobile.daoimpl.MobileCheckupTrackerDaoImpl;
import com.example.phr.mobile.daoimpl.MobileFoodTrackerDaoImpl;
import com.example.phr.mobile.daoimpl.MobileNoteTrackerDaoImpl;
import com.example.phr.mobile.daoimpl.MobileWeightTrackerDaoImpl;
import com.example.phr.mobile.models.TrackerEntry;
import com.example.phr.service.TimelineService;

public class TimelineServiceImpl implements TimelineService {

	MobileActivityTrackerDao mobileActivityTrackerDao;
	MobileBloodPressureTrackerDao mobileBloodPressureTrackerDao;
	MobileBloodSugarTrackerDao mobileBloodSugarTrackerDao;
	MobileCheckupTrackerDao mobileCheckUpTrackerDao;
	MobileFoodTrackerDao mobileFoodTrackerDao;
	MobileNoteTrackerDao mobileNoteTrackerDao;
	MobileWeightTrackerDao mobileWeightTrackerDao;

	public TimelineServiceImpl(){
		mobileActivityTrackerDao = new MobileActivityTrackerDaoImpl();
		mobileBloodPressureTrackerDao = new MobileBloodPressureTrackerDaoImpl();
		mobileBloodSugarTrackerDao = new MobileBloodSugarTrackerDaoImpl();
		mobileCheckUpTrackerDao = new MobileCheckupTrackerDaoImpl();
		mobileFoodTrackerDao = new MobileFoodTrackerDaoImpl();
		mobileNoteTrackerDao= new MobileNoteTrackerDaoImpl();
		mobileWeightTrackerDao = new MobileWeightTrackerDaoImpl();
	}


	@Override
	public List getAll() throws ServiceException {
		List<TrackerEntry> list = new ArrayList<TrackerEntry>();

		try {
			list.addAll(mobileActivityTrackerDao.getAllReversed());
			list.addAll(mobileBloodPressureTrackerDao.getAllReversed());
			list.addAll(mobileBloodSugarTrackerDao.getAllReversed());
			list.addAll(mobileCheckUpTrackerDao.getAllReversed());
			list.addAll(mobileFoodTrackerDao.getAllReversed());
			list.addAll(mobileNoteTrackerDao.getAllReversed());
			list.addAll(mobileWeightTrackerDao.getAllReversed());

			Collections.sort(list, new Comparator<TrackerEntry>() {
				@Override public int compare(TrackerEntry e1, TrackerEntry e2) {
					return e1.getTimestamp().compareTo(e2.getTimestamp());
				}
			});
			
			Collections.reverse(list);
			
		} catch (DataAccessException e) {
			throw new ServiceException(
					"An error occured while trying to get latest foodTracker from local db", e);
		}

		return list;
	}

}
