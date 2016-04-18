package com.auroratechdevelopment.ausoshare.ui.startup;

import com.auroratechdevelopment.ausoshare.R;
import com.auroratechdevelopment.ausoshare.ui.home.HomeActivity;
import com.auroratechdevelopment.common.ViewUtils;

import android.app.Activity;
import android.os.Bundle;

public class FirstTimeUseGuideActivity extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        
        ViewUtils.startPageWithClearStack(null, this, HomeActivity.class);
	}

}
