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
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Toast;

import java.util.Locale;

import com.auroratechdevelopment.ausoshare.CustomApplication;
import com.auroratechdevelopment.ausoshare.R;
import com.auroratechdevelopment.ausoshare.ui.ActivityBase;
import com.auroratechdevelopment.ausoshare.ui.home.HomeActivity;
import com.auroratechdevelopment.ausoshare.ui.login.LoginActivity;
import com.auroratechdevelopment.ausoshare.util.AppLocationService;
import com.auroratechdevelopment.ausoshare.util.CheckNetworkStatus;
import com.auroratechdevelopment.ausoshare.util.LocationAddress;
import com.auroratechdevelopment.common.ViewUtils;
import com.auroratechdevelopment.common.util.LoadNetPicture;
import com.auroratechdevelopment.common.webservice.WebServiceConstants;

public class SplashActivity extends Activity {

    public static final int SPLASH_SECOND = 2;
    boolean isNotFirstTime = true;
    private AppLocationService appLocationService;
    private ImageView splashAdImage;
    public CheckNetworkStatus checkNetworkStatus;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splashAdImage = (ImageView)findViewById(R.id.splash_ad_image);
        
        
        checkNetworkStatus = new CheckNetworkStatus(this);

        if (checkNetworkStatus.getNetworkStatus()) {
        	splashAdImage.setScaleType(ScaleType.FIT_CENTER);
        	new LoadNetPicture().getPicture(WebServiceConstants.splashAdImageURL, splashAdImage);
        }else{
        	splashAdImage.setScaleType(ScaleType.CENTER_INSIDE);
        	splashAdImage.setImageDrawable(getResources().getDrawable(R.drawable.splash_share));
        }

        isNotFirstTime = CustomApplication.getInstance().getNotFirstTimeUse();
        
        getDeviceLocation();
        
        
        //is not the first time, means from the second times
//        if(isNotFirstTime){
        	new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                    showHomeOrLogin();
                }
            }, SPLASH_SECOND * 1000);
//        }
        //is the first time
//        else{
//        	CustomApplication.getInstance().setNotFirstTimeUse(true);
//        	showFirstTimeUseGuide();
//        }

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
    	
    	ViewUtils.startPageWithClearStack(null, this, HomeActivity.class);
    	
//        if(CustomApplication.getInstance().getRememberMeChecked()){
//            ViewUtils.startPageWithClearStack(null, this, HomeActivity.class);
//        }
//        else{
//            ViewUtils.startPageWithClearStack(null, this, LoginActivity.class);
//        }


    }

}
