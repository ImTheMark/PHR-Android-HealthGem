package com.example.phr.serviceimpl;

import java.util.List;

import com.example.phr.exceptions.DataAccessException;
import com.example.phr.exceptions.EntryNotFoundException;
import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.exceptions.WebServerException;
import com.example.phr.mobile.dao.MobileNoteDao;
import com.example.phr.mobile.daoimpl.MobileNoteDaoImpl;
import com.example.phr.mobile.models.Note;
import com.example.phr.service.NoteService;
import com.example.phr.web.dao.WebNoteDao;
import com.example.phr.web.daoimpl.WebNoteDaoImpl;

public class NoteServiceImpl implements NoteService {

	WebNoteDao webNoteDao;
	MobileNoteDao mobileNoteDao;

	public NoteServiceImpl() {
		webNoteDao = new WebNoteDaoImpl();
		mobileNoteDao = new MobileNoteDaoImpl();
	}

	@Override
	public void add(Note note) throws ServiceException,
			OutdatedAccessTokenException {
		int entryID;
		try {
			entryID = webNoteDao.add_ReturnEntryIdInWeb(note);
			note.setEntryID(entryID); 
			mobileNoteDao.add(note);
		} catch (WebServerException e) {
			throw new ServiceException(
					"An error occured while trying to add note to web", e);
		} catch (DataAccessException e) {
			throw new ServiceException(
					"An error occured while trying to add note to web", e);
		} 
	}

	@Override
	public void edit(Note note) throws ServiceException,
			OutdatedAccessTokenException, EntryNotFoundException {
		try {
			webNoteDao.edit(note);
			mobileNoteDao.edit(note);
		} catch (WebServerException e) {
			throw new ServiceException(
					"An error occured while trying to edit note to web", e);
		} catch (DataAccessException e) {
			throw new ServiceException(
					"An error occured while trying to edit note to web", e);
		}
	}

	@Override
	public void delete(Note note) throws OutdatedAccessTokenException, 
			ServiceException, EntryNotFoundException {
		try {
			webNoteDao.delete(note);
			mobileNoteDao.delete(note);
		} catch (WebServerException e) {
			throw new ServiceException(
					"An error occured while trying to delete note to web", e);
		} catch (DataAccessException e) {
			throw new ServiceException(
					"An error occured while trying to delete note to web", e);
		}
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
