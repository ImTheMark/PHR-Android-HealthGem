package com.example.phr.adapter;

import java.util.List;

import com.example.phr.R;
import com.example.phr.model.StatusAction;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ActionAdapter extends BaseAdapter {

	private Context mContext;
	private List<StatusAction> mListOfStatusAction;
	private int positionSelected;

	private static class ViewHolder {
		TextView actionName;
		ImageView img;
	}

	public ActionAdapter(Context aContext, List<StatusAction> aListOfJournals) {
		mListOfStatusAction = aListOfJournals;
		mContext = aContext;
	}

	@Override
	public int getCount() {
		return mListOfStatusAction.size();
	}

	@Override
	public Object getItem(int position) {
		return mListOfStatusAction.get(position);
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
			convertView = inflater.inflate(R.layout.item_image_text, parent,
					false);

			viewHolder = new ViewHolder();
			viewHolder.actionName = (TextView) convertView.findViewById(R.id.txtStatusAction);
			viewHolder.img = (ImageView) convertView
					.findViewById(R.id.imageViewAction);

			convertView.setTag(viewHolder);
		}

		viewHolder = (ViewHolder) convertView.getTag();
		viewHolder.actionName.setText(mListOfStatusAction.get(position).getActionName()
				.toString());
		viewHolder.img.setImageDrawable(mListOfStatusAction.get(position)
				.getImgUrl());

		return convertView;
	}

}
