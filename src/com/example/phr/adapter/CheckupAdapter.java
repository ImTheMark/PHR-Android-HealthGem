package com.example.phr.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.phr.R;
import com.example.phr.mobile.models.CheckUp;
import com.example.phr.tools.DateTimeParser;

public class CheckupAdapter extends BaseAdapter {

	private final Context mContext;
	private final List<CheckUp> mListOfCheckup;

	private static class ViewHolder {
		TextView ailment;
		TextView doctor;
		TextView day;
		TextView month;
		TextView date;
		TextView time;
	}

	public CheckupAdapter(Context aContext, List<CheckUp> aListOfCheckups) {
		mListOfCheckup = aListOfCheckups;
		mContext = aContext;
	}

	@Override
	public int getCount() {
		return mListOfCheckup.size();
	}

	@Override
	public Object getItem(int position) {
		return mListOfCheckup.get(position);
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
					.inflate(R.layout.item_checkup, parent, false);

			viewHolder = new ViewHolder();
			viewHolder.ailment = (TextView) convertView
					.findViewById(R.id.txtPurpose);
			viewHolder.doctor = (TextView) convertView
					.findViewById(R.id.txtDoctor);
			viewHolder.date = (TextView) convertView
					.findViewById(R.id.txtCheckupdate);

			viewHolder.time = (TextView) convertView
					.findViewById(R.id.txtCheckuptime);
			// viewHolder.day = (TextView)
			// convertView.findViewById(R.id.txtDay);

			// viewHolder.month = (TextView) convertView
			// .findViewById(R.id.txtMonth);

			convertView.setTag(viewHolder);
		}

		viewHolder = (ViewHolder) convertView.getTag();
		viewHolder.ailment.setText(mListOfCheckup.get(position).getPurpose()
				.toString());
		viewHolder.doctor.setText(mListOfCheckup.get(position).getDoctorsName()
				.toString());

		// viewHolder.day.setText(String.valueOf(DateTimeParser
		// .getDate(mListOfCheckup.get(position).getTimestamp())));
		//
		// viewHolder.month.setText(String.valueOf(DateTimeParser
		// .getDate(mListOfCheckup.get(position).getTimestamp())));

		viewHolder.date.setText(String.valueOf(DateTimeParser
				.getDate(mListOfCheckup.get(position).getTimestamp())));

		viewHolder.time.setText(String.valueOf(DateTimeParser
				.getTime(mListOfCheckup.get(position).getTimestamp())));
		return convertView;
	}

}
