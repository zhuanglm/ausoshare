package com.auroratechdevelopment.ausoshare.ui.profile;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.auroratechdevelopment.ausoshare.CustomApplication;
import com.auroratechdevelopment.ausoshare.R;
import com.auroratechdevelopment.ausoshare.ui.ActivityBase;
import com.auroratechdevelopment.ausoshare.ui.contact.ContactURLActivity;
import com.auroratechdevelopment.ausoshare.ui.ext.CustomAlertDialog;
import com.auroratechdevelopment.ausoshare.ui.ext.EdwardAlertDialog;
import com.auroratechdevelopment.ausoshare.ui.home.HomeActivity;
import com.auroratechdevelopment.ausoshare.ui.home.HomeActivity.HomeAvatarUpdated;
import com.auroratechdevelopment.ausoshare.ui.home.HomeActivity.HomeIncomeUpdated;
import com.auroratechdevelopment.ausoshare.ui.home.HomeFragmentBase;
import com.auroratechdevelopment.ausoshare.ui.home.PrepareShareAdActivity;
import com.auroratechdevelopment.ausoshare.ui.login.LoginActivity;
import com.auroratechdevelopment.ausoshare.ui.login.RegisterActivity;
import com.auroratechdevelopment.ausoshare.ui.photopicker.PhotoPickerActivity;
import com.auroratechdevelopment.ausoshare.util.Constants;
import com.auroratechdevelopment.common.ViewUtils;
import com.auroratechdevelopment.common.util.FileUtils;
import com.auroratechdevelopment.common.webservice.WebService;
import com.auroratechdevelopment.common.webservice.WebServiceConstants;
import com.auroratechdevelopment.common.webservice.WebServiceHelper;
import com.auroratechdevelopment.common.webservice.WebServiceHelper.WebServiceListener;
import com.auroratechdevelopment.common.webservice.models.Top10Item;
import com.auroratechdevelopment.common.webservice.request.ForwardedAdHistoryRequest;
import com.auroratechdevelopment.common.webservice.response.CurrentIncomeResponse;
import com.auroratechdevelopment.common.webservice.response.ForwardedAdHistoryResponse;
import com.auroratechdevelopment.common.webservice.response.GetOnGoingEntertainmentListResponse;
import com.auroratechdevelopment.common.webservice.response.GetURLResponse;
import com.auroratechdevelopment.common.webservice.response.ResponseBase;
import com.auroratechdevelopment.common.webservice.response.WithdrawRequestResponse;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.w3c.dom.Text;

/**
 * Created by happy pan on 2015/10/30.
 * Updated by Raymond Zhuang 2016/4/25
 */
