package com.auroratechdevelopment.ausoshare.ui.startup;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.util.Locale;

import com.auroratechdevelopment.ausoshare.CustomApplication;
import com.auroratechdevelopment.ausoshare.R;
import com.auroratechdevelopment.ausoshare.ui.ActivityBase;
import com.auroratechdevelopment.ausoshare.ui.ext.MyViewFilpper;
import com.auroratechdevelopment.ausoshare.ui.ext.MyViewFilpper.OnDisplayChagnedListener;
import com.auroratechdevelopment.ausoshare.ui.home.HomeActivity;
import com.auroratechdevelopment.ausoshare.ui.login.LoginActivity;
import com.auroratechdevelopment.ausoshare.util.AppLocationService;
import com.auroratechdevelopment.ausoshare.util.CheckNetworkStatus;
import com.auroratechdevelopment.ausoshare.util.LocationAddress;
import com.auroratechdevelopment.common.ViewUtils;
import com.auroratechdevelopment.common.util.LoadNetPicture;
import com.auroratechdevelopment.common.webservice.WebServiceConstants;

//Updated by Raymond Zhuang May 1 2016

public class SplashActivity extends Activity implements OnGestureListener,OnDisplayChagnedListener{

    public static final int SPLASH_SECOND = 3;
    boolean isNotFirstTime = true;
    private AppLocationService appLocationService;
    private ImageView splashAdImage1,splashAdImage2;
    public CheckNetworkStatus checkNetworkStatus;
    
    private MyViewFilpper img_vf = null;
    private ImageView[] indicators;
    LinearLayout pointLayout = null;
    private GestureDetector detector;
    private int m_nPages;
    private int m_currentImg = 0;
    private Button m_BtnSkip,m_BtnStart;
    private boolean m_isSkipped;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //splashAdImage = (ImageView)findViewById(R.id.splash_ad_image);
        m_isSkipped = false;
        m_nPages = getResources().getInteger(R.integer.max_splash_page);
        
        detector = new GestureDetector(this);
        img_vf = (MyViewFilpper)this.findViewById(R.id.splash_img_vf);
        splashAdImage1 = new ImageView(this);
        
        img_vf.addView(splashAdImage1);
        
        pointLayout = (LinearLayout) findViewById(R.id.point_layout);
        m_BtnSkip = (Button) findViewById(R.id.button_skip);
        m_BtnStart = (Button) findViewById(R.id.button_start);
        
        m_BtnSkip.setOnClickListener(new OnClickListener() 
        { 
			@Override
			public void onClick(View v) {
				m_isSkipped = true;
				finish();
                showHomeOrLogin();
				
			} 
        });
        
        m_BtnStart.setOnClickListener(new OnClickListener() 
        { 
			@Override
			public void onClick(View v) {
				m_isSkipped = true;
				finish();
                showHomeOrLogin();
				
			} 
        });
        
        checkNetworkStatus = new CheckNetworkStatus(this);
        
        isNotFirstTime = CustomApplication.getInstance().getNotFirstTimeUse();

        if (isNotFirstTime && checkNetworkStatus.getNetworkStatus()) {
        	splashAdImage1.setScaleType(ScaleType.FIT_XY);
        	new LoadNetPicture().getPicture(WebServiceConstants.splashAdImageURL, splashAdImage1);
        	//splashAdImage2.setScaleType(ScaleType.CENTER_INSIDE);
        	//splashAdImage2.setImageDrawable(getResources().getDrawable(R.drawable.splash_share));
            
            img_vf.setOnDisplayChagnedListener(this);
            
            
                       
        }else{
        	splashAdImage1.setScaleType(ScaleType.FIT_XY);
        	splashAdImage1.setImageDrawable(getResources().getDrawable(R.drawable.firsttime_launch_app_1));
        	//splashAdImage1.setImageDrawable(getResources().getDrawable(R.drawable.splash_share));
        	
        }
        
        getDeviceLocation();
        
        indicators = new ImageView[m_nPages];
        for(int i = 0; i< m_nPages;i++) {
        	indicators[i] = (ImageView) pointLayout.getChildAt(i);
        	indicators[i].setEnabled(false);
        	indicators[i].setTag(i);
            }
        indicators[0].setEnabled(true);//初始，第0个img的属性Enable为true
        
