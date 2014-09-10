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

import com.example.phr.NoteReadModeActivity;
import com.example.phr.NoteTrackerActivity;
import com.example.phr.R;
import com.example.phr.StatusFeedActivity;
import com.example.phr.model.Note;

public class NoteAdapter extends BaseAdapter{

	private Context mContext;
	private List<Note> mListOfNote;

	private static class ViewHolder {
		TextView title;
		TextView date;
		TextView time;
	}
	
	
	public NoteAdapter(Context aContext, List<Note> aListOfNotes) {
		mListOfNote = aListOfNotes;
		mContext = aContext;
	}

	public int getCount() {
		return mListOfNote.size();
	}

	public Object getItem(int position) {
		return mListOfNote.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;

		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(mContext);
			convertView = inflater.inflate(R.layout.item_note, parent,
					false);

			viewHolder = new ViewHolder();
			viewHolder.title = (TextView) convertView
					.findViewById(R.id.txtNoteTitle);
			viewHolder.date = (TextView) convertView
					.findViewById(R.id.txtNoteDate);

			viewHolder.time = (TextView) convertView
					.findViewById(R.id.txtNoteTime);

			
			convertView.setTag(viewHolder);
		}

		viewHolder = (ViewHolder) convertView.getTag();
		viewHolder.title.setText(String.valueOf(mListOfNote.get(position).getTitle()));

		viewHolder.date.setText(String.valueOf(mListOfNote.get(
				position).getDate()));

		viewHolder.time.setText(String.valueOf(mListOfNote.get(
				position).getTime()));
		

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				// FOR STATUS ON CLICK
				Intent intent = new Intent(mContext,
						NoteReadModeActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);;
				v.getContext().startActivity(intent);
			}
		});
		return convertView;
	}
}
