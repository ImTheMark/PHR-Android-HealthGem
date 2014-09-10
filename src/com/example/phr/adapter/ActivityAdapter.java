package com.example.phr.adapter;

import java.util.List;

import com.example.phr.R;
import com.example.phr.model.Activity;
import com.example.phr.model.StatusAction;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityAdapter extends BaseAdapter {

	private Context mContext;
	private List<Activity> mListOfActivity;
	private int positionSelected;

	private static class ViewHolder {
		TextView action;
		TextView date;
		TextView time;
		TextView duration;
		TextView calBurned;
	}

	public ActivityAdapter(Context aContext, List<Activity> aListOfJournals) {
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
			viewHolder.action = (TextView) convertView.findViewById(R.id.txtActivityDone);
			viewHolder.date = (TextView) convertView.findViewById(R.id.txtActivityDate);
			viewHolder.time = (TextView) convertView.findViewById(R.id.txtActivityTime);
			viewHolder.duration = (TextView) convertView.findViewById(R.id.txtActivityDuration);
			viewHolder.calBurned = (TextView) convertView.findViewById(R.id.txtActivityCalBurned);

			convertView.setTag(viewHolder);
		}

		viewHolder = (ViewHolder) convertView.getTag();
		viewHolder.action.setText(mListOfActivity.get(position).getAction()
				.toString());
		viewHolder.date.setText(mListOfActivity.get(position).getDate()
				.toString());
		viewHolder.time.setText(mListOfActivity.get(position).getTime()
				.toString());
		viewHolder.duration.setText(mListOfActivity.get(position).getDuration()
				.toString());
		viewHolder.calBurned.setText(mListOfActivity.get(position).getCalBurned()
				.toString());
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});
		return convertView;
	}

}
