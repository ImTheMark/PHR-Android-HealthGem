package com.example.phr.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.phr.R;
import com.example.phr.model.FoodSingle;

public class SingleFoodAdapter extends BaseAdapter {

	private Context mContext;
	private List<FoodSingle> mListOfFoodSingle;

	private static class ViewHolder {
		TextView fat;
		TextView carbs;
		TextView protein;
		TextView cal;
		TextView time;
		TextView food;
		TextView serving;
	}

	public SingleFoodAdapter(Context aContext, List<FoodSingle> aListOfFoodSingles) {
		mListOfFoodSingle = aListOfFoodSingles;
		mContext = aContext;
	}

	@Override
	public int getCount() {
		return mListOfFoodSingle.size();
	}

	@Override
	public Object getItem(int position) {
		return mListOfFoodSingle.get(position);
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
			convertView = inflater.inflate(R.layout.item_food_single, parent,
					false);

			viewHolder = new ViewHolder();
			viewHolder.fat = (TextView) convertView
					.findViewById(R.id.foodCal);
			viewHolder.carbs = (TextView) convertView
					.findViewById(R.id.foodCarbs);
			viewHolder.protein = (TextView) convertView
					.findViewById(R.id.foodProtein);

			viewHolder.cal = (TextView) convertView
					.findViewById(R.id.foodCal);
			
			viewHolder.food = (TextView) convertView
					.findViewById(R.id.food);
			
			viewHolder.time = (TextView) convertView
					.findViewById(R.id.time);
			
			viewHolder.serving = (TextView) convertView
					.findViewById(R.id.serving);
			convertView.setTag(viewHolder);
		}

		viewHolder = (ViewHolder) convertView.getTag();
		viewHolder.fat.setText(String.valueOf(mListOfFoodSingle.get(position).getFat()));
		viewHolder.carbs.setText(String.valueOf(mListOfFoodSingle.get(position).getCarbs()));
		viewHolder.protein.setText(String.valueOf(mListOfFoodSingle.get(
				position).getProtein()));

		viewHolder.cal.setText(String.valueOf(mListOfFoodSingle.get(
				position).getCal()));
		
		viewHolder.food.setText(mListOfFoodSingle.get(
				position).getFood());
		
		viewHolder.time.setText(mListOfFoodSingle.get(
				position).getTime());
		
		viewHolder.serving.setText(String.valueOf(mListOfFoodSingle.get(
				position).getServing()));
		
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				// FOR STATUS ON CLICK
			}
		});
		return convertView;
	}

}
