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

public class ImageHandler {
	
	public String saveImage(Bitmap bitmapImage, String filename){
        ContextWrapper cw = new ContextWrapper(HealthGem.getContext());
        
        // path to /data/data/yourapp/app_data/imagesDirectory
        File directory = cw.getDir("imagesDirectory", Context.MODE_PRIVATE);
        
        // Create imagesDirectory
        // file name with .jpg
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
        
        // return image path
        return directory.getAbsolutePath();
    }
	
	public Bitmap loadImage(String path)
	{
	    try {
	    	
	        File f = new File(path);
	        Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
	        return b;
	    } 
	    catch (FileNotFoundException e) 
	    {
	        e.printStackTrace();
	    }
	    
		return null;
	}

/*	public String encodeImageToBase64(Bitmap imageFromPost){
		Bitmap image = toBufferedImage(imageFromPost);
		String encodedImage = encodeImageToBase64(image);
		return encodedImage;
	}

	public Bitmap decodeImage(String encodedImage){
		Base64 base64 = new Base64();
		byte[] imageInByte = base64.decode(encodedImage);
		Bitmap decodedByte = BitmapFactory.decodeByteArray(imageInByte, 0, imageInByte.length);
		return decodedByte;
	}*/

/*	
 * CORRECT VERSION
 * bitmap object needs fix
 * 
 	public static String encodeImageToBase64(Bitmap image)
	{
	    Bitmap immagex=image;
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();  
	    immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
	    byte[] b = baos.toByteArray();
		Base64 base64 = new Base64();
	    String imageEncoded = base64.encode(b);
	    return imageEncoded;
	}
	
	public static Bitmap decodeImage(String encodedImage) 
	{
		Base64 base64 = new Base64();
		byte[] imageInByte = base64.decode(encodedImage);
	    return BitmapFactory.decodeByteArray(imageInByte, 0, imageInByte.length); 
	}*/


}