//public class ProfileFragment extends HomeFragmentBase implements
public class ProfileFragment extends HomeFragmentBase implements
    View.OnClickListener, 
    HomeActivity.HomeWithdrawUpdated,HomeIncomeUpdated, HomeAvatarUpdated
    {
    private RelativeLayout userProfileLayout;
    //private ImageView loginOutImageThumb,userImageThumb,myCurrentIncomeImage,myForwardedAdImage,myWithdrawRecordImage, myWithdrawRequestImage;
    //private TextView loginOutProfileText,userProfileText,myCurrentIncomeText,myForwardedAdText,myWithdrawRecordText,myWithdrawRequestText;
    private TextView m_tv_username,m_tv_asset,m_leaderboard,m_tV_shared,m_tV_withdraw_history,m_tV_withdraw_request;
    private TextView m_tV_shared_l,m_tV_withdraw_history_l,m_tV_withdraw_request_l;
    private LinearLayout m_layout_shared,m_layout_withdraw,m_layout_request;
    private Button m_btn_logout,m_btn_profile;
    private TextView m_tv_currentIncome;
    private ImageView m_img_avatar;
    private TextView m_tv_avatar;
    private Bitmap m_bm_Avatar;
    private CompoundButton m_Notification_switch;
    
    private static final int REQUEST_PICK_PICTURE = 0xaf;
    
    
    @Override
	public void onSaveInstanceState(Bundle outState) {
       super.onSaveInstanceState(outState);
       
       if(m_bm_Avatar != null){
    	   	
    	   	if(m_bm_Avatar.isRecycled() == false){
	    		m_bm_Avatar.recycle(); 
	 			m_bm_Avatar = null; 
	 			System.gc();
    		}
       }
       
    }
    
    @Override
    public void onDestroyView(){
    	super.onDestroyView();
    	
    	if(m_bm_Avatar != null){
    		
    		if(m_bm_Avatar.isRecycled() == false){
	    		m_bm_Avatar.recycle(); 
	 			m_bm_Avatar = null; 
	 			System.gc();
    		}
        }
    }
    
    /*@Override
    public void onActivityCreated(Bundle savedInstanceState) {
       super.onActivityCreated(savedInstanceState);
       
       if(m_bm_Avatar != null){
    	   m_img_avatar.setImageBitmap(m_bm_Avatar);
       }else if(FileUtils.isFileExist(CustomApplication.getInstance().getUsername(),"JPG")){
	       m_bm_Avatar = FileUtils.loadBitmap(CustomApplication.getInstance().getUsername(),"JPG");
	       m_img_avatar.setImageBitmap(m_bm_Avatar);
       }
       
    }*/
    
    public void onResume(){
    	super.onResume();
    	
    	if(m_bm_Avatar != null){
     	   m_img_avatar.setImageBitmap(m_bm_Avatar);
        }else if(FileUtils.isFileExist(CustomApplication.getInstance().getEmail(),"JPG")){
 	       m_bm_Avatar = FileUtils.loadBitmap(CustomApplication.getInstance().getEmail(),"JPG");
 	       m_img_avatar.setImageBitmap(m_bm_Avatar);
        }
    }
    

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_profile_v2, container,
                false);

        /*userProfileLayout = (RelativeLayout)rootView.findViewById(R.id.user_profile_layout);
        
        loginOutImageThumb = (ImageView)rootView.findViewById(R.id.login_out_image_thumb);
        loginOutProfileText = (TextView)rootView.findViewById(R.id.login_out_profile_text);
        userImageThumb = (ImageView)rootView.findViewById(R.id.user_image_thumb);
        userProfileText = (TextView)rootView.findViewById(R.id.user_profile_text);
        myCurrentIncomeText = (TextView)rootView.findViewById(R.id.my_current_income_tv);
        myForwardedAdText = (TextView)rootView.findViewById(R.id.my_forwarded_ad_tv);
        myWithdrawRecordText = (TextView)rootView.findViewById(R.id.my_withdraw_record_tv);
        myWithdrawRequestText = (TextView)rootView.findViewById(R.id.my_withdraw_request_tv);
        myCurrentIncomeImage = (ImageView)rootView.findViewById(R.id.my_current_income_image);
        myForwardedAdImage = (ImageView)rootView.findViewById(R.id.my_forwarded_ad_image);
        myWithdrawRecordImage = (ImageView)rootView.findViewById(R.id.my_withdraw_record_image);
        myWithdrawRequestImage = (ImageView)rootView.findViewById(R.id.my_withdraw_request_image);

        loginOutImageThumb.setOnClickListener(this);
        loginOutProfileText.setOnClickListener(this);
        userImageThumb.setOnClickListener(this);
        userProfileText.setOnClickListener(this);
        myCurrentIncomeText.setOnClickListener(this);
        myForwardedAdText.setOnClickListener(this);
        myWithdrawRecordText.setOnClickListener(this);
        myWithdrawRequestText.setOnClickListener(this);
        
        myCurrentIncomeImage.setOnClickListener(this);
        myForwardedAdImage.setOnClickListener(this);
        myWithdrawRecordImage.setOnClickListener(this);
        userProfileLayout.setOnClickListener(this);
        myWithdrawRequestImage.setOnClickListener(this);
                
        Intent loginIntent = getActivity().getIntent();
        boolean login_status_flag = loginIntent.getBooleanExtra(Constants.LOGIN_STATUS, false);
    	if(login_status_flag){
    		loginOutProfileText.setText(getResources().getString(R.string.my_log_out));
        }
        else{
        	loginOutProfileText.setText(getResources().getString(R.string.my_log_in));
        }

        
        if(CustomApplication.getInstance().getLoginOutStatus()){
        	loginOutProfileText.setText(getResources().getString(R.string.my_log_out));
        }else{
        	loginOutProfileText.setText(getResources().getString(R.string.my_log_in));
        }*/
        
        //for v2
        m_img_avatar = (ImageView)rootView.findViewById(R.id.img_avatar);
        m_tv_avatar = (TextView)rootView.findViewById(R.id.text_upload_avatar);
        m_img_avatar.setOnClickListener(this);
        m_tv_avatar.setOnClickListener(this);
                        
        m_tv_currentIncome = (TextView)rootView.findViewById(R.id.current_income_tV);
        m_tv_username = (TextView)rootView.findViewById(R.id.user_name_tV);
        //m_tv_asset = (TextView)rootView.findViewById(R.id.asset_tV);
        m_leaderboard = (TextView)rootView.findViewById(R.id.leaderboard_tV);
        m_tv_username.setText(CustomApplication.getInstance().getUsername());
        m_leaderboard.setOnClickListener(this);
        
        m_btn_logout=(Button)rootView.findViewById(R.id.button_logout);
        m_btn_profile=(Button)rootView.findViewById(R.id.button_profile);
        m_tV_shared=(TextView)rootView.findViewById(R.id.shared_history);
        m_tV_shared_l=(TextView)rootView.findViewById(R.id.textView1);
        m_tV_withdraw_history=(TextView)rootView.findViewById(R.id.withdraw_history);
        m_tV_withdraw_history_l=(TextView)rootView.findViewById(R.id.textView2);
        m_tV_withdraw_request=(TextView)rootView.findViewById(R.id.withdraw_request);
        m_tV_withdraw_request_l=(TextView)rootView.findViewById(R.id.textView3);
        m_layout_shared = (LinearLayout)rootView.findViewById(R.id.shared_history_layout);
        m_layout_withdraw = (LinearLayout)rootView.findViewById(R.id.withdraw_history_layout);
        m_layout_request = (LinearLayout)rootView.findViewById(R.id.withdraw_request_layout);
        m_btn_logout.setOnClickListener(this);
        m_btn_profile.setOnClickListener(this);
        m_layout_shared.setOnClickListener(this);
        m_layout_withdraw.setOnClickListener(this);
        m_layout_request.setOnClickListener(this);
        m_tV_shared.setOnClickListener(this);
        m_tV_shared_l.setOnClickListener(this);
        m_tV_withdraw_history.setOnClickListener(this);
        m_tV_withdraw_history_l.setOnClickListener(this);
        m_tV_withdraw_request.setOnClickListener(this);
        m_tV_withdraw_request_l.setOnClickListener(this);
        
        m_Notification_switch = ((CompoundButton)rootView.findViewById(R.id.noti_switch));
        m_Notification_switch.setChecked(CustomApplication.getInstance().getNotificationChecked());
        m_Notification_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                CustomApplication.getInstance().setNotificationChecked(isChecked);
            }
        });
        
        
        return rootView;
    }
    
    public void  SelectPhoto() {
    	
		final Bundle bundle = new Bundle(); 
        bundle.putString(Constants.LAST_PAGE, Constants.LOGIN_PAGE_FROM_LOGIN);
        ViewUtils.startPageForResult(bundle, getActivity(),REQUEST_PICK_PICTURE, PhotoPickerActivity.class);
    	//Intent intent = new Intent();
		//intent.setClass(getActivity(), PhotoPickerActivity.class);
    	//startActivityForResult(intent,REQUEST_PICK_PICTURE);
    
    }
    
        
    @Override
    public void onClick(View view){
        switch (view.getId()){
        	case R.id.button_logout:
        		Logout_transferToHome();
        		
        		break;
        		
        	case R.id.img_avatar:
        	case R.id.text_upload_avatar:
        		
        		SelectPhoto();
        		
        		break;
        		
	        case R.id.login_out_image_thumb:
	        case R.id.login_out_profile_text:
	        	if(CustomApplication.getInstance().getEmail().equalsIgnoreCase("")){
	        		transferToLogin();
            	}
            	else{
            		transferToLogout();
            	}
                break;
            case R.id.user_profile_layout:
            case R.id.user_image_thumb:
            case R.id.user_profile_text:
            case R.id.button_profile:
            	if(CustomApplication.getInstance().getEmail().equalsIgnoreCase("")){
            		ViewUtils.startPage(null, getActivity(), LoginActivity.class);
//            		getActivity().finish();
            	}
            	else{
            		transferToUserProfile();
            	}
                break;
            case R.id.my_current_income_tv:
            case R.id.my_current_income_image:
            case R.id.leaderboard_tV:
            	if(CustomApplication.getInstance().getEmail().equalsIgnoreCase("")){
            		ViewUtils.startPage(null, getActivity(), LoginActivity.class);
//            		getActivity().finish();
            	}
            	else{
            		getMyCurrentIncome();
            	}
                break;
            case R.id.my_forwarded_ad_tv:
            case R.id.my_forwarded_ad_image:
            case R.id.shared_history_layout:
            case R.id.shared_history:
            case R.id.textView1:
            	if(CustomApplication.getInstance().getEmail().equalsIgnoreCase("")){
            		ViewUtils.startPage(null, getActivity(), LoginActivity.class);
//            		getActivity().finish();
            	}
            	else{
            		getMyForwardedAd();
            	}
                break;
            case R.id.my_withdraw_record_tv:
            case R.id.my_withdraw_record_image:
            case R.id.withdraw_history_layout:
            case R.id.withdraw_history:
            case R.id.textView2:
            	if(CustomApplication.getInstance().getEmail().equalsIgnoreCase("")){
            		ViewUtils.startPage(null, getActivity(), LoginActivity.class);
//            		getActivity().finish();
            	}
            	else{
            		getMyWithDrawRecord();
            	}
                break;
            case R.id.my_withdraw_request_tv:
            case R.id.my_withdraw_request_image:
            case R.id.withdraw_request_layout:
            case R.id.withdraw_request:
            case R.id.textView3:
            	if(CustomApplication.getInstance().getEmail().equalsIgnoreCase("")){
            		ViewUtils.startPage(null, getActivity(), LoginActivity.class);
//            		getActivity().finish();
            	}
            	else{
            		withdrawRequest();
            	}
            default:
                break;
        }
    }

    private void transferToLogin(){
        final Bundle bundle = new Bundle(); 
        bundle.putString(Constants.LAST_PAGE, Constants.LOGIN_PAGE_FROM_LOGIN);
        
    	ViewUtils.startPage(bundle, getActivity(), LoginActivity.class);

    }
    
    private void Logout_transferToHome(){
    	final EdwardAlertDialog ad = new EdwardAlertDialog(getActivity());
    	ad.setTitle(getResources().getString(R.string.my_logout_info_title));
    	ad.setMessage(getResources().getString(R.string.my_logout_info_content));
    	ad.setPositiveButton(getResources().getString(R.string.button_ok), new OnClickListener() { 
    	@Override                  
    	public void onClick(View v) {
    	    // TODO Auto-generated method stub
    	    ad.dismiss();
    	    CustomApplication.getInstance().setEmail("");
            CustomApplication.getInstance().setLoginOutStatus(false);
            
            HomeActivity pA = (HomeActivity)getActivity();
    		pA.resetBottomSelected();
    		pA.showHome();

            //loginOutProfileText.setText(getResources().getString(R.string.my_log_in));
//    	    Toast.makeText(Test.this, "被点到确定", Toast.LENGTH_LONG).show();        
    	}
    	}); 
    	ad.setNegativeButton(getResources().getString(R.string.button_cancel), new OnClickListener() { 
    	@Override                  
    	public void onClick(View v) {
    	// TODO Auto-generated method stub
    	ad.dismiss();
//    	Toast.makeText(Test.this, "被点到取消", Toast.LENGTH_LONG).show();
    	}
    	});
    	
    }
    
    private void transferToLogout(){
    	final EdwardAlertDialog ad = new EdwardAlertDialog(getActivity());
    	ad.setTitle(getResources().getString(R.string.my_logout_info_title));
    	ad.setMessage(getResources().getString(R.string.my_logout_info_content));
    	ad.setPositiveButton(getResources().getString(R.string.button_ok), new OnClickListener() { 
    	@Override                  
    	public void onClick(View v) {
    	    // TODO Auto-generated method stub
    	    ad.dismiss();
    	    CustomApplication.getInstance().setEmail("");
            CustomApplication.getInstance().setLoginOutStatus(false);
            //loginOutProfileText.setText(getResources().getString(R.string.my_log_in));
//    	    Toast.makeText(Test.this, "被点到确定", Toast.LENGTH_LONG).show();        
    	}
    	}); 
    	ad.setNegativeButton(getResources().getString(R.string.button_cancel), new OnClickListener() { 
    	@Override                  
    	public void onClick(View v) {
    	// TODO Auto-generated method stub
    	ad.dismiss();
//    	Toast.makeText(Test.this, "被点到取消", Toast.LENGTH_LONG).show();
    	}
    	});
    	
    }
    
    private void withdrawRequest(){
    	WebServiceHelper.getInstance().withdrawRequest(
                CustomApplication.getInstance().getAndroidID(),
                CustomApplication.getInstance().getEmail()
        );
    }
    
    protected void getMyCurrentIncome(){
        getActivity().startActivity(new Intent(getActivity(),CurrentIncomeActivity.class));
        getActivity().finish();
    }

    protected void getMyForwardedAd(){
        Intent urlIntent = new Intent();
        urlIntent.putExtra(Constants.CONTACT_URL, WebServiceConstants.statsForwardedAdHistory);
        urlIntent.setClass(getActivity(), ProfileURLActivity.class);

        getActivity().startActivity(urlIntent);
        getActivity().finish();
    }

    protected void getMyWithDrawRecord(){
        Intent urlIntent = new Intent();
        urlIntent.putExtra(Constants.CONTACT_URL, WebServiceConstants.statsWithdrawHistory);
        urlIntent.setClass(getActivity(), ProfileURLActivity.class);

        getActivity().startActivity(urlIntent);
        getActivity().finish();
    }


    protected void transferToUserProfile(){	
    	startActivity(new Intent(getActivity(), UpdateProfileActivity.class));
        getActivity().finish();
    }

    
    

	@Override
	public void onHomeWithdrawUpdated(int tag, final WithdrawRequestResponse response) {
		Log.e("Edward", "onHomeWithdrawUpdated ****");
        if(homeActivity != null){
            homeActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                	homeActivity.showAlert(getActivity(),
                            getResources().getString(R.string.my_withdraw_request),
                            response.responseMessage);
                }
            });
        }
		
	}

	@Override
	public void onHomeCurrentIncomeUpdated(int tag, CurrentIncomeResponse response) {
		final CurrentIncomeResponse currentIncome = response;
		
		if(homeActivity != null){
            homeActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                	if(isAdded()){
                		m_tv_currentIncome.setText(getResources().getString(R.string.ui_current_income)+
                            currentIncome.data.currentIncome);
                	}
                }
            });
        }
		
	}
	

	@Override
	public void onHomeAvatarUpdated(Bitmap data) {
		/*Bundle extras = data.getExtras();
		if (extras != null) {
			m_bm_Avatar = extras.getParcelable("data");
			m_img_avatar.setImageBitmap(m_bm_Avatar);
			
			
		}*/
		
		if (data != null) {
			m_bm_Avatar = data;
			m_img_avatar.setImageBitmap(m_bm_Avatar);
		}
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		
		if(m_bm_Avatar != null && !m_bm_Avatar.isRecycled()){ 
        // 回收并且置为null
		m_bm_Avatar.recycle(); 
		m_bm_Avatar = null; 
		} 
		System.gc();
		
	}
	

}
