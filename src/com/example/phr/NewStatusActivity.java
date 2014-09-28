package com.example.phr;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.mobile.models.BloodPressure;
import com.example.phr.mobile.models.BloodSugar;
import com.example.phr.mobile.models.PHRImage;
import com.example.phr.mobile.models.PHRImageType;
import com.example.phr.mobile.models.Weight;
import com.example.phr.service.BloodPressureService;
import com.example.phr.serviceimpl.BloodPressureServiceImpl;

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
	final Context context = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("Post a Status");
		setContentView(R.layout.activity_new_status);
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
		dateFormat = new SimpleDateFormat("MMM dd, yyyy",
				Locale.ENGLISH);
		timeFormat = new SimpleDateFormat("HH:mm:ss",
				Locale.ENGLISH);
		fmt = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss",
				Locale.ENGLISH);
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
		// blood sugar post
		txtSugar = (TextView) findViewById(R.id.sugar);
		bsStatus = (EditText) findViewById(R.id.txtBSStatus);
		txtSugarType = (TextView) findViewById(R.id.txtSugarType);
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
		//activity post
		txtActivity = (TextView) findViewById(R.id.activity);
		activityStatus = (EditText) findViewById(R.id.txtActivityStatus);
		txtActivityDurationUnit = (TextView) findViewById(R.id.activityDurationUnit);
		txtActivityDuration = (TextView) findViewById(R.id.activityDuration);
		activityCal = (LinearLayout) findViewById(R.id.activityCal);
		txtActivityCal = (TextView) findViewById(R.id.txtActivityCal);
		
		//food post
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
		Intent in = getIntent();
		Bundle extras = getIntent().getExtras();
		if (extras != null && in.hasExtra("tracker")) {
			String tracker = extras.getString("tracker");

			if (tracker.equals(TrackerInputType.BLOOD_PRESSURE)) {
				currentTracker = TrackerInputType.BLOOD_PRESSURE;
				callBloodPressureInput();
			} else if (tracker.equals(TrackerInputType.BLOOD_SUGAR)) {
				currentTracker = TrackerInputType.BLOOD_SUGAR;
				callBloodSugarInput();
			} else if (tracker.equals(TrackerInputType.NOTES)) {
				currentTracker = TrackerInputType.NOTES;
				callNotesInput();
			}else if (tracker.equals(TrackerInputType.WEIGHT)) {
				currentTracker = TrackerInputType.WEIGHT;
				callWeightInput();
			} else if (tracker.equals(TrackerInputType.FOOD)) {
				currentTracker = TrackerInputType.FOOD;
				callFoodInput();
			} else if (tracker.equals(TrackerInputType.CHECKUP)) {
				currentTracker = TrackerInputType.CHECKUP;
				callCheckUpInput();
			} else if (tracker.equals(TrackerInputType.ACTIVITY)) {
				currentTracker = TrackerInputType.ACTIVITY;
				callActivityInput();
			}
		}
		else if (extras != null && in.hasExtra("from")){
			String from = extras.getString("from");
			
			if(from.equals("new activity")){
				currentTracker = TrackerInputType.ACTIVITY;
				setNewActivity(extras.getString("activity_name"),extras.getString("activity_cal"),
						extras.getString("activity_duration"),extras.getString("activity_unit"));
				
			}else if (from.equals("new food")){
				currentTracker = TrackerInputType.FOOD;
				setNewFood(extras.getString("food_name"),extras.getString("food_cal"),
						extras.getString("food_protein"),extras.getString("food_carbs"),
						extras.getString("food_fat"),extras.getString("food_serving"),
						extras.getString("food_unit"));
			}
			
		}
	}

	private void setNewFood(String food, String cal, String protein, String carbs, 
									String fat, String serving, String unit) {
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
		
	}

	private void setNewActivity(String name, String cal, String duration, String unit) {
		// TODO Auto-generated method stub
		setAllTemplateGone();
		activityTemplate.setVisibility(View.VISIBLE);
		activityCal.setVisibility(View.VISIBLE);
		
		txtActivity.setText(name);
		txtActivityDurationUnit.setText(unit);
		txtActivityDuration.setText(duration);
		txtActivityCal.setText(cal);
		
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
				callBloodPressureInput();
			} else if (item.equals(TrackerInputType.BLOOD_SUGAR)) {
				currentTracker = TrackerInputType.BLOOD_SUGAR;
				callBloodSugarInput();
			} else if (item.equals(TrackerInputType.NOTES)) {
				currentTracker = TrackerInputType.NOTES;
				callNotesInput();
			}else if (item.equals(TrackerInputType.WEIGHT)) {
				currentTracker = TrackerInputType.WEIGHT;
				callWeightInput();
			} else if (item.equals(TrackerInputType.FOOD)) {
				currentTracker = TrackerInputType.FOOD;
				callFoodInput();
			} else if (item.equals(TrackerInputType.CHECKUP)) {
				currentTracker = TrackerInputType.CHECKUP;
				callCheckUpInput();
			} else if (item.equals(TrackerInputType.ACTIVITY)) {
				currentTracker = TrackerInputType.ACTIVITY;
				callActivityInput();
			}
		}
		else if (requestCode == 3){
			String activity = data.getStringExtra("activity chosen");
			currentTracker = TrackerInputType.ACTIVITY;
			callActivityDurationInput(activity);
		}
		else if (requestCode == 4){
			String food = data.getStringExtra("food chosen");
			String serving = data.getStringExtra("serving");
			double cal = data.getDoubleExtra("cal", 0.0);
			double protein = data.getDoubleExtra("protein", 0.0);
			double carbs = data.getDoubleExtra("carbs", 0.0);
			double fat = data.getDoubleExtra("fat", 0.0);
			currentTracker = TrackerInputType.FOOD;
			callFoodServingInput(food,serving,cal,protein,carbs,fat);
		}
	}

	private void callFoodServingInput(final String food,final String serving, final double cal
			, final double protein, final double carbs, final double fat) {
		// TODO Auto-generated method stub
		
		LayoutInflater layoutInflater = LayoutInflater.from(context);

		View foodView = layoutInflater.inflate(R.layout.item_food_serving_input,
				null);

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
						// set activity cal  -- get from database
						setNewFood(food,String.valueOf(cal),String.valueOf(protein),
								String.valueOf(carbs),String.valueOf(fat),
								String.valueOf(txtFoodSize.getText()),serving);
						
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

		View activityView = layoutInflater.inflate(R.layout.item_activity_duration_input,
				null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);
		alertDialogBuilder.setView(activityView);
		activityDuration = (EditText) activityView.findViewById(R.id.txtActivityDurationTime);
		activityUnitSpinner = (Spinner) activityView
				.findViewById(R.id.activityUnitSpinner);
		alertDialogBuilder
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						// set activity cal  -- get from database
						setNewActivity(activity,"30",
								activityDuration.getText().toString(),String.valueOf(activityUnitSpinner
										.getSelectedItem()));
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

	private void callCheckUpInput() {
		// TODO Auto-generated method stub
		setAllTemplateGone();
		
		LayoutInflater layoutInflater = LayoutInflater.from(context);

		View checkupView = layoutInflater.inflate(R.layout.item_checkup_input,
				null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);
		alertDialogBuilder.setView(checkupView);
		doctor = (EditText) checkupView.findViewById(R.id.txtDoctor);
		purpose = (EditText) checkupView.findViewById(R.id.txtPurpose);
		alertDialogBuilder
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {

						setAllTemplateGone();
						checkupTemplate.setVisibility(View.VISIBLE);

						txtDoctor.setText(doctor.getText());
						txtPurpose.setText(purpose.getText());
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
		setAllTemplateGone();
		notesTemplate.setVisibility(View.VISIBLE);
	}

	private void callWeightInput() {
		// TODO Auto-generated method stub
		setAllTemplateGone();
		LayoutInflater layoutInflater = LayoutInflater.from(context);

		View weightView = layoutInflater.inflate(R.layout.item_weight_input,
				null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);
		alertDialogBuilder.setView(weightView);
		weight = (EditText) weightView.findViewById(R.id.txtWeight);
		weightUnitSpinner = (Spinner) weightView
				.findViewById(R.id.weightUnitSpinner);
		alertDialogBuilder
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {

						setAllTemplateGone();
						weightTemplate.setVisibility(View.VISIBLE);

						txtWeight.setText(weight.getText());
						txtWeightUnit.setText(String.valueOf(weightUnitSpinner
								.getSelectedItem()));
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

	private void callBloodSugarInput() {
		// TODO Auto-generated method stub
		LayoutInflater layoutInflater = LayoutInflater.from(context);

		View bsView = layoutInflater.inflate(R.layout.item_bloodsugar_input,
				null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);
		alertDialogBuilder.setView(bsView);
		sugarPicker = (NumberPicker) bsView.findViewById(R.id.sugarPicker);
		sugarTypeSpinner = (Spinner) bsView.findViewById(R.id.sugarTypeSpinner);
		sugarPicker.setCurrent(120);
		alertDialogBuilder
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {

						setAllTemplateGone();
						bsTemplate.setVisibility(View.VISIBLE);
						Log.e("sugar",
								Integer.toString(sugarPicker.getCurrent()));
						txtSugar.setText(Integer.toString(sugarPicker
								.getCurrent()));
						txtSugarType.setText(String.valueOf(sugarTypeSpinner
								.getSelectedItem()));
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

	private void callBloodPressureInput() {
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
		systolicPicker.setCurrent(120);
		diastolicPicker.setCurrent(80);
		alertDialogBuilder
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {

						setAllTemplateGone();
						bpTemplate.setVisibility(View.VISIBLE);
						Log.e("in", "kkdks");
						Log.e("systolic",
								Integer.toString(systolicPicker.getCurrent()));
						txtSystolic.setText(Integer.toString(systolicPicker
								.getCurrent()));
						txtDiastolic.setText(Integer.toString(diastolicPicker
								.getCurrent()));

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
			OutdatedAccessTokenException {

		try {
			Date date = fmt.parse(dateFormat.format(calobj.getTime()) + " "
					+ timeFormat.format(calobj.getTime()));
			Timestamp timestamp = new Timestamp(date.getTime());

			PHRImage image = new PHRImage("test-image", PHRImageType.IMAGE);
			BloodPressure bp = new BloodPressure(timestamp, bpStatus.getText()
					.toString(), image, systolicPicker.getCurrent(),
					diastolicPicker.getCurrent());

			BloodPressureService bpService = new BloodPressureServiceImpl();
			bpService.add(bp);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void addBloodSugarToDatabase() throws ServiceException,
			OutdatedAccessTokenException {

		try {
			
			Date date = fmt.parse(dateFormat.format(calobj.getTime()) + " "
					+ timeFormat.format(calobj.getTime()));
			Timestamp timestamp = new Timestamp(date.getTime());

			PHRImage image = new PHRImage("test-image", PHRImageType.IMAGE);
			BloodSugar bs = new BloodSugar(timestamp, bsStatus.getText()
					.toString(), image, sugarPicker.getCurrent(),
					String.valueOf(sugarTypeSpinner.getSelectedItem())
					);
//			BloodSugarService bsService = new BloodSugarServiceImpl();
//			bsService.add(bs);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private void addWeightToDatabase() throws ServiceException,
	OutdatedAccessTokenException {
		
		try {
			
			Date date = fmt.parse(dateFormat.format(calobj.getTime()) + " "
					+ timeFormat.format(calobj.getTime()));
			Timestamp timestamp = new Timestamp(date.getTime());
		
			PHRImage image = new PHRImage("test-image", PHRImageType.IMAGE);
			double newWeight;
			Weight weight = new Weight(timestamp, weightStatus.getText()
					.toString(), image, Double.parseDouble(String.valueOf(txtWeight.getText()))
					);
			
		//	BloodSugarService bsService = new BloodSugarServiceImpl();
		//	bsService.add(bs);
		
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
		getMenuInflater().inflate(R.menu.menu_status_post, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_item_status_post:
			if (currentTracker.equals(TrackerInputType.BLOOD_PRESSURE)) {
				try {
					addBloodPressureToDatabase();
				} catch (ServiceException e) {
					// output error message or something
					System.out.println(e.getMessage());
				} catch (OutdatedAccessTokenException e) {
					// Message - > Log user out
					e.printStackTrace();
				}
				// onBackPressed();
				Log.e("added","bp");
				Intent intent = new Intent(getApplicationContext(),
						BloodPressureTrackerActivity.class);
				startActivity(intent);
			}

			else if (currentTracker.equals(TrackerInputType.BLOOD_SUGAR)) {
				try {
					addBloodSugarToDatabase();
				} catch (ServiceException e) {
					// output error message or something
					System.out.println(e.getMessage());
				} catch (OutdatedAccessTokenException e) {
					// Message - > Log user out
					e.printStackTrace();
				}
				// onBackPressed();
				Intent intent = new Intent(getApplicationContext(),
						BloodSugarTrackerActivity.class);
				startActivity(intent);
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

}
