package com.example.phr.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.phr.R;
import com.example.phr.mobile.models.CalorieTrackerEntry;
import com.example.phr.tools.DateTimeParser;

public class CalorieAdapter extends BaseAdapter {

	private final Context mContext;
	private final List<CalorieTrackerEntry> mListOfCalorie;

	private static class ViewHolder {
		TextView totalCal;
		TextView totalRequiredCal;
		TextView foodCal;
		TextView activityCal;
		TextView day;
		TextView month;
		ProgressBar bar;
	}

	public CalorieAdapter(Context aContext,
			List<CalorieTrackerEntry> aListOfCalories) {
		mListOfCalorie = aListOfCalories;
		mContext = aContext;
	}

	@Override
	public int getCount() {
		return mListOfCalorie.size();
	}

	@Override
	public Object getItem(int position) {
		return mListOfCalorie.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;

		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(mContext);
			convertView = inflater
					.inflate(R.layout.item_calorie, parent, false);

			viewHolder = new ViewHolder();
			viewHolder.totalCal = (TextView) convertView
					.findViewById(R.id.txtProgressTotalCal);
			viewHolder.totalRequiredCal = (TextView) convertView
					.findViewById(R.id.txtProgressRequire);
			viewHolder.foodCal = (TextView) convertView
					.findViewById(R.id.txtFoodCal);
			viewHolder.activityCal = (TextView) convertView
					.findViewById(R.id.txtActCal);

			viewHolder.day = (TextView) convertView.findViewById(R.id.txtDay);

			viewHolder.month = (TextView) convertView
					.findViewById(R.id.txtMonth);

			viewHolder.bar = (ProgressBar) convertView
					.findViewById(R.id.progressBar2);
			convertView.setTag(viewHolder);
		}

		viewHolder = (ViewHolder) convertView.getTag();
		double totalcal = mListOfCalorie.get(position).getTotalCalIntake()
				- mListOfCalorie.get(position).getTotalCalBurned();
		viewHolder.totalCal.setText(String.valueOf(totalcal));
		viewHolder.totalRequiredCal.setText(String.valueOf(mListOfCalorie.get(
				position).getRequireCal()));
		viewHolder.foodCal.setText(String.valueOf(mListOfCalorie.get(position)
				.getTotalCalIntake()));
		viewHolder.activityCal.setText(String.valueOf(mListOfCalorie.get(
				position).getTotalCalBurned()));

		viewHolder.day.setText(String.valueOf(DateTimeParser
				.getDay(mListOfCalorie.get(position).getDate())));

		viewHolder.month.setText(String.valueOf(DateTimeParser
				.getMonth(mListOfCalorie.get(position).getDate())));
		int progress = (int) Math.round((totalcal / mListOfCalorie
				.get(position).getRequireCal()) * 100);

		viewHolder.bar.setProgress(progress);

		return convertView;
	}

}
