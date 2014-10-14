package com.example.phr.adapter;

import java.text.DecimalFormat;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.phr.R;
import com.example.phr.mobile.models.Weight;
import com.example.phr.tools.DateTimeParser;

public class WeightAdapter extends BaseAdapter {

	private final Context mContext;
	private final List<List<Weight>> mListOfGroupedWeightByDate;
	private int positionSelected;

	private static class ViewHolder {
		TextView month;
		TextView day;
		TextView average;
		LinearLayout trackerListForDay;
	}

	public WeightAdapter(Context aContext, List<List<Weight>> weightGroupedList) {
		mListOfGroupedWeightByDate = weightGroupedList;
		mContext = aContext;
	}

	@Override
	public int getCount() {
		return mListOfGroupedWeightByDate.size();
	}

	@Override
	public Object getItem(int position) {
		return mListOfGroupedWeightByDate.get(position);
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
			convertView = inflater.inflate(R.layout.item_status_grouped,
					parent, false);

			viewHolder = new ViewHolder();
			viewHolder.month = (TextView) convertView
					.findViewById(R.id.txtTrackerMonth);
			viewHolder.day = (TextView) convertView
					.findViewById(R.id.txtTrackerDay);
			viewHolder.average = (TextView) convertView
					.findViewById(R.id.txtTrackerAverage);
			viewHolder.trackerListForDay = (LinearLayout) convertView
					.findViewById(R.id.trackerListForDay);

			convertView.setTag(viewHolder);
		}

		String month = DateTimeParser.getMonth(mListOfGroupedWeightByDate
				.get(position).get(0).getTimestamp());
		String day = DateTimeParser.getDay(mListOfGroupedWeightByDate
				.get(position).get(0).getTimestamp());
		float average = 0;

		viewHolder = (ViewHolder) convertView.getTag();
		viewHolder.month.setText(month);
		viewHolder.day.setText(day);
		viewHolder.average.setText(String.valueOf(average));

		viewHolder.trackerListForDay.removeAllViews();

		for (Weight status : mListOfGroupedWeightByDate.get(position)) {
			RelativeLayout row = new RelativeLayout(mContext);
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.MATCH_PARENT,
					RelativeLayout.LayoutParams.WRAP_CONTENT);
			row.setLayoutParams(params);
			row.setPadding(0, 2, 0, 0);

			TextView txtTime = new TextView(mContext);
			txtTime.setText(DateTimeParser.getTime(status.getTimestamp()));
			RelativeLayout.LayoutParams t = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.WRAP_CONTENT,
					RelativeLayout.LayoutParams.WRAP_CONTENT);
			t.addRule(RelativeLayout.ALIGN_PARENT_TOP, row.getId());
			t.addRule(RelativeLayout.ALIGN_PARENT_LEFT, row.getId());
			txtTime.setLayoutParams(t);
			row.addView(txtTime);

			// kg for now

			TextView txtWeight = new TextView(mContext);
			DecimalFormat df = new DecimalFormat("#.00");
			String weightFormated = df.format(status.getWeightInKilograms());
			txtWeight.setText(weightFormated);
			RelativeLayout.LayoutParams w = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.WRAP_CONTENT,
					RelativeLayout.LayoutParams.WRAP_CONTENT);
			w.addRule(RelativeLayout.ALIGN_PARENT_TOP, row.getId());
			w.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, row.getId());
			txtWeight.setLayoutParams(w);
			row.addView(txtWeight);

			viewHolder.trackerListForDay.addView(row);

			average += status.getWeightInKilograms();
		}

		average /= mListOfGroupedWeightByDate.get(position).size();
		viewHolder.average.setText(String.valueOf(average));

		return convertView;
	}

}
