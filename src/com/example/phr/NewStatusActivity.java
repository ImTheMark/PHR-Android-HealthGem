package com.example.phr;

import java.io.File;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
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
import android.widget.Toast;

import com.example.phr.application.HealthGem;
import com.example.phr.enums.TrackerInputType;
import com.example.phr.exceptions.EntryNotFoundException;
import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.mobile.models.Activity;
import com.example.phr.mobile.models.ActivityTrackerEntry;
import com.example.phr.mobile.models.BloodPressure;
import com.example.phr.mobile.models.BloodSugar;
import com.example.phr.mobile.models.CheckUp;
import com.example.phr.mobile.models.Food;
import com.example.phr.mobile.models.FoodTrackerEntry;
import com.example.phr.mobile.models.Note;
import com.example.phr.mobile.models.PHRImage;
import com.example.phr.mobile.models.PHRImageType;
import com.example.phr.mobile.models.UnverifiedActivityEntry;
import com.example.phr.mobile.models.UnverifiedFoodEntry;
import com.example.phr.mobile.models.UnverifiedRestaurantEntry;
import com.example.phr.mobile.models.UnverifiedSportsEstablishmentEntry;
import com.example.phr.mobile.models.Weight;
import com.example.phr.service.ActivityTrackerService;
import com.example.phr.service.BloodPressureTrackerService;
import com.example.phr.service.BloodSugarTrackerService;
import com.example.phr.service.CheckUpTrackerService;
import com.example.phr.service.FacebookPostService;
import com.example.phr.service.FoodTrackerService;
import com.example.phr.service.NoteTrackerService;
import com.example.phr.service.VerificationService;
import com.example.phr.service.WeightTrackerService;
import com.example.phr.serviceimpl.ActivityTrackerServiceImpl;
import com.example.phr.serviceimpl.BloodPressureTrackerServiceImpl;
import com.example.phr.serviceimpl.BloodSugarTrackerServiceImpl;
import com.example.phr.serviceimpl.CheckUpTrackerServiceImpl;
import com.example.phr.serviceimpl.FacebookPostServiceImpl;
import com.example.phr.serviceimpl.FoodTrackerServiceImpl;
import com.example.phr.serviceimpl.NoteTrackerServiceImpl;
import com.example.phr.serviceimpl.VerificationServiceImpl;
import com.example.phr.serviceimpl.WeightTrackerServiceImpl;
import com.example.phr.tools.DecodeImage;
import com.example.phr.tools.ImageHandler;
import com.example.phr.tools.WeightConverter;

import facebook4j.FacebookException;

public class NewStatusActivity extends android.app.Activity {

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
	EditText editFoodCal;
	EditText editFoodCarbs;
	EditText editFoodProtein;
	EditText editFoodFats;
	LinearLayout bpTemplate;
	LinearLayout bsTemplate;
	LinearLayout weightTemplate;
	com.example.phr.PredicateLayout checkupTemplate;
	com.example.phr.PredicateLayout foodTemplate;
	com.example.phr.PredicateLayout activityTemplate;
	LinearLayout activityCal;
	LinearLayout foodCal;
	String currentTracker;
	RelativeLayout imageTemplate;
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
	Activity chosenActivity;
	Food chosenFood;
	FoodTrackerEntry editFoodTrackerEntry;
	FoodTrackerEntry verifyFoodTrackerEntry;
	ActivityTrackerEntry editActivityTrackerEntry;
	ActivityTrackerEntry verifyActivityTrackerEntry;
	ImageView statusImage;
	Bitmap photo;
	Boolean setImage;
	TextView txtCurrentTracker;
	UnverifiedFoodEntry unferifiedFood;
	UnverifiedActivityEntry unferifiedActivity;
	UnverifiedRestaurantEntry unferifiedRestaurant;
	UnverifiedSportsEstablishmentEntry unferifiedSport;
	public static final int CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE = 1777;
	public static final int SELECT_FILE = 2000;
	DecimalFormat df = new DecimalFormat("#.00");
	android.app.Activity activity;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("Post a Status");
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();

		StrictMode.setThreadPolicy(policy);
		setContentView(R.layout.activity_new_status);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4A3A47")));
		Log.e("in", "ew status");
		activity = this;

		dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
		timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
		fmt = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss", Locale.ENGLISH);
		calobj = Calendar.getInstance();
		txtCurrentTracker = (TextView) findViewById(R.id.txtCurrentTracker);

