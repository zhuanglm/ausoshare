package com.auroratechdevelopment.common;


import android.util.Log;

import com.auroratechdevelopment.ausoshare.BuildConfig;

import java.util.Date;

public class DebugLogUtil {
	
    public static boolean debugEnable = BuildConfig.DEBUG;
    private static final boolean logCat = true;
    private static final boolean file = false;
    private static final boolean network = false;
    
    /*
    public static boolean debugEnable = false;
    private static final boolean logCat = false;
    private static final boolean file = false;
    private static final boolean network = false;
    */
    
    public static final String TAG = "ausoshare";
    
	public static void LogD(String logInfo)
    {
    	LogD(logInfo,true);
    }
    @SuppressWarnings("unused")
	public static void LogD(String logInfo,boolean send)
    {
    	if(debugEnable && send)
    	{
    		if(logInfo == null){
    			logInfo = "LOGINFO IS NULL.";
    		}
    		
	    	if(logCat)  // write to logcat
	    	{
	    		Log.d(TAG, logInfo);
	    	}
	    	
	    	if(file || network) // write to file
	    	{
	    		Date now = new Date();
	    		StringBuffer debugData = new StringBuffer(TAG);
	    		debugData.append(" ");
	    		debugData.append(now.toString());
	    		debugData.append(" ");
	    	    debugData.append(logInfo);
	    	
		    	if (file) // write to network
		    	{
		    	}
		    	if (network) // write to network
		    	{
		    	}
	    	}
    	}
     
    }
    
    public static void Log(Exception ex, String msg)
    {
    	if (ex != null) {
    		ex.printStackTrace();
    	}
    	Log.d(TAG, msg);
    }
    
    public static void LogD(String module,String logInfo)
    {
    	LogD(module,logInfo,true);
    }
    
    @SuppressWarnings("unused")
    public static void LogD(String module,String logInfo,boolean send)
    {
    	if(debugEnable & send)
    	{
	    	if(logCat)  // write to logcat
	    	{
	    		Log.d(module, logInfo);
	    	}
	    	
	    	if (file || network)
	    	{
	    		Date now = new Date();
	    		StringBuffer debugData = new StringBuffer(module);
	    		debugData.append(" ");
	    		debugData.append(now.toString());
	    		debugData.append(" ");
	    	    debugData.append(logInfo);

	    	
		    	if(file) // write to file
		    	{		    	
		    	}
		    	if (network) // write to network
		    	{		    		
		    	}
	    	}
    	}
    }
    
    public static void LogE(String logInfo)
    {
    	LogE(logInfo,true);
    }   
    @SuppressWarnings("unused")
    public static void LogE(String logInfo,boolean send)
    {
    	if(debugEnable && send)
    	{
	    	if(logCat)  // write to logcat
	    	{
	    		Log.e(TAG, logInfo);
	    	}
	    	if(file || network) // write to file
	    	{
	    		Date now = new Date();
	    		StringBuffer debugData = new StringBuffer(TAG);
	    		debugData.append(" ");
	    		debugData.append(now.toString());
	    		debugData.append(" ");
	    	    debugData.append(logInfo);
	    	
		    	if (file) // write to network
		    	{
		    	}
		    	if (network) // write to network
		    	{
		    	}
	    	}
    	}
    }
 
    public static void LogE(String module,String logInfo)
    {
    	LogE(module,logInfo,true);
    }

    @SuppressWarnings("unused")
    public static void LogE(String module,String logInfo,boolean send)
    {
    	if(debugEnable && send)
    	{
	    	if(logCat)  // write to logcat
	    	{
	    		Log.e(module, logInfo);
	    	}
	    	if (file || network)
	    	{
	    		Date now = new Date();
	    		StringBuffer debugData = new StringBuffer(module);
	    		debugData.append(" ");
	    		debugData.append(now.toString());
	    		debugData.append(" ");
	    	    debugData.append(logInfo);

	    	
		    	if(file) // write to file
		    	{		    	
		    	}
		    	if (network) // write to network
		    	{		    		
		    	}
	    	}
    	}
    }
    
    public static void LogStackDump()
    {
    		LogStackDump(true);
    }
    public static void LogStackDump(boolean send)
    {
    	if(debugEnable && send)
    	{
	    	if(logCat)  // write to logcat
	    	{
		        StackTraceElement[] sTraces = Thread.currentThread().getStackTrace();
		        StackTraceElement sTrace = null;
		        for(int i = 3;i<sTraces.length;i++)
		        {
		        	sTrace = sTraces[i];
		        	Log.d(TAG, "Stack Dump  Filename:" + sTrace.getFileName().toString() +
							" Class:" + sTrace.getClassName().toString() +
							" Method:" + sTrace.getMethodName().toString() +
							" Line Number:" + Integer.toString(sTrace.getLineNumber()));
		        }
	    	}
    	}
        
    }

}