package com.auroratechdevelopment.ausoshare.ui.profile;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import com.auroratechdevelopment.ausoshare.CustomApplication;
import com.auroratechdevelopment.ausoshare.R;
import com.auroratechdevelopment.ausoshare.ui.ActivityBase;
import com.auroratechdevelopment.ausoshare.ui.contact.ContactURLActivity;
import com.auroratechdevelopment.ausoshare.ui.home.HomeActivity;
import com.auroratechdevelopment.ausoshare.ui.home.OnGoingAdItemsAdapter;
import com.auroratechdevelopment.ausoshare.util.Constants;
import com.auroratechdevelopment.ausoshare.validation.EmailValidator;
import com.auroratechdevelopment.ausoshare.validation.UsernameValidator;
import com.auroratechdevelopment.common.ViewUtils;
import com.auroratechdevelopment.common.webservice.WebServiceHelper;
import com.auroratechdevelopment.common.webservice.models.AdDataItem;
import com.auroratechdevelopment.common.webservice.models.Top10Item;
import com.auroratechdevelopment.common.webservice.response.CurrentIncomeResponse;
import com.auroratechdevelopment.common.webservice.response.GetOnGoingAdListResponse;
import com.auroratechdevelopment.common.webservice.response.ResponseBase;
import com.auroratechdevelopment.common.webservice.response.UpdatePasswordResponse;
import com.auroratechdevelopment.common.webservice.response.UpdateUserProfileResponse;
//import com.tencent.mm.sdk.platformtools.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by happy pan on 2015/11/12.
 * Updated by Raymond Zhuang 2016/4/28
 */
public class UpdateProfileActivity extends ActivityBase implements OnClickListener {

    //private ListView top10List;
    //private Top10IncomeAdapter adapter;

    private TextView currentIncomeText;
    
    private TextView textTitle;
    private ImageView headerTitleImage;
    
    
    private TextView textUsername, textRegisterEmail,m_tv_pwd,m_tv_old_pwd,m_tv_new_pwd,m_tv_confirm_pwd;
    private RelativeLayout m_layout_bk;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setView() {
        setContentView(R.layout.activity_update_profile);
        
        m_layout_bk = (RelativeLayout)findViewById(R.id.profile_bg);
        m_layout_bk.setOnClickListener(this);
        textUsername = (TextView)findViewById(R.id.text_username);
        textRegisterEmail = (TextView)findViewById(R.id.text_register_email);
        
        m_tv_pwd = (TextView)findViewById(R.id.text_update_password);
        m_tv_old_pwd = (TextView)findViewById(R.id.text_old_password);
        m_tv_new_pwd = (TextView)findViewById(R.id.text_new_password);
        m_tv_confirm_pwd = (TextView)findViewById(R.id.text_confirm_password);
        
        textTitle = (TextView)findViewById(R.id.text_title);
        headerTitleImage = (ImageView)findViewById(R.id.header_title_image);
        //textTitle.setVisibility(View.VISIBLE);
        //textTitle.setText(getResources().getString(R.string.my_update_profile));
        //headerTitleImage.setVisibility(View.GONE);
        
        setUserInfo();
    }

    private void setUserInfo(){
    	textUsername.setText(CustomApplication.getInstance().getUsername());
    	textRegisterEmail.setText(CustomApplication.getInstance().getEmail());

    }
    
    @Override  
    public void onClick(View v) {  
        switch (v.getId()) {  
        case R.id.profile_bg:  
             InputMethodManager imm = (InputMethodManager)  
             getSystemService(Context.INPUT_METHOD_SERVICE);  
             imm.hideSoftInputFromWindow(v.getWindowToken(), 0);  
            break;  
        }  
      
    } 
   

    @Override
    public void onBackPressed() {
    	final Bundle bundle = new Bundle(); 
        bundle.putString(Constants.LAST_PAGE, Constants.MINE_PAGE);
		ViewUtils.startPageWithClearStack(bundle, UpdateProfileActivity.this, HomeActivity.class);
        super.onBackPressed();
    }
    
    @Override
    public void viewInitialized(){

    }
    
