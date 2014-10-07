package com.example.phr.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.phr.R;
import com.example.phr.mobile.models.Note;
import com.example.phr.tools.DateTimeParser;

public class NoteAdapter extends BaseAdapter {

	private final Context mContext;
	private final List<Note> mListOfNote;

	private static class ViewHolder {
		TextView title;
		TextView date;
		TextView time;
	}

	public NoteAdapter(Context aContext, List<Note> aListOfNotes) {
		mListOfNote = aListOfNotes;
		mContext = aContext;
	}

	@Override
	public int getCount() {
		return mListOfNote.size();
	}

	@Override
	public Object getItem(int position) {
		return mListOfNote.get(position);
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
			convertView = inflater.inflate(R.layout.item_note, parent, false);

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
		String title;
		if (String.valueOf(mListOfNote.get(position).getNote()).length() > 16)
			title = String.valueOf(mListOfNote.get(position).getNote())
					.substring(0, 15) + "...";
		else
			title = String.valueOf(mListOfNote.get(position).getNote());

		viewHolder.title.setText(title);

		viewHolder.date.setText(String.valueOf(DateTimeParser
				.getDate(mListOfNote.get(position).getTimestamp())));

		viewHolder.time.setText(String.valueOf(DateTimeParser
				.getTime(mListOfNote.get(position).getTimestamp())));

		return convertView;
	}
}
