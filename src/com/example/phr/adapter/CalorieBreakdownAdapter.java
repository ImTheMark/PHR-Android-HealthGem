package com.example.phr.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.phr.R;
import com.example.phr.mobile.models.ActivityTrackerEntry;
import com.example.phr.mobile.models.FoodTrackerEntry;
import com.example.phr.mobile.models.TrackerEntry;
import com.example.phr.tools.DateTimeParser;

public class CalorieBreakdownAdapter extends BaseAdapter {

	private final Context mContext;
	private final List<TrackerEntry> mListOfCalorie;

	private static class ViewHolder {
		TextView calorie;
		TextView action;
		TextView servingCount;
		TextView unit;
		TextView time;
		ImageView image;
	}

	public CalorieBreakdownAdapter(Context aContext,
			List<TrackerEntry> aListOfTrackerEntry) {
		mListOfCalorie = aListOfTrackerEntry;
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
			convertView = inflater.inflate(R.layout.item_calorie_breakdown,
					parent, false);

			viewHolder = new ViewHolder();
			viewHolder.calorie = (TextView) convertView.findViewById(R.id.cal);
			viewHolder.action = (TextView) convertView
					.findViewById(R.id.actionName);

			viewHolder.servingCount = (TextView) convertView
					.findViewById(R.id.calBreakdownQuantity);
			viewHolder.unit = (TextView) convertView
					.findViewById(R.id.calBreakdownUnit);

			viewHolder.time = (TextView) convertView.findViewById(R.id.txttime);

			viewHolder.image = (ImageView) convertView
					.findViewById(R.id.actionImage);

			convertView.setTag(viewHolder);
		}

		viewHolder = (ViewHolder) convertView.getTag();
		if (mListOfCalorie.get(position).getClass()
				.equals(FoodTrackerEntry.class)) {
			
			viewHolder.calorie.setText(String
					.valueOf(((FoodTrackerEntry) mListOfCalorie.get(position))
							.getFood().getCalorie()));
			viewHolder.action.setText(((FoodTrackerEntry) mListOfCalorie
					.get(position)).getFood().getName());
			viewHolder.servingCount.setText(String
					.valueOf(((FoodTrackerEntry) mListOfCalorie.get(position)).getServingCount()));
			
			viewHolder.unit.setText(((FoodTrackerEntry) mListOfCalorie
					.get(position)).getFood().getServing());

			viewHolder.time.setText(String.valueOf(DateTimeParser
					.getTime(((FoodTrackerEntry) mListOfCalorie.get(position))
							.getTimestamp())));

			viewHolder.image.setImageResource(R.drawable.icon_food);
		} else if (mListOfCalorie.get(position).getClass()
				.equals(ActivityTrackerEntry.class)) {
			
			viewHolder.calorie.setText(String
					.valueOf(((ActivityTrackerEntry) mListOfCalorie
							.get(position)).getCaloriesBurnedPerHour()));
			viewHolder.action.setText(((ActivityTrackerEntry) mListOfCalorie
					.get(position)).getActivity().getName());
			double hr = ((ActivityTrackerEntry) mListOfCalorie.get(position))
					.getDurationInSeconds() / 3600;
			viewHolder.servingCount.setText(String.valueOf(hr));
			viewHolder.unit.setText("hr");

			viewHolder.time.setText(String.valueOf(DateTimeParser
					.getTime(((ActivityTrackerEntry) mListOfCalorie
							.get(position)).getTimestamp())));

			viewHolder.image.setImageResource(R.drawable.icon_activity);
		}
		return convertView;
	}
}
