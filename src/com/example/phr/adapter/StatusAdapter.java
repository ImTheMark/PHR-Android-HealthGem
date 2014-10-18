package com.example.phr.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.phr.ActivitiesTrackerActivity;
import com.example.phr.R;
import com.example.phr.mobile.models.ActivityTrackerEntry;
import com.example.phr.mobile.models.BloodPressure;
import com.example.phr.mobile.models.BloodSugar;
import com.example.phr.mobile.models.CheckUp;
import com.example.phr.mobile.models.FoodTrackerEntry;
import com.example.phr.mobile.models.Note;
import com.example.phr.mobile.models.TrackerEntry;
import com.example.phr.mobile.models.Weight;
import com.example.phr.tools.DateTimeParser;
import com.example.phr.tools.ImageHandler;

public class StatusAdapter extends BaseAdapter {

	private final Context mContext;
	private final List<TrackerEntry> mListOfStatus;
	private int positionSelected;

	private static class ViewHolder {
		TextView actionHolder;
		TextView actionName;
		TextView datettime;
		TextView status;
		ImageView imgAction;
		ImageView imgPostVia;
		ImageView statusImg;
	}

	public StatusAdapter(Context aContext, List<TrackerEntry> aListOfStatus) {
		mListOfStatus = aListOfStatus;
		mContext = aContext;
	}

	@Override
	public int getCount() {
		return mListOfStatus.size();
	}

	@Override
	public Object getItem(int position) {
		return mListOfStatus.get(position);
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
			convertView = inflater.inflate(R.layout.item_status, parent, false);

			viewHolder = new ViewHolder();
			viewHolder.actionHolder = (TextView) convertView
					.findViewById(R.id.textViewStatusActionHolder);
			viewHolder.actionName = (TextView) convertView
					.findViewById(R.id.textViewStatusAction);
			viewHolder.datettime = (TextView) convertView
					.findViewById(R.id.textViewDateTime);
			viewHolder.status = (TextView) convertView
					.findViewById(R.id.textViewStatus);
			viewHolder.imgPostVia = (ImageView) convertView
					.findViewById(R.id.imageViewViaPosted);
			viewHolder.imgAction = (ImageView) convertView
					.findViewById(R.id.imageViewStatusAction);
			viewHolder.statusImg = (ImageView) convertView
					.findViewById(R.id.statusPhotoHolder);

			convertView.setTag(viewHolder);
		}

		viewHolder = (ViewHolder) convertView.getTag();
		
		viewHolder.datettime.setText(DateTimeParser.getDateTime(mListOfStatus.get(position).getTimestamp()));
		
		viewHolder.status.setText(mListOfStatus.get(position).getStatus());
		
		viewHolder.imgPostVia.setImageResource(R.drawable.activitystatusfeed_heart_gem_supersmall);
		
/*		if(mListOfStatus.get(position).getFbPost().getId() != null)
			viewHolder.imgPostVia.setImageDrawable();
		else
			viewHolder.imgPostVia.setImageDrawable(mListOfStatus.get(position)
					.getPostViaImgUrl());
		
		*/
		if(mListOfStatus.get(position).getClass().equals(ActivityTrackerEntry.class)){
			viewHolder.actionHolder.setText("Doing ");
			viewHolder.actionName.setText(((ActivityTrackerEntry) mListOfStatus.get(position)).getActivity().getName());
			viewHolder.imgAction.setImageResource(R.drawable.icon_activity);
		}
		else if(mListOfStatus.get(position).getClass().equals(BloodPressure.class)){
			viewHolder.actionHolder.setText("Blood Pressure at ");
			viewHolder.actionName.setText(((BloodPressure)mListOfStatus.get(position)).getSystolic() + "/" + ((BloodPressure)mListOfStatus.get(position)).getDiastolic());
			viewHolder.imgAction.setImageResource(R.drawable.icon_blood_pressure);
		}
		else if(mListOfStatus.get(position).getClass().equals(BloodSugar.class)){
			viewHolder.actionHolder.setText("Blood Sugar at ");
			viewHolder.actionName.setText(((BloodSugar)mListOfStatus.get(position)).getBloodSugar()+"");
			viewHolder.imgAction.setImageResource(R.drawable.icon_blood_sugar);
		}
		else if(mListOfStatus.get(position).getClass().equals(CheckUp.class)){
			viewHolder.actionHolder.setText("Checkup for  ");
			viewHolder.actionName.setText(((CheckUp)mListOfStatus.get(position)).getPurpose());
			viewHolder.imgAction.setImageResource(R.drawable.icon_checkup);
		}
		else if(mListOfStatus.get(position).getClass().equals(FoodTrackerEntry.class)){
			viewHolder.actionHolder.setText("Eating ");
			viewHolder.actionName.setText(((FoodTrackerEntry)mListOfStatus.get(position)).getFood().getName());
			viewHolder.imgAction.setImageResource(R.drawable.icon_food);
		}
		else if(mListOfStatus.get(position).getClass().equals(Note.class)){
			viewHolder.actionHolder.setText("Note ");
			viewHolder.actionName.setText(((Note)mListOfStatus.get(position)).getStatus());
			viewHolder.imgAction.setImageResource(R.drawable.icon_note);
		}
		else if(mListOfStatus.get(position).getClass().equals(Weight.class)){
			viewHolder.actionHolder.setText("Weight at ");
			viewHolder.actionName.setText(((Weight)mListOfStatus.get(position)).getWeightInKilograms()+"");
			viewHolder.imgAction.setImageResource(R.drawable.icon_weight);
		}
		
		
		
		if (mListOfStatus.get(position).getImage().getFileName() != null) {
			
			viewHolder.statusImg.setImageDrawable(
					new BitmapDrawable(
							mContext.getResources(), 
							ImageHandler.loadImage(mListOfStatus.get(position).getImage().getFileName())
							)
			);
			viewHolder.statusImg.setVisibility(View.VISIBLE);

		} else {
			viewHolder.statusImg.setVisibility(View.GONE);
		}

		return convertView;
	}

}
