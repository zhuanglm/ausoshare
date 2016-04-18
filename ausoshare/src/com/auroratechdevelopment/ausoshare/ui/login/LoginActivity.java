package com.auroratechdevelopment.ausoshare.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.auroratechdevelopment.ausoshare.CustomApplication;
import com.auroratechdevelopment.ausoshare.R;
import com.auroratechdevelopment.ausoshare.ui.ActivityBase;
import com.auroratechdevelopment.ausoshare.ui.contact.ContactURLActivity;
import com.auroratechdevelopment.ausoshare.ui.ext.CustomAlertDialog;
import com.auroratechdevelopment.ausoshare.ui.home.HomeActivity;
import com.auroratechdevelopment.ausoshare.ui.home.PrepareShareAdActivity;
import com.auroratechdevelopment.ausoshare.util.Constants;
import com.auroratechdevelopment.ausoshare.validation.EmailValidator;
import com.auroratechdevelopment.ausoshare.validation.PasswordValidator;
import com.auroratechdevelopment.common.ViewUtils;
import com.auroratechdevelopment.common.webservice.WebServiceHelper;
import com.auroratechdevelopment.common.webservice.models.AdDataItem;
import com.auroratechdevelopment.common.webservice.response.ForgotPasswordResponse;
import com.auroratechdevelopment.common.webservice.response.LoginResponse;
import com.auroratechdevelopment.common.webservice.response.OnGoingAdDetailResponse;
import com.auroratechdevelopment.common.webservice.response.ResponseBase;

/**
 * Created by happy pan on 2015/11/5.
 */
public class LoginActivity extends ActivityBase implements View.OnClickListener{

    private TextView emailTv, passwordTv;
    private Button loginConfirmBtm, backButton;
    private TextView registerNewUserTv, forgotPasswordTv;
    private CheckBox rememberMeCheckBox;

    private AdDataItem adDataItem;
    private String share_adID;
    private boolean getDetailADFlag=false;
    private String emailInputed;
    private String fromWhichPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setView() {
        
        setContentView(R.layout.activity_user_login);
        backButton = (Button)findViewById(R.id.backButton);

        emailTv = (TextView)findViewById(R.id.email_tv);
        passwordTv = (TextView)findViewById(R.id.password_tv);
        loginConfirmBtm = (Button)findViewById(R.id.login_confirm_btn);
        registerNewUserTv = (TextView)findViewById(R.id.register_new_user_tv);
        forgotPasswordTv = (TextView)findViewById(R.id.forgot_password_tv);
        rememberMeCheckBox = (CheckBox)findViewById(R.id.remember_me);

        loginConfirmBtm.setOnClickListener(this);
        registerNewUserTv.setOnClickListener(this);
        forgotPasswordTv.setOnClickListener(this);
        rememberMeCheckBox.setOnClickListener(rememberClicked);
        
        CustomApplication.getInstance().setLoginOutStatus(false);
        
        
        Intent urlIntent = getIntent();
        String lastPage = urlIntent.getStringExtra(Constants.LAST_PAGE);
        if(lastPage != null){
            if(lastPage.equals(Constants.LOGIN_PAGE_FROM_LOGIN)){
            	fromWhichPage = Constants.LOGIN_PAGE_FROM_LOGIN;
            }
            else if(lastPage.equals(Constants.LOGIN_PAGE_FROM_AD_PREPARE_SHARE))
            {
            	fromWhichPage = Constants.LOGIN_PAGE_FROM_AD_PREPARE_SHARE;
            	share_adID = urlIntent.getStringExtra(Constants.SHARED_AD_ID);
            }
            else{
            	fromWhichPage = "";
            }
        }
        else{
        	fromWhichPage = "";
        }
    }

    private View.OnClickListener rememberClicked = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.remember_me:
                    if (!(rememberMeCheckBox.isChecked())) {
                    }
                    break;

