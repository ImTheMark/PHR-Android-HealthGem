package com.example.phr.mobile.models;

import java.io.FileNotFoundException;

import com.example.phr.exceptions.ImageHandlerException;
import com.example.phr.tools.ImageHandler;

public class PHRImage {

	private String encodedImage;
	private String fileName;

	public PHRImage(String s, PHRImageType type) {
		super();
		if (type == PHRImageType.IMAGE)
			this.encodedImage = s;
		else if (type == PHRImageType.FILENAME)
			this.fileName = s;
	}

	public String getEncodedImage() throws FileNotFoundException,
			ImageHandlerException {
		if (encodedImage == null && fileName != null) {
			ImageHandler imageHandler = new ImageHandler();
			encodedImage = imageHandler.getEncodedImageFromFile(fileName);
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
