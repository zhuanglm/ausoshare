package com.auroratechdevelopment.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

/**
 * Created by Raymond Zhuang 2016/4/25
 */

public class FileUtils {
	public static String SDPATH = Environment.getExternalStorageDirectory() + "/";

	public static void saveBitmap(Bitmap bm, String picName) {
		try {
			if (!isFileExist("",".JPG")) {
				File tempf = createSDDir("");
			}
			File f = new File(SDPATH, picName + ".JPG"); 
			if (f.exists()) {
				f.delete();
			}
			FileOutputStream out = new FileOutputStream(f);
			bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Bitmap loadBitmap(String url,String format) {
        try {
             FileInputStream fis = new FileInputStream(SDPATH+url+"."+format);
             return BitmapFactory.decodeStream(fis);  ///把流转化为Bitmap图片        

          } catch (FileNotFoundException e) {
             e.printStackTrace();
             return null;
        }
   }

	public static File createSDDir(String dirName) throws IOException {
		File dir = new File(SDPATH + dirName);
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {

			System.out.println("createSDDir:" + dir.getAbsolutePath());
			System.out.println("createSDDir:" + dir.mkdir());
		}
		return dir;
	}

	public static boolean isFileExist(String fileName,String format) {
		File file = new File(SDPATH + fileName +"."+ format);
		file.isFile();
		return file.exists();
	}
	
	public static void delFile(String fileName,String format){
		File file = new File(SDPATH + fileName +"."+ format);
		if(file.isFile()){
			file.delete();
        }
		file.exists();
	}

	public static void deleteDir() {
		File dir = new File(SDPATH);
		if (dir == null || !dir.exists() || !dir.isDirectory())
			return;
		
		for (File file : dir.listFiles()) {
			if (file.isFile())
				file.delete(); 
			else if (file.isDirectory())
				deleteDir(); 
		}
		dir.delete();
	}

	public static boolean fileIsExists(String path) {
		try {
			File f = new File(path);
			if (!f.exists()) {
				return false;
			}
		} catch (Exception e) {

			return false;
		}
		return true;
	}

}
