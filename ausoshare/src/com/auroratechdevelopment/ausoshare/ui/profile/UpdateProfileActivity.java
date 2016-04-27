package com.auroratechdevelopment.ausoshare.ui.profile;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.provider.Settings;
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
import com.auroratechdevelopment.common.webservice.response.UpdateUserProfileResponse;
//import com.tencent.mm.sdk.platformtools.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by happy pan on 2015/11/12.
 */
public class UpdateProfileActivity extends ActivityBase {

    private ListView top10List;
    private Top10IncomeAdapter adapter;

    private TextView currentIncomeText;
    
    private TextView textTitle;
    private ImageView headerTitleImage;
    
    
    private TextView textUsername, textRegisterEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setView() {
        setContentView(R.layout.activity_update_profile);
        textUsername = (TextView)findViewById(R.id.text_username);
        textRegisterEmail = (TextView)findViewById(R.id.text_register_email);
        textTitle = (TextView)findViewById(R.id.text_title);
        headerTitleImage = (ImageView)findViewById(R.id.header_title_image);
        textTitle.setVisibility(View.VISIBLE);
        textTitle.setText(getResources().getString(R.string.my_update_profile));
        headerTitleImage.setVisibility(View.GONE);
        
        setUserInfo();
    }

    private void setUserInfo(){
    	textUsername.setText(CustomApplication.getInstance().getUsername());
    	textRegisterEmail.setText(CustomApplication.getInstance().getEmail());

    }
    public void fetchCurrentIncome(){
        WebServiceHelper.getInstance().getCurrentIncome(CustomApplication.getInstance().getEmail(),
                        CustomApplication.getInstance().getAndroidID(),
                        Constants.CURRENT_INCOME_TOP_USER_MAXNUMBER);
        showWaiting();
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
    
    public void onUpdateButtonClicked(View view){
    	String username = textUsername.getText().toString().trim();
        String email = textRegisterEmail.getText().toString().trim();

        Log.e("Edward", "Username is: " + username);
        if(username.equals("")){
        	showAlert(CustomApplication.getInstance().getCurrentActivity(),
                getString(R.string.title_update_profile),
                getResources().getString(R.string.validate_empty_username));
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
                textUsername.getText().toString().trim(),
                CustomApplication.getInstance().getEmail()
        );
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
                    	
                    }
//                    ViewUtils.startPageWithClearStack(null, UpdateProfileActivity.this, HomeActivity.class);
                }
            });
        }
    }
}
