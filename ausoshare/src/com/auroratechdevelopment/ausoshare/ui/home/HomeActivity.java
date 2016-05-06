package com.auroratechdevelopment.ausoshare.ui.home;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.auroratechdevelopment.ausoshare.CustomApplication;
import com.auroratechdevelopment.ausoshare.R;
import com.auroratechdevelopment.ausoshare.ui.ActivityBase;
import com.auroratechdevelopment.ausoshare.ui.contact.ContactFragment;
import com.auroratechdevelopment.ausoshare.ui.entertainment.EntertainmentFragment;
import com.auroratechdevelopment.ausoshare.ui.ext.CustomAlertDialog;
import com.auroratechdevelopment.ausoshare.ui.login.LoginActivity;
import com.auroratechdevelopment.ausoshare.ui.profile.CurrentIncomeActivity;
import com.auroratechdevelopment.ausoshare.ui.profile.ProfileFragment;
import com.auroratechdevelopment.ausoshare.ui.profile.Top10IncomeAdapter;
import com.auroratechdevelopment.ausoshare.ui.yellowpage.YellowPageFragment;
import com.auroratechdevelopment.ausoshare.util.AppLocationService;
import com.auroratechdevelopment.ausoshare.util.Constants;
import com.auroratechdevelopment.ausoshare.util.LocationAddress;
import com.auroratechdevelopment.ausoshare.util.PushHelper;
import com.auroratechdevelopment.common.ViewUtils;
import com.auroratechdevelopment.common.ui.ViewPagerEx;
import com.auroratechdevelopment.common.webservice.WebServiceHelper;
import com.auroratechdevelopment.common.webservice.models.AdDataItem;
import com.auroratechdevelopment.common.webservice.models.Top10Item;
import com.auroratechdevelopment.common.webservice.models.UserInfo;
import com.auroratechdevelopment.common.webservice.response.CurrentIncomeResponse;
import com.auroratechdevelopment.common.webservice.response.GetOnGoingAdListResponse;
import com.auroratechdevelopment.common.webservice.response.GetOnGoingEntertainmentListResponse;
import com.auroratechdevelopment.common.webservice.response.ResponseBase;
import com.auroratechdevelopment.common.webservice.response.WithdrawRequestResponse;


/**
 * Created by happy pan on 2015/10/29.
 * updated by Raymond Zhuang 2016/4/22
 */
