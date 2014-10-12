package com.example.phr;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.phr.enums.TrackerInputType;
import com.example.phr.exceptions.DataAccessException;
import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.exceptions.WebServerException;
import com.example.phr.mobile.models.ActivitySingle;
import com.example.phr.mobile.models.ActivityTrackerEntry;
import com.example.phr.mobile.models.BloodPressure;
import com.example.phr.mobile.models.BloodSugar;
import com.example.phr.mobile.models.CheckUp;
import com.example.phr.mobile.models.Note;
import com.example.phr.mobile.models.PHRImage;
import com.example.phr.mobile.models.PHRImageType;
import com.example.phr.mobile.models.Weight;
import com.example.phr.service.ActivityTrackerService;
import com.example.phr.service.BloodPressureTrackerService;
import com.example.phr.service.BloodSugarTrackerService;
import com.example.phr.service.CheckUpTrackerService;
import com.example.phr.service.NoteTrackerService;
import com.example.phr.service.WeightTrackerService;
import com.example.phr.serviceimpl.ActivityTrackerServiceImpl;
import com.example.phr.serviceimpl.BloodPressureTrackerServiceImpl;
import com.example.phr.serviceimpl.BloodSugarTrackerServiceImpl;
import com.example.phr.serviceimpl.CheckUpTrackerServiceImpl;
import com.example.phr.serviceimpl.NoteTrackerServiceImpl;
import com.example.phr.serviceimpl.WeightTrackerServiceImpl;
import com.example.phr.tools.ImageHandler;
import com.example.phr.tools.WeightConverter;

public class NewStatusActivity extends Activity {

	ImageButton mBtnTagFriend;
	ImageButton mBtnCheckinLocation;
	ImageButton mBtnAddPhoto;
	ImageButton mBtnAddActions;
	ImageButton mBtnFb;
	ImageButton mBtnImageDelete;
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
	EditText notesStatus;
	EditText weight;
	EditText activityDuration;
	EditText purpose;
	EditText doctor;
	LinearLayout bpTemplate;
	LinearLayout bsTemplate;
	LinearLayout weightTemplate;
	LinearLayout checkupTemplate;
	LinearLayout foodTemplate;
	LinearLayout activityTemplate;
	LinearLayout activityCal;
	LinearLayout foodCal;
	String currentTracker;
	RelativeLayout imageTemplate;
	DateFormat dateFormat;
	DateFormat timeFormat;
	DateFormat fmt;
	Calendar calobj;
	String mode;
	String kind;
	final Context context = this;
	BloodSugar editBs;
	BloodPressure editBp;
	Weight editWeight;
	Note editNote;
	CheckUp editCheckup;
	ActivitySingle chosenActivity;
	ActivitySingle addActivity;
	private static final int CAMERA_REQUEST = 1888;
	ImageView statusImage;
	Bitmap photo;
	Boolean setImage;

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

