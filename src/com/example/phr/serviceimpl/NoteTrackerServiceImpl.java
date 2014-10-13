package com.example.phr.serviceimpl;

import java.util.List;

import com.example.phr.exceptions.DataAccessException;
import com.example.phr.exceptions.EntryNotFoundException;
import com.example.phr.exceptions.OutdatedAccessTokenException;
import com.example.phr.exceptions.ServiceException;
import com.example.phr.exceptions.WebServerException;
import com.example.phr.mobile.dao.MobileNoteTrackerDao;
import com.example.phr.mobile.daoimpl.MobileNoteTrackerDaoImpl;
import com.example.phr.mobile.models.Note;
import com.example.phr.service.NoteTrackerService;
import com.example.phr.web.dao.WebNoteTrackerDao;
import com.example.phr.web.daoimpl.WebNoteTrackerDaoImpl;

public class NoteTrackerServiceImpl implements NoteTrackerService {

	WebNoteTrackerDao webNoteTrackerDao;
	MobileNoteTrackerDao mobileNoteTrackerDao;

	public NoteTrackerServiceImpl() {
		webNoteTrackerDao = new WebNoteTrackerDaoImpl();
		mobileNoteTrackerDao = new MobileNoteTrackerDaoImpl();
	}

	@Override
	public void add(Note note) throws ServiceException,
			OutdatedAccessTokenException {
		int entryID;
		try {
			entryID = webNoteTrackerDao.add_ReturnEntryIdInWeb(note);
			note.setEntryID(entryID);
			mobileNoteTrackerDao.add(note);
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
			webNoteTrackerDao.edit(note);
			mobileNoteTrackerDao.edit(note);
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
			webNoteTrackerDao.delete(note);
			mobileNoteTrackerDao.delete(note);
		} catch (WebServerException e) {
			throw new ServiceException(
					"An error occured while trying to delete note to web", e);
		} catch (DataAccessException e) {
			throw new ServiceException(
					"An error occured while trying to delete note to web", e);
		}
	}

	@Override
	public List<Note> getAll() throws ServiceException {
		try {
			return mobileNoteTrackerDao.getAll();
		} catch (DataAccessException e) {
			throw new ServiceException(
					"An error occured while trying to retrieve", e);
		}
	}

	@Override
	public Note get(int entryID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Note getLatest() {
		return mobileNoteTrackerDao.getLatest();
	}

}
