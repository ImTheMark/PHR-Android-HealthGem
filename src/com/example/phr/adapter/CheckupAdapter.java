package com.example.phr.adapter;

import java.util.List;

import com.example.phr.CheckupTrackerActivity;
import com.example.phr.CheckupTrackerReadModeActivity;
import com.example.phr.R;
import com.example.phr.model.Checkup;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CheckupAdapter extends BaseAdapter {

	private Context mContext;
	private List<Checkup> mListOfCheckup;

	private static class ViewHolder {
		TextView ailment;
		TextView doctor;
		TextView clinic;
		TextView day;
		TextView month;
	}

	public CheckupAdapter(Context aContext, List<Checkup> aListOfCheckups) {
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
			convertView = inflater.inflate(R.layout.item_checkup, parent,
					false);

			viewHolder = new ViewHolder();
			viewHolder.ailment = (TextView) convertView
					.findViewById(R.id.txtPurpose);
			viewHolder.doctor = (TextView) convertView
					.findViewById(R.id.txtDoctor);
			viewHolder.clinic = (TextView) convertView
					.findViewById(R.id.txtPlace);

			viewHolder.day = (TextView) convertView
					.findViewById(R.id.txtDay);
			
			viewHolder.month = (TextView) convertView
					.findViewById(R.id.txtMonth);
			
			convertView.setTag(viewHolder);
		}

		viewHolder = (ViewHolder) convertView.getTag();
		viewHolder.ailment.setText(mListOfCheckup.get(position).getAilment()
				.toString());
		viewHolder.doctor.setText(mListOfCheckup.get(position).getDoctor()
				.toString());
		viewHolder.clinic.setText(String.valueOf(mListOfCheckup.get(
				position).getClinic()));

		viewHolder.day.setText(String.valueOf(mListOfCheckup.get(
				position).getDay()));
		
		viewHolder.month.setText(String.valueOf(mListOfCheckup.get(
				position).getMonth()));
		
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				// FOR STATUS ON CLICK
				Intent intent = new Intent(mContext,
						CheckupTrackerReadModeActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);;
				v.getContext().startActivity(intent);
			}
		});
		return convertView;
	}

}
