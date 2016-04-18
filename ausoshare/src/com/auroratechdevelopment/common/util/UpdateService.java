package com.auroratechdevelopment.common.util;

import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;

public class UpdateService extends Service{
	//android system download class
	DownloadManager manager;
	String downloadUri = "https://play.google.com/store/apps/details?id=com.auroratechdevelopment.ausoshare";
	
	//broadcast of receiver of download
	DownloadCompleteReceiver receiver;
	
	private void initDownManager(){
		manager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
		receiver = new DownloadCompleteReceiver();
		
		
		//set download URL
		DownloadManager.Request down = new DownloadManager.Request(Uri.parse(downloadUri));
		
		//set network type, only WIFI can be used
		down.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
		
		//during downloading, notification shows
		//TODO
		down.setNotificationVisibility(Request.VISIBILITY_VISIBLE);
		//display the download page
		down.setVisibleInDownloadsUi(true);
		//set the path after downloading
		down.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, "ausoshare.apk");
		
		//set the download into the queue
		manager.enqueue(down);
		
		//register download broadcast
		registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
		
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId){
		//call the download
		initDownManager();
		
		return super.onStartCommand(intent, flags, startId);
	}
	
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onDestroy(){
		//unregister the download broadcast
		unregisterReceiver(receiver);
		
		super.onDestroy();
	}
	
	//receive the intent after downloading
	class DownloadCompleteReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			if(intent.getAction().equals(
					DownloadManager.ACTION_DOWNLOAD_COMPLETE)){
				//get download file ID
				long downId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
				
				//auto installation APK
				installAPK(manager.getUriForDownloadedFile(downId));
				
				//stop service and close broadcast
				UpdateService.this.stopSelf();		
			}
		}
		
		/*
		 * Install APK file
		 */
		private void installAPK(Uri apk){
			//install APK by Intent
			Intent intents = new Intent();
			intents.setAction("android.intent.action.View");
			intents.addCategory("android.intent.category.DEFAULT");
			intents.setType("application/vnd.android.package-archive");
			intents.setData(apk);
			intents.setDataAndType(apk, "application/vnd.android.package-archive");
			intents.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			android.os.Process.killProcess(android.os.Process.myPid());
			
			startActivity(intents);
			
		}
		
	}
	

	

}
