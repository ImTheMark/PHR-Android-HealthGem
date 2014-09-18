package com.example.phr.tools;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.example.phr.application.HealthGem;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

public class ImageHandler {

	
	public String saveImage(Bitmap bitmapImage, String filename){
        ContextWrapper cw = new ContextWrapper(HealthGem.getContext());
        
        // path to /data/data/yourapp/app_data/imagesDirectory
        File directory = cw.getDir("images", Context.MODE_PRIVATE);
        
        directory.mkdirs();
        
        // Create imagesDirectory
        File mypath = new File(directory, filename+".jpg");

        FileOutputStream fos = null;
        try {           

            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
		Log.e("saveImage", directory.getAbsolutePath());
        
        // return image path
        return directory.getAbsolutePath();
    }
	
	public Bitmap loadImage(String path)
	{
	    try {
	    	
	        File f = new File(path, "heart.jpg");
	        Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
	        return b;
	    } 
	    catch (FileNotFoundException e) 
	    {
	        e.printStackTrace();
	    }
	    
		return null;
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
}
