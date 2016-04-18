package com.auroratechdevelopment.ausoshare.ui.profile;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.auroratechdevelopment.ausoshare.CustomApplication;
import com.auroratechdevelopment.ausoshare.R;
import com.auroratechdevelopment.ausoshare.ui.ActivityBase;
import com.auroratechdevelopment.ausoshare.ui.contact.ContactURLActivity;
import com.auroratechdevelopment.ausoshare.ui.ext.CustomAlertDialog;
import com.auroratechdevelopment.ausoshare.ui.ext.EdwardAlertDialog;
import com.auroratechdevelopment.ausoshare.ui.home.HomeActivity;
import com.auroratechdevelopment.ausoshare.ui.home.HomeFragmentBase;
import com.auroratechdevelopment.ausoshare.ui.home.PrepareShareAdActivity;
import com.auroratechdevelopment.ausoshare.ui.login.LoginActivity;
import com.auroratechdevelopment.ausoshare.ui.login.RegisterActivity;
import com.auroratechdevelopment.ausoshare.util.Constants;
import com.auroratechdevelopment.common.ViewUtils;
import com.auroratechdevelopment.common.webservice.WebService;
import com.auroratechdevelopment.common.webservice.WebServiceConstants;
import com.auroratechdevelopment.common.webservice.WebServiceHelper;
import com.auroratechdevelopment.common.webservice.request.ForwardedAdHistoryRequest;
import com.auroratechdevelopment.common.webservice.response.CurrentIncomeResponse;
import com.auroratechdevelopment.common.webservice.response.ForwardedAdHistoryResponse;
import com.auroratechdevelopment.common.webservice.response.GetOnGoingEntertainmentListResponse;
import com.auroratechdevelopment.common.webservice.response.GetURLResponse;
import com.auroratechdevelopment.common.webservice.response.ResponseBase;
import com.auroratechdevelopment.common.webservice.response.WithdrawRequestResponse;

import org.w3c.dom.Text;

/**
 * Created by happy pan on 2015/10/30.
 */
public class ProfileFragment extends HomeFragmentBase implements 
    View.OnClickListener, 
    HomeActivity.HomeWithdrawUpdated
    {
    private RelativeLayout userProfileLayout;
    private ImageView loginOutImageThumb,userImageThumb,myCurrentIncomeImage,myForwardedAdImage,myWithdrawRecordImage, myWithdrawRequestImage;
    private TextView loginOutProfileText,userProfileText,myCurrentIncomeText,myForwardedAdText,myWithdrawRecordText,myWithdrawRequestText;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_profile, container,
                false);

        userProfileLayout = (RelativeLayout)rootView.findViewById(R.id.user_profile_layout);
        
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
        }

        return rootView;
    }

    
    @Override
    public void onClick(View view){
        switch (view.getId()){
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
//		getActivity().finish();
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
            loginOutProfileText.setText(getResources().getString(R.string.my_log_in));
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
    public boolean ResponseFailedCallBack(int tag, final ResponseBase response) {
        homeActivity.dismissWaiting();
//        dismissDialogWaiting();
        super.ResponseFailedCallBack(tag, response);

        return false;
    }

    @Override
    public boolean ResponseSuccessCallBack(int tag, final ResponseBase response) {
        homeActivity.dismissWaiting();
//    	dismissDialogWaiting();

        if (response instanceof CurrentIncomeResponse) {
            final GetURLResponse getURLResponse = (GetURLResponse) response;

            homeActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {


                }
            });
        }
        else if (response instanceof ForwardedAdHistoryResponse){
            final ForwardedAdHistoryResponse forwardedAdHistoryResponse = (ForwardedAdHistoryResponse) response;

            homeActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                }
            });
        }
        else if(response instanceof GetURLResponse){
            final GetURLResponse getURLResponse = (GetURLResponse) response;

            homeActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {


                }
            });
        }
        else if(response instanceof WithdrawRequestResponse){
        	final WithdrawRequestResponse withdrawRequestResponse = (WithdrawRequestResponse) response;
        	homeActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                	homeActivity.showAlert(getActivity(),
                            getResources().getString(R.string.my_withdraw_request),
                            response.responseMessage);
                }
            });
        	
        }

        return true;
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

}
