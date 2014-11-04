package com.example.phr.tools;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.example.phr.application.HealthGem;

public class ImageHandler {

	public static String saveImage(Bitmap bitmapImage) {
		ContextWrapper cw = new ContextWrapper(HealthGem.getContext());

		// path to /data/data/yourapp/app_data/images
		File directory = cw.getDir("images", Context.MODE_PRIVATE);

		directory.mkdirs();

		long time = TimestampHandler.getCurrentTimestamp().getTime();
		String filename = UUIDGenerator.generateUniqueString() +".jpg";

		// Create images
		File mypath = new File(directory, filename);

		FileOutputStream fos = null;
		try {

			fos = new FileOutputStream(mypath);
			bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
			fos.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Saved as: " + mypath);
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
	
	public static Boolean deleteImage(String filename){
		ContextWrapper cw = new ContextWrapper(HealthGem.getContext());
		File directory = cw.getDir("images", Context.MODE_PRIVATE);
		File file = new File(directory, filename);
		boolean deleted = file.delete();
		return deleted;
	}
	
 	public static String encodeImageToBase64(Bitmap image)
	{
	    Bitmap immagex=image;
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();  
	    immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
	    byte[] b = baos.toByteArray();
	    String imageEncoded = Base64.encodeToString(b,Base64.DEFAULT);
	    return imageEncoded;
	}
	
	public static Bitmap decodeImage(String encodedImage) 
	{
		byte[] imageInByte = Base64.decode(encodedImage, 0);
	    return BitmapFactory.decodeByteArray(imageInByte, 0, imageInByte.length); 
	}

	public static String getEncodedImageFromFile(String fileName) {
		return encodeImageToBase64(loadImage(fileName));
	}

	public static String saveImageReturnFileName(String encodedImage) {
		return saveImage(decodeImage(encodedImage));
	}
}
