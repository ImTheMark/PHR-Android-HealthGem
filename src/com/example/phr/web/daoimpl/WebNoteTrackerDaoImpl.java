package com.example.phr.web.daoimpl;

import java.lang.reflect.Type;
import java.util.List;

import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.WebServerException;
import com.example.phr.mobile.models.BloodPressure;
import com.example.phr.mobile.models.Note;
import com.example.phr.web.dao.WebNoteTrackerDao;
import com.google.gson.reflect.TypeToken;

public class WebNoteTrackerDaoImpl extends
		GenericWebTrackerDaoImpl<Note> implements WebNoteTrackerDao {

	@Override
	public int add_ReturnEntryIdInWeb(Note object) throws WebServerException,
			OutdatedAccessTokenException {
		String command = "tracker/addNote";
		return add_ReturnEntryIDToWebUsingHttp(command, object);
	}

	@Override
	public void edit(Note object) throws WebServerException,
			OutdatedAccessTokenException {
		String command = "tracker/editNote";
		editUsingHttp(command, object);
	}

	@Override
	public void delete(Note object) throws WebServerException,
			OutdatedAccessTokenException {
		String command = "tracker/deleteNote";
		deleteUsingHttp(command, object);
	}

	@Override
	public List<Note> getAll() throws WebServerException,
			OutdatedAccessTokenException {
		String command = "/tracker/getAllNote";
		Type type = new TypeToken<List<Note>>() {
		}.getType();
		return getAllUsingHttp(command, type);
	}

}
