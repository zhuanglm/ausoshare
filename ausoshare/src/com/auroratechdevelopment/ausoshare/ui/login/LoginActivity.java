package com.auroratechdevelopment.ausoshare.ui.login;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import com.auroratechdevelopment.ausoshare.CustomApplication;
import com.auroratechdevelopment.ausoshare.R;
import com.auroratechdevelopment.ausoshare.ui.ActivityBase;
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
import com.tencent.mm.sdk.openapi.BaseResp;
import com.tencent.mm.sdk.openapi.ConstantsAPI;
import com.tencent.mm.sdk.openapi.IWXAPI;
//import com.tencent.mm.sdk.constants.ConstantsAPI;
//import com.tencent.mm.sdk.modelbase.BaseResp;
//import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.SendAuth;

import com.tencent.mm.sdk.openapi.WXAPIFactory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by happy pan on 2015/11/5.
 * Updated by Raymond Zhuang 2016/4/25
 */
public class LoginActivity extends ActivityBase implements View.OnClickListener{

    private TextView emailTv, passwordTv;
    private Button loginConfirmBtm, backButton;
    private TextView registerNewUserTv, forgotPasswordTv;
    private CheckBox rememberMeCheckBox;
    private ImageView m_btn_wechat;

    private AdDataItem adDataItem;
    private String share_adID;
    private boolean getDetailADFlag=false;
    private String emailInputed;
    private String fromWhichPage;
    
    private IWXAPI m_WXapi;
    private String weixinCode;
    private static String get_access_token = "";
 // 获取第一步的code后，请求以下链接获取access_token
 	public static String GetCodeRequest = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
  //获取用户个人信息
  	public static String GetUserInfo="https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
	protected void onResume() {
		super.onResume();
		
		if(Constants.WXresp != null){
			BaseResp resp = Constants.WXresp;
			
			if (resp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {
				// code返回
				weixinCode = ((SendAuth.Resp) resp).token;
				
				 //将你前面得到的AppID、AppSecret、code，拼接成URL
				 
				get_access_token = getCodeRequest(weixinCode);
				Thread thread=new Thread(downloadRun);
				thread.start();
				try {
					thread.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
    }
    
    public  Runnable downloadRun = new Runnable() {

		@Override
		public void run() {
			WXGetAccessToken();
			
		}
	};
    
   
	//获取access_token的URL（微信）
	//@param code 授权时，微信回调给的
	//@return URL
	
	public static String getCodeRequest(String code) {
		String result = null;
		GetCodeRequest = GetCodeRequest.replace("APPID",
				urlEnodeUTF8(Constants.APP_ID));
		GetCodeRequest = GetCodeRequest.replace("SECRET",
				urlEnodeUTF8(Constants.WX_APP_SECRET));
		GetCodeRequest = GetCodeRequest.replace("CODE",urlEnodeUTF8( code));
		result = GetCodeRequest;
		return result;
	}
	
	 // 获取用户个人信息的URL（微信）
	 // @param access_token 获取access_token时给的
	 // @param openid 获取access_token时给的
	 // @return URL
	 
	public static String getUserInfo(String access_token,String openid){
		String result = null;
		GetUserInfo = GetUserInfo.replace("ACCESS_TOKEN",
				urlEnodeUTF8(access_token));
		GetUserInfo = GetUserInfo.replace("OPENID",
				urlEnodeUTF8(openid));
		result = GetUserInfo;
		return result;
	}
	
	public static String urlEnodeUTF8(String str) {
		String result = str;
		try {
			result = URLEncoder.encode(str, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	 //获取access_token等等的信息(微信)
	 
	private  void WXGetAccessToken(){
		HttpClient get_access_token_httpClient = new DefaultHttpClient();
		HttpClient get_user_info_httpClient = new DefaultHttpClient();
		String access_token="";
		String openid ="";
		try {
			HttpPost postMethod = new HttpPost(get_access_token);
			HttpResponse response = get_access_token_httpClient.execute(postMethod); // 执行POST方法
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				InputStream is = response.getEntity().getContent();
				BufferedReader br = new BufferedReader(
						new InputStreamReader(is));
				String str = "";
				StringBuffer sb = new StringBuffer();
				while ((str = br.readLine()) != null) {
					sb.append(str);
				}
				is.close();
				String josn = sb.toString();
				JSONObject json1 = new JSONObject(josn);
				access_token = (String) json1.get("access_token");
				openid = (String) json1.get("openid");
			
				
			} else {
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		String get_user_info_url=getUserInfo(access_token,openid);
		WXGetUserInfo(get_user_info_url);
	}
	
	
	 // 获取微信用户个人信息
	 // @param get_user_info_url 调用URL
	 
		private  void WXGetUserInfo(String get_user_info_url){
			HttpClient get_access_token_httpClient = new DefaultHttpClient();
			String openid="";
			String nickname="";
			String headimgurl="";
			try {
				HttpGet getMethod = new HttpGet(get_user_info_url);
				HttpResponse response = get_access_token_httpClient.execute(getMethod); // 执行GET方法
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					InputStream is = response.getEntity().getContent();
					BufferedReader br = new BufferedReader(
							new InputStreamReader(is));
					String str = "";
					StringBuffer sb = new StringBuffer();
					while ((str = br.readLine()) != null) {
						sb.append(str);
					}
					is.close();
					String josn = sb.toString();
					JSONObject json1 = new JSONObject(josn);
					openid = (String) json1.get("openid");
					nickname = (String) json1.get("nickname");
					headimgurl=(String)json1.get("headimgurl");
					
				} else {
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		}

	

    @Override
    protected void setView() {
        
        setContentView(R.layout.activity_user_login);
        
        m_WXapi = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        m_WXapi.registerApp(Constants.APP_ID);
                
        backButton = (Button)findViewById(R.id.backButton);

        emailTv = (TextView)findViewById(R.id.email_tv);
        passwordTv = (TextView)findViewById(R.id.password_tv);
        loginConfirmBtm = (Button)findViewById(R.id.login_confirm_btn);
        registerNewUserTv = (TextView)findViewById(R.id.register_new_user_tv);
        forgotPasswordTv = (TextView)findViewById(R.id.forgot_password_tv);
        rememberMeCheckBox = (CheckBox)findViewById(R.id.remember_me);
        
        //m_btn_wechat = (ImageView)findViewById(R.id.button_wechat);
        //m_btn_wechat.setOnClickListener(this);

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
            else if(lastPage.equals(Constants.HOME_PAGE))
            {
            	fromWhichPage = Constants.HOME_PAGE;
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
                
            /*case R.id.button_wechat:
            	final SendAuth.Req req = new SendAuth.Req();
            	req.scope = "snsapi_userinfo";
            	req.state = "raymond_wx_login";
            	m_WXapi.sendReq(req);
            	//SendAuth.Resp code;
            	break;*/

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
                    
                    if(fromWhichPage.equalsIgnoreCase(Constants.LOGIN_PAGE_FROM_LOGIN) 
                    		|| fromWhichPage.equalsIgnoreCase(Constants.HOME_PAGE)){
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
