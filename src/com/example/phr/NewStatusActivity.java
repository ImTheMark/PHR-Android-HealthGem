package com.example.phr;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.phr.enums.TrackerInputType;
import com.example.phr.exceptions.DataAccessException;
import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.exceptions.WebServerException;
import com.example.phr.mobile.models.ActivityTrackerEntry;
import com.example.phr.mobile.models.BloodPressure;
import com.example.phr.mobile.models.BloodSugar;
import com.example.phr.mobile.models.CheckUp;
import com.example.phr.mobile.models.Note;
import com.example.phr.mobile.models.PHRImage;
import com.example.phr.mobile.models.PHRImageType;
import com.example.phr.mobile.models.Weight;
import com.example.phr.service.ActivityService;
import com.example.phr.service.BloodPressureService;
import com.example.phr.service.BloodSugarService;
import com.example.phr.service.CheckUpService;
import com.example.phr.service.NoteService;
import com.example.phr.service.WeightService;
import com.example.phr.serviceimpl.ActivityServiceImpl;
import com.example.phr.serviceimpl.BloodPressureServiceImpl;
import com.example.phr.serviceimpl.BloodSugarServiceImpl;
import com.example.phr.serviceimpl.CheckUpServiceImpl;
import com.example.phr.serviceimpl.NoteServiceImpl;
import com.example.phr.serviceimpl.WeightServiceImpl;
import com.example.phr.tools.WeightConverter;

public class NewStatusActivity extends Activity {

	ImageButton mBtnTagFriend;
	ImageButton mBtnCheckinLocation;
	ImageButton mBtnAddPhoto;
	ImageButton mBtnAddActions;
	ImageButton mBtnFb;
	NumberPicker systolicPicker;
	NumberPicker diastolicPicker;
	NumberPicker sugarPicker;
	Spinner sugarTypeSpinner;
	Spinner weightUnitSpinner;
	Spinner activityUnitSpinner;
	TextView txtSystolic;
	TextView txtDiastolic;
	TextView txtSugar;
	TextView txtSugarType;
	TextView txtWeight;
	TextView txtWeightUnit;
	TextView txtPurpose;
	TextView txtDoctor;
	TextView txtActivity;
	TextView txtActivityDuration;
	TextView txtActivityDurationUnit;
	TextView txtActivityCal;
	TextView txtFood;
	TextView txtFoodQuantity;
	TextView txtFoodQuantityUnit;
	TextView txtFoodCal;
	TextView txtFoodCarbs;
	TextView txtFoodProtein;
	TextView txtFoodFat;
	TextView txtFoodSize;
	TextView txtFoodUnit;
	EditText bpStatus;
	EditText bsStatus;
	EditText foodStatus;
	EditText notesStatus;
	EditText weight;
	EditText weightStatus;
	EditText checkupStatus;
	EditText activityStatus;
	EditText activityDuration;
	EditText purpose;
	EditText doctor;
	ScrollView bpTemplate;
	ScrollView bsTemplate;
	ScrollView notesTemplate;
	ScrollView weightTemplate;
	ScrollView checkupTemplate;
	ScrollView foodTemplate;
	ScrollView activityTemplate;
	LinearLayout activityCal;
	LinearLayout foodCal;
	String currentTracker;
	DateFormat dateFormat;
	DateFormat timeFormat;
	DateFormat fmt;
	Calendar calobj;
	String mode;
	final Context context = this;
	BloodSugar editBs;
	BloodPressure editBp;
	Weight editWeight;
	Note editNote;
	CheckUp editCheckup;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("Post a Status");
		setContentView(R.layout.activity_new_status);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();

		StrictMode.setThreadPolicy(policy);

		/*
		 * mBtnTagFriend = (ImageButton)findViewById(R.id.btnTagFriend);
		 * mBtnTagFriend.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { Intent intent = new
		 * Intent(getApplicationContext(), TagFriendActivity.class);
		 * startActivity(intent); } });
		 * 
		 * mBtnCheckinLocation =
		 * (ImageButton)findViewById(R.id.btnCheckinLocation);
		 * mBtnCheckinLocation.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { Intent intent = new
		 * Intent(getApplicationContext(), CheckinLocationActivity.class);
		 * startActivity(intent); } });
		 */
		dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
		timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
		fmt = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss", Locale.ENGLISH);
		calobj = Calendar.getInstance();

