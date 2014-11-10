package com.example.phr;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.phr.adapter.NoteAdapter;
import com.example.phr.application.HealthGem;
import com.example.phr.enums.TrackerInputType;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.mobile.models.Note;
import com.example.phr.serviceimpl.NoteTrackerServiceImpl;

public class NoteTrackerActivity extends Activity {

	ListView mNoteList;
	NoteAdapter noteAdapter;
	ImageView mBtnNotePost;
	List<Note> list;
	NoteTrackerServiceImpl noteServiceImpl;
	Note chosenItem;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();

		StrictMode.setThreadPolicy(policy);
		setContentView(R.layout.activity_note_tracker);
		setTitle("Notes");
		getActionBar().setDisplayHomeAsUpEnabled(true);
		mNoteList = (ListView) findViewById(R.id.listView_notes);

		list = new ArrayList<Note>();
		noteServiceImpl = new NoteTrackerServiceImpl();
		try {

			list = noteServiceImpl.getAll();

		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			Toast.makeText(HealthGem.getContext(), "No Internet Connection !",
					Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
		Log.e(String.valueOf(list.size()), "size");
		noteAdapter = new NoteAdapter(getApplicationContext(), list);
		mNoteList.setAdapter(noteAdapter);
		mNoteList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Log.e("note", "CLICKED!");
				Intent i = new Intent(getApplicationContext(),
						NoteReadModeActivity.class);
				chosenItem = (Note) arg0.getAdapter().getItem(arg2);
				i.putExtra("object", chosenItem);
				startActivity(i);
			}
		});

		mBtnNotePost = (ImageView) findViewById(R.id.noteBanner);
		mBtnNotePost.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),
						NewStatusActivity.class);
				i.putExtra("tracker", TrackerInputType.NOTES);
				startActivity(i);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_tracker_help, menu);
		return super.onCreateOptionsMenu(menu);
	}

	private void displayhelp() {

		Dialog dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.item_help);
		ImageView image = (ImageView) dialog.findViewById(R.id.help_imageview);
		image.setBackgroundResource(R.drawable.notetracker_help);
		dialog.getWindow().setBackgroundDrawable(null);
		dialog.show();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			break;
		case R.id.action_help:
			displayhelp();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(getApplicationContext(), MainActivity.class);
		intent.putExtra("backToMenu", 2);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}

}
