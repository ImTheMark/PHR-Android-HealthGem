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

import com.example.phr.BloodSugarTrackerActivity;
import com.example.phr.CalorieBreakdownTrackerActivity;
import com.example.phr.R;
import com.example.phr.model.FoodActivityCalorie;

public class FoodActivityCalorieAdapter extends BaseAdapter{

	
	private Context mContext;
	private List<FoodActivityCalorie> mListOfCalorie;

	private static class ViewHolder {
		TextView calorie;
		TextView action;
		TextView unit;
		TextView time;
		ImageView image;
	}
	
	
	public FoodActivityCalorieAdapter(Context aContext, List<FoodActivityCalorie> aListOfBloodSugar) {
		mListOfCalorie = aListOfBloodSugar;
		mContext = aContext;
	}

	public int getCount() {
		return mListOfCalorie.size();
	}

	public Object getItem(int position) {
		return mListOfCalorie.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;

		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(mContext);
			convertView = inflater.inflate(R.layout.item_calorie_breakdown, parent,
					false);

			viewHolder = new ViewHolder();
			viewHolder.calorie = (TextView) convertView
					.findViewById(R.id.cal);
			viewHolder.action = (TextView) convertView
					.findViewById(R.id.actionName);
			viewHolder.unit = (TextView) convertView
					.findViewById(R.id.unit);

			viewHolder.time = (TextView) convertView
					.findViewById(R.id.txttime);
			
			viewHolder.image = (ImageView) convertView
					.findViewById(R.id.actionImage);
			
			convertView.setTag(viewHolder);
		}

		viewHolder = (ViewHolder) convertView.getTag();
		viewHolder.calorie.setText(mListOfCalorie.get(position).getCalorie());
		viewHolder.action.setText(mListOfCalorie.get(position).getAction());
		viewHolder.unit.setText(mListOfCalorie.get(
				position).getUnit());

		viewHolder.time.setText(mListOfCalorie.get(
				position).getTime());
		
		viewHolder.image.setImageDrawable(mListOfCalorie.get(position)
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