		setImage = false;
		currentTracker = TrackerInputType.NOTES;
		// txtCurrentTracker.setText(TrackerInputType.NOTES);
		// templates
		bsTemplate = (LinearLayout) findViewById(R.id.bloodsugar_template);
		bpTemplate = (LinearLayout) findViewById(R.id.bloodpressure_template);
		weightTemplate = (LinearLayout) findViewById(R.id.weight_template);
		checkupTemplate = (com.example.phr.PredicateLayout) findViewById(R.id.checkup_template);
		foodTemplate = (com.example.phr.PredicateLayout) findViewById(R.id.food_template);
		activityTemplate = (com.example.phr.PredicateLayout) findViewById(R.id.activity_template);
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
				callActivityDurationInput(Double
						.parseDouble(txtActivityDuration.getText().toString()),
						txtActivityDurationUnit.toString());
			}
		});
		txtActivityDurationUnit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				callActivityDurationInput(Double
						.parseDouble(txtActivityDuration.getText().toString()),
						txtActivityDurationUnit.toString());
			}
		});
		txtActivity.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						ActivitiesSearchListActivity.class);
				startActivityForResult(intent, 3);
			}
		});

		// food post
		txtFood = (TextView) findViewById(R.id.food);
		txtFoodQuantityUnit = (TextView) findViewById(R.id.foodQuantityUnit);
		txtFoodQuantity = (TextView) findViewById(R.id.foodQuantitySize);
		foodCal = (LinearLayout) findViewById(R.id.foodCal);
		foodCal.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				callFoodInfoEdit();
			}
		});
		txtFoodCal = (TextView) findViewById(R.id.txtfoodCal);
		txtFoodCarbs = (TextView) findViewById(R.id.txtfoodCarbs);
		txtFoodFat = (TextView) findViewById(R.id.txtfoodFat);
		txtFoodProtein = (TextView) findViewById(R.id.txtfoodProtein);
		txtFoodQuantity.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				callFoodServingInput(chosenFood, Double
						.parseDouble(txtFoodQuantity.getText().toString()));
			}
		});
		txtFood.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						FoodSearchListActivity.class);
				startActivityForResult(intent, 4);
			}
		});

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
				selectImage();
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
		mBtnAddActions.setVisibility(View.VISIBLE);
		mode = "add";

		Intent in = getIntent();
		Bundle extras = getIntent().getExtras();
		if (extras != null && in.hasExtra("tracker")) {
			mode = "add";
			String tracker = extras.getString("tracker");

			if (tracker.equals(TrackerInputType.BLOOD_PRESSURE)) {
				mBtnAddActions.setVisibility(View.GONE);
				currentTracker = TrackerInputType.BLOOD_PRESSURE;
				txtCurrentTracker.setText(TrackerInputType.BLOOD_PRESSURE);
				callBloodPressureInput(100, 70);
			} else if (tracker.equals(TrackerInputType.BLOOD_SUGAR)) {
				mBtnAddActions.setVisibility(View.GONE);
				currentTracker = TrackerInputType.BLOOD_SUGAR;
				txtCurrentTracker.setText(TrackerInputType.BLOOD_SUGAR);
				callBloodSugarInput(4, "before meal");
			} else if (tracker.equals(TrackerInputType.NOTES)) {
				mBtnAddActions.setVisibility(View.GONE);
				txtCurrentTracker.setText(TrackerInputType.NOTES);
				currentTracker = TrackerInputType.NOTES;
				callNotesInput();
			} else if (tracker.equals(TrackerInputType.WEIGHT)) {
				mBtnAddActions.setVisibility(View.GONE);
				txtCurrentTracker.setText(TrackerInputType.WEIGHT);
				currentTracker = TrackerInputType.WEIGHT;
				callWeightInput("100", "lb");
			} else if (tracker.equals(TrackerInputType.FOOD)) {
				mBtnAddActions.setVisibility(View.GONE);
				txtCurrentTracker.setText(TrackerInputType.FOOD);
				currentTracker = TrackerInputType.FOOD;
				callFoodInput();
			} else if (tracker.equals(TrackerInputType.CHECKUP)) {
				mBtnAddActions.setVisibility(View.GONE);
				txtCurrentTracker.setText(TrackerInputType.CHECKUP);
				currentTracker = TrackerInputType.CHECKUP;
				callCheckUpInput("", "");
			} else if (tracker.equals(TrackerInputType.ACTIVITY)) {
				mBtnAddActions.setVisibility(View.GONE);
				txtCurrentTracker.setText(TrackerInputType.ACTIVITY);
				currentTracker = TrackerInputType.ACTIVITY;
				callActivityInput();
			}
		} else if (extras != null && in.hasExtra("from")) {
			String from = extras.getString("from");

			if (from.equals("new activity")) {
				mBtnAddActions.setVisibility(View.GONE);
				currentTracker = TrackerInputType.ACTIVITY;
				txtCurrentTracker.setText(TrackerInputType.ACTIVITY);

				chosenActivity = (Activity) in.getExtras().getSerializable(
						"activity added");
				Log.e("new activity", chosenActivity.getName());
				setActivityTemplate(chosenActivity.getName(),
						chosenActivity.getMET(),
						extras.getDouble("activity_duration"),
						extras.getString("activity_unit"), "", photo);

			} else if (from.equals("new food")) {
				mBtnAddActions.setVisibility(View.GONE);
				currentTracker = TrackerInputType.FOOD;
				txtCurrentTracker.setText(TrackerInputType.FOOD);

				chosenFood = (Food) in.getExtras()
						.getSerializable("food added");
				setFoodTemplate(1.0, chosenFood, notesStatus.getText()
						.toString(), photo);

			}
		} else if (extras != null && in.hasExtra("edit")) {
			String editTracker = extras.getString("edit");
			Bitmap temp = null;
			mode = "edit";

			if (editTracker.equals(TrackerInputType.BLOOD_SUGAR)) {
				editBs = (BloodSugar) in.getExtras().getSerializable("object");
				currentTracker = TrackerInputType.BLOOD_SUGAR;
				txtCurrentTracker.setText(TrackerInputType.BLOOD_SUGAR);
				if (editBs.getImage() != null) {
					temp = ImageHandler.loadImage(editBs.getImage()
							.getFileName());
					setImage = true;
				}
				Log.e("editbsobject", String.valueOf(editBs.getEntryID()));
				setBloodSugarTemplate(String.valueOf(editBs.getBloodSugar()),
						editBs.getType(), editBs.getStatus(), temp);

			} else if (editTracker.equals(TrackerInputType.BLOOD_PRESSURE)) {
				txtCurrentTracker.setText(TrackerInputType.BLOOD_PRESSURE);
				editBp = (BloodPressure) in.getExtras().getSerializable(
						"object");
				currentTracker = TrackerInputType.BLOOD_PRESSURE;
				Log.e("editbsobject", String.valueOf(editBp.getEntryID()));
				if (editBp.getImage() != null) {
					temp = ImageHandler.loadImage(editBp.getImage()
							.getFileName());
					setImage = true;
				}

				setBloodPressureTemplate(String.valueOf(editBp.getSystolic()),
						String.valueOf(editBp.getDiastolic()),
						editBp.getStatus(), temp);

			} else if (editTracker.equals(TrackerInputType.CHECKUP)) {
				txtCurrentTracker.setText(TrackerInputType.CHECKUP);
				editCheckup = (CheckUp) in.getExtras()
						.getSerializable("object");
				currentTracker = TrackerInputType.CHECKUP;
				Log.e("editbsobject", String.valueOf(editCheckup.getEntryID()));
				if (editCheckup.getImage() != null) {
					temp = ImageHandler.loadImage(editCheckup.getImage()
							.getFileName());
					setImage = true;
				}
				setCheckupTemplate(editCheckup.getDoctorsName(),
						editCheckup.getPurpose(), editCheckup.getNotes(), temp);

			} else if (editTracker.equals(TrackerInputType.NOTES)) {
				txtCurrentTracker.setText(TrackerInputType.NOTES);
				editNote = (Note) in.getExtras().getSerializable("object");
				currentTracker = TrackerInputType.NOTES;
				Log.e("editbsobject", String.valueOf(editNote.getEntryID()));
				if (editNote.getImage() != null) {
					temp = ImageHandler.loadImage(editNote.getImage()
							.getFileName());
					setImage = true;
				}
				setNoteTemplate(editNote.getNote(), temp);

			} else if (editTracker.equals(TrackerInputType.WEIGHT)) {
				txtCurrentTracker.setText(TrackerInputType.WEIGHT);
				editWeight = (Weight) in.getExtras().getSerializable("object");
				currentTracker = TrackerInputType.WEIGHT;
				if (editWeight.getImage() != null) {
					temp = ImageHandler.loadImage(editWeight.getImage()
							.getFileName());
					setImage = true;
				}
				Log.e("editbsobject", String.valueOf(editWeight.getEntryID()));
				setWeightTemplate(
						String.valueOf(editWeight.getWeightInPounds()), "lb",
						editWeight.getStatus(), temp);
			} else if (editTracker.equals(TrackerInputType.FOOD)) {
				txtCurrentTracker.setText(TrackerInputType.FOOD);
				editFoodTrackerEntry = (FoodTrackerEntry) in.getExtras()
						.getSerializable("object");
				currentTracker = TrackerInputType.FOOD;
				if (editFoodTrackerEntry.getImage() != null) {
					temp = ImageHandler.loadImage(editFoodTrackerEntry
							.getImage().getFileName());
					setImage = true;
				}
				chosenFood = editFoodTrackerEntry.getFood();
				Log.e("editbsobject",
						String.valueOf(editFoodTrackerEntry.getEntryID()));
				setFoodTemplate(editFoodTrackerEntry.getServingCount(),
						chosenFood, editFoodTrackerEntry.getStatus(), temp);

			} else if (editTracker.equals(TrackerInputType.ACTIVITY)) {
				txtCurrentTracker.setText(TrackerInputType.ACTIVITY);
				editActivityTrackerEntry = (ActivityTrackerEntry) in
						.getExtras().getSerializable("object");
				currentTracker = TrackerInputType.ACTIVITY;
				if (editActivityTrackerEntry.getImage() != null) {
					temp = ImageHandler.loadImage(editActivityTrackerEntry
							.getImage().getFileName());
					setImage = true;
				}
				chosenActivity = editActivityTrackerEntry.getActivity();
				Log.e("editbsobject",
						String.valueOf(editActivityTrackerEntry.getEntryID()));
				setActivityTemplate(editActivityTrackerEntry.getActivity()
						.getName(), editActivityTrackerEntry.getActivity()
						.getMET(),
						editActivityTrackerEntry.getDurationInSeconds() / 3600,
						"hr", editActivityTrackerEntry.getStatus(), photo);

			}
		} else if (extras != null && in.hasExtra("unverified")) {
			String verifyTracker = extras.getString("unverified");
			Bitmap temp = null;
			mode = "verify";
			if (verifyTracker.equals(TrackerInputType.FOOD)) {
				unferifiedFood = (UnverifiedFoodEntry) in.getExtras()
						.getSerializable("object");
				currentTracker = TrackerInputType.FOOD;
				txtCurrentTracker.setText(TrackerInputType.FOOD);
				if (unferifiedFood.getImage() != null) {
					temp = ImageHandler.loadImage(unferifiedFood.getImage()
							.getFileName());
					setImage = true;
				}
				Log.e("verifiyingfood",
						String.valueOf(unferifiedFood.getEntryID()));
				chosenFood = unferifiedFood.getFood();

				setFoodTemplate(unferifiedFood.getServingCount(), chosenFood,
						unferifiedFood.getStatus(), temp);

			} else if (verifyTracker.equals(TrackerInputType.ACTIVITY)) {
				unferifiedActivity = (UnverifiedActivityEntry) in.getExtras()
						.getSerializable("object");
				currentTracker = TrackerInputType.ACTIVITY;
				txtCurrentTracker.setText(TrackerInputType.ACTIVITY);
				if (unferifiedActivity.getImage() != null) {
					temp = ImageHandler.loadImage(unferifiedActivity.getImage()
							.getFileName());
					setImage = true;
				}
				Log.e("verifiyingactivity",
						String.valueOf(unferifiedActivity.getEntryID()));

				chosenActivity = unferifiedActivity.getActivity();

				setActivityTemplate(unferifiedActivity.getActivity().getName(),
						unferifiedActivity.getActivity().getMET(),
						unferifiedActivity.getDurationInSeconds() / 3600, "hr",
						unferifiedActivity.getStatus(), photo);

			} else if (verifyTracker.equals(TrackerInputType.RESTAURANT)) {
				unferifiedRestaurant = (UnverifiedRestaurantEntry) in
						.getExtras().getSerializable("object");
				verifyFoodTrackerEntry = (FoodTrackerEntry) in.getExtras()
						.getSerializable("object1");
				currentTracker = TrackerInputType.RESTAURANT;
				txtCurrentTracker.setText(TrackerInputType.RESTAURANT);
				if (verifyFoodTrackerEntry.getImage() != null) {
					temp = ImageHandler.loadImage(verifyFoodTrackerEntry
							.getImage().getFileName());
					setImage = true;
				}
				Log.e("verifiyingfood",
						String.valueOf(verifyFoodTrackerEntry.getEntryID()));
				chosenFood = verifyFoodTrackerEntry.getFood();

				setFoodTemplate(verifyFoodTrackerEntry.getServingCount(),
						chosenFood, verifyFoodTrackerEntry.getStatus(), temp);

			} else if (verifyTracker.equals(TrackerInputType.SPORTS)) {
				unferifiedSport = (UnverifiedSportsEstablishmentEntry) in
						.getExtras().getSerializable("object");
				verifyActivityTrackerEntry = (ActivityTrackerEntry) in
						.getExtras().getSerializable("object1");
				currentTracker = TrackerInputType.SPORTS;
				txtCurrentTracker.setText(TrackerInputType.SPORTS);
				if (verifyActivityTrackerEntry.getImage() != null) {
					temp = ImageHandler.loadImage(verifyActivityTrackerEntry
							.getImage().getFileName());
					setImage = true;
				}
				Log.e("verifiyingactivity",
						String.valueOf(verifyActivityTrackerEntry.getEntryID()));
				chosenActivity = verifyActivityTrackerEntry.getActivity();

				setActivityTemplate(
						verifyActivityTrackerEntry.getActivity().getName(),
						verifyActivityTrackerEntry.getActivity().getMET(),
						verifyActivityTrackerEntry.getDurationInSeconds() / 3600,
						"hr", verifyActivityTrackerEntry.getStatus(), photo);

			}
		}
	}

	private void setEditTemplate() {
		mBtnAddActions.setVisibility(View.GONE);
		mBtnFb.setVisibility(View.GONE);
	}

	private void setAddTemplate() {

		mBtnFb.setVisibility(View.VISIBLE);
	}

	private void setFoodTemplate(double serving, Food food, String status,
			Bitmap image) {
		// TODO Auto-generated method stub
		setAllTemplateGone();
		foodTemplate.setVisibility(View.VISIBLE);
		foodCal.setVisibility(View.VISIBLE);

		double cal = serving * food.getCalorie();
		double protein = serving * food.getProtein();
		double carbs = serving * food.getCarbohydrate();
		double fats = serving * food.getFat();
		txtFoodCal.setText(df.format(cal));
		txtFoodProtein.setText(df.format(protein));
		txtFoodCarbs.setText(df.format(carbs));
		txtFoodFat.setText(df.format(fats));
		txtFood.setText(food.getName());
		if (food.getServing() == null || food.getServing().equals(""))
			txtFoodQuantityUnit.setText("1 serving");
		else
			txtFoodQuantityUnit.setText(food.getServing());

		txtFoodQuantity.setText(String.valueOf(serving));
		if (image != null) {
			Log.e("in", "set food template");
			imageTemplate.setVisibility(View.VISIBLE);
			photo = image;
			statusImage.setImageBitmap(image);
		}
		if (mode.equals("add")) {
			notesStatus.setHint("how you feel? ");
			setAddTemplate();
		} else if (mode.equals("edit") || mode.equals("verify")) {
			notesStatus.setText(status);
			setEditTemplate();
		}

	}

	private void setActivityTemplate(String name, Double met, double duration,
			String unit, String status, Bitmap image) {
		// TODO Auto-generated method stub
		setAllTemplateGone();
		activityTemplate.setVisibility(View.VISIBLE);
		activityCal.setVisibility(View.VISIBLE);
		txtActivity.setText(name);
		txtActivityDurationUnit.setText(unit);
		txtActivityDuration.setText(String.valueOf(duration));
		double cal = 0; // not true
		Weight weight = null;
		WeightTrackerService weightService = new WeightTrackerServiceImpl();
		try {
			weight = weightService.getLatest();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (unit.equals("hr"))
			cal = met * weight.getWeightInKilograms() * duration;
		else if (unit.equals("min"))
			cal = met * weight.getWeightInKilograms() * (duration / 60.0);

		txtActivityCal.setText(df.format(cal));
		if (image != null) {
			Log.e("in", "set activity template");
			imageTemplate.setVisibility(View.VISIBLE);
			photo = image;
			statusImage.setImageBitmap(image);
		}
		if (mode.equals("add")) {
			notesStatus.setHint("how you feel? ");
			setAddTemplate();
		} else if (mode.equals("edit") || mode.equals("verify")) {
			notesStatus.setText(status);
			setEditTemplate();
		}
	}

	private void setCheckupTemplate(String doctor, String purpose,
			String status, Bitmap image) {
		setAllTemplateGone();
		checkupTemplate.setVisibility(View.VISIBLE);
		txtDoctor.setText(doctor);
		txtPurpose.setText(purpose);
		if (image != null) {
			Log.e("in", "set check up template");
			imageTemplate.setVisibility(View.VISIBLE);
			photo = image;
			statusImage.setImageBitmap(image);
		}
		if (mode.equals("add")) {
			notesStatus.setHint("how you feel? ");
			setAddTemplate();
		} else if (mode.equals("edit")) {
			notesStatus.setText(status);
			setEditTemplate();
		}
	}

	private void setWeightTemplate(String weight, String unit, String status,
			Bitmap image) {
		setAllTemplateGone();
		weightTemplate.setVisibility(View.VISIBLE);
		txtWeight.setText(weight);
		txtWeightUnit.setText(unit);
		if (image != null) {
			Log.e("in", "set check up template");
			imageTemplate.setVisibility(View.VISIBLE);
			photo = image;
			statusImage.setImageBitmap(image);
		}
		if (mode.equals("add")) {
			notesStatus.setHint("how you feel? ");
			setAddTemplate();
		} else if (mode.equals("edit")) {
			notesStatus.setText(status);
			setEditTemplate();
		}
	}

	private void setBloodSugarTemplate(String bloodsugar,
			String bloodsugartype, String status, Bitmap image) {
		setAllTemplateGone();
		bsTemplate.setVisibility(View.VISIBLE);
		txtSugar.setText(bloodsugar);
		txtSugarType.setText(bloodsugartype);
		if (image != null) {
			Log.e("in", "set check up template");
			imageTemplate.setVisibility(View.VISIBLE);
			photo = image;
			statusImage.setImageBitmap(image);
		}
		if (mode.equals("add")) {
			notesStatus.setHint("how you feel? ");
			setAddTemplate();
		} else if (mode.equals("edit")) {
			notesStatus.setText(status);
			setEditTemplate();
		}
	}

	private void setBloodPressureTemplate(String systolic, String diastolic,
			String status, Bitmap image) {
		setAllTemplateGone();
		bpTemplate.setVisibility(View.VISIBLE);
		txtSystolic.setText(systolic);
		txtDiastolic.setText(diastolic);
		if (image != null) {
			Log.e("in", "set check up template");
			imageTemplate.setVisibility(View.VISIBLE);
			photo = image;
			statusImage.setImageBitmap(image);
		}
		if (mode.equals("add")) {
			notesStatus.setHint("how you feel? ");
			setAddTemplate();
		} else if (mode.equals("edit")) {
			notesStatus.setText(status);
			setEditTemplate();
		}
	}

	private void setNoteTemplate(String note, Bitmap image) {
		setAllTemplateGone();
		if (image != null) {
			Log.e("in", "set check up template");
			imageTemplate.setVisibility(View.VISIBLE);
			photo = image;
			statusImage.setImageBitmap(image);
		}
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
			if (data != null) {
				String item = data.getStringExtra("itemValue");
				Log.e("itemValue = ", item);
				if (item.equals(TrackerInputType.BLOOD_PRESSURE)) {
					currentTracker = TrackerInputType.BLOOD_PRESSURE;
					txtCurrentTracker.setText(TrackerInputType.BLOOD_PRESSURE);
					callBloodPressureInput(120, 80);
				} else if (item.equals(TrackerInputType.BLOOD_SUGAR)) {
					txtCurrentTracker.setText(TrackerInputType.BLOOD_SUGAR);
					Log.e("txtCuurent", txtCurrentTracker.getText().toString());
					currentTracker = TrackerInputType.BLOOD_SUGAR;
					callBloodSugarInput(120, "before meal");
				} else if (item.equals(TrackerInputType.NOTES)) {
					txtCurrentTracker.setText(TrackerInputType.NOTES);
					currentTracker = TrackerInputType.NOTES;
					callNotesInput();
				} else if (item.equals(TrackerInputType.WEIGHT)) {
					txtCurrentTracker.setText(TrackerInputType.WEIGHT);
					currentTracker = TrackerInputType.WEIGHT;
					callWeightInput("100", "lb");
				} else if (item.equals(TrackerInputType.FOOD)) {
					txtCurrentTracker.setText(TrackerInputType.FOOD);
					currentTracker = TrackerInputType.FOOD;
					callFoodInput();
				} else if (item.equals(TrackerInputType.CHECKUP)) {
					txtCurrentTracker.setText(TrackerInputType.CHECKUP);
					currentTracker = TrackerInputType.CHECKUP;
					callCheckUpInput("", "");
				} else if (item.equals(TrackerInputType.ACTIVITY)) {
					txtCurrentTracker.setText(TrackerInputType.ACTIVITY);
					currentTracker = TrackerInputType.ACTIVITY;
					callActivityInput();
				}
			}
		} else if (requestCode == 3) {
			// String activity = data.getStringExtra("activity chosen");
			if (data != null && data.getExtras() != null) {
				chosenActivity = (Activity) data.getExtras().getSerializable(
						"activity chosen");
				currentTracker = TrackerInputType.ACTIVITY;
				txtCurrentTracker.setText(TrackerInputType.ACTIVITY);
				callActivityDurationInput(1, "hr");
			}
		} else if (requestCode == 4) {
			if (data != null && data.getExtras() != null) {

				chosenFood = (Food) data.getExtras().getSerializable(
						"food chosen");
				txtCurrentTracker.setText(TrackerInputType.FOOD);
				currentTracker = TrackerInputType.FOOD;
				callFoodServingInput(chosenFood, 1.0);
			}

		} else if (requestCode == CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE) {
			// Get our saved file into a bitmap object:
			File file = new File(Environment.getExternalStorageDirectory()
					+ File.separator + "image.jpg");

			photo = DecodeImage.decodeFile(file.getAbsolutePath());
			statusImage.setImageBitmap(photo);
			setImage = true;
			imageTemplate.setVisibility(View.VISIBLE);
		} else if (requestCode == SELECT_FILE) {
			Uri selectedImageUri = data.getData();

			String tempPath = getPath(context,selectedImageUri);
			BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
			//photo = BitmapFactory.decodeFile(tempPath, btmapOptions);
			photo = DecodeImage.decodeFile(tempPath);
			statusImage.setImageBitmap(photo);
			setImage = true;
			imageTemplate.setVisibility(View.VISIBLE);
		}

	}
	
	
	

	private void callFoodServingInput(final Food food, final double serving) {
		// TODO Auto-generated method stub

		LayoutInflater layoutInflater = LayoutInflater.from(context);

		View foodView = layoutInflater.inflate(
				R.layout.item_food_serving_input, null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);
		alertDialogBuilder.setView(foodView);
		txtFoodSize = (EditText) foodView.findViewById(R.id.txtFoodServingSize);
		txtFoodSize.setText(String.valueOf(serving));
		txtFoodUnit = (TextView) foodView.findViewById(R.id.foodUnit);
		txtFoodUnit.setText(food.getServing());
		alertDialogBuilder.setCancelable(false).setPositiveButton("OK",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {

						setFoodTemplate(Double.parseDouble(txtFoodSize
								.getText().toString()), food, notesStatus
								.getText().toString(), photo);

					}
				});

		// create an alert dialog
		AlertDialog alertD = alertDialogBuilder.create();
		alertD.show();

	}

	private void callFoodInfoEdit() {
		LayoutInflater layoutInflater = LayoutInflater.from(context);

		View foodInfoView = layoutInflater.inflate(
				R.layout.item_food_info_edit, null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);
		alertDialogBuilder.setView(foodInfoView);

		editFoodCal = (EditText) foodInfoView.findViewById(R.id.txtEditFoodCal);
		editFoodCal.setText(String.valueOf(chosenFood.getCalorie()));
		editFoodProtein = (EditText) foodInfoView
				.findViewById(R.id.txtEditFoodProtein);
		editFoodProtein.setText(String.valueOf(chosenFood.getProtein()));
		editFoodCarbs = (EditText) foodInfoView
				.findViewById(R.id.txtEditFoodCarbs);
		editFoodCarbs.setText(String.valueOf(chosenFood.getCarbohydrate()));
		editFoodFats = (EditText) foodInfoView
				.findViewById(R.id.txtEditFoodFat);
		editFoodFats.setText(String.valueOf(chosenFood.getFat()));

		alertDialogBuilder
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						// set activity met -- get from database
						chosenFood.setCalorie(Double.parseDouble(editFoodCal
								.getText().toString()));
						chosenFood.setProtein(Double
								.parseDouble(editFoodProtein.getText()
										.toString()));
						chosenFood.setCarbohydrate(Double
								.parseDouble(editFoodCarbs.getText().toString()));
						chosenFood.setFat(Double.parseDouble(editFoodFats
								.getText().toString()));
						chosenFood.setEntryID(null); // to change to new food
														// entry
						setFoodTemplate(Double.parseDouble(txtFoodQuantity
								.getText().toString()), chosenFood, notesStatus
								.getText().toString(), photo);
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

	private void callActivityDurationInput(double number, String unit) {
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
		alertDialogBuilder.setCancelable(false).setPositiveButton("OK",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						// set activity met -- get from database
						setActivityTemplate(chosenActivity.getName(),
								chosenActivity.getMET(), Double
										.parseDouble(activityDuration.getText()
												.toString()), String
										.valueOf(activityUnitSpinner
												.getSelectedItem()),
								"How you feel? ", photo);
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
		alertDialogBuilder.setCancelable(false).setPositiveButton("OK",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						if (doctor.getText().toString().equals(""))
							doctor.setText("N/A");
						if (purpose.getText().toString().equals(""))
							purpose.setText("N/A");
						setCheckupTemplate(doctor.getText().toString(), purpose
								.getText().toString(), notesStatus.getText()
								.toString(), photo);
					}
				});

		// create an alert dialog
		AlertDialog alertD = alertDialogBuilder.create();
		alertD.show();
	}

	private void callFoodInput() {
		// TODO Auto-generated method stub

		Intent intent = new Intent(getApplicationContext(),
				FoodSearchListActivity.class);
		startActivityForResult(intent, 4);
	}

	private void callNotesInput() {
		// TODO Auto-generated method stub
		setNoteTemplate(notesStatus.getText().toString(), photo);
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

		alertDialogBuilder.setCancelable(false).setPositiveButton("OK",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {

						if (weight.getText().toString().equals(""))
							Toast.makeText(context, "Please enter your weight",
									Toast.LENGTH_LONG).show();
						else
							setWeightTemplate(weight.getText().toString(),
									String.valueOf(weightUnitSpinner
											.getSelectedItem()), notesStatus
											.getText().toString(), photo);

					}
					/*
					 * }) .setNegativeButton("Cancel", new
					 * DialogInterface.OnClickListener() {
					 * 
					 * @Override public void onClick(DialogInterface dialog, int
					 * id) { dialog.cancel(); }
					 */
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
		alertDialogBuilder.setCancelable(false).setPositiveButton("OK",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {

						Log.e("sugar",
								Integer.toString(sugarPicker.getCurrent()));
						setBloodSugarTemplate(Integer.toString(sugarPicker
								.getCurrent()), String.valueOf(sugarTypeSpinner
								.getSelectedItem()), notesStatus.getText()
								.toString(), photo);
					}
					/*
					 * }) .setNegativeButton("Cancel", new
					 * DialogInterface.OnClickListener() {
					 * 
					 * @Override public void onClick(DialogInterface dialog, int
					 * id) { dialog.cancel(); }
					 */
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
		alertDialogBuilder.setCancelable(false).setPositiveButton("OK",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {

						Log.e("systolic",
								Integer.toString(systolicPicker.getCurrent()));
						setBloodPressureTemplate(
								Integer.toString(systolicPicker.getCurrent()),
								Integer.toString(diastolicPicker.getCurrent()),
								notesStatus.getText().toString(), photo);

					}
					/*
					 * }) .setNegativeButton("Cancel", new
					 * DialogInterface.OnClickListener() {
					 * 
					 * @Override public void onClick(DialogInterface dialog, int
					 * id) { dialog.cancel(); }
					 */
				});

		// create an alert dialog
		AlertDialog alertD = alertDialogBuilder.create();
		alertD.show();
	}

	private void addBloodPressureToDatabase() {

		try {
			Date date = fmt.parse(dateFormat.format(calobj.getTime()) + " "
					+ timeFormat.format(calobj.getTime()));
			Timestamp timestamp = new Timestamp(date.getTime());
			PHRImage image;
			if (setImage == true) {
				String encodedImage = ImageHandler.encodeImageToBase64(photo);
				image = new PHRImage(encodedImage, PHRImageType.IMAGE);
			} else {
				image = null;

			}

			FacebookPostService fbPostService = new FacebookPostServiceImpl();
			String fbId = null;
			if (((Boolean) mBtnFb.getTag()) == true)
				try {
					Log.e("fb", "call");
					fbId = fbPostService.publish(notesStatus.getText()
							+ " - measuring blood presure at "
							+ String.valueOf(systolicPicker.getCurrent()) + "/"
							+ String.valueOf(diastolicPicker.getCurrent())
							+ " mm hg");
					Log.e("fbId", fbId);
				} catch (FacebookException e) {
					Log.e("Unable to publish", e.getErrorMessage());
				}

			BloodPressure bp = new BloodPressure(timestamp, notesStatus
					.getText().toString(), image, systolicPicker.getCurrent(),
					diastolicPicker.getCurrent());
			bp.setFacebookID(fbId);
			BloodPressureTrackerService bpTrackerService = new BloodPressureTrackerServiceImpl();
			bpTrackerService.add(bp);

			Intent intent = new Intent(getApplicationContext(),
					BloodPressureTrackerActivity.class);
			startActivity(intent);
			finish();

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			runOnUiThread(new Runnable(){
		        public void run() {
					Toast.makeText(HealthGem.getContext(), "No Internet Connection !",
							Toast.LENGTH_LONG).show();
		        }
		    });
			e.printStackTrace();
		} catch (OutdatedAccessTokenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void addBloodSugarToDatabase() {

		try {

			Date date = fmt.parse(dateFormat.format(calobj.getTime()) + " "
					+ timeFormat.format(calobj.getTime()));
			Timestamp timestamp = new Timestamp(date.getTime());
			PHRImage image;
			if (setImage == true) {
				String encodedImage = ImageHandler.encodeImageToBase64(photo);
				image = new PHRImage(encodedImage, PHRImageType.IMAGE);
			} else
				image = null;

			FacebookPostService fbPostService = new FacebookPostServiceImpl();
			String fbId = null;
			if (((Boolean) mBtnFb.getTag()) == true)
				try {
					Log.e("fb", "call");
					fbId = fbPostService
							.publish(notesStatus.getText()
									+ " - measuring blood sugar at "
									+ String.valueOf(sugarPicker.getCurrent())
									+ " mmol/L"
									+ " "
									+ String.valueOf(sugarTypeSpinner
											.getSelectedItem()));
					Log.e("fbId", fbId);
				} catch (FacebookException e) {
					Log.e("Unable to publish", e.getErrorMessage());
				}
			BloodSugar bs = new BloodSugar(timestamp, notesStatus.getText()
					.toString(), image, sugarPicker.getCurrent(),
					String.valueOf(sugarTypeSpinner.getSelectedItem()));
			bs.setFacebookID(fbId);
			BloodSugarTrackerService bsTrackerService = new BloodSugarTrackerServiceImpl();

			bsTrackerService.add(bs);
			Intent intent = new Intent(getApplicationContext(),
					BloodSugarTrackerActivity.class);
			startActivity(intent);
			finish();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			runOnUiThread(new Runnable(){
		        public void run() {
					Toast.makeText(HealthGem.getContext(), "No Internet Connection !",
							Toast.LENGTH_LONG).show();
		        }
		    });
			e.printStackTrace();
		} catch (OutdatedAccessTokenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	private void addWeightToDatabase() {

		try {

			Date date = fmt.parse(dateFormat.format(calobj.getTime()) + " "
					+ timeFormat.format(calobj.getTime()));
			Timestamp timestamp = new Timestamp(date.getTime());

			PHRImage image;
			if (setImage == true) {
				String encodedImage = ImageHandler.encodeImageToBase64(photo);
				image = new PHRImage(encodedImage, PHRImageType.IMAGE);
			} else
				image = null;
			double newWeight;
			if (String.valueOf(weightUnitSpinner.getSelectedItem())
					.equals("kg")) {
				newWeight = WeightConverter.convertKgToLbs(Double
						.parseDouble(String.valueOf(txtWeight.getText())));
			} else {
				newWeight = Double.parseDouble(String.valueOf(txtWeight
						.getText()));
			}
			FacebookPostService fbPostService = new FacebookPostServiceImpl();
			String fbId = null;
			if (((Boolean) mBtnFb.getTag()) == true)
				try {
					Log.e("fb", "call");
					fbId = fbPostService.publish(notesStatus.getText()
							.toString()
							+ " - measuring weight at "
							+ String.valueOf(newWeight) + " lbs");
					Log.e("fbId", fbId);
				} catch (FacebookException e) {
					Log.e("Unable to publish", e.getErrorMessage());
				}
			Weight weight = new Weight(timestamp, notesStatus.getText()
					.toString(), image, newWeight);
			weight.setFacebookID(fbId);
			WeightTrackerService weightTrackerService = new WeightTrackerServiceImpl();
			weightTrackerService.add(weight);

			Intent intent = new Intent(getApplicationContext(),
					WeightTrackerActivity.class);
			intent.putExtra("mode", "add");
			startActivity(intent);
			finish();

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			runOnUiThread(new Runnable(){
		        public void run() {
					Toast.makeText(HealthGem.getContext(), "No Internet Connection !",
							Toast.LENGTH_LONG).show();
		        }
		    });
			e.printStackTrace();
		} catch (OutdatedAccessTokenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void addNoteToDatabase() {

		try {

			Date date = fmt.parse(dateFormat.format(calobj.getTime()) + " "
					+ timeFormat.format(calobj.getTime()));
			Timestamp timestamp = new Timestamp(date.getTime());
			PHRImage image;
			if (setImage == true) {
				String encodedImage = ImageHandler.encodeImageToBase64(photo);
				image = new PHRImage(encodedImage, PHRImageType.IMAGE);
			} else
				image = null;
			FacebookPostService fbPostService = new FacebookPostServiceImpl();
			String fbId = null;
			if (((Boolean) mBtnFb.getTag()) == true)
				try {
					Log.e("fb", "call");
					fbId = fbPostService.publish(notesStatus.getText()
							.toString());
					Log.e("fbId", fbId);
				} catch (FacebookException e) {
					Log.e("Unable to publish", e.getErrorMessage());
				}

			Note note = new Note(timestamp, null, image, notesStatus.getText()
					.toString());
			note.setFacebookID(fbId);
			NoteTrackerService noteTrackerService = new NoteTrackerServiceImpl();
			noteTrackerService.add(note);

			Intent intent = new Intent(getApplicationContext(),
					NoteTrackerActivity.class);
			startActivity(intent);
			finish();

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			runOnUiThread(new Runnable(){
		        public void run() {
					Toast.makeText(HealthGem.getContext(), "No Internet Connection !",
							Toast.LENGTH_LONG).show();
		        }
		    });
			e.printStackTrace();
		} catch (OutdatedAccessTokenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void addFoodToDatabase() {

		try {
			FacebookPostService fbPostService = new FacebookPostServiceImpl();
			Date date = fmt.parse(dateFormat.format(calobj.getTime()) + " "
					+ timeFormat.format(calobj.getTime()));
			Timestamp timestamp = new Timestamp(date.getTime());
			PHRImage image;
			String fbId = null;
			if (setImage == true) {
				String encodedImage = ImageHandler.encodeImageToBase64(photo);
				image = new PHRImage(encodedImage, PHRImageType.IMAGE);
			} else
				image = null;
			Log.e("fb", String.valueOf(mBtnFb.getTag()));
			if (((Boolean) mBtnFb.getTag()) == true)
				try {
					Log.e("fb", "call");
					fbId = fbPostService.publish(notesStatus.getText()
							.toString() + " - eating " + chosenFood.getName());
					Log.e("fbId", fbId);
				} catch (FacebookException e) {
					Log.e("Unable to publish", e.getErrorMessage());
				}

			FoodTrackerEntry foodEntry = null;
			foodEntry = new FoodTrackerEntry(timestamp, notesStatus.getText()
					.toString(), image, chosenFood,
					Double.parseDouble(txtFoodQuantity.getText().toString()));
			foodEntry.setFacebookID(fbId);
			FoodTrackerService foodTrackerService = new FoodTrackerServiceImpl();
			foodTrackerService.add(foodEntry);

			Date date1 = null;
			try {
				date1 = fmt.parse(dateFormat.format(calobj.getTime()) + " "
						+ timeFormat.format(calobj.getTime()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Timestamp timestamp1 = new Timestamp(date1.getTime());
			SimpleDateFormat fmtFood = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
			String txtdate = fmtFood.format(timestamp1);
			Intent intent = new Intent(getApplicationContext(),
					FoodTrackerDailyActivity.class);
			Log.e("newstatus", txtdate);
			intent.putExtra("date", txtdate);
			startActivity(intent);
			finish();

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			runOnUiThread(new Runnable(){
		        public void run() {
					Toast.makeText(HealthGem.getContext(), "No Internet Connection !",
							Toast.LENGTH_LONG).show();
		        }
		    });
			e.printStackTrace();
		} catch (OutdatedAccessTokenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void addCheckUpToDatabase() {

		try {

			Date date = fmt.parse(dateFormat.format(calobj.getTime()) + " "
					+ timeFormat.format(calobj.getTime()));
			Timestamp timestamp = new Timestamp(date.getTime());
			PHRImage image;
			if (setImage == true) {
				String encodedImage = ImageHandler.encodeImageToBase64(photo);
				image = new PHRImage(encodedImage, PHRImageType.IMAGE);
			} else
				image = null;

			FacebookPostService fbPostService = new FacebookPostServiceImpl();
			String fbId = null;
			if (((Boolean) mBtnFb.getTag()) == true)
				try {
					Log.e("fb", "call");
					fbId = fbPostService.publish(notesStatus.getText()
							.toString()
							+ " - check up with "
							+ txtDoctor.getText().toString()
							+ " due to "
							+ txtPurpose.getText().toString());
					Log.e("fbId", fbId);
				} catch (FacebookException e) {
					Log.e("Unable to publish", e.getErrorMessage());
				}

			CheckUp checkup = new CheckUp(timestamp, null, image, txtPurpose
					.getText().toString(), txtDoctor.getText().toString(),
					notesStatus.getText().toString());
			checkup.setFacebookID(fbId);
			CheckUpTrackerService checkupTrackerService = new CheckUpTrackerServiceImpl();
			checkupTrackerService.add(checkup);

			Intent intent = new Intent(getApplicationContext(),
					CheckupTrackerActivity.class);
			startActivity(intent);
			finish();

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			runOnUiThread(new Runnable(){
		        public void run() {
					Toast.makeText(HealthGem.getContext(), "No Internet Connection !",
							Toast.LENGTH_LONG).show();
		        }
		    });
			e.printStackTrace();
		} catch (OutdatedAccessTokenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void addActivityToDatabase() {

		try {

			Date date = fmt.parse(dateFormat.format(calobj.getTime()) + " "
					+ timeFormat.format(calobj.getTime()));
			Timestamp timestamp = new Timestamp(date.getTime());
			PHRImage image;
			if (setImage == true) {
				String encodedImage = ImageHandler.encodeImageToBase64(photo);
				image = new PHRImage(encodedImage, PHRImageType.IMAGE);
			} else
				image = null;
			ActivityTrackerEntry activityEntry = null;
			int sec = 0;
			if (txtActivityDurationUnit.getText().toString().equals("hr"))
				sec = (int) Math.round(Double.parseDouble(txtActivityDuration
						.getText().toString()) * 3600);
			else if (txtActivityDurationUnit.getText().toString().equals("min"))
				sec = (int) Math.round(Double.parseDouble(txtActivityDuration
						.getText().toString()) * 60);

			FacebookPostService fbPostService = new FacebookPostServiceImpl();
			String fbId = null;
			if (((Boolean) mBtnFb.getTag()) == true)
				try {
					Log.e("fb", "call");
					fbId = fbPostService.publish(notesStatus.getText()
							.toString()
							+ " - doing "
							+ chosenActivity.getName());
					Log.e("fbId", fbId);
				} catch (FacebookException e) {
					Log.e("Unable to publish", e.getErrorMessage());
				}
			Weight weight = null;
			WeightTrackerService weightService = new WeightTrackerServiceImpl();
			try {
				weight = weightService.getLatest();
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			double calBurned = chosenActivity.getMET()
					* weight.getWeightInKilograms();
			activityEntry = new ActivityTrackerEntry(timestamp, notesStatus
					.getText().toString(), image, chosenActivity, calBurned,
					sec);
			activityEntry.setFacebookID(fbId);
			Log.e("newstatus secs", activityEntry.getDurationInSeconds() + "");

			ActivityTrackerService activityTrackerService = new ActivityTrackerServiceImpl();
			activityTrackerService.add(activityEntry);

			Intent intent = new Intent(getApplicationContext(),
					ActivitiesTrackerActivity.class);
			startActivity(intent);
			finish();

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			runOnUiThread(new Runnable(){
		        public void run() {
					Toast.makeText(HealthGem.getContext(), "No Internet Connection !",
							Toast.LENGTH_LONG).show();
		        }
		    });
			e.printStackTrace();
		} catch (OutdatedAccessTokenException e) {
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
		else if (mode.equals("verify"))
			getMenuInflater().inflate(R.menu.menu_verify_status, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		final ProgressDialog progressDialog = new ProgressDialog(activity);
		progressDialog.setCancelable(false);
		progressDialog.setMessage("Saving post to tracker...");
		
		switch (item.getItemId()) {
		case R.id.menu_item_status_post:
			


			progressDialog.show();
			new AsyncTask<Void, Void, Void>(){
		        @Override
		        protected Void doInBackground(Void... params) {
		        	if (currentTracker.equals(TrackerInputType.BLOOD_PRESSURE)) {

						addBloodPressureToDatabase();
						Log.e("added", "bp");

					}

					else if (currentTracker.equals(TrackerInputType.BLOOD_SUGAR)) {

						addBloodSugarToDatabase();

					} else if (currentTracker.equals(TrackerInputType.WEIGHT)) {

						addWeightToDatabase();

					} else if (currentTracker.equals(TrackerInputType.CHECKUP)) {

						addCheckUpToDatabase();

					} else if (currentTracker.equals(TrackerInputType.NOTES)) {

						addNoteToDatabase();

					} else if (currentTracker.equals(TrackerInputType.ACTIVITY)) {

						addActivityToDatabase();

					} else if (currentTracker.equals(TrackerInputType.FOOD)) {

						addFoodToDatabase();

					}
		            return null;
		        }
		        
		        @Override
		        protected void onPostExecute(Void result2) {
					
		        	if(progressDialog.isShowing())
						progressDialog.dismiss();
		        };
		    }.execute();

			

			return true;

		case R.id.menu_item_status_edit:
			

			progressDialog.show();
			new AsyncTask<Void, Void, Void>(){
		        @Override
		        protected Void doInBackground(Void... params) {
		        	
		        	if (currentTracker.equals(TrackerInputType.BLOOD_SUGAR)) {
						Log.e("call", "edit");
						editBloodSugarToDatabase();

					} else if (currentTracker.equals(TrackerInputType.BLOOD_PRESSURE)) {
						editBloodPressureToDatabase();
						Log.e("call", "bpActivity");

					} else if (currentTracker.equals(TrackerInputType.WEIGHT)) {
						editWeightToDatabase();
						Log.e("call", "weightActivity");

					} else if (currentTracker.equals(TrackerInputType.CHECKUP)) {
						editCheckUpToDatabase();
						Log.e("call", "checkupActivity");

					} else if (currentTracker.equals(TrackerInputType.NOTES)) {
						editNoteToDatabase();
						Log.e("call", "noteActivity");

					} else if (currentTracker.equals(TrackerInputType.FOOD)) {
						editFoodToDatabase();
						Log.e("call", "foodActivity");

					} else if (currentTracker.equals(TrackerInputType.ACTIVITY)) {
						editActivityToDatabase();
						Log.e("call", "acitivityActivity");

					}
					
		            return null;
		        }
		        
		        @Override
		        protected void onPostExecute(Void result2) {
					
		        	if(progressDialog.isShowing())
						progressDialog.dismiss();
		        };
		    }.execute();

			return true;

		case R.id.menu_item_status_verify:

			if (currentTracker.equals(TrackerInputType.FOOD)) {
				Log.e("call", "verify");
				verifyFoodToDatabase();
				Log.e("call", "foodActivity");

			} else if (currentTracker.equals(TrackerInputType.ACTIVITY)) {
				verifyActivityToDatabase();
				Log.e("call", "acitivityActivity");

			} else if (currentTracker.equals(TrackerInputType.RESTAURANT)) {
				verifyRestaurantToDatabase();
				Log.e("call", "foodActivity");

			} else if (currentTracker.equals(TrackerInputType.SPORTS)) {
				verifySportToDatabase();
				Log.e("call", "sportActivity");

			}

			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	private void editBloodSugarToDatabase() {

		try {
			Log.e("in", "edit");
			PHRImage image;
			if (setImage == true) {
				String encodedImage = ImageHandler.encodeImageToBase64(photo);
				image = new PHRImage(encodedImage, PHRImageType.IMAGE);
			} else
				image = null;
			BloodSugar bs = new BloodSugar(editBs.getEntryID(),
					editBs.getTimestamp(), notesStatus.getText().toString(),
					image, Double.parseDouble(txtSugar.getText().toString()),
					txtSugarType.getText().toString());
			BloodSugarTrackerService bsTrackerService = new BloodSugarTrackerServiceImpl();

			bsTrackerService.edit(bs);

			Log.e("call", "bsActivity");
			Intent intent = new Intent(getApplicationContext(),
					BloodSugarTrackerActivity.class);
			startActivity(intent);
			finish();
		} catch (OutdatedAccessTokenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EntryNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block

			runOnUiThread(new Runnable(){
		        public void run() {
					Toast.makeText(HealthGem.getContext(), "No Internet Connection !",
							Toast.LENGTH_LONG).show();
		        }
		    });
			e.printStackTrace();
		}
		Log.e("in", "edited");

	}

	private void editBloodPressureToDatabase() {
		try {
			Log.e("edit", "bloodpressure");
			PHRImage image;
			if (setImage == true) {
				String encodedImage = ImageHandler.encodeImageToBase64(photo);
				image = new PHRImage(encodedImage, PHRImageType.IMAGE);
			} else
				image = null;
			BloodPressure bp = new BloodPressure(editBp.getEntryID(),
					editBp.getTimestamp(), notesStatus.getText().toString(),
					image, Integer.parseInt(txtSystolic.getText().toString()),
					Integer.parseInt(txtDiastolic.getText().toString()));

			BloodPressureTrackerService bpTrackerService = new BloodPressureTrackerServiceImpl();

			bpTrackerService.edit(bp);

			Intent intent = new Intent(getApplicationContext(),
					BloodPressureTrackerActivity.class);
			startActivity(intent);
			finish();
		} catch (OutdatedAccessTokenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EntryNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			runOnUiThread(new Runnable(){
		        public void run() {
					Toast.makeText(HealthGem.getContext(), "No Internet Connection !",
							Toast.LENGTH_LONG).show();
		        }
		    });
			e.printStackTrace();
		}

		Log.e("in", "edited");

	}

	private void editWeightToDatabase() {
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

			PHRImage image;
			if (setImage == true) {
				String encodedImage = ImageHandler.encodeImageToBase64(photo);
				image = new PHRImage(encodedImage, PHRImageType.IMAGE);
			} else
				image = null;
			Weight weight = new Weight(editWeight.getEntryID(),
					editWeight.getTimestamp(),
					notesStatus.getText().toString(), image, newWeight);

			WeightTrackerService weightTrackerService = new WeightTrackerServiceImpl();
			weightTrackerService.edit(weight);
			Intent intent = new Intent(getApplicationContext(),
					WeightTrackerActivity.class);
			startActivity(intent);
			finish();

		} catch (EntryNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OutdatedAccessTokenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			runOnUiThread(new Runnable(){
		        public void run() {
					Toast.makeText(HealthGem.getContext(), "No Internet Connection !",
							Toast.LENGTH_LONG).show();
		        }
		    });
			e.printStackTrace();
		}

	}

	private void editCheckUpToDatabase() {

		try {
			Log.e("in", "edit");
			PHRImage image;
			if (setImage == true) {
				String encodedImage = ImageHandler.encodeImageToBase64(photo);
				image = new PHRImage(encodedImage, PHRImageType.IMAGE);
			} else
				image = null;
			CheckUp checkup = new CheckUp(editCheckup.getEntryID(),
					editCheckup.getTimestamp(), null, image, txtPurpose
							.getText().toString(), txtDoctor.getText()
							.toString(), notesStatus.getText().toString());

			CheckUpTrackerService checkupTrackerService = new CheckUpTrackerServiceImpl();

			checkupTrackerService.edit(checkup);
			Intent intent = new Intent(getApplicationContext(),
					CheckupTrackerActivity.class);
			startActivity(intent);
			finish();
		} catch (OutdatedAccessTokenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EntryNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			runOnUiThread(new Runnable(){
		        public void run() {
					Toast.makeText(HealthGem.getContext(), "No Internet Connection !",
							Toast.LENGTH_LONG).show();
		        }
		    });
			e.printStackTrace();
		}

	}

	private void editNoteToDatabase() {
		try {
			Log.e("in", "edit");
			PHRImage image;
			if (setImage == true) {
				String encodedImage = ImageHandler.encodeImageToBase64(photo);
				image = new PHRImage(encodedImage, PHRImageType.IMAGE);
			} else
				image = null;
			Note note = new Note(editNote.getEntryID(),
					editNote.getTimestamp(), null, image, notesStatus.getText()
							.toString());

			NoteTrackerService noteTrackerService = new NoteTrackerServiceImpl();

			noteTrackerService.edit(note);
			Intent intent = new Intent(getApplicationContext(),
					NoteTrackerActivity.class);
			startActivity(intent);
			finish();
		} catch (OutdatedAccessTokenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EntryNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			runOnUiThread(new Runnable(){
		        public void run() {
					Toast.makeText(HealthGem.getContext(), "No Internet Connection !",
							Toast.LENGTH_LONG).show();
		        }
		    });
			e.printStackTrace();
		}

	}

	private void editFoodToDatabase() {

		try {
			Log.e("in", "edit");
			PHRImage image;
			Log.e("editfoodsetimage", setImage.toString());
			if (setImage == true) {
				Log.e("editfoodsetimage", "in");
				String encodedImage = ImageHandler.encodeImageToBase64(photo);
				image = new PHRImage(encodedImage, PHRImageType.IMAGE);
			} else {
				image = null;
				Log.e("newstatuact", "image null");
			}
			FoodTrackerEntry foodEntry = null;
			Log.e("newstatuact", String.valueOf(image));
			foodEntry = new FoodTrackerEntry(editFoodTrackerEntry.getEntryID(),
					editFoodTrackerEntry.getTimestamp(), notesStatus.getText()
							.toString(), image, chosenFood,
					Double.parseDouble(txtFoodQuantity.getText().toString()));

			FoodTrackerService foodTrackerService = new FoodTrackerServiceImpl();
			foodTrackerService.edit(foodEntry);

			Intent intent = new Intent(getApplicationContext(),
					FoodTrackerDailyActivity.class);
			SimpleDateFormat fmtFood = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
			String txtdate = fmtFood
					.format(editFoodTrackerEntry.getTimestamp());
			Log.e("newstatus", txtdate);
			intent.putExtra("date", txtdate);
			startActivity(intent);
			finish();
		} catch (OutdatedAccessTokenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EntryNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			runOnUiThread(new Runnable(){
		        public void run() {
					Toast.makeText(HealthGem.getContext(), "No Internet Connection !",
							Toast.LENGTH_LONG).show();
		        }
		    });
			e.printStackTrace();
		}

	}

	private void editActivityToDatabase() {
		try {

			Log.e("in", "edit");
			PHRImage image;
			if (setImage == true) {
				String encodedImage = ImageHandler.encodeImageToBase64(photo);
				image = new PHRImage(encodedImage, PHRImageType.IMAGE);
			} else
				image = null;

			int sec = 0;
			if (txtActivityDurationUnit.getText().toString().equals("hr"))
				sec = (int) Math.round(Double.parseDouble(txtActivityDuration
						.getText().toString()) * 3600);
			else if (txtActivityDurationUnit.getText().toString().equals("min"))
				sec = (int) Math.round(Double.parseDouble(txtActivityDuration
						.getText().toString()) * 60);

			Weight weight = null;
			WeightTrackerService weightService = new WeightTrackerServiceImpl();
			weight = weightService.getLatest();

			double calBurned = chosenActivity.getMET()
					* weight.getWeightInKilograms();
			ActivityTrackerEntry activityEntry = new ActivityTrackerEntry(
					editActivityTrackerEntry.getEntryID(),
					editActivityTrackerEntry.getTimestamp(), notesStatus
							.getText().toString(), image, chosenActivity,
					calBurned, sec);

			ActivityTrackerService activityTrackerService = new ActivityTrackerServiceImpl();

			activityTrackerService.edit(activityEntry);
			Intent intent = new Intent(getApplicationContext(),
					ActivitiesTrackerActivity.class);
			startActivity(intent);
			finish();
		} catch (OutdatedAccessTokenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EntryNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			runOnUiThread(new Runnable(){
		        public void run() {
					Toast.makeText(HealthGem.getContext(), "No Internet Connection !",
							Toast.LENGTH_LONG).show();
		        }
		    });
			e.printStackTrace();
		}

	}

	private void verifyFoodToDatabase() {
		try {
			Log.e("in", "verify");
			PHRImage image;
			Log.e("verifyfoodsetimage", setImage.toString());
			if (setImage == true) {
				Log.e("verifyfoodsetimage", "in");
				String encodedImage = ImageHandler.encodeImageToBase64(photo);
				image = new PHRImage(encodedImage, PHRImageType.IMAGE);
			} else {
				image = null;
				Log.e("newstatuact", "image null");
			}
			VerificationService verificationService = new VerificationServiceImpl();
			FoodTrackerEntry foodEntry = new FoodTrackerEntry(
					unferifiedFood.getFacebookID(),
					unferifiedFood.getTimestamp(), notesStatus.getText()
							.toString(), image, chosenFood,
					Double.parseDouble(txtFoodQuantity.getText().toString()));
			FoodTrackerService foodTrackerService = new FoodTrackerServiceImpl();
			foodTrackerService.add(foodEntry);

			verificationService.delete(unferifiedFood);

			Intent intent = new Intent(getApplicationContext(),
					FoodTrackerDailyActivity.class);
			SimpleDateFormat fmtFood = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
			String txtdate = fmtFood.format(unferifiedFood.getTimestamp());
			Log.e("newstatus", txtdate);
			intent.putExtra("date", txtdate);
			startActivity(intent);
			finish();
		} catch (EntryNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			runOnUiThread(new Runnable(){
		        public void run() {
					Toast.makeText(HealthGem.getContext(), "No Internet Connection !",
							Toast.LENGTH_LONG).show();
		        }
		    });
			e.printStackTrace();
		} catch (OutdatedAccessTokenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void verifyRestaurantToDatabase() {
		try {

			Log.e("in", "verify");
			PHRImage image;
			Log.e("verifyfoodsetimage", setImage.toString());
			if (setImage == true) {
				Log.e("verifyfoodsetimage", "in");
				String encodedImage = ImageHandler.encodeImageToBase64(photo);
				image = new PHRImage(encodedImage, PHRImageType.IMAGE);
			} else {
				image = null;
				Log.e("newstatusact", "image null");
			}
			VerificationService verificationService = new VerificationServiceImpl();
			FoodTrackerEntry foodEntry = new FoodTrackerEntry(
					verifyFoodTrackerEntry.getFacebookID(),
					verifyFoodTrackerEntry.getTimestamp(), notesStatus
							.getText().toString(), image, chosenFood,
					Double.parseDouble(txtFoodQuantity.getText().toString()));
			FoodTrackerService foodTrackerService = new FoodTrackerServiceImpl();
			foodTrackerService.add(foodEntry);

			verificationService.delete(unferifiedRestaurant);

			Intent intent = new Intent(getApplicationContext(),
					FoodTrackerDailyActivity.class);
			SimpleDateFormat fmtFood = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
			String txtdate = fmtFood.format(verifyFoodTrackerEntry
					.getTimestamp());
			Log.e("newstatus", txtdate);
			intent.putExtra("date", txtdate);
			startActivity(intent);
			finish();
		} catch (EntryNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			runOnUiThread(new Runnable(){
		        public void run() {
					Toast.makeText(HealthGem.getContext(), "No Internet Connection !",
							Toast.LENGTH_LONG).show();
		        }
		    });
			e.printStackTrace();
		} catch (OutdatedAccessTokenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void verifySportToDatabase() {

		try {
			Log.e("in", "verify");
			PHRImage image;
			Log.e("verifySportsetimage", setImage.toString());
			if (setImage == true) {
				Log.e("verifySportsetimage", "in");
				String encodedImage = ImageHandler.encodeImageToBase64(photo);
				image = new PHRImage(encodedImage, PHRImageType.IMAGE);
			} else {
				image = null;
				Log.e("newstatusact", "image null");
			}
			Weight weight = null;
			WeightTrackerService weightService = new WeightTrackerServiceImpl();
			try {
				weight = weightService.getLatest();
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			double calBurned = chosenActivity.getMET()
					* weight.getWeightInKilograms();
			int sec = 0;
			if (txtActivityDurationUnit.getText().toString().equals("hr"))
				sec = (int) Math.round(Double.parseDouble(txtActivityDuration
						.getText().toString()) * 3600);
			else if (txtActivityDurationUnit.getText().toString().equals("min"))
				sec = (int) Math.round(Double.parseDouble(activityDuration
						.getText().toString()) * 60);

			VerificationService verificationService = new VerificationServiceImpl();
			ActivityTrackerEntry activityEntry = new ActivityTrackerEntry(
					verifyActivityTrackerEntry.getFacebookID(),
					verifyActivityTrackerEntry.getTimestamp(), notesStatus
							.getText().toString(), image, chosenActivity,
					calBurned, sec);
			ActivityTrackerService activityTrackerService = new ActivityTrackerServiceImpl();
			activityTrackerService.add(activityEntry);

			verificationService.delete(unferifiedSport);

			Intent intent = new Intent(getApplicationContext(),
					ActivitiesTrackerActivity.class);
			startActivity(intent);
			finish();
		} catch (EntryNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			runOnUiThread(new Runnable(){
		        public void run() {
					Toast.makeText(HealthGem.getContext(), "No Internet Connection !",
							Toast.LENGTH_LONG).show();
		        }
		    });
			e.printStackTrace();
		} catch (OutdatedAccessTokenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void verifyActivityToDatabase() {

		try {
			Log.e("in", "verify");
			PHRImage image;
			Log.e("verifyactivitysetimage", setImage.toString());
			if (setImage == true) {
				Log.e("verifyactivitysetimage", "in");
				String encodedImage = ImageHandler.encodeImageToBase64(photo);
				image = new PHRImage(encodedImage, PHRImageType.IMAGE);
			} else {
				image = null;
				Log.e("newstatuact", "image null");
			}
			VerificationService verificationService = new VerificationServiceImpl();
			int sec = 0;
			if (txtActivityDurationUnit.getText().toString().equals("hr"))
				sec = (int) Math.round(Double.parseDouble(txtActivityDuration
						.getText().toString()) * 3600);
			else if (txtActivityDurationUnit.getText().toString().equals("min"))
				sec = (int) Math.round(Double.parseDouble(activityDuration
						.getText().toString()) * 60);

			Weight weight = null;
			WeightTrackerService weightService = new WeightTrackerServiceImpl();

			weight = weightService.getLatest();

			double calBurned = chosenActivity.getMET()
					* weight.getWeightInKilograms();

			ActivityTrackerEntry activityEntry = new ActivityTrackerEntry(
					unferifiedActivity.getFacebookID(),
					unferifiedActivity.getTimestamp(), notesStatus.getText()
							.toString(), image, chosenActivity, calBurned, sec);

			ActivityTrackerService activityTrackerService = new ActivityTrackerServiceImpl();
			activityTrackerService.add(activityEntry);

			verificationService.delete(unferifiedActivity);
			Intent intent = new Intent(getApplicationContext(),
					ActivitiesTrackerActivity.class);
			startActivity(intent);
			finish();
		} catch (EntryNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			runOnUiThread(new Runnable(){
		        public void run() {
					Toast.makeText(HealthGem.getContext(), "No Internet Connection !",
							Toast.LENGTH_LONG).show();
		        }
		    });
			e.printStackTrace();
		} catch (OutdatedAccessTokenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}

	private void selectImage() {
		final CharSequence[] items = { "Take Photo", "Choose from Library",
				"Cancel" };

		AlertDialog.Builder builder = new AlertDialog.Builder(
				NewStatusActivity.this);
		builder.setTitle("Add Photo!");
		builder.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int item) {
				if (items[item].equals("Take Photo")) {

					Intent intent = new Intent(
							"android.media.action.IMAGE_CAPTURE");
					File file = new File(Environment
							.getExternalStorageDirectory()
							+ File.separator
							+ "image.jpg");

					intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
					startActivityForResult(intent,
							CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE);

				} else if (items[item].equals("Choose from Library")) {
					Intent intent = new Intent(
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					intent.setType("image/*");
					startActivityForResult(
							Intent.createChooser(intent, "Select File"),
							SELECT_FILE);
				} else if (items[item].equals("Cancel")) {
					dialog.dismiss();
				}
			}
		});
		builder.show();
	}

/*	public String getPath(Uri uri) {
		String[] projection = { MediaColumns.DATA };
		Cursor cursor = HealthGem.getContext().getContentResolver()
				.query(uri, projection, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}*/
	
	/**
	 * Get a file path from a Uri. This will get the the path for Storage Access
	 * Framework Documents, as well as the _data field for the MediaStore and
	 * other file-based ContentProviders.
	 *
	 * @param context The context.
	 * @param uri The Uri to query.
	 * @author paulburke
	 */
	@SuppressLint("NewApi")
	public static String getPath(final Context context, final Uri uri) {

	    final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

	    // DocumentProvider
	    if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
	        // ExternalStorageProvider
	        if (isExternalStorageDocument(uri)) {
	            final String docId = DocumentsContract.getDocumentId(uri);
	            final String[] split = docId.split(":");
	            final String type = split[0];

	            if ("primary".equalsIgnoreCase(type)) {
	                return Environment.getExternalStorageDirectory() + "/" + split[1];
	            }

	            // TODO handle non-primary volumes
	        }
	        // DownloadsProvider
	        else if (isDownloadsDocument(uri)) {

	            final String id = DocumentsContract.getDocumentId(uri);
	            final Uri contentUri = ContentUris.withAppendedId(
	                    Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

	            return getDataColumn(context, contentUri, null, null);
	        }
	        // MediaProvider
	        else if (isMediaDocument(uri)) {
	            final String docId = DocumentsContract.getDocumentId(uri);
	            final String[] split = docId.split(":");
	            final String type = split[0];

	            Uri contentUri = null;
	            if ("image".equals(type)) {
	                contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
	            } else if ("video".equals(type)) {
	                contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
	            } else if ("audio".equals(type)) {
	                contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
	            }

	            final String selection = "_id=?";
	            final String[] selectionArgs = new String[] {
	                    split[1]
	            };

	            return getDataColumn(context, contentUri, selection, selectionArgs);
	        }
	    }
	    // MediaStore (and general)
	    else if ("content".equalsIgnoreCase(uri.getScheme())) {

	        // Return the remote address
	        if (isGooglePhotosUri(uri))
	            return uri.getLastPathSegment();

	        return getDataColumn(context, uri, null, null);
	    }
	    // File
	    else if ("file".equalsIgnoreCase(uri.getScheme())) {
	        return uri.getPath();
	    }

	    return null;
	}

	/**
	 * Get the value of the data column for this Uri. This is useful for
	 * MediaStore Uris, and other file-based ContentProviders.
	 *
	 * @param context The context.
	 * @param uri The Uri to query.
	 * @param selection (Optional) Filter used in the query.
	 * @param selectionArgs (Optional) Selection arguments used in the query.
	 * @return The value of the _data column, which is typically a file path.
	 */
	public static String getDataColumn(Context context, Uri uri, String selection,
	        String[] selectionArgs) {

	    Cursor cursor = null;
	    final String column = "_data";
	    final String[] projection = {
	            column
	    };

	    try {
	        cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
	                null);
	        if (cursor != null && cursor.moveToFirst()) {
	            final int index = cursor.getColumnIndexOrThrow(column);
	            return cursor.getString(index);
	        }
	    } finally {
	        if (cursor != null)
	            cursor.close();
	    }
	    return null;
	}


	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is ExternalStorageProvider.
	 */
	public static boolean isExternalStorageDocument(Uri uri) {
	    return "com.android.externalstorage.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is DownloadsProvider.
	 */
	public static boolean isDownloadsDocument(Uri uri) {
	    return "com.android.providers.downloads.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is MediaProvider.
	 */
	public static boolean isMediaDocument(Uri uri) {
	    return "com.android.providers.media.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is Google Photos.
	 */
	public static boolean isGooglePhotosUri(Uri uri) {
	    return "com.google.android.apps.photos.content".equals(uri.getAuthority());
	}

}
