package com.example.phr.serviceimpl;

import java.util.List;

import com.example.phr.exceptions.DataAccessException;
import com.example.phr.exceptions.EntryNotFoundException;
import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.exceptions.WebServerException;
import com.example.phr.mobile.models.Note;
import com.example.phr.service.NoteService;

public class NoteServiceImpl implements NoteService {

	@Override
	public void add(Note object) throws ServiceException,
			OutdatedAccessTokenException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void edit(Note object) throws WebServerException,
			OutdatedAccessTokenException, DataAccessException,
			EntryNotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Note object) throws WebServerException,
			OutdatedAccessTokenException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Note> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Note get(int entryID) {
		// TODO Auto-generated method stub
		return null;
	}

}