    public void onUpdateProfileClicked(View view){
    	String username = textUsername.getText().toString().trim();
        String email = textRegisterEmail.getText().toString().trim();
        String password = m_tv_pwd.getText().toString().trim();

        Log.e("Edward", "Username is: " + username);
        if(username.equals("")){
        	showAlert(CustomApplication.getInstance().getCurrentActivity(),
                getString(R.string.title_update_profile),
                getResources().getString(R.string.validate_empty_username));
        	return;
        }
        
        if(email.equals("")){
        	showAlert(CustomApplication.getInstance().getCurrentActivity(),
                getString(R.string.title_update_profile),
                getResources().getString(R.string.validate_empty_email));
        	return;
        }
        
        if(password.equals("")){
        	showAlert(CustomApplication.getInstance().getCurrentActivity(),
                getString(R.string.title_update_profile),
                getResources().getString(R.string.validate_empty_password));
        	return;
        }
//        if (!runValidation(UsernameValidator.class, username)) {
//            return;
//        }

//        if (!runValidation(EmailValidator.class, email)) {
//            return;
//        }

        showWaiting();

        WebServiceHelper.getInstance().updateUserProfile(
                Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID),
                textUsername.getText().toString().trim(),//CustomApplication.getInstance().getEmail(),
                email,password
        );
    }
    
    private boolean isPasswordMatched(String pwd1,String pwd2) {
        //TODO: Replace this with your own logic
        return pwd1.equals(pwd2);
    }
    
    public void onUpdatePasswordClicked(View view){
    	
        String password = m_tv_old_pwd.getText().toString().trim();
        String new_password = m_tv_new_pwd.getText().toString().trim();
        String confirmed_password = m_tv_confirm_pwd.getText().toString().trim();
        
        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(confirmed_password) && !isPasswordMatched(new_password, confirmed_password)) {
            m_tv_confirm_pwd.setError(getString(R.string.error_incorrect_password));
            focusView = m_tv_confirm_pwd;
            cancel = true;
        }
        
        
        if(password.equals("") || new_password.equals("")){
        	showAlert(CustomApplication.getInstance().getCurrentActivity(),
                getString(R.string.title_update_profile),
                getResources().getString(R.string.validate_empty_password));
        	return;
        }

        if (cancel) {
            focusView.requestFocus();
        }else{

	        showWaiting();
	
	        WebServiceHelper.getInstance().updateUserPassword(
	                Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID),
	                CustomApplication.getInstance().getEmail(),password,new_password
	        );
        }
    }

    @Override
    public void ResponseFailedCallBack(int tag, final ResponseBase response) {
        dismissWaiting();
        super.ResponseFailedCallBack(tag, response);
    }

    @Override
    public void ResponseSuccessCallBack(int tag, ResponseBase response) {
        dismissWaiting();

        final UpdateUserProfileResponse updateUserProfileResponse;
        final UpdatePasswordResponse updatePasswordResponse;

        if (response instanceof UpdateUserProfileResponse) {
            updateUserProfileResponse = (UpdateUserProfileResponse) response;
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if(updateUserProfileResponse.responseMessage.equalsIgnoreCase("Success") || updateUserProfileResponse.reasonCode == 0){
//                    	showToast(getResources().getString(R.string.my_update_profile_success));
                    	showAlert(CustomApplication.getInstance().getCurrentActivity(),
                                getString(R.string.title_update_profile),
                                getResources().getString(R.string.my_update_profile_success));
                    	
                    	String sNmae = textUsername.getText().toString().trim();
                    	if(!sNmae.equals("")){
                    		CustomApplication.getInstance().setUsername(sNmae);
                    	}
                    	String email = textRegisterEmail.getText().toString().trim();
                    	if(!email.equals("")){
                    		CustomApplication.getInstance().setEmail(email);
                    	}
                    	
                    }
//                    ViewUtils.startPageWithClearStack(null, UpdateProfileActivity.this, HomeActivity.class);
                }
            });
        }
        else if (response instanceof UpdatePasswordResponse) {
        	updatePasswordResponse = (UpdatePasswordResponse) response;
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if(updatePasswordResponse.responseMessage.equalsIgnoreCase("Success") || updatePasswordResponse.reasonCode == 0){

                    	showAlert(CustomApplication.getInstance().getCurrentActivity(),
                                getString(R.string.title_update_profile),
                                getResources().getString(R.string.my_update_password_success));
                    	
                    }

                }
            });
        }
    }
    

}
