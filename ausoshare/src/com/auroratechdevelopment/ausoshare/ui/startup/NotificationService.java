package com.auroratechdevelopment.ausoshare.ui.startup;

import java.io.IOException;

import com.auroratechdevelopment.ausoshare.CustomApplication;
import com.auroratechdevelopment.ausoshare.util.Constants;
import com.auroratechdevelopment.ausoshare.R;
import com.auroratechdevelopment.ausoshare.ui.home.HomeActivity;
import com.auroratechdevelopment.ausoshare.util.Constants;
import com.auroratechdevelopment.common.webservice.WebServiceHelper;
import com.auroratechdevelopment.common.webservice.WebServiceHelper.WebServiceListener;
import com.auroratechdevelopment.common.webservice.response.AcquireUpdateResponse;
import com.auroratechdevelopment.common.webservice.response.ResponseBase;
import com.auroratechdevelopment.common.webservice.response.UpdateGCMResponse;
import com.auroratechdevelopment.common.webservice.response.UpdateUserProfileResponse;
import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.tencent.mm.sdk.platformtools.Log;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

//Created by Raymond Zhuang at May 20 2016

public class NotificationService extends Service implements
WebServiceListener {
	
	private boolean isRun;// 线程是否继续的标志
	private Handler handler;
	private int notificationCounter;
	NotificationManager notificationManager = null;// 注册通知管理器
	
	private static final String[] TOPICS = {"global"};

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		isRun = false;
		if(notificationManager!=null){
			notificationManager.cancel(1);
			notificationManager=null;
		}
				
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if(!CustomApplication.getInstance().getNotificationChecked()) {
			stopSelf();
		}
		
		WebServiceHelper.getInstance().setServiceListener(this);
		
		isRun = true;
		notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		
		
		new Thread(new Runnable() {
			@Override
			// 在Runnable中，如果要让线程自己一直跑下去，必须自己定义while结构
			// 如果这个run()方法读完了，则整个线程自然死亡
			public void run() {
				// 定义一个线程中止标志
				while (isRun) {
					try {
						Thread.sleep(Constants.NOTI_INT*1000);// Java中线程的休眠，必须在try-catch结构中
						
						//register to gcm
						InstanceID instanceID = InstanceID.getInstance(getApplicationContext());
						//String authorizedEntity = "ausoshare-1324";
						String authorizedEntity = Constants.G_PROJECT_ID;
						String token = instanceID.getToken(authorizedEntity,GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
						
						Log.i("Raymond GCM", "GCM Registration Token: " + token);
						
			            //sendRegistrationToServer(token);
						WebServiceHelper.getInstance().updateGCMToken(token);

			            // Subscribe to topic channels
			            subscribeTopics(token);
			            isRun = false;
			            
						
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (IOException e) {
						// gettoken error
						Log.i("Raymond GCM", "Failed to complete token refresh", e);
						e.printStackTrace();
					}
					if (!isRun) {
						break;
					}
					Message msg = new Message();
					handler.sendMessage(msg);
				}
			}
		}).start();// 默认线程不启动，必须自己start()
		
		handler = new Handler(new Handler.Callback(){
				@Override
				public boolean handleMessage(Message msg) {
					
					//WebServiceHelper.getInstance().acquireUpdate(CustomApplication.getInstance().getLastAD());
					if(!isRun)
						stopSelf();
					
					return false;
				}
			});
		return START_STICKY;
	}
	
	@Override
    public void ResponseSuccessCallBack(int tag, final ResponseBase response)  {
		
		if (response instanceof AcquireUpdateResponse) {
			notificationCounter++;// 计数器+1
			Notification.Builder builder = new Notification.Builder(getApplicationContext());
			
			// 以下三行：在安卓设备任意环境中中，如果点击信息则打开MainActivity
			Intent newintent = new Intent(getApplicationContext(),HomeActivity.class);
			PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0,newintent, 0);
			
			builder.setContentIntent(pendingIntent);
			builder.setSmallIcon(R.drawable.noti_icon_small);
			builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.noti_icon));
			//try{
			//	builder.setLargeIcon(BitmapFactory.decodeStream(getAssets().open("noti_icon.png")));
			//}catch( IOException e){
			//	Log.e("Raymond notification", e.getMessage());
			//}
			builder.setWhen(System.currentTimeMillis());// 设置发送时间
			builder.setTicker("显示通知");// 在用户没有拉开标题栏之前，在标题栏中显示的文字
			builder.setContentTitle("极速分享");// 设置通知的标题
	        builder.setContentText("有"+ response.responseMessage+"条新内容");// 设置通知的内容
	        builder.setAutoCancel(true);//自己维护通知的消失
			
			Notification notification = builder.build();
			notificationManager.notify(1,notification);// 要求通知管理器发送这条通知，其中第一个参数是通知在系统的id

		}else if(response instanceof UpdateGCMResponse){

		}
		
	}

	@Override
	public void ResponseFailedCallBack(int tag, ResponseBase response) {
		Log.e("Raymond send token",response.responseMessage);
		stopSelf();
	}

	@Override
	public void ResponseConnectionError(int tag, ResponseBase response) {
		Log.e("Raymond connect for notification",response.responseMessage);
		stopSelf();
	}
	
	private void subscribeTopics(String token) throws IOException {
        GcmPubSub pubSub = GcmPubSub.getInstance(this);
        for (String topic : TOPICS) {
            pubSub.subscribe(token, Constants.GCM_TOPIC + topic, null);
        }
    }

}
