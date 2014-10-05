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
import com.example.phr.model.Status;

public class StatusAdapter extends BaseAdapter {

	private final Context mContext;
	private final List<Status> mListOfStatus;
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

	public StatusAdapter(Context aContext, List<Status> aListOfStatus) {
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
		viewHolder.actionHolder.setText(mListOfStatus.get(position)
				.getActionHolder().toString());
		viewHolder.actionName.setText(mListOfStatus.get(position)
				.getActionName().toString());
		viewHolder.datettime.setText(mListOfStatus.get(position).getDatettime()
				.toString());
		viewHolder.status.setText(mListOfStatus.get(position).getStatus()
				.toString());
		viewHolder.imgPostVia.setImageDrawable(mListOfStatus.get(position)
				.getPostViaImgUrl());
		viewHolder.imgAction.setImageDrawable(mListOfStatus.get(position)
				.getActionImgUrl());
		if (mListOfStatus.get(position).getStatusImgUrl() != null) {
			viewHolder.statusImg.setImageDrawable(mListOfStatus.get(position)
					.getStatusImgUrl());
			viewHolder.statusImg.setVisibility(View.VISIBLE);

		} else {
			viewHolder.statusImg.setVisibility(View.GONE);
			// Log.e("HAHAHA",
			// mListOfStatus.get(position).getStatusImgUrl().toString());

		}

		return convertView;
	}

}
