package com.auroratechdevelopment.ausoshare.ui.profile;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.auroratechdevelopment.ausoshare.CustomApplication;
import com.auroratechdevelopment.ausoshare.R;
import com.auroratechdevelopment.ausoshare.ui.ActivityBase;
import com.auroratechdevelopment.ausoshare.ui.home.HomeActivity;
import com.auroratechdevelopment.ausoshare.ui.home.PrepareShareAdActivity;
import com.auroratechdevelopment.ausoshare.util.Constants;
import com.auroratechdevelopment.common.ViewUtils;
import com.auroratechdevelopment.common.webservice.WebServiceConstants;
import com.auroratechdevelopment.common.webservice.WebServiceHelper;
import com.auroratechdevelopment.common.webservice.response.GetURLResponse;
import com.auroratechdevelopment.common.webservice.response.ResponseBase;

/**
 * Created by happy pan on 2015/11/10.
 */
public class ProfileURLActivity extends ActivityBase implements View.OnClickListener{

//    private WebView contactURLWebView;
    private WebView wv;

    private TextView textTitle;
    private ImageView headerTitleImage;
    private Button contactConfirmBtn;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setView() {
        setContentView(R.layout.activity_contact_url);

//        wv = (WebView)findViewById(R.id.contact_url_webview);
      //define the webview
        wv = new WebView(getApplicationContext());
        WebSettings settings = wv.getSettings();
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        wv.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        FrameLayout layoutWebView =  (FrameLayout)findViewById(R.id.layoutWebView);
        layoutWebView.addView(wv);
        
        textTitle = (TextView)findViewById(R.id.text_title);
        headerTitleImage = (ImageView)findViewById(R.id.header_title_image);
        contactConfirmBtn = (Button)findViewById(R.id.contact_confirm_btn);
        contactConfirmBtn.setOnClickListener(this);
        
        sendURLRequest();
    }
    
    private void setHeaderTitle(String header_title){
    	textTitle.setVisibility(View.VISIBLE);
    	headerTitleImage.setVisibility(View.GONE);
    	if(header_title.equalsIgnoreCase(WebServiceConstants.statsWithdrawHistory)){
    		textTitle.setText(getResources().getString(R.string.my_withdraw_record));
    	}else if (header_title.equals(WebServiceConstants.statsForwardedAdHistory)){
    		textTitle.setText(getResources().getString(R.string.my_forwarded_ad));
    	}
    }
    
    @Override
    public void onDestroy(){

    	if(wv != null){
    		wv.clearCache(true);
    		wv.clearFormData();
    		wv.clearHistory();
    		wv.removeAllViews();
    		wv.destroy();
    		
    	}
    	
    	super.onDestroy();
    }
    
    @Override
    public void onBackPressed() {
    	final Bundle bundle = new Bundle(); 
        bundle.putString(Constants.LAST_PAGE, Constants.MINE_PAGE);
    	
		ViewUtils.startPageWithClearStack(bundle, ProfileURLActivity.this, HomeActivity.class);
        super.onBackPressed();
    }
    
    private void sendURLRequest(){
        Intent urlIntent = getIntent();
        String url = urlIntent.getStringExtra(Constants.CONTACT_URL);

        setHeaderTitle(url);
        
        WebServiceHelper.getInstance().getContactURL(CustomApplication.getInstance().getAndroidID(),
                CustomApplication.getInstance().getEmail()
                , url);
    }

    private void openURLWeb(String url){
        WebSettings ws = wv.getSettings();
        ws.setJavaScriptEnabled(true);
        wv.loadUrl(url);
        wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    public void ResponseFailedCallBack(int tag, final ResponseBase response) {
        dismissWaiting();
        super.ResponseFailedCallBack(tag, response);
    }

    @Override
    public void ResponseSuccessCallBack(int tag, ResponseBase response) {
        dismissWaiting();

        if (response instanceof GetURLResponse) {
            final GetURLResponse getURLResponse = (GetURLResponse) response;

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    openURLWeb(getURLResponse.URL);
                }
            });
        }

    }

    @Override
    protected void viewInitialized() {

    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