        //is not the first time, means from the second times
        if(isNotFirstTime){
        	pointLayout.setVisibility(View.INVISIBLE);
        	m_BtnSkip.setVisibility(View.VISIBLE);
        	
        	new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                    if(!m_isSkipped)
                    	showHomeOrLogin();
                }
            }, SPLASH_SECOND * 1000);
        }
        //is the first time
        else{
        	// the first time should show 2 pages
        	splashAdImage2 = new ImageView(this);
        	img_vf.addView(splashAdImage2);
        	splashAdImage2.setScaleType(ScaleType.FIT_XY);
        	splashAdImage2.setImageDrawable(getResources().getDrawable(R.drawable.firsttime_launch_app_2));
        	//img_vf.setAutoStart(true);
        	
        	CustomApplication.getInstance().setNotificationChecked(false);
        	CustomApplication.getInstance().setNotFirstTimeUse(true);
        	//showFirstTimeUseGuide();
        }

    }
    
    public void getDeviceLocation(){
        appLocationService = new AppLocationService(SplashActivity.this);

        //get location city name
        double latitude = 43.835764;
        double longitude = -79.332938;

        Location location = appLocationService.getLocation(LocationManager.GPS_PROVIDER);
        if(location != null){
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }

        LocationAddress locationAddress = new LocationAddress();
        locationAddress.getAddressFromLocation(latitude, longitude,
                getApplicationContext(), new GeocoderHandler());

    }
    
    
    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
        }
    }

    protected void showFirstTimeUseGuide(){
    	this.startActivity(new Intent(SplashActivity.this,FirstTimeUseGuideActivity.class));
    	finish();
    }
    

    protected void showHomeOrLogin() {
    	stopService(new Intent(this,NotificationService.class));
    	
    	ViewUtils.startPageWithClearStack(null, this, HomeActivity.class);
    	
//        if(CustomApplication.getInstance().getRememberMeChecked()){
//            ViewUtils.startPageWithClearStack(null, this, HomeActivity.class);
//        }
//        else{
//            ViewUtils.startPageWithClearStack(null, this, LoginActivity.class);
//        }


    }
    
    public boolean onTouchEvent(MotionEvent event) {
        return detector.onTouchEvent(event);
    }

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		
		img_vf.setAutoStart(false);
		if (e1.getX() - e2.getX() > 120) { 
            if(img_vf.getDisplayedChild() == m_nPages-1){//如果滑动到最后
                   img_vf.stopFlipping();                                   //停止切换
                   
                   return false;
            }else{

                   //向右切换时，视图进和出的动画

                    img_vf.setInAnimation(getApplicationContext(), R.anim.right_in);
                    img_vf.setOutAnimation(getApplicationContext(), R.anim.left_out);
                    ViewChange(img_vf.getDisplayedChild()+1);
                     img_vf.showNext(); 
            }
          return true;
		} 
		else if (e1.getX() - e2.getX() < -120) {  
          if(img_vf.getDisplayedChild() == 0){//如果向前滑动到第一个
                 img_vf.stopFlipping();                    //停止切换
               return false;
          }else{

                  //向左切换时，视图进和出的动画

                 img_vf.setInAnimation(getApplicationContext(), R.anim.left_in);
                 img_vf.setOutAnimation(getApplicationContext(), R.anim.right_out);
                  ViewChange(img_vf.getDisplayedChild()-1);
                  img_vf.showPrevious();     
          }
         return true;
		}
		return false;
	}
	
	public void  ViewChange(int position) {
        if(position < 0 || position > m_nPages -1 || m_currentImg == position) {
                     return;
          }
        indicators[m_currentImg].setEnabled(false);//当前的属性改为false
        indicators[position].setEnabled(true);//要切换过去的img属性改为true
        m_currentImg = position;
        
        if(!isNotFirstTime)
        	m_BtnStart.setVisibility(View.VISIBLE);
	}

	@Override
	public void OnDisplayChildChanging(ViewFlipper view, int index) {
		indicators[m_currentImg].setEnabled(false);//当前的属性改为false
        indicators[index].setEnabled(true);//要切换过去的img属性改为true
        m_currentImg = index;
        
        
		
	}

	

}
