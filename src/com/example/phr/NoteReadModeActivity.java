package com.example.phr;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phr.application.HealthGem;
import com.example.phr.enums.TrackerInputType;
import com.example.phr.exceptions.EntryNotFoundException;
import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.mobile.models.Note;
import com.example.phr.serviceimpl.NoteTrackerServiceImpl;
import com.example.phr.tools.DateTimeParser;
import com.example.phr.tools.ImageHandler;

public class NoteReadModeActivity extends Activity {

	Note chosenItem;
	NoteTrackerServiceImpl noteServiceImpl;
	TextView date;
	TextView time;
	TextView note;
	ImageView image;
	LinearLayout noteImageLayout;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();

		StrictMode.setThreadPolicy(policy);
		setTitle("Notes");
		setContentView(R.layout.activity_note_read_mode);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#030203")));

		Intent in = getIntent();
		chosenItem = (Note) in.getExtras().getSerializable("object");
		noteImageLayout = (LinearLayout) findViewById(R.id.noteImageLayout);

		date = (TextView) findViewById(R.id.textViewNoteReadDate);
		time = (TextView) findViewById(R.id.textViewNoteReadTime);
		note = (TextView) findViewById(R.id.txtFullNotes);
		image = (ImageView) findViewById(R.id.imageViewNoteReadHolder);
		System.out.println("PASOK");
		note.setText(chosenItem.getNote());
		date.setText(String.valueOf(DateTimeParser.getDate(chosenItem
				.getTimestamp())));
		time.setText(String.valueOf(DateTimeParser.getTime(chosenItem
				.getTimestamp())));

		if (chosenItem.getImage() != null) {

			Log.e("img", chosenItem.getImage().getFileName());
			image.setImageBitmap(ImageHandler.loadImage(chosenItem.getImage()
					.getFileName()));
		} else
			noteImageLayout.setVisibility(View.GONE);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			break;
		case R.id.menu_edit:
			Intent i = new Intent(getApplicationContext(),
					NewStatusActivity.class);
			i.putExtra("edit", TrackerInputType.NOTES);
			i.putExtra("object", chosenItem);
			startActivity(i);
			return true;

		case R.id.menu_delete:
			try {
				Log.e("checkup", "del");
				noteServiceImpl = new NoteTrackerServiceImpl();
				noteServiceImpl.delete(chosenItem);
				Log.e("note", "del_done");
				Intent in = new Intent(getApplicationContext(),
						NoteTrackerActivity.class);
				startActivity(in);
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				Toast.makeText(HealthGem.getContext(),
						"No Internet Connection !", Toast.LENGTH_LONG).show();
				e.printStackTrace();
				e.printStackTrace();
			} catch (OutdatedAccessTokenException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (EntryNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_edit_delete, menu);
		return super.onCreateOptionsMenu(menu);
	}
}
