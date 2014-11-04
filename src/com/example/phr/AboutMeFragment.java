package com.example.phr;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.phr.exceptions.ServiceException;
import com.example.phr.mobile.models.BloodPressure;
import com.example.phr.mobile.models.BloodSugar;
import com.example.phr.mobile.models.CheckUp;
import com.example.phr.mobile.models.User;
import com.example.phr.service.BloodPressureTrackerService;
import com.example.phr.service.BloodSugarTrackerService;
import com.example.phr.service.CheckUpTrackerService;
import com.example.phr.service.UserService;
import com.example.phr.serviceimpl.BloodPressureTrackerServiceImpl;
import com.example.phr.serviceimpl.BloodSugarTrackerServiceImpl;
import com.example.phr.serviceimpl.CheckUpTrackerServiceImpl;
import com.example.phr.serviceimpl.UserServiceImpl;
import com.example.phr.tools.DateTimeParser;

public class AboutMeFragment extends Fragment {

	TextView name;
	TextView birthday;
	TextView sex;
	TextView email;
	TextView fb;
	TextView contact;
	TextView weight;
	TextView height;
	TextView systolic;
	TextView diastolic;
	TextView bloodsugar;
	TextView allergies;
	TextView knownHealthProblems;
	TextView emergencyContactNumber;
	TextView emergencyPerson;
	TextView lastcheckup;
	ImageView image;
	BloodPressure bp;
	BloodSugar bs;
	CheckUp checkup;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_about_me, container,
				false);

		birthday = (TextView) rootView.findViewById(R.id.profBday);
		sex = (TextView) rootView.findViewById(R.id.profSex);
		email = (TextView) rootView.findViewById(R.id.profEmail);
		fb = (TextView) rootView.findViewById(R.id.profFb);
		contact = (TextView) rootView.findViewById(R.id.profContact);
		name = (TextView) rootView.findViewById(R.id.profName);
		weight = (TextView) rootView.findViewById(R.id.profWeight);
		height = (TextView) rootView.findViewById(R.id.profHeight);
		systolic = (TextView) rootView.findViewById(R.id.profSystolic);
		diastolic = (TextView) rootView.findViewById(R.id.profDiastolic);
		bloodsugar = (TextView) rootView.findViewById(R.id.profSugarLvl);
		allergies = (TextView) rootView.findViewById(R.id.profAllergies);
		knownHealthProblems = (TextView) rootView
				.findViewById(R.id.profHealthProblem);
		emergencyContactNumber = (TextView) rootView
				.findViewById(R.id.profContactNum);
		emergencyPerson = (TextView) rootView
				.findViewById(R.id.profContactPerson);
		lastcheckup = (TextView) rootView.findViewById(R.id.profLastCheckup);
		image = (ImageView) rootView.findViewById(R.id.profImg);

		UserService userService = new UserServiceImpl();
		User user = userService.getUser();

		name.setText(user.getName());
		birthday.setText(String.valueOf(DateTimeParser.getDate(user
				.getDateOfBirth())));
		sex.setText(user.getGender());
		email.setText(user.getEmail());
		fb.setText(user.getFbAccessToken());
		contact.setText(user.getContactNumber());
		weight.setText(String.valueOf(user.getWeight()));
		height.setText(String.valueOf(user.getHeight()));
		allergies.setText(user.getAllergies());
		knownHealthProblems.setText(user.getKnownHealthProblems());
		emergencyContactNumber.setText(user.getContactNumber());
		emergencyPerson.setText(user.getEmergencyPerson());
		// image.setImageBitmap(ImageHandler.loadImage(user.getPhoto()
		// .getFileName()));
		BloodPressureTrackerService bpService = new BloodPressureTrackerServiceImpl();
		BloodSugarTrackerService bsService = new BloodSugarTrackerServiceImpl();
		CheckUpTrackerService checkupService = new CheckUpTrackerServiceImpl();
		try {
			bp = bpService.getLatest();
			bs = bsService.getLatest();
			checkup = checkupService.getLatest();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (bp != null) {
			systolic.setText(String.valueOf(bp.getSystolic()));
			diastolic.setText(String.valueOf(bp.getDiastolic()));
		} else {
			systolic.setText("N");
			diastolic.setText("A");
		}
		if (bs != null) {
			bloodsugar.setText(String.valueOf(bs.getBloodSugar()));
		} else {
			bloodsugar.setText("N/A");
		}
		if (checkup != null)
			lastcheckup.setText(String.valueOf(DateTimeParser.getDate(checkup
					.getTimestamp())));
		else
			lastcheckup.setText("N/A");
		return rootView;
	}
}
