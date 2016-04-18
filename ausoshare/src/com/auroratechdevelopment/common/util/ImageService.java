package com.auroratechdevelopment.common.util;

import java.io.IOException;  
import java.io.InputStream;  
import java.net.HttpURLConnection;  
import java.net.URL;

import com.auroratechdevelopment.ausoshare.CustomApplication;
import com.auroratechdevelopment.ausoshare.R;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;  
  
  
public class ImageService {  
      
	private Bitmap adBitmap;
	
    public static byte[] getImage(String path) throws IOException {  
        URL url = new URL(path);  
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
        conn.setRequestMethod("GET");   //设置请求方法为GET  
        conn.setReadTimeout(5*1000);    //设置请求过时时间为5秒  
        InputStream inputStream = conn.getInputStream();   //通过输入流获得图片数据  
        byte[] data = StreamTool.readInputStream(inputStream);     //获得图片的二进制数据  
        return data;  
    }  
    
    public void fetchAdImage(String urlPaththumb){
//    	String urlPaththumb = CustomApplication.getInstance().getSharedAdThumb();
    	
    	try{
    		byte[] data = ImageService.getImage(urlPaththumb);
    		adBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
    	}catch(IOException e){
    		
    	}
    }
    
    public Bitmap getAdImage(){
    	return adBitmap;
    }
    
    
} 