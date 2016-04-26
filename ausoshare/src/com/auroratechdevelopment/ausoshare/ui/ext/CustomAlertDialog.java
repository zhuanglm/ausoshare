package com.auroratechdevelopment.ausoshare.ui.ext;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.auroratechdevelopment.ausoshare.R;
import com.auroratechdevelopment.common.DebugLogUtil;
import com.auroratechdevelopment.common.ThemesSetting;
import com.tencent.mm.sdk.platformtools.Log;

import java.util.ArrayList;


public class CustomAlertDialog extends Dialog {
	private Context context;
    
	private ArrayList<String> alertMsgs = new ArrayList<String>();
    private String button1 = "";
    private OnClickListener onClick1 = null;
    private boolean dismiss1 = true;

    private String button2 = "";
    private OnClickListener onClick2 = null;
    private boolean dismiss2 = true;
    
    private ArrayList<Integer> ids = new ArrayList<Integer>();
    private CharSequence alertTitle = "";
    
    public interface AlertCallback {
    	public void GetAlertButton(int which);
    	public void AlertCancelled();
    }

    
	@Override
    public boolean onSearchRequested()
    {
    	DebugLogUtil.LogD("Search requested");
    	return false;
    }
    public CustomAlertDialog(Context context)
	{
		super(context);	
	}

	public CustomAlertDialog(Context context,int theme)
	{
		super(context,theme);
	}
	
	public CustomAlertDialog(Context context, CharSequence alertTitle, String alertMsg){
		super(context);

		this.context = context;
		this.alertMsgs.add(alertMsg);
		this.alertTitle = alertTitle;
	}

	@Override
    public void onCreate(Bundle savedInstanceState)
    {
		try{
			
			ThemesSetting.setActivityTheme();
			if(context != null) {
				ThemesSetting.onActivityCreateSetTheme((Activity) context);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
    	super.onCreate(savedInstanceState);
    }
	
    public void setMessage(String msg,int resourseID)
    {
    	alertMsgs.add(msg);
    	ids.add(resourseID);
    }
    
   
    public void setTitle(CharSequence title, int resourceID){
    	this.alertTitle = title;
    	ids.add(resourceID);
    }
    
    public void setTitle(CharSequence title) {
    	this.alertTitle = title;
    }
    
	@Override
    public void show(){

    	try
    	{
    		Button backButton = (Button)findViewById(R.id.backButton);
    		if(backButton != null) {
    			backButton.setOnClickListener(backListener);
    		}
	    	if(!alertMsgs.isEmpty())
	    	{
    			TextView tv = (TextView)findViewById(R.id.alert_title);
    			if(tv != null ) {
	    			if(alertTitle != null 
	    				&&alertTitle.length() > 0) {
	    				tv.setText(alertTitle);
	    			}else {
	    				tv.setVisibility(View.GONE);
	    			}
    			}
    			
	    		int length = alertMsgs.size();
	    		for(int index = 0;index<length;index++)
	    		{
	    			DebugLogUtil.LogD("show id =" + String.valueOf(ids.get(index)));
	    			DebugLogUtil.LogD("show msg =" + alertMsgs.get(index));
	    			TextView view = ((TextView)findViewById(ids.get(index)));
	    			if(view != null) {
	    				view.setText(alertMsgs.get(index));
	    			}
	    		}
	    	}
	    	
        	Button button1 = (Button)findViewById(R.id.alert_button_0);
	     	if(onClick1 != null) {
	        	button1.setText(this.button1);
	        	button1.setOnClickListener(onClickListener1);
	        	button1.setVisibility(View.VISIBLE);
	    	}
	     	else {
	    		button1.setVisibility(View.GONE);
	    	}
	     	
        	Button button2 = (Button)findViewById(R.id.alert_button_1);
	    	if(onClick2 != null) {
	        	button2.setText(this.button2);
	        	button2.setOnClickListener(onClickListener2);
	        	button2.setVisibility(View.VISIBLE);
	    	}
	    	else {
	    		button2.setVisibility(View.GONE);
	    	}
	    
	    	setInFullScreen();
	    	super.show();
    	}
    	catch (Exception e)
    	{
    		e.printStackTrace();
    		// catch exception-  this can occur if send alert and app shut down
    	}
    }
	
    public void setButton(String buttonTitle,boolean dismiss, OnClickListener onClick)
    {
    	dismiss1 = dismiss;
    	button1 = buttonTitle;
    	onClick1 = onClick;
    }

    public void setButton(String buttonTitle, OnClickListener onClick)
    {
    	button1 = buttonTitle;
    	onClick1 = onClick;
    }
    
    public void setButton2(String buttonTitle,boolean dismiss,OnClickListener onClick)
    {
    	dismiss2 = dismiss;
    	button2 = buttonTitle;
    	onClick2 = onClick;
    }

    public void setButton2(String buttonTitle,OnClickListener onClick)
    {
    	button2 = buttonTitle;
    	onClick2 = onClick;
    }	
    
    public void setBackListener(View.OnClickListener listener){
    	if(listener != null) {
    		this.backListener = listener;
    	}
    }

	private View.OnClickListener backListener = new View.OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			try
			{
				CustomAlertDialog.this.dismiss();
			}
			catch (Exception e)
			{
				
			}
		}

	};
    
	private View.OnClickListener onClickListener1 = new View.OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			try
			{
				DebugLogUtil.LogD("CAD Button 1");
				onClick1.onClick(CustomAlertDialog.this, DialogInterface.BUTTON_POSITIVE);
				if(dismiss1)
				{
					CustomAlertDialog.this.dismiss();
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

	};

    private View.OnClickListener onClickListener2 = new View.OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			onClick2.onClick(CustomAlertDialog.this, DialogInterface.BUTTON_NEGATIVE);
			if(dismiss2)
			{
				CustomAlertDialog.this.dismiss();
			}
		}

	};

	public void setInFullScreen() {
		try {
	        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
	        lp.copyFrom(super.getWindow().getAttributes()); 
	        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
	        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
	
	        super.getWindow().setAttributes(lp);
		}catch (Exception e) {
			DebugLogUtil.Log(e, "setInFullScreen");
		}
	}

}