		currentTracker = TrackerInputType.NOTES;
		// templates
		bsTemplate = (ScrollView) findViewById(R.id.bloodsugar_template);
		bpTemplate = (ScrollView) findViewById(R.id.bloodpressure_template);
		notesTemplate = (ScrollView) findViewById(R.id.notes_template);
		weightTemplate = (ScrollView) findViewById(R.id.weight_template);
		checkupTemplate = (ScrollView) findViewById(R.id.checkup_template);
		foodTemplate = (ScrollView) findViewById(R.id.food_template);
		activityTemplate = (ScrollView) findViewById(R.id.activity_template);
		// blood pressure post
		txtSystolic = (TextView) findViewById(R.id.systolic);
		txtDiastolic = (TextView) findViewById(R.id.diastolic);
		bpStatus = (EditText) findViewById(R.id.txtBPStatus);
		txtSystolic.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				callBloodPressureInput((int) Double.parseDouble(txtSystolic
						.getText().toString()), (int) Double
						.parseDouble(txtDiastolic.getText().toString()));
			}
		});
		txtDiastolic.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				callBloodPressureInput((int) Double.parseDouble(txtSystolic
						.getText().toString()), (int) Double
						.parseDouble(txtDiastolic.getText().toString()));
			}
		});

		// blood sugar post
		txtSugar = (TextView) findViewById(R.id.sugar);
		bsStatus = (EditText) findViewById(R.id.txtBSStatus);
		txtSugarType = (TextView) findViewById(R.id.txtSugarType);

		txtSugar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				callBloodSugarInput(
						(int) Double.parseDouble(txtSugar.getText().toString()),
						txtSugarType.getText().toString());
			}
		});
		txtSugarType.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				callBloodSugarInput(
						(int) Double.parseDouble(txtSugar.getText().toString()),
						txtSugarType.getText().toString());
			}
		});
		// weight post
		txtWeight = (TextView) findViewById(R.id.weight);
		weightStatus = (EditText) findViewById(R.id.txtWeightStatus);
		txtWeightUnit = (TextView) findViewById(R.id.txtWeightUnit);
		// note post
		notesStatus = (EditText) findViewById(R.id.txtNotesStatus);
		// checkup post
		txtDoctor = (TextView) findViewById(R.id.doctor);
		checkupStatus = (EditText) findViewById(R.id.txtBSStatus);
		txtPurpose = (TextView) findViewById(R.id.purpose);
		txtDoctor.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				callCheckUpInput(txtDoctor.getText().toString(), txtPurpose
						.getText().toString());
			}
		});

		txtPurpose.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				callCheckUpInput(txtDoctor.getText().toString(), txtPurpose
						.getText().toString());
			}
		});

		// activity post
		txtActivity = (TextView) findViewById(R.id.activity);
		activityStatus = (EditText) findViewById(R.id.txtActivityStatus);
		txtActivityDurationUnit = (TextView) findViewById(R.id.activityDurationUnit);
		txtActivityDuration = (TextView) findViewById(R.id.activityDuration);
		activityCal = (LinearLayout) findViewById(R.id.activityCal);
		txtActivityCal = (TextView) findViewById(R.id.txtActivityCal);

		// food post
		txtFood = (TextView) findViewById(R.id.food);
		foodStatus = (EditText) findViewById(R.id.txtFoodStatus);
		txtFoodQuantityUnit = (TextView) findViewById(R.id.foodQuantityUnit);
		txtFoodQuantity = (TextView) findViewById(R.id.foodQuantitySize);
		foodCal = (LinearLayout) findViewById(R.id.foodCal);
		txtFoodCal = (TextView) findViewById(R.id.txtfoodCal);
		txtFoodCarbs = (TextView) findViewById(R.id.txtfoodCarbs);
		txtFoodFat = (TextView) findViewById(R.id.txtfoodFat);
		txtFoodProtein = (TextView) findViewById(R.id.txtfoodProtein);

		mBtnAddPhoto = (ImageButton) findViewById(R.id.btnAddPhoto);
		mBtnAddPhoto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
				startActivity(intent);
			}
		});
		mBtnFb = (ImageButton) findViewById(R.id.btnFb);
		mBtnFb.setTag(new Boolean(false)); // wasn't clicked
		mBtnFb.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (((Boolean) mBtnFb.getTag()) == false) {
					mBtnFb.setImageResource(R.drawable.activitynewstatus_fb_click);
					mBtnFb.setTag(new Boolean(true));
				} else {
					mBtnFb.setImageResource(R.drawable.activitynewstatus_fb);
					mBtnFb.setTag(new Boolean(false));
				}
			}
		});

		mBtnAddActions = (ImageButton) findViewById(R.id.btnAddActions);
		mBtnAddActions.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(NewStatusActivity.this,
						AddActionActivity.class);
				startActivityForResult(intent, 2);
			}
		});

		mode = "add";

		Intent in = getIntent();
		Bundle extras = getIntent().getExtras();
		if (extras != null && in.hasExtra("tracker")) {
			mode = "add";
			String tracker = extras.getString("tracker");

			if (tracker.equals(TrackerInputType.BLOOD_PRESSURE)) {
				currentTracker = TrackerInputType.BLOOD_PRESSURE;
				callBloodPressureInput(120, 80);
			} else if (tracker.equals(TrackerInputType.BLOOD_SUGAR)) {
				currentTracker = TrackerInputType.BLOOD_SUGAR;
				callBloodSugarInput(120, "before meal");
			} else if (tracker.equals(TrackerInputType.NOTES)) {
				currentTracker = TrackerInputType.NOTES;
				callNotesInput();
			} else if (tracker.equals(TrackerInputType.WEIGHT)) {
				currentTracker = TrackerInputType.WEIGHT;
				callWeightInput("100", "lb");
			} else if (tracker.equals(TrackerInputType.FOOD)) {
				currentTracker = TrackerInputType.FOOD;
				callFoodInput();
			} else if (tracker.equals(TrackerInputType.CHECKUP)) {
				currentTracker = TrackerInputType.CHECKUP;
				callCheckUpInput("", "");
			} else if (tracker.equals(TrackerInputType.ACTIVITY)) {
				currentTracker = TrackerInputType.ACTIVITY;
				callActivityInput();
			}
		} else if (extras != null && in.hasExtra("from")) {
			String from = extras.getString("from");

			if (from.equals("new activity")) {
				currentTracker = TrackerInputType.ACTIVITY;
				setActivityTemplate(extras.getString("activity_name"),
						extras.getString("activity_cal"),
						extras.getString("activity_duration"),
						extras.getString("activity_unit"), "");

			} else if (from.equals("new food")) {
				currentTracker = TrackerInputType.FOOD;
				setFoodTemplate(extras.getString("food_name"),
						extras.getString("food_cal"),
						extras.getString("food_protein"),
						extras.getString("food_carbs"),
						extras.getString("food_fat"),
						extras.getString("food_serving"),
						extras.getString("food_unit"), "");
			}
		} else if (extras != null && in.hasExtra("edit")) {
			String editTracker = extras.getString("edit");
			if (editTracker.equals(TrackerInputType.BLOOD_SUGAR)) {
				editBs = (BloodSugar) in.getExtras().getSerializable("object");
				mode = "edit";
				currentTracker = TrackerInputType.BLOOD_SUGAR;
				Log.e("editbsobject", String.valueOf(editBs.getEntryID()));
				setBloodSugarTemplate(String.valueOf(editBs.getBloodSugar()),
						editBs.getType(), editBs.getStatus());

			} else if (editTracker.equals(TrackerInputType.BLOOD_PRESSURE)) {
				editBp = (BloodPressure) in.getExtras().getSerializable(
						"object");
				mode = "edit";
				currentTracker = TrackerInputType.BLOOD_PRESSURE;
				Log.e("editbsobject", String.valueOf(editBp.getEntryID()));
				setBloodPressureTemplate(String.valueOf(editBp.getSystolic()),
						String.valueOf(editBp.getDiastolic()),
						editBp.getStatus());
			} else if (editTracker.equals(TrackerInputType.CHECKUP)) {
				editCheckup = (CheckUp) in.getExtras()
						.getSerializable("object");
				mode = "edit";
				currentTracker = TrackerInputType.CHECKUP;
				Log.e("editbsobject", String.valueOf(editCheckup.getEntryID()));
				setCheckupTemplate(editCheckup.getDoctorsName(),
						editCheckup.getPurpose(), editCheckup.getNotes());
			}
		}
	}

	private void setFoodTemplate(String food, String cal, String protein,
			String carbs, String fat, String serving, String unit, String status) {
		// TODO Auto-generated method stub
		setAllTemplateGone();
		foodTemplate.setVisibility(View.VISIBLE);
		foodCal.setVisibility(View.VISIBLE);
		txtFoodCal.setText(cal);
		txtFoodProtein.setText(protein);
		txtFoodCarbs.setText(carbs);
		txtFoodFat.setText(fat);
		txtFood.setText(food);
		txtFoodQuantityUnit.setText(unit);
		txtFoodQuantity.setText(serving);
		if (mode.equals("add"))
			foodStatus.setHint("how you feel? ");
		else if (mode.equals("edit"))
			foodStatus.setText(status);

	}

	private void setActivityTemplate(String name, String met, String duration,
			String unit, String status) {
		// TODO Auto-generated method stub
		setAllTemplateGone();
		activityTemplate.setVisibility(View.VISIBLE);
		activityCal.setVisibility(View.VISIBLE);
		txtActivity.setText(name);
		txtActivityDurationUnit.setText(unit);
		txtActivityDuration.setText(duration);
		double cal = Double.parseDouble(met) * 5; // not true
		txtActivityCal.setText(String.valueOf(cal));
		if (mode.equals("add"))
			activityStatus.setHint("how you feel? ");
		else if (mode.equals("edit"))
			activityStatus.setText(status);
	}

	private void setCheckupTemplate(String doctor, String purpose, String status) {
		setAllTemplateGone();
		checkupTemplate.setVisibility(View.VISIBLE);
		txtDoctor.setText(doctor);
		txtPurpose.setText(purpose);
		if (mode.equals("add"))
			checkupStatus.setHint("how you feel? ");
		else if (mode.equals("edit"))
			checkupStatus.setText(status);
	}

	private void setWeightTemplate(String weight, String unit, String status) {
		setAllTemplateGone();
		weightTemplate.setVisibility(View.VISIBLE);
		txtWeight.setText(weight);
		txtWeightUnit.setText(unit);
		if (mode.equals("add"))
			weightStatus.setHint("how you feel? ");
		else if (mode.equals("edit"))
			weightStatus.setHint(status);
	}

	private void setBloodSugarTemplate(String bloodsugar,
			String bloodsugartype, String status) {
		setAllTemplateGone();
		bsTemplate.setVisibility(View.VISIBLE);
		txtSugar.setText(bloodsugar);
		txtSugarType.setText(bloodsugartype);
		if (mode.equals("add"))
			bsStatus.setHint("how you feel? ");
		else if (mode.equals("edit"))
			bsStatus.setText(status);
	}

	private void setBloodPressureTemplate(String systolic, String diastolic,
			String status) {
		setAllTemplateGone();
		bpTemplate.setVisibility(View.VISIBLE);
		txtSystolic.setText(systolic);
		txtDiastolic.setText(diastolic);
		if (mode.equals("add"))
			bpStatus.setHint("how you feel? ");
		else if (mode.equals("edit"))
			bpStatus.setText(status);
	}

	private void setNoteTemplate(String note) {
		setAllTemplateGone();
		notesTemplate.setVisibility(View.VISIBLE);
		if (mode.equals("add"))
			notesStatus.setHint("how you feel? ");
		else if (mode.equals("edit"))
			notesStatus.setText(note);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		// check if the request code is same as what is passed here it is 2
		if (requestCode == 2) {
			String item = data.getStringExtra("itemValue");
			Log.e("itemValue = ", item);
			if (item.equals(TrackerInputType.BLOOD_PRESSURE)) {
				currentTracker = TrackerInputType.BLOOD_PRESSURE;
				callBloodPressureInput(120, 80);
			} else if (item.equals(TrackerInputType.BLOOD_SUGAR)) {
				currentTracker = TrackerInputType.BLOOD_SUGAR;
				callBloodSugarInput(120, "before meal");
			} else if (item.equals(TrackerInputType.NOTES)) {
				currentTracker = TrackerInputType.NOTES;
				callNotesInput();
			} else if (item.equals(TrackerInputType.WEIGHT)) {
				currentTracker = TrackerInputType.WEIGHT;
				callWeightInput("100", "lb");
			} else if (item.equals(TrackerInputType.FOOD)) {
				currentTracker = TrackerInputType.FOOD;
				callFoodInput();
			} else if (item.equals(TrackerInputType.CHECKUP)) {
				currentTracker = TrackerInputType.CHECKUP;
				callCheckUpInput("doctor's name", "purpose");
			} else if (item.equals(TrackerInputType.ACTIVITY)) {
				currentTracker = TrackerInputType.ACTIVITY;
				callActivityInput();
			}
		} else if (requestCode == 3) {
			String activity = data.getStringExtra("activity chosen");
			currentTracker = TrackerInputType.ACTIVITY;
			callActivityDurationInput(activity);
		} else if (requestCode == 4) {
			String food = data.getStringExtra("food chosen");
			String serving = data.getStringExtra("serving");
			double cal = data.getDoubleExtra("cal", 0.0);
			double protein = data.getDoubleExtra("protein", 0.0);
			double carbs = data.getDoubleExtra("carbs", 0.0);
			double fat = data.getDoubleExtra("fat", 0.0);
			currentTracker = TrackerInputType.FOOD;
			callFoodServingInput(food, serving, cal, protein, carbs, fat);
		}
	}

	private void callFoodServingInput(final String food, final String serving,
			final double cal, final double protein, final double carbs,
			final double fat) {
		// TODO Auto-generated method stub

		LayoutInflater layoutInflater = LayoutInflater.from(context);

		View foodView = layoutInflater.inflate(
				R.layout.item_food_serving_input, null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);
		alertDialogBuilder.setView(foodView);
		txtFoodSize = (EditText) foodView.findViewById(R.id.txtFoodServingSize);
		txtFoodUnit = (TextView) foodView.findViewById(R.id.foodUnit);
		txtFoodUnit.setText(serving);
		alertDialogBuilder
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						// set activity cal -- get from database
						setFoodTemplate(food, String.valueOf(cal),
								String.valueOf(protein), String.valueOf(carbs),
								String.valueOf(fat),
								String.valueOf(txtFoodSize.getText()), serving,
								foodStatus.getText().toString());

					}
				})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});

		// create an alert dialog
		AlertDialog alertD = alertDialogBuilder.create();
		alertD.show();

	}

	private void callActivityDurationInput(final String activity) {
		// TODO Auto-generated method stub

		LayoutInflater layoutInflater = LayoutInflater.from(context);

		View activityView = layoutInflater.inflate(
				R.layout.item_activity_duration_input, null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);
		alertDialogBuilder.setView(activityView);
		activityDuration = (EditText) activityView
				.findViewById(R.id.txtActivityDurationTime);
		activityUnitSpinner = (Spinner) activityView
				.findViewById(R.id.activityUnitSpinner);
		alertDialogBuilder
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						// set activity met -- get from database
						setActivityTemplate(activity, "30", activityDuration
								.getText().toString(),
								String.valueOf(activityUnitSpinner
										.getSelectedItem()), "How you feel? ");
					}
				})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});

		// create an alert dialog
		AlertDialog alertD = alertDialogBuilder.create();
		alertD.show();
	}

	private void callActivityInput() {
		// TODO Auto-generated method stub

		Intent intent = new Intent(getApplicationContext(),
				ActivitiesSearchListActivity.class);
		startActivityForResult(intent, 3);

	}

	private void callCheckUpInput(String txtDoctor, String txtPurpose) {
		// TODO Auto-generated method stub
		setAllTemplateGone();

		LayoutInflater layoutInflater = LayoutInflater.from(context);

		View checkupView = layoutInflater.inflate(R.layout.item_checkup_input,
				null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);
		alertDialogBuilder.setView(checkupView);
		doctor = (EditText) checkupView.findViewById(R.id.txtDoctor);
		doctor.setText(txtDoctor);
		purpose = (EditText) checkupView.findViewById(R.id.txtPurpose);
		purpose.setText(txtPurpose);
		alertDialogBuilder
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {

						setCheckupTemplate(doctor.getText().toString(), purpose
								.getText().toString(), checkupStatus.getText()
								.toString());
					}
				})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});

		// create an alert dialog
		AlertDialog alertD = alertDialogBuilder.create();
		alertD.show();
	}

	private void callFoodInput() {
		// TODO Auto-generated method stub

		Intent intent = new Intent(getApplicationContext(),
				foodSearchListActivity.class);
		startActivityForResult(intent, 4);
	}

	private void callNotesInput() {
		// TODO Auto-generated method stub
		setNoteTemplate(notesStatus.getText().toString());
	}

	private void callWeightInput(String txtWeight, String txtUnit) {
		// TODO Auto-generated method stub
		LayoutInflater layoutInflater = LayoutInflater.from(context);

		View weightView = layoutInflater.inflate(R.layout.item_weight_input,
				null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);
		alertDialogBuilder.setView(weightView);
		weight = (EditText) weightView.findViewById(R.id.txtWeight);
		weight.setText(txtWeight);
		weightUnitSpinner = (Spinner) weightView
				.findViewById(R.id.weightUnitSpinner);
		alertDialogBuilder
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {

						setWeightTemplate(weight.getText().toString(), String
								.valueOf(weightUnitSpinner.getSelectedItem()),
								weightStatus.getText().toString());

					}
				})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});

		// create an alert dialog
		AlertDialog alertD = alertDialogBuilder.create();
		alertD.show();
	}

	private void callBloodSugarInput(int txtbs, String txttype) {
		// TODO Auto-generated method stub
		LayoutInflater layoutInflater = LayoutInflater.from(context);

		View bsView = layoutInflater.inflate(R.layout.item_bloodsugar_input,
				null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);
		alertDialogBuilder.setView(bsView);
		sugarPicker = (NumberPicker) bsView.findViewById(R.id.sugarPicker);
		sugarTypeSpinner = (Spinner) bsView.findViewById(R.id.sugarTypeSpinner);
		sugarPicker.setCurrent(txtbs);
		alertDialogBuilder
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {

						Log.e("sugar",
								Integer.toString(sugarPicker.getCurrent()));
						setBloodSugarTemplate(Integer.toString(sugarPicker
								.getCurrent()), String.valueOf(sugarTypeSpinner
								.getSelectedItem()), bsStatus.getText()
								.toString());
					}
				})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});

		// create an alert dialog
		AlertDialog alertD = alertDialogBuilder.create();
		alertD.show();

	}

	private void callBloodPressureInput(int systolic, int diastolic) {
		// TODO Auto-generated method stub

		LayoutInflater layoutInflater = LayoutInflater.from(context);
		View bpView = layoutInflater.inflate(R.layout.item_bloodpressure_input,
				null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);
		alertDialogBuilder.setView(bpView);
		systolicPicker = (NumberPicker) bpView
				.findViewById(R.id.systolicPicker);
		diastolicPicker = (NumberPicker) bpView
				.findViewById(R.id.diastolicPicker);
		systolicPicker.setCurrent(systolic);
		diastolicPicker.setCurrent(diastolic);
		alertDialogBuilder
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {

						Log.e("systolic",
								Integer.toString(systolicPicker.getCurrent()));
						setBloodPressureTemplate(
								Integer.toString(systolicPicker.getCurrent()),
								Integer.toString(diastolicPicker.getCurrent()),
								bpStatus.getText().toString());

					}
				})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});

		// create an alert dialog
		AlertDialog alertD = alertDialogBuilder.create();
		alertD.show();
	}

	private void addBloodPressureToDatabase() throws ServiceException,
			OutdatedAccessTokenException, WebServerException,
			DataAccessException {

		try {
			Date date = fmt.parse(dateFormat.format(calobj.getTime()) + " "
					+ timeFormat.format(calobj.getTime()));
			Timestamp timestamp = new Timestamp(date.getTime());

			PHRImage image = new PHRImage("test-image", PHRImageType.IMAGE);
			BloodPressure bp = new BloodPressure(timestamp, bpStatus.getText()
					.toString(), null, systolicPicker.getCurrent(),
					diastolicPicker.getCurrent());

			BloodPressureService bpService = new BloodPressureServiceImpl();
			bpService.add(bp);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void addBloodSugarToDatabase() throws ServiceException,
			OutdatedAccessTokenException, WebServerException,
			DataAccessException {

		try {

			Date date = fmt.parse(dateFormat.format(calobj.getTime()) + " "
					+ timeFormat.format(calobj.getTime()));
			Timestamp timestamp = new Timestamp(date.getTime());

			PHRImage image = new PHRImage("test-image", PHRImageType.IMAGE);
			BloodSugar bs = new BloodSugar(timestamp, bsStatus.getText()
					.toString(), null, sugarPicker.getCurrent(),
					String.valueOf(sugarTypeSpinner.getSelectedItem()));
			BloodSugarService bsService = new BloodSugarServiceImpl();
			bsService.add(bs);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void addWeightToDatabase() throws ServiceException,
			OutdatedAccessTokenException, WebServerException,
			DataAccessException {

		try {

			Date date = fmt.parse(dateFormat.format(calobj.getTime()) + " "
					+ timeFormat.format(calobj.getTime()));
			Timestamp timestamp = new Timestamp(date.getTime());

			PHRImage image = new PHRImage("test-image", PHRImageType.IMAGE);
			double newWeight;
			if (String.valueOf(weightUnitSpinner.getSelectedItem())
					.equals("kg")) {
				newWeight = WeightConverter.convertKgToLbs(Double
						.parseDouble(String.valueOf(txtWeight.getText())));
			} else {
				newWeight = Double.parseDouble(String.valueOf(txtWeight
						.getText()));
			}
			Weight weight = new Weight(timestamp, weightStatus.getText()
					.toString(), null, newWeight);

			WeightService weightService = new WeightServiceImpl();
			weightService.add(weight);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void addNoteToDatabase() throws ServiceException,
			OutdatedAccessTokenException, WebServerException,
			DataAccessException {

		try {

			Date date = fmt.parse(dateFormat.format(calobj.getTime()) + " "
					+ timeFormat.format(calobj.getTime()));
			Timestamp timestamp = new Timestamp(date.getTime());
			PHRImage image = new PHRImage("test-image", PHRImageType.IMAGE);
			Note note = new Note(timestamp, null, null, notesStatus.getText()
					.toString());

			NoteService noteService = new NoteServiceImpl();
			noteService.add(note);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void addFoodToDatabase() throws ServiceException,
			OutdatedAccessTokenException {

		try {

			Date date = fmt.parse(dateFormat.format(calobj.getTime()) + " "
					+ timeFormat.format(calobj.getTime()));
			Timestamp timestamp = new Timestamp(date.getTime());
			PHRImage image = new PHRImage("test-image", PHRImageType.IMAGE);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void addCheckUpToDatabase() throws ServiceException,
			OutdatedAccessTokenException {

		try {

			Date date = fmt.parse(dateFormat.format(calobj.getTime()) + " "
					+ timeFormat.format(calobj.getTime()));
			Timestamp timestamp = new Timestamp(date.getTime());
			PHRImage image = new PHRImage("test-image", PHRImageType.IMAGE);
			CheckUp checkup = new CheckUp(timestamp, null, null, txtPurpose
					.getText().toString(), txtDoctor.getText().toString(),
					checkupStatus.getText().toString());

			CheckUpService checkupService = new CheckUpServiceImpl();
			checkupService.add(checkup);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void addActivityToDatabase() throws ServiceException,
			OutdatedAccessTokenException {

		try {

			Date date = fmt.parse(dateFormat.format(calobj.getTime()) + " "
					+ timeFormat.format(calobj.getTime()));
			Timestamp timestamp = new Timestamp(date.getTime());
			PHRImage image = new PHRImage("test-image", PHRImageType.IMAGE);
			com.example.phr.mobile.models.Activity activity = new com.example.phr.mobile.models.Activity(
					txtActivity.getText().toString(), 30.0);
			ActivityTrackerEntry activityEntry = new ActivityTrackerEntry(
					timestamp, activityStatus.getText().toString(), null,
					activity, Double.parseDouble(txtActivityCal.getText()
							.toString()));

			// reference for calling
			// txtActivityDurationUnit.setText(unit);
			// txtActivityDuration.setText(duration);

			ActivityService activityService = new ActivityServiceImpl();
			activityService.add(activityEntry);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void setAllTemplateGone() {
		bsTemplate.setVisibility(View.GONE);
		bpTemplate.setVisibility(View.GONE);
		notesTemplate.setVisibility(View.GONE);
		weightTemplate.setVisibility(View.GONE);
		checkupTemplate.setVisibility(View.GONE);
		activityTemplate.setVisibility(View.GONE);
		foodTemplate.setVisibility(View.GONE);
		activityCal.setVisibility(View.GONE);
		foodCal.setVisibility(View.GONE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (mode.equals("add"))
			getMenuInflater().inflate(R.menu.menu_status_post, menu);
		else if (mode.equals("edit"))
			getMenuInflater().inflate(R.menu.menu_edit_status, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_item_status_post:
			try {
				if (currentTracker.equals(TrackerInputType.BLOOD_PRESSURE)) {

					addBloodPressureToDatabase();
					Log.e("added", "bp");
					Intent intent = new Intent(getApplicationContext(),
							BloodPressureTrackerActivity.class);
					startActivity(intent);
				}

				else if (currentTracker.equals(TrackerInputType.BLOOD_SUGAR)) {

					addBloodSugarToDatabase();
					Intent intent = new Intent(getApplicationContext(),
							BloodSugarTrackerActivity.class);
					startActivity(intent);
				} else if (currentTracker.equals(TrackerInputType.WEIGHT)) {

					addWeightToDatabase();
					Intent intent = new Intent(getApplicationContext(),
							WeightTrackerActivity.class);
					startActivity(intent);
				} else if (currentTracker.equals(TrackerInputType.CHECKUP)) {

					addCheckUpToDatabase();
					Intent intent = new Intent(getApplicationContext(),
							CheckupTrackerActivity.class);
					startActivity(intent);
				} else if (currentTracker.equals(TrackerInputType.NOTES)) {

					addNoteToDatabase();
					Intent intent = new Intent(getApplicationContext(),
							NoteTrackerActivity.class);
					startActivity(intent);
				} else if (currentTracker.equals(TrackerInputType.ACTIVITY)) {

					addActivityToDatabase();
					Intent intent = new Intent(getApplicationContext(),
							ActivitiesTrackerActivity.class);
					startActivity(intent);
				} else if (currentTracker.equals(TrackerInputType.FOOD)) {

					addFoodToDatabase();
					Intent intent = new Intent(getApplicationContext(),
							FoodTrackerDailyActivity.class);
					startActivity(intent);
				}
			} catch (ServiceException e) {
				// output error message or something
				System.out.println(e.getMessage());
			} catch (OutdatedAccessTokenException e) {
				// Message - > Log user out
				e.printStackTrace();
			} catch (WebServerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DataAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;

		case R.id.menu_item_status_edit:
			try {
				if (currentTracker.equals(TrackerInputType.BLOOD_SUGAR)) {
					Log.e("call", "edit");
					editBloodSugarToDatabase();
					Log.e("call", "bsActivity");
					Intent intent = new Intent(getApplicationContext(),
							BloodSugarTrackerActivity.class);
					startActivity(intent);
				} else if (currentTracker
						.equals(TrackerInputType.BLOOD_PRESSURE)) {
					editBloodPressureToDatabase();
					Log.e("call", "bpActivity");
					Intent intent = new Intent(getApplicationContext(),
							BloodPressureTrackerActivity.class);
					startActivity(intent);
				} else if (currentTracker.equals(TrackerInputType.WEIGHT)) {
					editWeightToDatabase();
					Log.e("call", "weightActivity");
					Intent intent = new Intent(getApplicationContext(),
							WeightTrackerActivity.class);
					startActivity(intent);
				} else if (currentTracker.equals(TrackerInputType.CHECKUP)) {
					editCheckUpToDatabase();
					Log.e("call", "checkupActivity");
					Intent intent = new Intent(getApplicationContext(),
							CheckupTrackerActivity.class);
					startActivity(intent);
				} else if (currentTracker.equals(TrackerInputType.NOTES)) {
					editNoteToDatabase();
					Log.e("call", "noteActivity");
					Intent intent = new Intent(getApplicationContext(),
							NoteTrackerActivity.class);
					startActivity(intent);
				} else if (currentTracker.equals(TrackerInputType.FOOD)) {
					editFoodToDatabase();
					Log.e("call", "foodActivity");
					Intent intent = new Intent(getApplicationContext(),
							FoodTrackerDailyActivity.class);
					startActivity(intent);
				} else if (currentTracker.equals(TrackerInputType.ACTIVITY)) {
					editActivityToDatabase();
					Log.e("call", "acitivityActivity");
					Intent intent = new Intent(getApplicationContext(),
							ActivitiesTrackerActivity.class);
					startActivity(intent);
				}
			} catch (ServiceException e) {
				// output error message or something
				System.out.println(e.getMessage());
			} catch (OutdatedAccessTokenException e) {
				// Message - > Log user out
				e.printStackTrace();
			} catch (WebServerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DataAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	private void editBloodSugarToDatabase() throws ServiceException,
			OutdatedAccessTokenException, WebServerException,
			DataAccessException {

		try {
			Log.e("in", "edit");
			BloodSugar bs = new BloodSugar(editBs.getEntryID(),
					editBs.getTimestamp(), bsStatus.getText().toString(), null,
					Double.parseDouble(txtSugar.getText().toString()),
					txtSugarType.getText().toString());
			BloodSugarService bsService = new BloodSugarServiceImpl();
			bsService.edit(bs);
			Log.e("in", "edited");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void editBloodPressureToDatabase() throws ServiceException,
			OutdatedAccessTokenException, WebServerException,
			DataAccessException {

		try {
			Log.e("in", "edit");
			BloodPressure bp = new BloodPressure(editBp.getEntryID(),
					editBp.getTimestamp(), bpStatus.getText().toString(), null,
					Integer.parseInt(txtSystolic.getText().toString()),
					Integer.parseInt(txtDiastolic.getText().toString()));

			BloodPressureService bpService = new BloodPressureServiceImpl();
			bpService.edit(bp);

			Log.e("in", "edited");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void editWeightToDatabase() throws ServiceException,
			OutdatedAccessTokenException, WebServerException,
			DataAccessException {

		try {
			Log.e("in", "edit");
			double newWeight;
			if (txtWeightUnit.equals("kg")) {
				newWeight = WeightConverter.convertKgToLbs(Double
						.parseDouble(String.valueOf(txtWeight.getText())));
			} else {
				newWeight = Double.parseDouble(String.valueOf(txtWeight
						.getText()));
			}
			Weight weight = new Weight(editWeight.getEntryID(),
					editWeight.getTimestamp(), weightStatus.getText()
							.toString(), null, newWeight);

			WeightService weightService = new WeightServiceImpl();
			weightService.edit(weight);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void editCheckUpToDatabase() throws ServiceException,
			OutdatedAccessTokenException, WebServerException,
			DataAccessException {

		try {
			Log.e("in", "edit");
			CheckUp checkup = new CheckUp(editCheckup.getEntryID(),
					editCheckup.getTimestamp(), null, null, txtPurpose
							.getText().toString(), txtDoctor.getText()
							.toString(), checkupStatus.getText().toString());

			CheckUpService checkupService = new CheckUpServiceImpl();
			checkupService.edit(checkup);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void editNoteToDatabase() throws ServiceException,
			OutdatedAccessTokenException, WebServerException,
			DataAccessException {

		try {
			Log.e("in", "edit");
			Note note = new Note(editNote.getEntryID(),
					editNote.getTimestamp(), null, null, notesStatus.getText()
							.toString());

			NoteService noteService = new NoteServiceImpl();
			noteService.edit(note);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void editFoodToDatabase() throws ServiceException,
			OutdatedAccessTokenException, WebServerException,
			DataAccessException {

		try {
			Log.e("in", "edit");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void editActivityToDatabase() throws ServiceException,
			OutdatedAccessTokenException, WebServerException,
			DataAccessException {

		try {
			Log.e("in", "edit");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
