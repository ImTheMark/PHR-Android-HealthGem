package com.example.phr.mobile.models;

import java.io.FileNotFoundException;
import java.io.Serializable;

import com.example.phr.exceptions.ImageHandlerException;
import com.example.phr.tools.ImageHandler;

public class PHRImage implements Serializable {

	private String encodedImage;
	private String fileName;

	public PHRImage(String s, PHRImageType type) {
		super();
		if (type == PHRImageType.IMAGE)
			this.encodedImage = s;
		else if (type == PHRImageType.FILENAME)
			this.fileName = s;
	}

	public PHRImage() {
	}

	public String getEncodedImage() throws FileNotFoundException,
			ImageHandlerException {
		if (encodedImage == null && fileName != null) {
			encodedImage = ImageHandler.getEncodedImageFromFile(fileName);
		}
		return encodedImage;
	}

	public void setEncodedImage(String encodedImage) {
		this.encodedImage = encodedImage;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
