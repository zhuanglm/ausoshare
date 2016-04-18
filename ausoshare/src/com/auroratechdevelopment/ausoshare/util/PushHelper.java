/**
 * 
 */
package com.auroratechdevelopment.ausoshare.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.auroratechdevelopment.ausoshare.CustomApplication;
import com.auroratechdevelopment.ausoshare.ui.ActivityBase;
import com.auroratechdevelopment.ausoshare.ui.home.HomeActivity;
import com.auroratechdevelopment.common.DebugLogUtil;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author edward Liu
 *
 */
public class PushHelper {
	
	public interface PushHelperListener {
		void DeviceRegisterSuccessCallBack();
	}
	
    public static final String EXTRA_MESSAGE = "message";
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private static final String PROPERTY_REG_USER = "registration_user";
    
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    String SENDER_ID = Constants.AndroidPushId;

    GoogleCloudMessaging gcm;
    AtomicInteger msgId = new AtomicInteger();
    ActivityBase activity;

    String regid;
    String userName;
    
    private PushHelperListener listener;
    
    public PushHelper(ActivityBase activity, PushHelperListener listener) {
    	this.activity = activity;
    	this.listener = listener;
    }
    
    public void registerDevice(String userName) {
    	this.userName = userName;

    	if (checkPlayServices()) {
            gcm = GoogleCloudMessaging.getInstance(activity);
            regid = getRegistrationId(activity.getApplicationContext());



            if (regid.isEmpty()) {
                registerInBackground();
            }else {
            }
           
        } else {
        	DebugLogUtil.LogD("checkPlayServices return false");
        }
        
    }
    
    public void clearRegister() {
        final SharedPreferences prefs = getGcmPreferences(activity.getApplicationContext());
        DebugLogUtil.LogD("clear register ");
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REG_ID, "");
        editor.putInt(PROPERTY_APP_VERSION, 0);
        editor.putString(PROPERTY_REG_USER, "");
        editor.commit();
    }

    public boolean checkPlayServices() {
    	if (userName == null || userName.length() ==0)
    		return false;
    	
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity.getApplicationContext());
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, activity,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
            	DebugLogUtil.LogD("This device is not supported.");
            }
            return false;
        }
        return true;
    }
    
    /**
     * @deprecated Not in used
     */
    @Deprecated
    public String sendMessage(String message) {
    	 String msg = "";
         try {
             Bundle data = new Bundle();
             data.putString("my_message", message);
             data.putString("my_action", "com.app.ECHO_NOW");
             String id = Integer.toString(msgId.incrementAndGet());
             gcm.send(SENDER_ID + "@com.app.custom", id, data);
             msg = "Sent message";
         } catch (IOException ex) {
             msg = "Error :" + ex.getMessage();
         }
         return msg;
    }
    
    /**
     * Stores the registration ID and the app versionCode in the application's
     * {@code SharedPreferences}.
     *
     * @param context application's context.
     * @param regId registration ID
     */
    private void storeRegistrationId(Context context, String regId) {
        final SharedPreferences prefs = getGcmPreferences(context);
        int appVersion = getAppVersion(context);
        DebugLogUtil.LogD("Saving regId on app version " + appVersion);
        DebugLogUtil.LogD("regId " + regId);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REG_ID, regId);
        editor.putString(PROPERTY_REG_USER, CustomApplication.getInstance().getUsername());
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }

    /**
     * Gets the current registration ID for application on GCM service, if there is one.
     * <p>
     * If result is empty, the app needs to register.
     *
     * @return registration ID, or empty string if there is no existing
     *         registration ID.
     */
    private String getRegistrationId(Context context) {
    	return "";
    	
    	/* always send registration
        final SharedPreferences prefs = getGcmPreferences(context);
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        if (registrationId.isEmpty()) {
        	DebugLogUtil.LogD("Registration not found.");
            return "";
        }
        
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
        	DebugLogUtil.LogD("App version changed.");
            return "";
        }
        
        String registeredUser = prefs.getString(PROPERTY_REG_USER, "");
        if (registeredUser.isEmpty() || Globals.user != null && !registeredUser.equals(Globals.user.userName)) {
        	DebugLogUtil.LogD("User changed changed.");
            return "";
        }
        return registrationId;
        */
    }

    /**
     * Registers the application with GCM servers asynchronously.
     * <p>
     * Stores the registration ID and the app versionCode in the application's
     * shared preferences.
     */
    private void registerInBackground() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(activity.getApplicationContext());
                    }
                    regid = gcm.register(SENDER_ID);
                    msg = "Device registered, registration ID=" + regid;

                    if (listener != null) {
                    	listener.DeviceRegisterSuccessCallBack();
                    }
                    // Persist the regID - no need to register again.
                    storeRegistrationId(activity.getApplicationContext(), regid);
                    
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                    // If there is an error, don't just keep trying to register.
                    // Require the user to click a button again, or perform
                    // exponential back-off.
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                DebugLogUtil.LogD(msg + "\n");
            }
        }.execute(null, null, null);
    }

    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    /**
     * @return Application's {@code SharedPreferences}.
     */
    private SharedPreferences getGcmPreferences(Context context) {
    	
        return activity.getSharedPreferences(HomeActivity.class.getSimpleName(),
                Context.MODE_PRIVATE);
    }
    
   
    public String getRegId() {
    	return regid;
    }
    
    
}