public class HomeActivity extends ActivityBase implements
        PushHelper.PushHelperListener
{
	
    private ViewPagerEx pager;
    private MyPagerAdapter adapter;

    private ImageButton ibtnHome;
    private ImageButton ibtnYellowPage;
    private ImageButton ibtnContact;
    private ImageButton ibtnProfile;
    private ImageButton ibtnEntertainment;
    private ImageButton ibtnPromotion;

    private AppLocationService appLocationService;
    
    private static final int REQUEST_PICK_PICTURE = 0xaf;

    private int iLastTab = -1;

    private boolean backPress;
    private PushHelper pushHelper;

    
    public interface HomeAdListUpdated {
    	public void onHomeAdListUpdated(int tag, GetOnGoingAdListResponse response);
    }
    
    public interface HomeEntertainmentListUpdated{
    	public void onHomeEntertainmentListUpdated(int tag, GetOnGoingEntertainmentListResponse response);
    }
    
    public interface HomeWithdrawUpdated{
    	public void onHomeWithdrawUpdated(int tag, WithdrawRequestResponse response);
    }
    
    public interface HomeIncomeUpdated{
    	public void onHomeCurrentIncomeUpdated(int tag, CurrentIncomeResponse response);
    }
    
    public interface HomeAvatarUpdated{
    	public void onHomeAvatarUpdated(Bitmap data);
    }
    
    private HomeAdListUpdated HomeAdListUpdatedListener;
    private HomeEntertainmentListUpdated HomeEntertainmentListUpdatedListener;
    private HomeWithdrawUpdated HomeWithdrawUpdatedListener;
    
    private HomeIncomeUpdated HomeIncomeUpdatedListener;
    private HomeAvatarUpdated HomeAvatarUpdatedListener;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void setListener() {
        WebServiceHelper.getInstance().setListener(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    
    //test here, April.18, 2016
    
    @Override
    protected void setView() {
        setContentView(R.layout.activity_main);

        getDeviceId();
        getDeviceLocation();

        pager = (ViewPagerEx) findViewById(R.id.pager);
        pager.setPagingEnabled(false);

        adapter = new MyPagerAdapter(getSupportFragmentManager());

        pager.setAdapter(adapter);

        final int pageMargin = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics()
        );
        pager.setPageMargin(pageMargin);
        pager.setOffscreenPageLimit(3); //adapter.TITLES.length);


        DisplayMetrics dm = new DisplayMetrics();
        //get window parameter
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        //get window width
        int halfScreenWidth = (dm.widthPixels - 30) / 2;
        CustomApplication.getInstance().setScreenWidth(halfScreenWidth);

        ibtnHome = (ImageButton) findViewById(R.id.bottomtab_home);
        ibtnYellowPage = (ImageButton) findViewById(R.id.bottomtab_yellow_page);
        ibtnContact = (ImageButton) findViewById(R.id.bottomtab_contact);
        ibtnProfile = (ImageButton) findViewById(R.id.bottomtab_profile);
        ibtnEntertainment = (ImageButton)findViewById(R.id.bottomtab_entertainment);

        
        pushHelper = new PushHelper(this, this);

        Intent urlIntent = getIntent();
        String lastPage = urlIntent.getStringExtra(Constants.LAST_PAGE);
        if(lastPage != null){
            if(lastPage.equals(Constants.CONTACT_PAGE)){
                pager.setCurrentItem(Constants.FRAG_CONTACT);
                iLastTab = Constants.FRAG_CONTACT;
            }
            else if (lastPage.equals(Constants.MINE_PAGE)){
                pager.setCurrentItem(Constants.FRAG_PROFILE);
                iLastTab = Constants.FRAG_PROFILE;
            }
            else if (lastPage.equals(Constants.ENTERTAINMENT_PAGE)){
            	pager.setCurrentItem(Constants.FRAG_ENTERTAINMENT);
            	iLastTab = Constants.FRAG_ENTERTAINMENT;
            	
            	Log.e("Edward", "from entertainment");
            }
            else if(lastPage.equals(Constants.PROMOTION_PAGE)){
            	pager.setCurrentItem(Constants.FRAG_PROMOTION);
            	iLastTab = Constants.FRAG_PROMOTION;
            }
            else {
                pager.setCurrentItem(Constants.FRAG_HOME);
                iLastTab = Constants.FRAG_HOME;
                Log.e("Edward", "else Home");
            }
            
        }
        else{
            pager.setCurrentItem(Constants.FRAG_HOME);
            iLastTab = Constants.FRAG_HOME;
            Log.e("Edward", "else else home");
        }
        
        getRegisteredUserInfo();
        setBottomBarSelected(iLastTab);
        
        fetchCurrentIncome();
        
    }
    
    private void setBottomBarSelected(int last_tab){
    	ibtnHome.setSelected(false);
    	
    	if(last_tab == Constants.FRAG_HOME){
        	ibtnHome.setSelected(true);
        }
        else if(last_tab == Constants.FRAG_CONTACT){
        	ibtnContact.setSelected(true);
        }
        else if(last_tab == Constants.FRAG_PROFILE){
        	ibtnProfile.setSelected(true);
        }
        else if(last_tab == Constants.FRAG_YELLOW_PAGE){
        	ibtnYellowPage.setSelected(true);
        }
        else if(last_tab == Constants.FRAG_ENTERTAINMENT){
        	ibtnEntertainment.setSelected(true);
        }
        else if(last_tab == Constants.FRAG_PROMOTION){
        	ibtnPromotion.setSelected(true);
        }
        else{
        	ibtnHome.setSelected(true);
        }
    }

    public void getRegisteredUserInfo(){
        String user_name = CustomApplication.getInstance().getUsername();
        String available_fund = CustomApplication.getInstance().getAvailableFund();
        available_fund = available_fund.equals("")? "0.00":available_fund;

        setTopBarTitle(CustomApplication.getInstance().getUsername() +
                getResources().getString(R.string.title_infinity_cellphone) + available_fund
        );

    }

    protected void getDeviceId(){
        String android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        CustomApplication.getInstance().setAndroidID(android_id);
    }

    public void getDeviceLocation(){
        appLocationService = new AppLocationService(HomeActivity.this);

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
//            System.out.print("abcde:" + locationAddress);
        }
    }
    
    public void resetBottomSelected(){
    	ibtnHome.setSelected(false);
        ibtnYellowPage.setSelected(false);
        ibtnContact.setSelected(false);
        ibtnProfile.setSelected(false);
        ibtnEntertainment.setSelected(false);
    }

    public void onBottombarItemClicked(View v) {

        if (v.isSelected()) {
            v.setSelected(false);
        } else {
//            ViewUtils
//                    .setBackgroundDrawable(
//                            v,
//                            getResources()
//                                    .getDrawable(
//                                            R.drawable.bottombar_button_background_selected));
            if(CustomApplication.getInstance().getEmail().equalsIgnoreCase("") && v.getId()==R.id.bottomtab_profile){
            	
            }else{
            	ViewUtils.setBackgroundDrawable(ibtnHome, null);
                ViewUtils.setBackgroundDrawable(ibtnEntertainment, null);
                ViewUtils.setBackgroundDrawable(ibtnYellowPage, null);
                ViewUtils.setBackgroundDrawable(ibtnContact, null);
                ViewUtils.setBackgroundDrawable(ibtnProfile, null);

                resetBottomSelected();
            	v.setSelected(true);
            }
        }

        switch (v.getId()) {
            case R.id.bottomtab_home:
                showHome();
                break;

            case R.id.bottomtab_yellow_page:
                showYellowPage();
                break;

            case R.id.bottomtab_contact:
                showContact();
                break;

            case R.id.bottomtab_profile:
                showProfile();
                break;

            case R.id.bottomtab_entertainment:
            	showEntertainment();
            	break;
            	
            default:
                break;
        }
    }


    @Override
    public void onBackPressed() {
        if (iLastTab > -1) {
            switch (iLastTab) {
                case Constants.FRAG_HOME:
                    showHome();
                    break;

                case Constants.FRAG_YELLOW_PAGE:
                    showYellowPage();
                    break;

                case Constants.FRAG_CONTACT:
                    showContact();
                    break;

                case Constants.FRAG_PROFILE:
                    showProfile();
                    break;
                    
                case Constants.FRAG_ENTERTAINMENT:
                	showEntertainment();
                	break;

                default:
                    break;
            }
            return;
        }

        if (iLastTab == -1) {
            if (!backPress) {
                backPress = true;
                showToast("Press back button again to exit the app.");
            } else {
                backPress = false;
                finish();
                return;
            }

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    backPress = false;
                }
            }, 2 * 1000);
        }

        // super.onBackPressed();
    }

    public void showHome() {
        backPress = false;
        iLastTab = Constants.FRAG_HOME;
        showBackButton(false);

        setTopBarTitle(CustomApplication.getInstance().getUsername() + getResources().getString(R.string.title_infinity_cellphone)
                + String.valueOf(CustomApplication.getInstance().getAvailableFund().equals("") ? "0.00" : CustomApplication.getInstance().getAvailableFund()));

        pager.setCurrentItem(Constants.FRAG_HOME);
        setBottomBarSelected(iLastTab);
    }


    public void showYellowPage(){
        backPress = false;
        iLastTab = Constants.FRAG_YELLOW_PAGE;
        showBackButton(false);
        setTopBarTitle(getResources().getString(R.string.title_ausomedia_yellow_page_text));
        pager.setCurrentItem(Constants.FRAG_YELLOW_PAGE);
        setBottomBarSelected(iLastTab);
    }


    public void showContact() {
        backPress = false;
        iLastTab = Constants.FRAG_CONTACT;
        setBottomBarSelected(iLastTab);
        showBackButton(false);
        setTopBarTitle(getResources().getString(R.string.title_infinity_cellphone3));
        pager.setCurrentItem(Constants.FRAG_CONTACT);
    }

    public void showProfile() { 
    	if(CustomApplication.getInstance().getEmail().equalsIgnoreCase("")){
			requestUserLogin();
    	}
    	else{
	        backPress = false;
	        iLastTab = Constants.FRAG_PROFILE;
	        showBackButton(false);
	        setTopBarTitle(getResources().getString(R.string.title_infinity_cellphone4));
	        pager.setCurrentItem(Constants.FRAG_PROFILE);
	        setBottomBarSelected(iLastTab);
    	}
    }
    //userlogin add by Raymond temporarily
    private void requestUserLogin(){
		Log.e("Edward", "Did not loginnnn");
		showCenterScreenOkCancelAlert(this,
                getResources().getString(R.string.login_prompt_text),
                getResources().getString(R.string.alert_share_ad_login_prompt_text),
                getString(R.string.button_ok), getString(R.string.button_cancel), null, true
				);
    }
    public CustomAlertDialog customADialog;
    public void showCenterScreenOkCancelAlert(final Context context,
            final CharSequence alertTitle, final String msg,
            final String okButtonName, final String cancelButtonName,
            final CustomAlertDialog.AlertCallback callback, final boolean isCancelable) {

    	Log.e("Edward", "showCenterScreenAlert");
    	
        this.runOnUiThread(new Runnable() {
            public void run() {
                dismissCustomAlertDialog();
                ((HomeActivity) context).customADialog = new CustomAlertDialog(
                        context, R.style.alertStyle);
                customADialog.setContentView(R.layout.alert_center_screen);
                customADialog.setMessage(msg, R.id.alert_message);
                customADialog.setTitle(alertTitle, R.id.alert_title);
                customADialog.setOnCancelListener(alertCancelClicked(callback));
                customADialog.setOnDismissListener(alertDismissClicked());
                if (okButtonName != null && okButtonName.length() > 0) {
                	customADialog.setButton(okButtonName, alertButtonOkayClicked(callback));
                    
                }

                if (cancelButtonName != null && cancelButtonName.length() > 0) {
                	customADialog.setButton2(cancelButtonName,
                            alertButtonCancelClicked(callback));
                }

                customADialog.setCancelable(isCancelable);
                customADialog.show();
            }
        });
    }
    protected DialogInterface.OnClickListener alertButtonCancelClicked(
            final CustomAlertDialog.AlertCallback callback) {
        return new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                try {
                    callback.GetAlertButton(which);
                    
                } catch (Exception e) {
                    // e.printStackTrace();
                }
                
                return;
            }
        };
    }
    protected DialogInterface.OnClickListener alertButtonOkayClicked(
            final CustomAlertDialog.AlertCallback callback) {
        return new android.content.DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                try {
                    callback.GetAlertButton(which);
                } catch (Exception e) {
                    // e.printStackTrace();
                }
                
                final Bundle bundle = new Bundle(); 
                bundle.putString(Constants.LAST_PAGE, Constants.HOME_PAGE);
            	ViewUtils.startPage(bundle, HomeActivity.this, LoginActivity.class);

            }
        };
    }

    
    public void showEntertainment(){
    	backPress = false;
    	iLastTab = Constants.FRAG_ENTERTAINMENT;
    	showBackButton(false);
    	setTopBarTitle(getResources().getString(R.string.title_infinity_cellphone4));
    	pager.setCurrentItem(Constants.FRAG_ENTERTAINMENT);
    	setBottomBarSelected(iLastTab);
    }

    @Override
    protected void viewInitialized() {
        if (btnBack != null) {
            btnBack.setVisibility(View.GONE);
        }
        setTopBarTitle(getResources().getString(R.string.title_infinity_cellphone));
    }


    public class MyPagerAdapter extends FragmentStatePagerAdapter {

        public final String[] TITLES = {getResources().getString(R.string.tab_title_home), 
        		getResources().getString(R.string.tab_title_entertainment),
        		getResources().getString(R.string.tab_title_yellowpage), 
        		getResources().getString(R.string.tab_title_contact),
        		getResources().getString(R.string.tab_title_profile)};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public Fragment getItem(int position) {
            HomeFragmentBase fg;
            switch (position) {

                case Constants.FRAG_HOME:
                    fg = new HomeFragment();
                    fg.setHomeActivity(HomeActivity.this);
                    HomeActivity.this.HomeAdListUpdatedListener = (HomeFragment) fg;
                    
                    return fg;

                case Constants.FRAG_YELLOW_PAGE:
                    fg = new YellowPageFragment();
                    fg.setHomeActivity(HomeActivity.this);
                    return fg;

                case Constants.FRAG_CONTACT:
                    fg = new ContactFragment();
                    fg.setHomeActivity(HomeActivity.this);
                    return fg;

                case Constants.FRAG_PROFILE:
                    fg = new ProfileFragment();
                    fg.setArguments(getIntent().getExtras());
                    fg.setHomeActivity(HomeActivity.this);
                    HomeActivity.this.HomeWithdrawUpdatedListener = (ProfileFragment) fg;
                    HomeActivity.this.HomeIncomeUpdatedListener = (ProfileFragment) fg;
                    HomeActivity.this.HomeAvatarUpdatedListener = (ProfileFragment) fg;
                    return fg;
                    
                case Constants.FRAG_ENTERTAINMENT:
                	fg = new EntertainmentFragment();
                	fg.setHomeActivity(HomeActivity.this);
                	HomeActivity.this.HomeEntertainmentListUpdatedListener = (EntertainmentFragment) fg;
                	return fg;

                default:
                    fg = new HomeFragment();
                    fg.setHomeActivity(HomeActivity.this);
                    return fg;
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        CustomApplication.getInstance().setHomeActivity(this);
    }

    @Override
    public void DeviceRegisterSuccessCallBack() {
        String userName = CustomApplication.getInstance().getUsername();
        String deviceToken = pushHelper.getRegId();
//        /WebServiceHelper.getInstance().pushRegister(userName, deviceToken);
    }

    @Override
    public void ResponseFailedCallBack(int tag, final ResponseBase response) {
        dismissWaiting();

        super.ResponseFailedCallBack(tag, response);
    }


    @Override
    public void ResponseSuccessCallBack(int tag, final ResponseBase response) {
//        dismissWaiting();
        if (response instanceof GetOnGoingAdListResponse) {
            if (HomeAdListUpdatedListener != null) {
            	HomeAdListUpdatedListener.onHomeAdListUpdated(tag, (GetOnGoingAdListResponse) response);
            }
        }
        else if (response instanceof GetOnGoingEntertainmentListResponse) {
            if (HomeEntertainmentListUpdatedListener != null) {
            	HomeEntertainmentListUpdatedListener.onHomeEntertainmentListUpdated(tag, (GetOnGoingEntertainmentListResponse) response);
            }
        }
        else if (response instanceof WithdrawRequestResponse) {
        	if (HomeWithdrawUpdatedListener != null){
        		HomeWithdrawUpdatedListener.onHomeWithdrawUpdated(tag, (WithdrawRequestResponse) response);
        	}
        }
        else if (response instanceof CurrentIncomeResponse) {
        	
        	if (HomeIncomeUpdatedListener != null){
        		HomeIncomeUpdatedListener.onHomeCurrentIncomeUpdated(tag, (CurrentIncomeResponse) response);
        	}
            
        }
        
    }
        
    
    private HomeFragmentBase getCurrentFragment() {
        FragmentStatePagerAdapter a = (FragmentStatePagerAdapter) pager.getAdapter();
        return (HomeFragmentBase) a.instantiateItem(pager, pager.getCurrentItem());
    }
    
    /**
     * return back button
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {  
            exitBy2Click();        //invoke the double click function
        }
        return false;
    }
    
    private void hideSoftBoard(){
        InputMethodManager inputmanger = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        if ( inputmanger.isActive( ) ) {     
        	inputmanger.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
        }

    }
    
    /**
     * double click 
     */
    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // ready to exit
            Toast.makeText(this, getResources().getString(R.string.double_click_exit_text), Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // cancel exit
                }
            }, 2000); 
        } else {
            finish();
            System.exit(0);
        }
    }
    
    public void fetchCurrentIncome(){
    	if(CustomApplication.getInstance().getLoginOutStatus()){
    	
	        WebServiceHelper.getInstance().getCurrentIncome(CustomApplication.getInstance().getEmail(),
	                        CustomApplication.getInstance().getAndroidID(),
	                        Constants.CURRENT_INCOME_TOP_USER_MAXNUMBER);
	        
    	}
        
    }
    
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
        case REQUEST_PICK_PICTURE:
            if (resultCode != Activity.RESULT_OK) {
                return;
            }else{
            	/*Bundle extras = data.getExtras();
        		if (extras != null) {
        			Bitmap photo = extras.getParcelable("data");
        			if (HomeAvatarUpdatedListener != null){
        				HomeAvatarUpdatedListener.onHomeAvatarUpdated(data);
        			}
        			
        			UploadAvatar(photo);
        		}*/
            	try{
	            	Uri uritempFile = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory().getPath() + "/" + Constants.TMP_CLIP_IMG);
	            	Bitmap photo = BitmapFactory.decodeStream(getContentResolver().openInputStream(uritempFile)); 
	            	if (HomeAvatarUpdatedListener != null){
        				HomeAvatarUpdatedListener.onHomeAvatarUpdated(photo);
        			}
	            	UploadAvatar(photo);
            	}catch(Exception e){
            		e.printStackTrace();
            		   return;
            	}
            }
            break;

        default:
            break;
        }
        
      }
    
    
    public void UploadAvatar(Bitmap src){
    	String img_base64 = Bitmap2StrByBase64(src);
    	
        WebServiceHelper.getInstance().UploadAvatar(CustomApplication.getInstance().getAndroidID(),
        		CustomApplication.getInstance().getEmail(),img_base64);
        //showWaiting();
    }
    
    /** 
	 * 通过Base32将Bitmap转换成Base64字符串 
	 * @param bit 
	 * @return 
	 */  
	public String Bitmap2StrByBase64(Bitmap bit){  
	   ByteArrayOutputStream bos=new ByteArrayOutputStream();  
	   bit.compress(CompressFormat.JPEG, 40, bos);//参数100表示不压缩  
	   byte[] bytes=bos.toByteArray();  
	   return Base64.encodeToString(bytes, Base64.DEFAULT);  
	}
	
	
    
    
}