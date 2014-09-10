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

import com.example.phr.R;
import com.example.phr.model.Retrieve_Food;

public class RetrieveFoodAdapter extends BaseAdapter{

	

	private Context mContext;
	private List<Retrieve_Food> mListOfRetrieve;

	private static class ViewHolder {
		TextView status;
		TextView guess;
		TextView date;
		TextView time;
		TextView what;
		TextView quantity;
	}
	
	
	public RetrieveFoodAdapter(Context aContext, List<Retrieve_Food> aListOfRetrieves) {
		mListOfRetrieve = aListOfRetrieves;
		mContext = aContext;
	}

	public int getCount() {
		return mListOfRetrieve.size();
	}

	public Object getItem(int position) {
		return mListOfRetrieve.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;

		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(mContext);
			convertView = inflater.inflate(R.layout.item_retrieve_food, parent,
					false);

			viewHolder = new ViewHolder();
			viewHolder.status = (TextView) convertView
					.findViewById(R.id.fbStatus);
			viewHolder.guess = (TextView) convertView
					.findViewById(R.id.guess);
			viewHolder.date = (TextView) convertView
					.findViewById(R.id.txtFBDate);

			viewHolder.time = (TextView) convertView
					.findViewById(R.id.txtFBTime);
			
		
			viewHolder.what = (TextView) convertView
					.findViewById(R.id.what);
			
			viewHolder.quantity = (TextView) convertView
					.findViewById(R.id.quantity);
			convertView.setTag(viewHolder);
		}

		viewHolder = (ViewHolder) convertView.getTag();
		viewHolder.status.setText(String.valueOf(mListOfRetrieve.get(position).getStatus()));
		viewHolder.guess.setText(String.valueOf(mListOfRetrieve.get(position).getGuess()));
		viewHolder.date.setText(String.valueOf(mListOfRetrieve.get(
				position).getDate()));

		viewHolder.time.setText(String.valueOf(mListOfRetrieve.get(
				position).getTime()));
		
		
		viewHolder.quantity.setText(String.valueOf(mListOfRetrieve.get(
				position).getQuantity()));
		viewHolder.what.setText(String.valueOf(mListOfRetrieve.get(
				position).getWhat()));
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				// FOR STATUS ON CLICK
			}
		});
		return convertView;
	}
}
