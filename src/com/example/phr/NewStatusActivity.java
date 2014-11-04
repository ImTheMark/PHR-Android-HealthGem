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
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
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

import com.example.phr.enums.TrackerInputType;
import com.example.phr.exceptions.DataAccessException;
import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.exceptions.WebServerException;
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
	ImageView statusImage;
	Bitmap photo;
	Boolean setImage;
	TextView txtCurrentTracker;
	UnverifiedFoodEntry unferifiedFood;
	UnverifiedActivityEntry unferifiedActivity;
	UnverifiedRestaurantEntry unferifiedRestaurant;
	public static final int CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE = 1777;
	DecimalFormat df = new DecimalFormat("#.00");

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("Post a Status");
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();

		StrictMode.setThreadPolicy(policy);
		setContentView(R.layout.activity_new_status);
		Log.e("in", "ew status");

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
		txtCurrentTracker = (TextView) findViewById(R.id.txtCurrentTracker);

		setImage = false;
		currentTracker = TrackerInputType.NOTES;
		// txtCurrentTracker.setText(TrackerInputType.NOTES);
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
				/*
				 * Intent cameraIntent = new Intent(
				 * android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				 * startActivityForResult(cameraIntent, CAMERA_REQUEST);
				 */
				Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
				File file = new File(Environment.getExternalStorageDirectory()
						+ File.separator + "image.jpg");

				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
				startActivityForResult(intent,
						CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE);

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
				txtCurrentTracker.setText(TrackerInputType.BLOOD_PRESSURE);
				callBloodPressureInput(100, 70);
			} else if (tracker.equals(TrackerInputType.BLOOD_SUGAR)) {
				currentTracker = TrackerInputType.BLOOD_SUGAR;
				txtCurrentTracker.setText(TrackerInputType.BLOOD_SUGAR);
				callBloodSugarInput(4, "before meal");
			} else if (tracker.equals(TrackerInputType.NOTES)) {
				txtCurrentTracker.setText(TrackerInputType.NOTES);
				currentTracker = TrackerInputType.NOTES;
				callNotesInput();
			} else if (tracker.equals(TrackerInputType.WEIGHT)) {
				txtCurrentTracker.setText(TrackerInputType.WEIGHT);
				currentTracker = TrackerInputType.WEIGHT;
				callWeightInput("100", "lb");
			} else if (tracker.equals(TrackerInputType.FOOD)) {
				txtCurrentTracker.setText(TrackerInputType.FOOD);
				currentTracker = TrackerInputType.FOOD;
				callFoodInput();
			} else if (tracker.equals(TrackerInputType.CHECKUP)) {
				txtCurrentTracker.setText(TrackerInputType.CHECKUP);
				currentTracker = TrackerInputType.CHECKUP;
				callCheckUpInput("", "");
			} else if (tracker.equals(TrackerInputType.ACTIVITY)) {
				txtCurrentTracker.setText(TrackerInputType.ACTIVITY);
				currentTracker = TrackerInputType.ACTIVITY;
				callActivityInput();
			}
		} else if (extras != null && in.hasExtra("from")) {
			String from = extras.getString("from");

			if (from.equals("new activity")) {
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
				currentTracker = TrackerInputType.FOOD;
				txtCurrentTracker.setText(TrackerInputType.FOOD);
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
			cal = met * weight.getWeightInKilograms() * duration * 60;

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
		} else if (requestCode == 3) {
			// String activity = data.getStringExtra("activity chosen");
			chosenActivity = (Activity) data.getExtras().getSerializable(
					"activity chosen");
			currentTracker = TrackerInputType.ACTIVITY;
			txtCurrentTracker.setText(TrackerInputType.ACTIVITY);
			callActivityDurationInput(1, "hr");
		} else if (requestCode == 4) {
			chosenFood = (Food) data.getExtras().getSerializable("food chosen");
			txtCurrentTracker.setText(TrackerInputType.FOOD);
			currentTracker = TrackerInputType.FOOD;
			callFoodServingInput(chosenFood, 1.0);

		} else if (requestCode == CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE) {
			// Get our saved file into a bitmap object:
			File file = new File(Environment.getExternalStorageDirectory()
					+ File.separator + "image.jpg");

			photo = DecodeImage.decodeFile(file.getAbsolutePath());
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
		alertDialogBuilder
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {

						setFoodTemplate(Double.parseDouble(txtFoodSize
								.getText().toString()), food, notesStatus
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

	private void callFoodInfoEdit() {
		LayoutInflater layoutInflater = LayoutInflater.from(context);

		View foodInfoView = layoutInflater.inflate(
				R.layout.item_food_info_edit, null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);
		alertDialogBuilder.setView(foodInfoView);

		chosenFood.setEntryID(null); // to change to new food entry
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
								chosenActivity.getMET(), Double
										.parseDouble(activityDuration.getText()
												.toString()), String
										.valueOf(activityUnitSpinner
												.getSelectedItem()),
								"How you feel? ", photo);
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
								.toString(), photo);
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

		alertDialogBuilder
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
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
								.toString(), photo);
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
								notesStatus.getText().toString(), photo);

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
			PHRImage image;
			if (setImage == true) {
				String encodedImage = ImageHandler.encodeImageToBase64(photo);
				image = new PHRImage(encodedImage, PHRImageType.IMAGE);
			} else
				image = null;
			BloodPressure bp = new BloodPressure(timestamp, notesStatus
					.getText().toString(), image, systolicPicker.getCurrent(),
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
			PHRImage image;
			if (setImage == true) {
				String encodedImage = ImageHandler.encodeImageToBase64(photo);
				image = new PHRImage(encodedImage, PHRImageType.IMAGE);
			} else
				image = null;
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
			Weight weight = new Weight(timestamp, notesStatus.getText()
					.toString(), image, newWeight);

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
			PHRImage image;
			if (setImage == true) {
				String encodedImage = ImageHandler.encodeImageToBase64(photo);
				image = new PHRImage(encodedImage, PHRImageType.IMAGE);
			} else
				image = null;
			Note note = new Note(timestamp, null, image, notesStatus.getText()
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
					fbId = fbPostService.publish("Hi, from Healthgem");
					Log.e("fb", "done");
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
			PHRImage image;
			if (setImage == true) {
				String encodedImage = ImageHandler.encodeImageToBase64(photo);
				image = new PHRImage(encodedImage, PHRImageType.IMAGE);
			} else
				image = null;
			CheckUp checkup = new CheckUp(timestamp, null, image, txtPurpose
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
			PHRImage image;
			if (setImage == true) {
				String encodedImage = ImageHandler.encodeImageToBase64(photo);
				image = new PHRImage(encodedImage, PHRImageType.IMAGE);
			} else
				image = null;
			ActivityTrackerEntry activityEntry = null;
			int sec = 0;
			if (txtActivityDurationUnit.toString().equals("hr"))
				sec = Integer.parseInt(txtActivityDuration.toString()) * 3600;
			else if (txtActivityDurationUnit.toString().equals("min"))
				sec = Integer.parseInt(activityDuration.toString()) * 60;

			activityEntry = new ActivityTrackerEntry(timestamp, notesStatus
					.getText().toString(), image, chosenActivity,
					Double.parseDouble(txtActivityCal.getText().toString()),
					sec);

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
		else if (mode.equals("verify"))
			getMenuInflater().inflate(R.menu.menu_verify_status, menu);
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
					Date date = null;
					try {
						date = fmt.parse(dateFormat.format(calobj.getTime())
								+ " " + timeFormat.format(calobj.getTime()));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Timestamp timestamp = new Timestamp(date.getTime());
					SimpleDateFormat fmtFood = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
					String txtdate = fmtFood.format(timestamp);
					Intent intent = new Intent(getApplicationContext(),
							FoodTrackerDailyActivity.class);
					Log.e("newstatus", txtdate);
					intent.putExtra("date", txtdate);
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
					SimpleDateFormat fmtFood = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
					String txtdate = fmtFood.format(editFoodTrackerEntry
							.getTimestamp());
					Log.e("newstatus", txtdate);
					intent.putExtra("date", txtdate);
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

		case R.id.menu_item_status_verify:
			try {
				if (currentTracker.equals(TrackerInputType.FOOD)) {
					Log.e("call", "verify");
					verifyFoodToDatabase();
					Log.e("call", "foodActivity");
					Intent intent = new Intent(getApplicationContext(),
							FoodTrackerDailyActivity.class);
					SimpleDateFormat fmtFood = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
					String txtdate = fmtFood.format(unferifiedFood
							.getTimestamp());
					Log.e("newstatus", txtdate);
					intent.putExtra("date", txtdate);
					startActivity(intent);
				} else if (currentTracker.equals(TrackerInputType.ACTIVITY)) {
					verifyActivityToDatabase();
					Log.e("call", "acitivityActivity");
					Intent intent = new Intent(getApplicationContext(),
							ActivitiesTrackerActivity.class);
					startActivity(intent);
				} else if (currentTracker.equals(TrackerInputType.RESTAURANT)) {
					verifyRestaurantToDatabase();
					Log.e("call", "foodActivity");
					Intent intent = new Intent(getApplicationContext(),
							FoodTrackerDailyActivity.class);
					SimpleDateFormat fmtFood = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
					String txtdate = fmtFood.format(verifyFoodTrackerEntry
							.getTimestamp());
					Log.e("newstatus", txtdate);
					intent.putExtra("date", txtdate);
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
			PHRImage image;
			if (setImage == true) {
				String encodedImage = ImageHandler.encodeImageToBase64(photo);
				image = new PHRImage(encodedImage, PHRImageType.IMAGE);
			} else
				image = null;

			int sec = 0;
			if (txtActivityDurationUnit.toString().equals("hr"))
				sec = Integer.parseInt(txtActivityDuration.toString()) * 3600;
			else if (txtActivityDurationUnit.toString().equals("min"))
				sec = Integer.parseInt(activityDuration.toString()) * 60;

			ActivityTrackerEntry activityEntry = new ActivityTrackerEntry(
					editActivityTrackerEntry.getEntryID(),
					editActivityTrackerEntry.getTimestamp(), notesStatus
							.getText().toString(), image, chosenActivity,
					Double.parseDouble(txtActivityCal.getText().toString()),
					sec);

			ActivityTrackerService activityTrackerService = new ActivityTrackerServiceImpl();
			activityTrackerService.edit(activityEntry);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void verifyFoodToDatabase() throws ServiceException,
			OutdatedAccessTokenException, WebServerException,
			DataAccessException {

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

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void verifyRestaurantToDatabase() throws ServiceException,
			OutdatedAccessTokenException, WebServerException,
			DataAccessException {

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
					verifyFoodTrackerEntry.getFacebookID(),
					verifyFoodTrackerEntry.getTimestamp(), notesStatus
							.getText().toString(), image, chosenFood,
					Double.parseDouble(txtFoodQuantity.getText().toString()));
			FoodTrackerService foodTrackerService = new FoodTrackerServiceImpl();
			foodTrackerService.add(foodEntry);
			verificationService.delete(unferifiedRestaurant);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void verifyActivityToDatabase() throws ServiceException,
			OutdatedAccessTokenException, WebServerException,
			DataAccessException {

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
			if (txtActivityDurationUnit.toString().equals("hr"))
				sec = Integer.parseInt(txtActivityDuration.toString()) * 3600;
			else if (txtActivityDurationUnit.toString().equals("min"))
				sec = Integer.parseInt(activityDuration.toString()) * 60;

			ActivityTrackerEntry activityEntry = new ActivityTrackerEntry(
					unferifiedActivity.getFacebookID(),
					unferifiedActivity.getTimestamp(), notesStatus.getText()
							.toString(), image, chosenActivity,
					Double.parseDouble(txtActivityCal.getText().toString()),
					sec);

			ActivityTrackerService activityTrackerService = new ActivityTrackerServiceImpl();
			activityTrackerService.add(activityEntry);
			verificationService.delete(unferifiedActivity);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);

		/*
		 * String stateSaved = savedInstanceState.getString("saved_state");
		 * Log.e("txtCuurent camera back",
		 * txtCurrentTracker.getText().toString()); if (stateSaved != null) {
		 * 
		 * if (stateSaved.equals(TrackerInputType.FOOD)) {
		 * foodTemplate.setVisibility(View.VISIBLE); currentTracker =
		 * TrackerInputType.FOOD; } else if
		 * (txtCurrentTracker.getText().toString()
		 * .equals(TrackerInputType.ACTIVITY)) {
		 * activityTemplate.setVisibility(View.VISIBLE); currentTracker =
		 * TrackerInputType.ACTIVITY; } else if
		 * (txtCurrentTracker.getText().toString()
		 * .equals(TrackerInputType.BLOOD_PRESSURE)) {
		 * bpTemplate.setVisibility(View.VISIBLE); currentTracker =
		 * TrackerInputType.BLOOD_PRESSURE; } else if
		 * (stateSaved.equals(TrackerInputType.BLOOD_SUGAR)) { Log.e("bs",
		 * "camera"); bsTemplate.setVisibility(View.VISIBLE); currentTracker =
		 * TrackerInputType.BLOOD_SUGAR; } else if
		 * (txtCurrentTracker.getText().toString()
		 * .equals(TrackerInputType.WEIGHT)) {
		 * weightTemplate.setVisibility(View.VISIBLE); currentTracker =
		 * TrackerInputType.WEIGHT; } else if
		 * (txtCurrentTracker.getText().toString()
		 * .equals(TrackerInputType.CHECKUP)) {
		 * checkupTemplate.setVisibility(View.VISIBLE); currentTracker =
		 * TrackerInputType.CHECKUP; }
		 * 
		 * }
		 */

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);

		/*
		 * String stateToSave = txtCurrentTracker.getText().toString();
		 * Log.e("stateToSave", stateToSave);
		 */
	}

}
