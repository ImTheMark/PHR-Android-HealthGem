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
import com.example.phr.mobile.models.BloodSugar;
import com.example.phr.tools.DateTimeParser;

public class BloodSugarAdapter extends BaseAdapter {

	private final Context mContext;
	private final List<BloodSugar> mListOfBloodSugar;

	private static class ViewHolder {
		TextView glucoseLevel;
		TextView type;
		TextView date;
		TextView time;
		ImageView image;
	}

	public BloodSugarAdapter(Context aContext,
			List<BloodSugar> aListOfBloodSugar) {
		mListOfBloodSugar = aListOfBloodSugar;
		mContext = aContext;
	}

	@Override
	public int getCount() {
		return mListOfBloodSugar.size();
	}

	@Override
	public Object getItem(int position) {
		return mListOfBloodSugar.get(position);
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
			convertView = inflater.inflate(R.layout.item_bloodsugar, parent,
					false);

			viewHolder = new ViewHolder();
			viewHolder.glucoseLevel = (TextView) convertView
					.findViewById(R.id.txtGlucose);
			viewHolder.type = (TextView) convertView.findViewById(R.id.txtType);
			viewHolder.date = (TextView) convertView
					.findViewById(R.id.txtglucosedate);

			viewHolder.time = (TextView) convertView
					.findViewById(R.id.txtglucosetime);

			viewHolder.image = (ImageView) convertView
					.findViewById(R.id.imageBs);

			convertView.setTag(viewHolder);
		}

		viewHolder = (ViewHolder) convertView.getTag();
		viewHolder.glucoseLevel.setText(String.valueOf(mListOfBloodSugar.get(
				position).getBloodSugar()));
		viewHolder.type.setText(String.valueOf(mListOfBloodSugar.get(position)
				.getType()));
		viewHolder.date.setText(String.valueOf(DateTimeParser
				.getDate(mListOfBloodSugar.get(position).getTimestamp())));

		viewHolder.time.setText(String.valueOf(DateTimeParser
				.getTime(mListOfBloodSugar.get(position).getTimestamp())));

		if (mListOfBloodSugar.get(position).getType().equals("Before meal")
				&& mListOfBloodSugar.get(position).getBloodSugar() >= 4.0
				&& mListOfBloodSugar.get(position).getBloodSugar() <= 5.9)
			viewHolder.image.setImageResource(R.drawable.bloodsugar_normal);
		else if (mListOfBloodSugar.get(position).getType().equals("After meal")
				&& mListOfBloodSugar.get(position).getBloodSugar() < 7.8)
			viewHolder.image.setImageResource(R.drawable.bloodsugar_normal);
		else
			viewHolder.image.setImageResource(R.drawable.bloodsugar_warning);

		return convertView;
	}
}
