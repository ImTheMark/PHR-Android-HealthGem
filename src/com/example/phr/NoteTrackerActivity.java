package com.example.phr;

import java.util.ArrayList;
import java.util.List;

import com.example.phr.adapter.NoteAdapter;
import com.example.phr.model.Note;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class NoteTrackerActivity extends Activity{
	

	ListView mNoteList;
	NoteAdapter noteAdapter;
	ImageView mBtnNotePost;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note_tracker);
		setTitle("Notes");
        getActionBar().setDisplayHomeAsUpEnabled(true);
		mNoteList = (ListView) findViewById(R.id.listView_notes);
				
		// FAKE DATA
		List<Note> list = new ArrayList<Note>();
		Note data1 = new Note("Foods to avoid", "","Jul 10, 2014","11:40 am");
		
		Note data2 = new Note("Medicine List", "","Jul 08, 2014","10:40 am");

		Note data3 = new Note("Workout plan", "","Jun 06, 2014","11:40 pm");
		
		Note data4 = new Note("Dengue prevention", "","Jun 05, 2014","5:40 am");
		
		Note data5 = new Note("My Medicine Checklist", "","Jul 12, 2014","7:45 am");
		
		list.add(data5);
		list.add(data1);
		list.add(data2);
		list.add(data3);
		list.add(data4);
		
		
		

		noteAdapter = new NoteAdapter(getApplicationContext(), list);
		mNoteList.setAdapter(noteAdapter);
		mNoteList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Log.e("note", "CLICKED!");
			}
		});
		
		
		mBtnNotePost = (ImageView) findViewById(R.id.noteBanner);
		mBtnNotePost.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {/*
				Intent intent = new Intent(getApplicationContext(),
						NotePostActivity.class);
				startActivity(intent);*/
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
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
        switch (item.getItemId()) 
        {
        case android.R.id.home: 
            onBackPressed();
            break;
        default:
            return super.onOptionsItemSelected(item);
        }
        return true;
    }
	

}
