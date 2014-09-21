package com.example.phr.tools;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.commons.codec.binary.Base64;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.phr.application.HealthGem;

public class ImageHandler {

	public static String saveImage(Bitmap bitmapImage) {
		ContextWrapper cw = new ContextWrapper(HealthGem.getContext());

		// path to /data/data/yourapp/app_data/images
		File directory = cw.getDir("images", Context.MODE_PRIVATE);

		directory.mkdirs();

		long time = TimestampHandler.getCurrentTimestamp().getTime();
		String filename = UUIDGenerator.generateUniqueString();

		// Create images
		File mypath = new File(directory, filename + ".jpg");

		FileOutputStream fos = null;
		try {

			fos = new FileOutputStream(mypath);
			bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
			fos.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return filename;
	}

	public static Bitmap loadImage(String filename) {
		try {
			ContextWrapper cw = new ContextWrapper(HealthGem.getContext());
			File directory = cw.getDir("images", Context.MODE_PRIVATE);
			File f = new File(directory, filename);
			Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
			return b;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String encodeImageToBase64(Bitmap image) {
		Bitmap immagex = image;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		Base64 base64 = new Base64();
		String imageEncoded = new String(base64.encode(baos.toByteArray()));
		return imageEncoded;
	}

	public static Bitmap decodeImage(String encodedImage) {
		Base64 base64 = new Base64();
		byte[] imageInByte = base64.decode(encodedImage);
		return BitmapFactory
				.decodeByteArray(imageInByte, 0, imageInByte.length);
	}

	public static String getEncodedImageFromFile(String fileName) {
		// TODO Auto-generated method stub
		return null;
	}

	public static String saveImageReturnFileName(String encodedImage) {

		return null;
	}
}
