package com.auroratechdevelopment.common.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Raymond Zhuang 2016/5/2
 */

public class Bimp {

		public static int max = 0;
		
		public static ArrayList<ImageItem> tempSelectBitmap = new ArrayList<ImageItem>();   //选择的图片的临时列表

		public static Bitmap revitionImageSize(String path) throws IOException {
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(
					new File(path)));
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(in, null, options);
			in.close();
			int i = 0;
			Bitmap bitmap = null;
			while (true) {
				if ((options.outWidth >> i <= 1000)
						&& (options.outHeight >> i <= 1000)) {
					in = new BufferedInputStream(
							new FileInputStream(new File(path)));
					options.inSampleSize = (int) Math.pow(2.0D, i);
					options.inJustDecodeBounds = false;
					bitmap = BitmapFactory.decodeStream(in, null, options);
					break;
				}
				i += 1;
			}
			return bitmap;
		}
		
		public static Bitmap getHttpBitmap(String url){
	        URL myFileURL;
	        Bitmap bitmap=null;
	        try{
	            myFileURL = new URL(url);
	            //获得连接
	            HttpURLConnection conn=(HttpURLConnection)myFileURL.openConnection();
	            //设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制
	            conn.setConnectTimeout(6000);
	            //连接设置获得数据流
	            conn.setDoInput(true);
	            //不使用缓存
	            conn.setUseCaches(false);
	            //这句可有可无，没有影响
	            //conn.connect();
	            //得到数据流
	            InputStream is = conn.getInputStream();
	            //解析得到图片
	            bitmap = BitmapFactory.decodeStream(is);
	            //关闭数据流
	            is.close();
	        }catch(Exception e){
	            e.printStackTrace();
	        }
	         
	        return bitmap;
	         
	    }
}
