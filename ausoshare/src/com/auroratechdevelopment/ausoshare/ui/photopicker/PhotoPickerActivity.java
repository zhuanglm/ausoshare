package com.auroratechdevelopment.ausoshare.ui.photopicker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

import com.auroratechdevelopment.ausoshare.R;
import com.auroratechdevelopment.ausoshare.ui.login.LoginActivity;
import com.auroratechdevelopment.ausoshare.util.Constants;
import com.auroratechdevelopment.common.ViewUtils;
import com.auroratechdevelopment.common.util.Bimp;
import com.auroratechdevelopment.common.util.FileUtils;
import com.auroratechdevelopment.common.util.ImageItem;


/**
 * Created by Raymond Zhuang 2016/5/2
 */

public class PhotoPickerActivity extends Activity {
	private static final int REQUEST_TAKE_PICTURE = 0xa1;
	private static final int REQUEST_PICK_PICTURE = 0xa2;
	private static final int REQUEST_CLIP_PICTURE = 0xa3;
	
	private static final String IMAGE_FILE_NAME = "temp_avatar.jpg";

	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.item_popupwindows);
        
        Button bt1 = (Button) findViewById(R.id.item_popupwindows_camera);
		Button bt2 = (Button) findViewById(R.id.item_popupwindows_Photo);
		Button bt3 = (Button) findViewById(R.id.item_popupwindows_cancel);
		
		bt1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				photo();
				//finish();
			}
		});
		
		bt2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				album();
				//finish();
			}
		});
		
		bt3.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	public void album() {
		
		Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
		pickIntent.setType("image/*");
		pickIntent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(pickIntent, REQUEST_PICK_PICTURE);

	}
	
	public void photo() {
		Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, 
				Uri.fromFile(new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME)));
		startActivityForResult(openCameraIntent, REQUEST_TAKE_PICTURE);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_CANCELED) {
			Toast.makeText(getApplication(), getResources().getString(R.string.camera_cancel), Toast.LENGTH_LONG).show();
			
		}else{
		
			switch (requestCode) {
			case REQUEST_TAKE_PICTURE:
				File temp = new File(Environment.getExternalStorageDirectory() + "/" + IMAGE_FILE_NAME);
				ClipPhoto(Uri.fromFile(temp));
				break;
			
			case REQUEST_PICK_PICTURE:
				ClipPhoto(data.getData());
				break;
				
			case REQUEST_CLIP_PICTURE:
				
				if (data != null) {
					setImageToView(data);
				}
				break;
			}
		}
	}
	
	/**
	 * 裁剪原始的图片
	 */
	public void ClipPhoto(Uri uri) {

		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");

		// 设置裁剪
		intent.putExtra("crop", "true");

		// aspectX , aspectY :宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);

		// outputX , outputY : 裁剪图片宽高
		intent.putExtra("outputX", 300);
		intent.putExtra("outputY", 300);
		intent.putExtra("return-data", true);

		startActivityForResult(intent, REQUEST_CLIP_PICTURE);
	}
	
	/**
	 * 提取保存裁剪之后的图片数据，并设置头像部分的View
	 */
	private void setImageToView(Intent intent) {
		/*Bundle extras = intent.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			//headImage.setImageBitmap(photo);
		}*/
		setResult(RESULT_OK,intent);
		finish();
	}

	@Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();  
    }
}
