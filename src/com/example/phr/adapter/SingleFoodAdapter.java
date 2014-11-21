package com.example.phr.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.phr.R;
import com.example.phr.mobile.models.Food;

public class SingleFoodAdapter extends BaseAdapter {

	private final Context mContext;
	private final List<Food> mListOfFoodSingle;

	private static class ViewHolder {
		TextView fat;
		TextView carbs;
		TextView protein;
		TextView cal;
		TextView time;
		TextView food;
		TextView servingNumber;
		// TextView servingUnit;
	}

	public SingleFoodAdapter(Context aContext, List<Food> aListOfFoodSingles) {
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
			viewHolder.fat = (TextView) convertView.findViewById(R.id.foodFat);
			viewHolder.carbs = (TextView) convertView
					.findViewById(R.id.foodCarbs);
			viewHolder.protein = (TextView) convertView
					.findViewById(R.id.foodProtein);

			viewHolder.cal = (TextView) convertView.findViewById(R.id.foodCal);

			viewHolder.food = (TextView) convertView.findViewById(R.id.food);

			viewHolder.time = (TextView) convertView.findViewById(R.id.time);

			viewHolder.servingNumber = (TextView) convertView
					.findViewById(R.id.serving);

			/*
			 * viewHolder.servingUnit = (TextView) convertView
			 * .findViewById(R.id.servingUnit);
			 */
			convertView.setTag(viewHolder);
		}

		viewHolder = (ViewHolder) convertView.getTag();
		viewHolder.fat.setText(String.valueOf(mListOfFoodSingle.get(position)
				.getFat()));
		viewHolder.carbs.setText(String.valueOf(mListOfFoodSingle.get(position)
				.getCarbohydrate()));
		viewHolder.protein.setText(String.valueOf(mListOfFoodSingle.get(
				position).getProtein()));

		viewHolder.cal.setText(String.valueOf(mListOfFoodSingle.get(position)
				.getCalorie()));

		viewHolder.food.setText(mListOfFoodSingle.get(position).getName());
		if ((mListOfFoodSingle.get(position).getFromFatsecret()))
			viewHolder.time.setText("via Fatsecret");
		else
			viewHolder.time.setText("");

		if (mListOfFoodSingle.get(position).getServing() == null
				|| mListOfFoodSingle.get(position).getServing().equals(""))
			viewHolder.servingNumber.setText("1 serving");
		else
			viewHolder.servingNumber.setText(String.valueOf(mListOfFoodSingle
					.get(position).getServing()));

		return convertView;
	}
}