                default:
                    break;
            }
        }
    };


    @Override
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.login_confirm_btn:
                    handleLoginConfirm();
                break;
            case R.id.register_new_user_tv:
                    transferToRegister();
                break;
            case R.id.forgot_password_tv:
                    handleForgotPassword();
                break;
            case R.id.remember_me:
                if (!(rememberMeCheckBox.isChecked())) {
                    rememberMeCheckBox.setChecked(true);
                }else{
                    rememberMeCheckBox.setChecked(false);
                }
                break;

            default:
                break;
        }
    }

    protected void handleForgotPassword() {
    	emailInputed = emailTv.getText().toString().trim();
    	if(!runValidation(EmailValidator.class, emailInputed)){
    		return;
    	}
    	
        WebServiceHelper.getInstance().forgotPassward(emailTv.getText().toString().trim(),
                Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID));
    }

    protected void transferToRegister(){
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        finish();
    }

    protected void handleLoginConfirm(){
    	emailInputed = emailTv.getText().toString().trim();
        String password = passwordTv.getText().toString().trim();

        if(!runValidation(EmailValidator.class,emailInputed)){
            return;
        }
        if(!runValidation(PasswordValidator.class, password)){
            return;
        }

        WebServiceHelper.getInstance().login(emailInputed, password,
                Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID));

    }

    @Override
    public void ResponseFailedCallBack(int tag, final ResponseBase response) {
        super.ResponseFailedCallBack(tag, response);
    }

    @Override
    public void ResponseSuccessCallBack(int tag, ResponseBase response) {
//        dismissWaiting();

        if (response instanceof LoginResponse) {
            final LoginResponse loginResponse = (LoginResponse) response;

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String user_name = loginResponse.nickname;
                    String available_fund = loginResponse.availableFund;

                    CustomApplication.getInstance().setUsername(loginResponse.nickname);
                    CustomApplication.getInstance().setEmail(emailInputed);
                    CustomApplication.getInstance().setLoginOutStatus(true);
                    
                    Log.e("Edward", "loginActivity: setUserToken is: " + loginResponse.token);
                    CustomApplication.getInstance().setUserToken(loginResponse.token);
                    CustomApplication.getInstance().setAvailableFund(loginResponse.availableFund);
                    CustomApplication.getInstance().setRememberMeChecked(rememberMeCheckBox.isChecked());
                    
                    if(fromWhichPage.equalsIgnoreCase(Constants.LOGIN_PAGE_FROM_LOGIN)){
                    	final Bundle bundle = new Bundle(); 
                    	bundle.putString(Constants.LAST_PAGE, Constants.MINE_PAGE);
                    	bundle.putBoolean(Constants.LOGIN_STATUS, true);
                    	ViewUtils.startPageWithClearStack(bundle, LoginActivity.this, HomeActivity.class);
                    	
                    	Log.e("Edward","from login");
                    	hideSoftBoard();
                    	finish();
                    }
                    else if(fromWhichPage.equalsIgnoreCase(Constants.LOGIN_PAGE_FROM_AD_PREPARE_SHARE)){
                    	getDetailAd();
                    }
                    else{
                    	Log.e("Edward", "from non-login");
                    	hideSoftBoard();
                    	finish();
                    }
   
                }
            });
        }
        else if(response instanceof ForgotPasswordResponse){
            newPasswordResetMessage();
        }
        else if(response instanceof OnGoingAdDetailResponse){
        	final OnGoingAdDetailResponse adDetailResponse = (OnGoingAdDetailResponse) response;
        	
        	runOnUiThread(new Runnable(){

				@Override
				public void run() {
					AdDataItem item = adDetailResponse.data.get(0);
					final Bundle bundle = new Bundle(); 
			        bundle.putParcelable(Constants.BUNDLE_AD_ITEM, item);
			        ViewUtils.startPage(bundle, LoginActivity.this, PrepareShareAdActivity.class);
			        finish();
				}

        	});
        }
    }

    
    private void hideSoftBoard(){
    	InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
    	imm.hideSoftInputFromWindow(emailTv.getWindowToken(), 0);
    	imm.hideSoftInputFromWindow(passwordTv.getWindowToken(), 0);
    }
    
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        hideSoftBoard();
        finish();  
    }
    
    private void getDetailAd(){
    	WebServiceHelper.getInstance().onGoingAdDetail("advert", share_adID);
    }

    private void newPasswordResetMessage(){
        showCenterScreenAlert(this,
                getString(R.string.forgot_password_title),
                getString(R.string.forgot_password_message),
                getString(R.string.button_ok),
                null,
                new CustomAlertDialog.AlertCallback() {
                    @Override
                    public void GetAlertButton(int which) {
                        if (which == -1) {
//                        	final Bundle bundle = new Bundle(); 
//                            bundle.putString(Constants.LAST_PAGE, Constants.CONTACT_PAGE);
//                    		ViewUtils.startPageWithClearStack(bundle, LoginActivity.this, HomeActivity.class);
                        }
                    }

                    @Override
                    public void AlertCancelled() {
//                    	Log.e("忘记密码", "forgot password 2");
                        finish();
                    }
                },
                false);
    }

    @Override
    protected void viewInitialized() {
        if(CustomApplication.getInstance().getRememberMeChecked()){
            rememberMeCheckBox.setChecked(true);
        }
    }
}
