package com.example.phr.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.phr.BloodPressureTrackerActivity;
import com.example.phr.BloodSugarTrackerActivity;
import com.example.phr.R;
import com.example.phr.model.BloodPressure;
import com.example.phr.model.BloodSugar;

public class BloodSugarAdapter extends BaseAdapter{

	
	private Context mContext;
	private List<BloodSugar> mListOfBloodSugar;

	private static class ViewHolder {
		TextView glucoseLevel;
		TextView type;
		TextView date;
		TextView time;
		ImageView image;
	}
	
	
	public BloodSugarAdapter(Context aContext, List<BloodSugar> aListOfBloodSugar) {
		mListOfBloodSugar = aListOfBloodSugar;
		mContext = aContext;
	}

	public int getCount() {
		return mListOfBloodSugar.size();
	}

	public Object getItem(int position) {
		return mListOfBloodSugar.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;

		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(mContext);
			convertView = inflater.inflate(R.layout.item_bloodsugar, parent,
					false);

			viewHolder = new ViewHolder();
			viewHolder.glucoseLevel = (TextView) convertView
					.findViewById(R.id.txtGlucose);
			viewHolder.type = (TextView) convertView
					.findViewById(R.id.txtType);
			viewHolder.date = (TextView) convertView
					.findViewById(R.id.txtglucosedate);

			viewHolder.time = (TextView) convertView
					.findViewById(R.id.txtglucosetime);
			
			viewHolder.image = (ImageView) convertView
					.findViewById(R.id.glucoseimage);
			
			convertView.setTag(viewHolder);
		}

		viewHolder = (ViewHolder) convertView.getTag();
		viewHolder.glucoseLevel.setText(String.valueOf(mListOfBloodSugar.get(position).getGlucoseLevel()));
		viewHolder.type.setText(String.valueOf(mListOfBloodSugar.get(position).getType()));
		viewHolder.date.setText(String.valueOf(mListOfBloodSugar.get(
				position).getDate()));

		viewHolder.time.setText(String.valueOf(mListOfBloodSugar.get(
				position).getTime()));
		
		viewHolder.image.setImageDrawable(mListOfBloodSugar.get(position)
				.getImage());
		
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				// FOR STATUS ON CLICK
			}
		});
		return convertView;
	}
}
