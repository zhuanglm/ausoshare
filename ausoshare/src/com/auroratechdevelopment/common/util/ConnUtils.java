package com.auroratechdevelopment.common.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnUtils {

	public static boolean isConnected(Context context)
	{
		ConnectivityManager conMgr = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] allNetInfo = conMgr.getAllNetworkInfo();
		if(allNetInfo == null)
		{
			//DebugLogUtil.LogD("net info = null");
		}
		boolean connected = false;
		//DebugLogUtil.LogD("netInfo count " + String.valueOf(allNetInfo.length));
        for (NetworkInfo netInfo : allNetInfo)
        {
			//DebugLogUtil.LogD("netInfo connected " + String.valueOf(netInfo.isConnectedOrConnecting()));
       	    if(netInfo.isConnectedOrConnecting())
        		connected = true;
        }
        //DebugLogUtil.LogD("isConnected " + String.valueOf(connected));
        return connected;
	}

	

	

}
