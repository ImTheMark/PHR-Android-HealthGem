package com.example.phr.mobile.dao;

import java.text.ParseException;
import java.util.List;
import com.example.phr.exceptions.DataAccessException;
import com.example.phr.mobile.models.Note;

public interface MobileNoteDao {

	void add(Note note) throws DataAccessException;

	List<Note> getAllNote() throws ParseException;

}
