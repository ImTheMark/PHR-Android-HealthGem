package com.example.phr.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.phr.R;
import com.example.phr.mobile.models.GroupedFood;
import com.example.phr.tools.DateTimeParser;

public class GroupedFoodAdapter extends BaseAdapter {

	private final Context mContext;
	private final List<GroupedFood> mListOfGroupedFood;

	private static class ViewHolder {
		TextView calorie;
		TextView fat;
		TextView protein;
		TextView carbs;
		TextView month;
		TextView day;
	}

	public GroupedFoodAdapter(Context aContext,
			List<GroupedFood> aListOfGroupedFoods) {
		mListOfGroupedFood = aListOfGroupedFoods;
		mContext = aContext;
	}

	@Override
	public int getCount() {
		return mListOfGroupedFood.size();
	}

	@Override
	public Object getItem(int position) {
		return mListOfGroupedFood.get(position);
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
			convertView = inflater.inflate(R.layout.item_groupedfood, parent,
					false);

			viewHolder = new ViewHolder();
			viewHolder.calorie = (TextView) convertView
					.findViewById(R.id.foodCal);
			viewHolder.fat = (TextView) convertView.findViewById(R.id.foodFat);
			viewHolder.protein = (TextView) convertView
					.findViewById(R.id.foodProtein);

			viewHolder.carbs = (TextView) convertView
					.findViewById(R.id.foodCarbs);

			viewHolder.month = (TextView) convertView
					.findViewById(R.id.txtMonth);

			viewHolder.day = (TextView) convertView.findViewById(R.id.txtDay);

			convertView.setTag(viewHolder);
		}

		viewHolder = (ViewHolder) convertView.getTag();
		viewHolder.calorie.setText(String.valueOf(mListOfGroupedFood.get(
				position).getCalorie()));
		viewHolder.fat.setText(String.valueOf(mListOfGroupedFood.get(position)
				.getFat()));
		viewHolder.protein.setText(String.valueOf(mListOfGroupedFood.get(
				position).getProtein()));

		viewHolder.carbs.setText(String.valueOf(mListOfGroupedFood
				.get(position).getCarbohydrates()));

		viewHolder.month.setText(String.valueOf(DateTimeParser
				.getMonth(mListOfGroupedFood.get(position).getDate())));

		viewHolder.day.setText(String.valueOf(DateTimeParser
				.getDay(mListOfGroupedFood.get(position).getDate())));

		return convertView;
	}

}
