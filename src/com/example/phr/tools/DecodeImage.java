package com.example.phr.tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.util.Log;

public class DecodeImage {

	public static Bitmap decodeFile(String path) {
		int orientation;
		try {
			if (path == null) {
				return null;
			}
			// decode image size
			/*
			 * BitmapFactory.Options o = new BitmapFactory.Options();
			 * o.inJustDecodeBounds = true; // Find the correct scale value. It
			 * should be the power of 2. final int REQUIRED_SIZE = 70; int
			 * width_tmp = o.outWidth, height_tmp = o.outHeight; int scale = 8;
			 * while (true) { if (width_tmp / 2 < REQUIRED_SIZE || height_tmp /
			 * 2 < REQUIRED_SIZE) break; width_tmp /= 2; height_tmp /= 2;
			 * scale++; } // decode with inSampleSize BitmapFactory.Options o2 =
			 * new BitmapFactory.Options(); o2.inSampleSize = scale; Bitmap bm =
			 * BitmapFactory.decodeFile(path, o2);
			 */

			final BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(path, options);

			// Calculate inSampleSize, Raw height and width of image
			final int height = options.outHeight;
			final int width = options.outWidth;

			options.inPreferredConfig = Bitmap.Config.RGB_565;
			int inSampleSize = 1;
			int reqHeight = 700;
			int reqWidth = 1000;
			if (height > reqHeight) {
				inSampleSize = Math.round((float) height / (float) reqHeight);
			}
			int expectedWidth = width / inSampleSize;

			if (expectedWidth > reqWidth) {
				// if(Math.round((float)width / (float)reqWidth) > inSampleSize)
				// //
				// If bigger SampSize..
				inSampleSize = Math.round((float) width / (float) reqWidth);
			}

			options.inSampleSize = inSampleSize;

			// Decode bitmap with inSampleSize set
			options.inJustDecodeBounds = false;

			Bitmap bm = BitmapFactory.decodeFile(path, options);
			Bitmap bitmap = bm;

			ExifInterface exif = new ExifInterface(path);
			orientation = exif
					.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
			Log.e("orientation", "" + orientation);
			Matrix m = new Matrix();

			if ((orientation == 3)) {
				m.postRotate(180);
				m.postScale(bm.getWidth(), bm.getHeight());
				// if(m.preRotate(90)){
				Log.e("in orientation", "" + orientation);
				bitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
						bm.getHeight(), m, true);
				return bitmap;
			} else if (orientation == 6) {
				m.postRotate(90);
				Log.e("in orientation", "" + orientation);
				bitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
						bm.getHeight(), m, true);
				return bitmap;
			} else if (orientation == 8) {
				m.postRotate(270);
				Log.e("in orientation", "" + orientation);
				bitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
						bm.getHeight(), m, true);
				return bitmap;
			}
			return bitmap;
		} catch (Exception e) {
		}
		return null;
	}

}
