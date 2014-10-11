package com.example.phr;

import java.util.ArrayList;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.phr.adapter.BloodSugarAdapter;
import com.example.phr.enums.TrackerInputType;
import com.example.phr.exceptions.EntryNotFoundException;
import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.mobile.models.BloodSugar;
import com.example.phr.serviceimpl.BloodSugarTrackerServiceImpl;

public class BloodSugarTrackerActivity extends Activity {

	ListView mBloodSugarList;
	BloodSugarAdapter bloodSugarAdapter;
	ImageView mBtnBloodsugarPost;
	List<BloodSugar> list;
	AlertDialog.Builder alertDialog;
	ArrayList<String> names;
	String mode;
	BloodSugarTrackerServiceImpl bsServiceImpl;
	AlertDialog alertD;
	BloodSugar chosenItem;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bloodsugar_tracker);
		setTitle("Blood Sugar Tracker");
		getActionBar().setDisplayHomeAsUpEnabled(true);
		mBloodSugarList = (ListView) findViewById(R.id.listView_bloodsugar);
		list = new ArrayList<BloodSugar>();

		// FAKE DATA
		/*
		 * BloodSugar data1 = new BloodSugar(1, 7.5, "Post prandial" ,""
		 * ,"Jul 12, 2014", "3:40pm",
		 * getResources().getDrawable(R.drawable.bloodsugar_normal));
		 * 
		 * BloodSugar data2 = new BloodSugar(2, 9, "Post prandial" ,""
		 * ,"Jul 05, 2014", "5:40pm",
		 * getResources().getDrawable(R.drawable.bloodsugar_warning));
		 * 
		 * BloodSugar data3 = new BloodSugar(3, 7.4, "Post prandial" ,""
		 * ,"Jun 28, 2014", "8:40pm",
		 * getResources().getDrawable(R.drawable.bloodsugar_normal));
		 * 
		 * BloodSugar data4 = new BloodSugar(4, 9, "Post prandial" ,""
		 * ,"Jun 21, 2014", "3:40pm",
		 * getResources().getDrawable(R.drawable.bloodsugar_warning));
		 * 
		 * BloodSugar data5 = new BloodSugar(2, 9, "Post prandial" ,""
		 * ,"Jun 14, 2014", "5:40pm",
		 * getResources().getDrawable(R.drawable.bloodsugar_warning));
		 * 
		 * BloodSugar data6 = new BloodSugar(3, 7.4, "Post prandial" ,""
		 * ,"Jun 07, 2014", "8:40pm",
		 * getResources().getDrawable(R.drawable.bloodsugar_normal));
		 * 
		 * BloodSugar data7 = new BloodSugar(4, 9, "Post prandial" ,""
		 * ,"May 31, 2014", "3:40pm",
		 * getResources().getDrawable(R.drawable.bloodsugar_warning));
		 * 
		 * list.add(data1); list.add(data2); list.add(data3); list.add(data4);
		 * list.add(data5); list.add(data6); list.add(data7);
		 */

		// MobileBloodSugarDaoImpl bsDaoImpl = new MobileBloodSugarDaoImpl();
		bsServiceImpl = new BloodSugarTrackerServiceImpl();
		try {
			// list = bsDaoImpl.getAllReversed();
			// list = bsDaoImpl.getAll();
			list = bsServiceImpl.getAll();

			/*
			 * } catch (DataAccessException e) { // TODO Auto-generated catch
			 * block e.printStackTrace();
			 */
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.e(String.valueOf(list.size()), "size");
		bloodSugarAdapter = new BloodSugarAdapter(getApplicationContext(), list);
		mBloodSugarList.setAdapter(bloodSugarAdapter);
		mBloodSugarList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Log.e("bloodsugar",
						String.valueOf(list.get(arg2).getBloodSugar()));
				/*
				 * Intent i = new Intent(); Bundle b = new Bundle();
				 * b.putParcelable("bs", list.get); i.putExtras(b);
				 * i.setClass(this, NewStatusActivity.class); startActivity(i);
				 */
				chosenItem = (BloodSugar) arg0.getAdapter().getItem(arg2);
				mode = "";
				names = new ArrayList<String>();
				names.add("Edit");
				names.add("Delete");
				alertDialog = new AlertDialog.Builder(
						BloodSugarTrackerActivity.this);
				LayoutInflater inflater = getLayoutInflater();
				View convertView = inflater.inflate(R.layout.item_dialogbox,
						null);
				alertDialog.setView(convertView);
				alertDialog.setTitle("What to do?");
				ListView lv = (ListView) convertView
						.findViewById(R.id.dialogList);
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						getApplicationContext(),
						android.R.layout.simple_list_item_1, names);
				lv.setAdapter(adapter);

				lv.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						mode = names.get(arg2);
						alertD.dismiss();
						Log.e("mode", names.get(arg2));

						if (mode.equals("Edit")) {

							Intent i = new Intent(getApplicationContext(),
									NewStatusActivity.class);
							i.putExtra("edit", TrackerInputType.BLOOD_SUGAR);
							i.putExtra("object", chosenItem);
							startActivity(i);
						} else if (mode.equals("Delete")) {

							try {
								Log.e("bloosugar", "del");
								bsServiceImpl.delete(chosenItem);
								Log.e("bloodsugar", "del_done");
							} catch (ServiceException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (OutdatedAccessTokenException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (EntryNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							Intent i = new Intent(getApplicationContext(),
									BloodSugarTrackerActivity.class);
							startActivity(i);
						}
					}

				});
				alertD = alertDialog.create();
				alertD.show();
				Log.e("in", "in");

			}

		});

		// Graph
		// ------------------------------
		View bloodSugarChart;

		String[] bloodSugarMonth = new String[] { "May 31", "Jun 07", "Jun 14",
				"Jun 21", "Jun 28", "Jul 05", "Jul 12" };

		int[] bloodSugarx = { 1, 2, 3, 4, 5, 6, 7 };
		double[] bloodsugar = { 9, 7.4, 9, 9, 7.4, 9, 7.5 };

		XYSeries bloodSugarSeries = new XYSeries("Glucose Level");

		for (int i = 0; i < bloodSugarx.length; i++) {
			bloodSugarSeries.add(bloodSugarx[i], bloodsugar[i]);
		}

		XYMultipleSeriesDataset bloodsugarDataset = new XYMultipleSeriesDataset();

		bloodsugarDataset.addSeries(bloodSugarSeries);

		XYSeriesRenderer mmolRenderer = new XYSeriesRenderer();
		mmolRenderer.setColor(Color.parseColor("#B559BA"));
		mmolRenderer.setPointStyle(PointStyle.CIRCLE);
		mmolRenderer.setFillPoints(true);
		mmolRenderer.setLineWidth(10);
		mmolRenderer.setDisplayChartValues(true);
		mmolRenderer.setChartValuesTextSize(25);
		mmolRenderer.setChartValuesSpacing(20);

		XYMultipleSeriesRenderer bloodSugarMultiRenderer = new

		XYMultipleSeriesRenderer();
		bloodSugarMultiRenderer.setXLabels(0);
		bloodSugarMultiRenderer.setChartTitle("Blood Sugar Graph");
		bloodSugarMultiRenderer.setXTitle("Year 2014");
		bloodSugarMultiRenderer.setYTitle("Glucose Level (mmol/L)");
		bloodSugarMultiRenderer.setZoomButtonsVisible(false);
		bloodSugarMultiRenderer.setAxisTitleTextSize(30);
		bloodSugarMultiRenderer.setChartTitleTextSize(30);
		bloodSugarMultiRenderer.setLegendTextSize(30);
		bloodSugarMultiRenderer.setPointSize(10);
		bloodSugarMultiRenderer.setXAxisMin(0);
		bloodSugarMultiRenderer.setXAxisMax(7);

		// margin --- top, left, bottom, right
		bloodSugarMultiRenderer.setMargins(new int[] { 90, 100, 120, 50 });
		bloodSugarMultiRenderer.setLegendHeight(60);

		for (int i = 0; i < bloodSugarx.length; i++) {
			bloodSugarMultiRenderer.addXTextLabel(i + 1, bloodSugarMonth[i]);
		}

		bloodSugarMultiRenderer.setApplyBackgroundColor(true);
		bloodSugarMultiRenderer.setBackgroundColor(Color.argb(0x00, 0x01, 0x01,
				0x01));
		bloodSugarMultiRenderer.setMarginsColor(Color.argb(0x00, 0x01, 0x01,
				0x01));
		bloodSugarMultiRenderer.setAxesColor(Color.BLACK);
		bloodSugarMultiRenderer.setLabelsColor(Color.BLACK);
		bloodSugarMultiRenderer.setXLabelsColor(Color.BLACK);
		bloodSugarMultiRenderer.setYLabelsColor(0, Color.BLACK);
		bloodSugarMultiRenderer.setAxisTitleTextSize(30);
		bloodSugarMultiRenderer.setLabelsTextSize(30);

		bloodSugarMultiRenderer.addSeriesRenderer(mmolRenderer);

		LinearLayout bloodSugarContainer = (LinearLayout) findViewById(R.id.bloodsugarGraph);

		bloodSugarChart = ChartFactory.getLineChartView(getBaseContext(),
				bloodsugarDataset, bloodSugarMultiRenderer);

		bloodSugarContainer.addView(bloodSugarChart);

		mBtnBloodsugarPost = (ImageView) findViewById(R.id.bloodsugarBanner);
		mBtnBloodsugarPost.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),
						NewStatusActivity.class);
				i.putExtra("tracker", TrackerInputType.BLOOD_SUGAR);
				startActivity(i);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_tracker_help, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}

}
