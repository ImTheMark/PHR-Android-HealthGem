package com.example.phr.adapter;

import java.util.List;

import com.example.phr.R;
import com.example.phr.model.GroupedStatus;
import com.example.phr.model.Status;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GroupedStatusAdapter extends BaseAdapter {

	private Context mContext;
	private List<GroupedStatus> mListOfGroupedStatus;
	private int positionSelected;

	private static class ViewHolder {
		TextView month;
		TextView day;
		TextView average;
		LinearLayout trackerListForDay;
	}

	public GroupedStatusAdapter(Context aContext, List<GroupedStatus> aListOfStatus) {
		mListOfGroupedStatus = aListOfStatus;
		mContext = aContext;
	}

	@Override
	public int getCount() {
		return mListOfGroupedStatus.size();
	}

	@Override
	public Object getItem(int position) {
		return mListOfGroupedStatus.get(position);
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
			convertView = inflater.inflate(R.layout.item_status_grouped, parent,
					false);

			viewHolder = new ViewHolder();
			viewHolder.month = (TextView) convertView.findViewById(R.id.txtTrackerMonth);
			viewHolder.day = (TextView) convertView.findViewById(R.id.txtTrackerDay);
			viewHolder.average = (TextView) convertView.findViewById(R.id.txtTrackerAverage);
			viewHolder.trackerListForDay = (LinearLayout) convertView.findViewById(R.id.trackerListForDay);
			
			convertView.setTag(viewHolder);
		}

		viewHolder = (ViewHolder) convertView.getTag();
		viewHolder.month.setText(mListOfGroupedStatus.get(position).getMonth()
				.toString());
		viewHolder.day.setText(mListOfGroupedStatus.get(position).getDay()
				.toString());
		viewHolder.average.setText(mListOfGroupedStatus.get(position).getAverage()
				.toString());

		viewHolder.trackerListForDay.removeAllViews();
		
		for(Status s : mListOfGroupedStatus.get(position).getWeightStatusList()){
			RelativeLayout row = new RelativeLayout(mContext);
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
			row.setLayoutParams(params);
			row.setPadding(0, 2, 0, 0);
			
			TextView txtTime = new TextView(mContext);
			txtTime.setText(s.getDatettime());
			RelativeLayout.LayoutParams t = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
			t.addRule(RelativeLayout.ALIGN_PARENT_TOP, row.getId());
			t.addRule(RelativeLayout.ALIGN_PARENT_LEFT, row.getId());
			txtTime.setLayoutParams(t);
			row.addView(txtTime);
			
			TextView txtWeight = new TextView(mContext);
			txtWeight.setText(s.getActionName());
			RelativeLayout.LayoutParams w = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
			w.addRule(RelativeLayout.ALIGN_PARENT_TOP, row.getId());
			w.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, row.getId());
			txtWeight.setLayoutParams(w);
			row.addView(txtWeight);
			
			viewHolder.trackerListForDay.addView(row);
		}
		

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});
		return convertView;
	}

}
