package com.example.phr.adapter;

import java.util.List;

import com.example.phr.CalorieBreakdownTrackerActivity;
import com.example.phr.CalorieTrackerActivity;
import com.example.phr.R;
import com.example.phr.model.Calorie;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

public class CalorieAdapter extends BaseAdapter {

	private Context mContext;
	private List<Calorie> mListOfCalorie;

	private static class ViewHolder {
		TextView progress;
		TextView foodCal;
		TextView activityCal;
		TextView day;
		TextView month;
		ProgressBar bar;
	}

	public CalorieAdapter(Context aContext, List<Calorie> aListOfCalories) {
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
			convertView = inflater.inflate(R.layout.item_calorie, parent,
					false);

			viewHolder = new ViewHolder();
			viewHolder.progress = (TextView) convertView
					.findViewById(R.id.txtProgress);
			viewHolder.foodCal = (TextView) convertView
					.findViewById(R.id.txtFood);
			viewHolder.activityCal = (TextView) convertView
					.findViewById(R.id.txtAct);

			viewHolder.day = (TextView) convertView
					.findViewById(R.id.txtDay);
			
			viewHolder.month = (TextView) convertView
					.findViewById(R.id.txtMonth);
			
			viewHolder.bar = (ProgressBar) convertView
					.findViewById(R.id.progressBar2);
			convertView.setTag(viewHolder);
		}

		viewHolder = (ViewHolder) convertView.getTag();
		viewHolder.progress.setText(mListOfCalorie.get(position).getProgress());
		viewHolder.foodCal.setText(String.valueOf(mListOfCalorie.get(position).getFoodCal()));
		viewHolder.activityCal.setText(String.valueOf(mListOfCalorie.get(
				position).getActivityCal()));

		viewHolder.day.setText(String.valueOf(mListOfCalorie.get(
				position).getDay()));
		
		viewHolder.month.setText(String.valueOf(mListOfCalorie.get(
				position).getMonth()));
		
		viewHolder.bar.setProgress(mListOfCalorie.get(
				position).getProgressBar());
		
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				// FOR STATUS ON CLICK
				Intent intent = new Intent(mContext,
						CalorieBreakdownTrackerActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);;
				v.getContext().startActivity(intent);

			}
		});
		return convertView;
	}

}
