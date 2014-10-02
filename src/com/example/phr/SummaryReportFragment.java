package com.example.phr;

import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.WebServerException;
import com.example.phr.mobile.models.BloodSugar;
import com.example.phr.web.dao.WebBloodSugarDao;
import com.example.phr.web.daoimpl.WebBloodSugarDaoImpl;

public class SummaryReportFragment extends Fragment {

	ProgressBar cProgress;

	int cProgressStatus = 13;
	Button mBtnRetrieve;
	Button mBtnWrite;
	Button mBtnStatus;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_summary_report,
				container, false);

		cProgress = (ProgressBar) rootView.findViewById(R.id.progressBar2);
		Drawable draw = getResources()
				.getDrawable(R.drawable.customprogressbar);
		cProgress.setProgressDrawable(draw);
		cProgress.setProgress(cProgressStatus);
		cProgress.setMax(100);

		mBtnRetrieve = (Button) rootView.findViewById(R.id.btnSync);
		mBtnRetrieve.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getActivity()
						.getApplicationContext(), RetrieveActivity.class);
				startActivity(intent);
			}
		});

		mBtnWrite = (Button) rootView.findViewById(R.id.btnWrite);
		mBtnWrite.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				/*
				 * Intent intent = new
				 * Intent(getActivity().getApplicationContext(),
				 * NewStatusActivity.class); startActivity(intent);
				 */
			}
		});

		mBtnStatus = (Button) rootView.findViewById(R.id.btnStatus);
		mBtnStatus.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),
						NewStatusActivity.class);
				startActivity(intent);
			}
		});

		View dailyChart;

		int[] x = { 0, 1, 2 };
		double[] intake = { 20.41, 24.89, 43.07 };
		double[] recommeded = { 41.25, 53.63, 247.5 };

		String[] mMonth = new String[] { "Protein", "Fats", "Carbohydrates" };

		// Creating an XYSeries for Income
		// CategorySeries incomeSeries = new CategorySeries("Income");
		XYSeries incomeSeries = new XYSeries("Current Intake");
		// Creating an XYSeries for Income
		XYSeries expenseSeries = new XYSeries("Recommended Intake");
		// Adding data to Income and Expense Series
		for (int i = 0; i < x.length; i++) {
			incomeSeries.add(i, intake[i]);
			expenseSeries.add(i, recommeded[i]);
		}

		// Creating a dataset to hold each series
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		// Adding Income Series to the dataset
		dataset.addSeries(incomeSeries);
		// Adding Expense Series to dataset
		dataset.addSeries(expenseSeries);

		// Creating XYSeriesRenderer to customize incomeSeries
		XYSeriesRenderer incomeRenderer = new XYSeriesRenderer();
		incomeRenderer.setColor(Color.parseColor("#C177C9"));
		incomeRenderer.setFillPoints(true);
		incomeRenderer.setLineWidth(2);
		incomeRenderer.setDisplayChartValues(true);
		incomeRenderer.setChartValuesTextSize(20);

		// Creating XYSeriesRenderer to customize expenseSeries
		XYSeriesRenderer expenseRenderer = new XYSeriesRenderer();
		expenseRenderer.setColor(Color.parseColor("#5C77D1"));
		expenseRenderer.setFillPoints(true);
		expenseRenderer.setLineWidth(2);

		expenseRenderer.setDisplayChartValues(true);
		expenseRenderer.setChartValuesTextSize(20);

		// Creating a XYMultipleSeriesRenderer to customize the whole chart
		XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
		multiRenderer.setXLabels(0);
		multiRenderer.setChartTitle("My Daily Nutritional Value Chart \n\n\n");
		multiRenderer.setAxisTitleTextSize(20);
		multiRenderer.setXTitle("\n\n\n Year 2012");
		multiRenderer.setYTitle("");
		multiRenderer.setZoomButtonsVisible(false);
		multiRenderer.setZoomEnabled(false);
		multiRenderer.setMargins(new int[] { 60, 30, 15, 0 });
		multiRenderer.setXAxisMin(-1);
		multiRenderer.setXAxisMax(3);
		multiRenderer.setYAxisMin(0);
		// multiRenderer.setAxisTitleTextSize(30);
		multiRenderer.setChartTitleTextSize(25);
		multiRenderer.setLabelsTextSize(15);
		multiRenderer.setAxesColor(Color.BLACK);
		multiRenderer.setLabelsColor(Color.BLACK);
		multiRenderer.setXLabelsColor(Color.BLACK);
		multiRenderer.setYLabelsColor(0, Color.BLACK);
		// multiRenderer.setLegendTextSize(15);
		multiRenderer.setLabelsTextSize(20);
		// multiRenderer.setLegendHeight(20);
		multiRenderer.setLegendTextSize(20);
		multiRenderer.setApplyBackgroundColor(true);
		multiRenderer.setBackgroundColor(Color.argb(0x00, 0x01, 0x01, 0x01));
		multiRenderer.setMarginsColor(Color.argb(0x00, 0x01, 0x01, 0x01));

		for (int i = 0; i < x.length; i++) {
			multiRenderer.addXTextLabel(i, mMonth[i]);
		}

		// Adding incomeRenderer and expenseRenderer to multipleRenderer
		// Note: The order of adding dataseries to dataset and renderers to
		// multipleRenderer
		// should be same
		multiRenderer.addSeriesRenderer(incomeRenderer);
		multiRenderer.addSeriesRenderer(expenseRenderer);

		// Creating an intent to plot bar chart using dataset and
		// multipleRenderer
		dailyChart = ChartFactory.getBarChartView(getActivity()
				.getBaseContext(), dataset, multiRenderer, Type.DEFAULT);

		LinearLayout dailyContainer = (LinearLayout) rootView
				.findViewById(R.id.piegraph);

		dailyContainer.addView(dailyChart);

		WebBloodSugarDao webBloodSugarDao = new WebBloodSugarDaoImpl();
		try {
			List<BloodSugar> bloodSugarList = webBloodSugarDao.getAll();
			for (BloodSugar b : bloodSugarList) {
				System.out.println(b.getBloodSugar() + " BLOOD SUGAR "
						+ b.getTimestamp());
			}
		} catch (WebServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OutdatedAccessTokenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rootView;
	}

	protected void setChartSettings(XYMultipleSeriesRenderer renderer,
			String title, String xTitle, String yTitle, double xMin,
			double xMax, double yMin, double yMax, int axesColor,
			int labelsColor) {
		renderer.setChartTitle(title);
		renderer.setXTitle(xTitle);
		renderer.setYTitle(yTitle);
		renderer.setXAxisMin(xMin);
		renderer.setXAxisMax(xMax);
		renderer.setYAxisMin(yMin);
		renderer.setYAxisMax(yMax);
		renderer.setAxesColor(axesColor);
		renderer.setLabelsColor(labelsColor);
	}

	protected XYMultipleSeriesRenderer buildBarRenderer(int[] colors) {
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		renderer.setAxisTitleTextSize(16);
		renderer.setChartTitleTextSize(20);
		renderer.setLabelsTextSize(15);
		renderer.setLegendTextSize(15);
		int length = colors.length;
		for (int i = 0; i < length; i++) {
			SimpleSeriesRenderer r = new SimpleSeriesRenderer();
			r.setColor(colors[i]);
			renderer.addSeriesRenderer(r);
		}
		return renderer;
	}

	protected XYMultipleSeriesDataset buildBarDataset(String[] titles,
			List<double[]> values) {// The data source and the pie chart almost,
									// is by some key value pairs
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		int length = titles.length;
		for (int i = 0; i < length; i++) {
			CategorySeries series = new CategorySeries(titles[i]);
			double[] v = values.get(i);
			int seriesLength = v.length;
			for (int k = 0; k < seriesLength; k++) {
				series.add(v[k]);
			}
			dataset.addSeries(series.toXYSeries());
		}
		return dataset;
	}
}
