package com.example.phr.adapter;

import java.text.DecimalFormat;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.phr.R;
import com.example.phr.mobile.models.ActivityTrackerEntry;
import com.example.phr.tools.DateTimeParser;

public class ActivityAdapter extends BaseAdapter {

	private final Context mContext;
	private final List<ActivityTrackerEntry> mListOfActivity;
	private int positionSelected;

	private static class ViewHolder {
		TextView action;
		TextView date;
		TextView time;
		TextView duration;
		TextView calBurned;
	}

	public ActivityAdapter(Context aContext,
			List<ActivityTrackerEntry> aListOfJournals) {
		mListOfActivity = aListOfJournals;
		mContext = aContext;
	}

	@Override
	public int getCount() {
		return mListOfActivity.size();
	}

	@Override
	public Object getItem(int position) {
		return mListOfActivity.get(position);
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
			convertView = inflater.inflate(R.layout.item_activity, parent,
					false);

			viewHolder = new ViewHolder();
			viewHolder.action = (TextView) convertView
					.findViewById(R.id.txtActivityDone);
			viewHolder.date = (TextView) convertView
					.findViewById(R.id.txtActivityDate);
			viewHolder.time = (TextView) convertView
					.findViewById(R.id.txtActivityTime);
			viewHolder.duration = (TextView) convertView
					.findViewById(R.id.txtActivityDuration);
			viewHolder.calBurned = (TextView) convertView
					.findViewById(R.id.txtActivityCalBurned);

			convertView.setTag(viewHolder);
		}

		DecimalFormat df = new DecimalFormat("#.00");
		viewHolder = (ViewHolder) convertView.getTag();
		viewHolder.action.setText(mListOfActivity.get(position).getActivity()
				.getName().toString());
		viewHolder.date.setText(String.valueOf(DateTimeParser
				.getDate(mListOfActivity.get(position).getTimestamp())));
		viewHolder.time.setText(String.valueOf(DateTimeParser
				.getTime(mListOfActivity.get(position).getTimestamp())));
		double hr = mListOfActivity.get(position).getDurationInSeconds() / 3600.0;
		Log.e("duration", mListOfActivity.get(position).getDurationInSeconds()
				+ "");
		Log.e("adapter hr", hr + "");
		viewHolder.duration.setText(df.format(hr));
		viewHolder.calBurned
				.setText(df
						.format(mListOfActivity.get(position)
								.getCaloriesBurnedPerHour()
								* (mListOfActivity.get(position)
										.getDurationInSeconds() / 3600.0)));

		return convertView;
	}

}