		setImage = false;
		currentTracker = TrackerInputType.NOTES;
		// templates
		bsTemplate = (LinearLayout) findViewById(R.id.bloodsugar_template);
		bpTemplate = (LinearLayout) findViewById(R.id.bloodpressure_template);
		weightTemplate = (LinearLayout) findViewById(R.id.weight_template);
		checkupTemplate = (LinearLayout) findViewById(R.id.checkup_template);
		foodTemplate = (LinearLayout) findViewById(R.id.food_template);
		activityTemplate = (LinearLayout) findViewById(R.id.activity_template);
		// blood pressure post
		txtSystolic = (TextView) findViewById(R.id.systolic);
		txtDiastolic = (TextView) findViewById(R.id.diastolic);
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
		txtWeightUnit = (TextView) findViewById(R.id.txtWeightUnit);
		txtWeight.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				callWeightInput(txtWeight.getText().toString(), txtWeightUnit
						.getText().toString());
			}
		});

		// note post
		notesStatus = (EditText) findViewById(R.id.txtStatus);
		// checkup post
		txtDoctor = (TextView) findViewById(R.id.doctor);
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
		txtActivityDurationUnit = (TextView) findViewById(R.id.activityDurationUnit);
		txtActivityDuration = (TextView) findViewById(R.id.activityDuration);
		activityCal = (LinearLayout) findViewById(R.id.activityCal);
		txtActivityCal = (TextView) findViewById(R.id.txtActivityCal);
		txtActivityDuration.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				callActivityDurationInput(Integer.parseInt(txtActivityDuration
						.getText().toString()), txtActivityDurationUnit
						.toString());
			}
		});
		txtActivityDurationUnit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				callActivityDurationInput(Integer.parseInt(txtActivityDuration
						.getText().toString()), txtActivityDurationUnit
						.toString());
			}
		});

		// food post
		txtFood = (TextView) findViewById(R.id.food);
		txtFoodQuantityUnit = (TextView) findViewById(R.id.foodQuantityUnit);
		txtFoodQuantity = (TextView) findViewById(R.id.foodQuantitySize);
		foodCal = (LinearLayout) findViewById(R.id.foodCal);
		txtFoodCal = (TextView) findViewById(R.id.txtfoodCal);
		txtFoodCarbs = (TextView) findViewById(R.id.txtfoodCarbs);
		txtFoodFat = (TextView) findViewById(R.id.txtfoodFat);
		txtFoodProtein = (TextView) findViewById(R.id.txtfoodProtein);

		imageTemplate = (RelativeLayout) findViewById(R.id.imageHolder);
		mBtnImageDelete = (ImageButton) findViewById(R.id.imageDelete);
		mBtnImageDelete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setImage = false;
				imageTemplate.setVisibility(View.GONE);
			}
		});
		statusImage = (ImageView) findViewById(R.id.statusImage);
		mBtnAddPhoto = (ImageButton) findViewById(R.id.btnAddPhoto);
		mBtnAddPhoto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent cameraIntent = new Intent(
						android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(cameraIntent, CAMERA_REQUEST);
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
				kind = "new";
				addActivity = (ActivitySingle) in.getExtras().getSerializable(
						"activity added");
				setActivityTemplate(extras.getString("activity_name"),
						addActivity.getMET(),
						extras.getString("activity_duration"),
						extras.getString("activity_unit"), "");

			} else if (from.equals("new food")) {
				currentTracker = TrackerInputType.FOOD;
				kind = "new";
				/*
				 * setFoodTemplate(extras.getString("food_name"),
				 * extras.getString("food_cal"),
				 * extras.getString("food_protein"),
				 * extras.getString("food_carbs"), extras.getString("food_fat"),
				 * extras.getString("food_serving"),
				 * extras.getString("food_unit"), "");
				 */

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
			} else if (editTracker.equals(TrackerInputType.NOTES)) {
				editNote = (Note) in.getExtras().getSerializable("object");
				mode = "edit";
				currentTracker = TrackerInputType.NOTES;
				Log.e("editbsobject", String.valueOf(editNote.getEntryID()));
				setNoteTemplate(editNote.getNote());
			} else if (editTracker.equals(TrackerInputType.WEIGHT)) {
				editWeight = (Weight) in.getExtras().getSerializable("object");
				mode = "edit";
				currentTracker = TrackerInputType.WEIGHT;
				Log.e("editbsobject", String.valueOf(editWeight.getEntryID()));
				setWeightTemplate(
						String.valueOf(editWeight.getWeightInPounds()), "lb",
						editWeight.getStatus());
			}
		}
	}

	private void setEditTemplate() {
		mBtnAddActions.setVisibility(View.GONE);
		mBtnFb.setVisibility(View.GONE);
	}

	private void setAddTemplate() {
		mBtnAddActions.setVisibility(View.VISIBLE);
		mBtnFb.setVisibility(View.VISIBLE);
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
		if (mode.equals("add")) {
			notesStatus.setHint("how you feel? ");
			setAddTemplate();
		} else if (mode.equals("edit")) {
			notesStatus.setText(status);
			setEditTemplate();
		}

	}

	private void setActivityTemplate(String name, Double met, String duration,
			String unit, String status) {
		// TODO Auto-generated method stub
		setAllTemplateGone();
		activityTemplate.setVisibility(View.VISIBLE);
		activityCal.setVisibility(View.VISIBLE);
		txtActivity.setText(name);
		txtActivityDurationUnit.setText(unit);
		txtActivityDuration.setText(duration);
		double cal = met * 5; // not true
		txtActivityCal.setText(String.valueOf(cal));
		if (mode.equals("add")) {
			notesStatus.setHint("how you feel? ");
			setAddTemplate();
		} else if (mode.equals("edit")) {
			notesStatus.setText(status);
			setEditTemplate();
		}
	}

	private void setCheckupTemplate(String doctor, String purpose, String status) {
		setAllTemplateGone();
		checkupTemplate.setVisibility(View.VISIBLE);
		txtDoctor.setText(doctor);
		txtPurpose.setText(purpose);
		if (mode.equals("add")) {
			notesStatus.setHint("how you feel? ");
			setAddTemplate();
		} else if (mode.equals("edit")) {
			notesStatus.setText(status);
			setEditTemplate();
		}
	}

	private void setWeightTemplate(String weight, String unit, String status) {
		setAllTemplateGone();
		weightTemplate.setVisibility(View.VISIBLE);
		txtWeight.setText(weight);
		txtWeightUnit.setText(unit);
		if (mode.equals("add")) {
			notesStatus.setHint("how you feel? ");
			setAddTemplate();
		} else if (mode.equals("edit")) {
			notesStatus.setText(status);
			setEditTemplate();
		}
	}

	private void setBloodSugarTemplate(String bloodsugar,
			String bloodsugartype, String status) {
		setAllTemplateGone();
		bsTemplate.setVisibility(View.VISIBLE);
		txtSugar.setText(bloodsugar);
		txtSugarType.setText(bloodsugartype);
		if (mode.equals("add")) {
			notesStatus.setHint("how you feel? ");
			setAddTemplate();
		} else if (mode.equals("edit")) {
			notesStatus.setText(status);
			setEditTemplate();
		}
	}

	private void setBloodPressureTemplate(String systolic, String diastolic,
			String status) {
		setAllTemplateGone();
		bpTemplate.setVisibility(View.VISIBLE);
		txtSystolic.setText(systolic);
		txtDiastolic.setText(diastolic);
		if (mode.equals("add")) {
			notesStatus.setHint("how you feel? ");
			setAddTemplate();
		} else if (mode.equals("edit")) {
			notesStatus.setText(status);
			setEditTemplate();
		}
	}

	private void setNoteTemplate(String note) {
		setAllTemplateGone();
		if (mode.equals("add")) {
			notesStatus.setHint("how you feel? ");
			setAddTemplate();
		} else if (mode.equals("edit")) {
			notesStatus.setText(note);
			setEditTemplate();
		}
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
				callCheckUpInput("", "");
			} else if (item.equals(TrackerInputType.ACTIVITY)) {
				currentTracker = TrackerInputType.ACTIVITY;
				callActivityInput();
			}
		} else if (requestCode == 3) {
			// String activity = data.getStringExtra("activity chosen");
			chosenActivity = (ActivitySingle) data.getExtras().getSerializable(
					"activity chosen");
			kind = "old";
			currentTracker = TrackerInputType.ACTIVITY;
			callActivityDurationInput(1, "hr");
		} else if (requestCode == 4) {
			kind = "old";
			String food = data.getStringExtra("food chosen");
			String serving = data.getStringExtra("serving");
			double cal = data.getDoubleExtra("cal", 0.0);
			double protein = data.getDoubleExtra("protein", 0.0);
			double carbs = data.getDoubleExtra("carbs", 0.0);
			double fat = data.getDoubleExtra("fat", 0.0);
			currentTracker = TrackerInputType.FOOD;
			callFoodServingInput(food, serving, cal, protein, carbs, fat);
		} else if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
			photo = (Bitmap) data.getExtras().get("data");
			statusImage.setImageBitmap(photo);
			setImage = true;
			imageTemplate.setVisibility(View.VISIBLE);

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
								notesStatus.getText().toString());

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

	private void callActivityDurationInput(int number, String unit) {
		// TODO Auto-generated method stub

		LayoutInflater layoutInflater = LayoutInflater.from(context);

		View activityView = layoutInflater.inflate(
				R.layout.item_activity_duration_input, null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);
		alertDialogBuilder.setView(activityView);
		activityDuration = (EditText) activityView
				.findViewById(R.id.txtActivityDurationTime);
		activityDuration.setText(String.valueOf(number));
		activityUnitSpinner = (Spinner) activityView
				.findViewById(R.id.activityUnitSpinner);

		List<String> list = new ArrayList<String>();
		list.add("min");
		list.add("hr");
		int index = list.indexOf(unit);
		if (index != -1)
			activityUnitSpinner.setSelection(index);
		alertDialogBuilder
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						// set activity met -- get from database
						setActivityTemplate(chosenActivity.getName(),
								chosenActivity.getMET(), activityDuration
										.getText().toString(), String
										.valueOf(activityUnitSpinner
												.getSelectedItem()),
								"How you feel? ");
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
								.getText().toString(), notesStatus.getText()
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
		List<String> list = new ArrayList<String>();
		list.add("kg");
		list.add("lb");
		int index = list.indexOf(txtUnit);
		if (index != -1)
			weightUnitSpinner.setSelection(index);

		alertDialogBuilder
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {

						setWeightTemplate(weight.getText().toString(), String
								.valueOf(weightUnitSpinner.getSelectedItem()),
								notesStatus.getText().toString());

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

		List<String> list = new ArrayList<String>();
		list.add("Before meal");
		list.add("After meal");
		int index = list.indexOf(txttype);
		if (index != -1)
			sugarTypeSpinner.setSelection(index);
		alertDialogBuilder
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {

						Log.e("sugar",
								Integer.toString(sugarPicker.getCurrent()));
						setBloodSugarTemplate(Integer.toString(sugarPicker
								.getCurrent()), String.valueOf(sugarTypeSpinner
								.getSelectedItem()), notesStatus.getText()
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
								notesStatus.getText().toString());

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
			BloodPressure bp = new BloodPressure(timestamp, notesStatus
					.getText().toString(), null, systolicPicker.getCurrent(),
					diastolicPicker.getCurrent());

			BloodPressureTrackerService bpTrackerService = new BloodPressureTrackerServiceImpl();
			bpTrackerService.add(bp);

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

			// String encodedImage =
			// "iVBORw0KGgoAAAANSUhEUgAAAAoAAAAKCAAAAACoWZBhAAAADElEQVR42mNgoCcAAABuAAF2oKnPAAAAAElFTkSuQmCC";
			String encodedImage = ImageHandler.encodeImageToBase64(photo);
			PHRImage image = new PHRImage(encodedImage, PHRImageType.IMAGE);
			BloodSugar bs = new BloodSugar(timestamp, notesStatus.getText()
					.toString(), image, sugarPicker.getCurrent(),
					String.valueOf(sugarTypeSpinner.getSelectedItem()));
			BloodSugarTrackerService bsTrackerService = new BloodSugarTrackerServiceImpl();
			bsTrackerService.add(bs);

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
			Weight weight = new Weight(timestamp, notesStatus.getText()
					.toString(), null, newWeight);

			WeightTrackerService weightTrackerService = new WeightTrackerServiceImpl();
			weightTrackerService.add(weight);

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

			NoteTrackerService noteTrackerService = new NoteTrackerServiceImpl();
			noteTrackerService.add(note);

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
					notesStatus.getText().toString());

			CheckUpTrackerService checkupTrackerService = new CheckUpTrackerServiceImpl();
			checkupTrackerService.add(checkup);

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
			ActivityTrackerEntry activityEntry = null;
			if (kind.equals("new")) {
				activityEntry = new ActivityTrackerEntry(timestamp, notesStatus
						.getText().toString(), null, chosenActivity,
						Double.parseDouble(txtActivityCal.getText().toString()));
			} else if (kind.equals("old")) {
				// add a new activity to database
				// get activity entry id
				// add to activitytrackerentry
			}
			// reference for calling
			// txtActivityDurationUnit.setText(unit);
			// txtActivityDuration.setText(duration);

			ActivityTrackerService activityTrackerService = new ActivityTrackerServiceImpl();
			activityTrackerService.add(activityEntry);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void setAllTemplateGone() {
		bsTemplate.setVisibility(View.GONE);
		bpTemplate.setVisibility(View.GONE);
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
					editBs.getTimestamp(), notesStatus.getText().toString(),
					null, Double.parseDouble(txtSugar.getText().toString()),
					txtSugarType.getText().toString());
			BloodSugarTrackerService bsTrackerService = new BloodSugarTrackerServiceImpl();
			bsTrackerService.edit(bs);
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
			Log.e("edit", "bloodpressure");
			BloodPressure bp = new BloodPressure(editBp.getEntryID(),
					editBp.getTimestamp(), notesStatus.getText().toString(),
					null, Integer.parseInt(txtSystolic.getText().toString()),
					Integer.parseInt(txtDiastolic.getText().toString()));

			BloodPressureTrackerService bpTrackerService = new BloodPressureTrackerServiceImpl();
			bpTrackerService.edit(bp);

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
					editWeight.getTimestamp(),
					notesStatus.getText().toString(), null, newWeight);

			WeightTrackerService weightTrackerService = new WeightTrackerServiceImpl();
			weightTrackerService.edit(weight);
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
							.toString(), notesStatus.getText().toString());

			CheckUpTrackerService checkupTrackerService = new CheckUpTrackerServiceImpl();
			checkupTrackerService.edit(checkup);

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

			NoteTrackerService noteTrackerService = new NoteTrackerServiceImpl();
			noteTrackerService.edit(note);

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
