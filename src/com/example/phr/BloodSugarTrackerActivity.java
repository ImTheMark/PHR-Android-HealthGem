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
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.phr.adapter.BloodSugarAdapter;
import com.example.phr.enums.TrackerInputType;
import com.example.phr.model.BloodSugar;

public class BloodSugarTrackerActivity extends Activity{

	
	ListView mBloodSugarList;
	BloodSugarAdapter bloodSugarAdapter;
	ImageView mBtnBloodsugarPost;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bloodsugar_tracker);
		setTitle("Blood Sugar Tracker");
        getActionBar().setDisplayHomeAsUpEnabled(true);
		mBloodSugarList = (ListView) findViewById(R.id.listView_bloodsugar);
				
		// FAKE DATA
		List<BloodSugar> list = new ArrayList<BloodSugar>();
		BloodSugar data1 = new BloodSugar(1, 7.5, "Post prandial" ,"" ,"Jul 12, 2014", "3:40pm", getResources().getDrawable(R.drawable.bloodsugar_normal));
		
		BloodSugar data2 = new BloodSugar(2, 9, "Post prandial" ,"" ,"Jul 05, 2014", "5:40pm", getResources().getDrawable(R.drawable.bloodsugar_warning));

		BloodSugar data3 = new BloodSugar(3, 7.4, "Post prandial" ,"" ,"Jun 28, 2014", "8:40pm", getResources().getDrawable(R.drawable.bloodsugar_normal));
		
		BloodSugar data4 = new BloodSugar(4, 9, "Post prandial" ,"" ,"Jun 21, 2014", "3:40pm", getResources().getDrawable(R.drawable.bloodsugar_warning));
		
		BloodSugar data5 = new BloodSugar(2, 9, "Post prandial" ,"" ,"Jun 14, 2014", "5:40pm", getResources().getDrawable(R.drawable.bloodsugar_warning));

		BloodSugar data6 = new BloodSugar(3, 7.4, "Post prandial" ,"" ,"Jun 07, 2014", "8:40pm", getResources().getDrawable(R.drawable.bloodsugar_normal));
		
		BloodSugar data7 = new BloodSugar(4, 9, "Post prandial" ,"" ,"May 31, 2014", "3:40pm", getResources().getDrawable(R.drawable.bloodsugar_warning));

		list.add(data1);
		list.add(data2);
		list.add(data3);
		list.add(data4);
		list.add(data5);
		list.add(data6);
		list.add(data7);
		
		

		bloodSugarAdapter = new BloodSugarAdapter(getApplicationContext(), list);
		mBloodSugarList.setAdapter(bloodSugarAdapter);
		mBloodSugarList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Log.e("bloodsugar", "CLICKED!");
			}
		});
		
		//Graph	
	//------------------------------
		View bloodSugarChart;

		String[] bloodSugarMonth = new String[] { "May 31", "Jun 07", "Jun 14", "Jun 21", "Jun 28","Jul 05", "Jul 12"};

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
		bloodSugarMultiRenderer.setBackgroundColor(Color.argb(0x00, 0x01, 0x01, 0x01));
		bloodSugarMultiRenderer.setMarginsColor(Color.argb(0x00, 0x01, 0x01, 0x01));
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
				Intent i = new Intent(getApplicationContext(), NewStatusActivity.class);
				i.putExtra("tracker",TrackerInputType.BLOOD_SUGAR);
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
    public boolean onOptionsItemSelected(MenuItem item) 
    {
        switch (item.getItemId()) 
        {
        case android.R.id.home: 
            onBackPressed();
            break;
        default:
            return super.onOptionsItemSelected(item);
        }
        return true;
    }
	
}
