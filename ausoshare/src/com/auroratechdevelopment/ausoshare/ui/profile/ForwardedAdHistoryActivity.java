package com.auroratechdevelopment.ausoshare.ui.profile;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.auroratechdevelopment.ausoshare.R;
import com.auroratechdevelopment.ausoshare.ui.ActivityBase;
import com.auroratechdevelopment.ausoshare.ui.contact.ContactURLActivity;
import com.auroratechdevelopment.ausoshare.ui.home.HomeActivity;
import com.auroratechdevelopment.ausoshare.util.Constants;
import com.auroratechdevelopment.common.ViewUtils;
import com.auroratechdevelopment.common.webservice.WebServiceHelper;
import com.auroratechdevelopment.common.webservice.models.ForwardedADItem;
import com.auroratechdevelopment.common.webservice.models.Top10Item;
import com.auroratechdevelopment.common.webservice.response.CurrentIncomeResponse;
import com.auroratechdevelopment.common.webservice.response.ForwardedAdHistoryResponse;
import com.auroratechdevelopment.common.webservice.response.GetOnGoingAdListResponse;
import com.auroratechdevelopment.common.webservice.response.ResponseBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by happy pan on 2015/11/12.
 */
public class ForwardedAdHistoryActivity extends ActivityBase {
    private ListView forwardedAdList;
    private ForwardedAdHistoryAdapter adapter;

    private TextView emptyList;

    private int startNumber = 0;
    private int count = 10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setView() {
        setContentView(R.layout.activity_forwared_ad_history);
        forwardedAdList = (ListView)findViewById(R.id.forwarded_ad_list);

        emptyList = (TextView)findViewById(R.id.empty_list);

        fetchForwardedAdHistory();

    }

    public void fetchForwardedAdHistory(){
        WebServiceHelper.getInstance().getForwardedAd(startNumber, count);
        showWaiting();
    }

    @Override
    public void viewInitialized(){

    }

    @Override
    public void onBackPressed() {
    	final Bundle bundle = new Bundle(); 
        bundle.putString(Constants.LAST_PAGE, Constants.MINE_PAGE);
		ViewUtils.startPageWithClearStack(bundle, ForwardedAdHistoryActivity.this, HomeActivity.class);
        super.onBackPressed();
    }
    
    @Override
    public void ResponseFailedCallBack(int tag, final ResponseBase response) {
        dismissWaiting();
        super.ResponseFailedCallBack(tag, response);
    }

    @Override
    public void ResponseSuccessCallBack(int tag, ResponseBase response) {
        dismissWaiting();


        if (response instanceof ForwardedAdHistoryResponse) {
            final ForwardedAdHistoryResponse forwarded_ADList = (ForwardedAdHistoryResponse) response;

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if(forwarded_ADList.data.isEmpty()){
                        emptyList.setText(getResources().getString(R.string.profile_empty_ad_text));
                    }else {
                        adapter = new ForwardedAdHistoryAdapter(ForwardedAdHistoryActivity.this, new ArrayList<ForwardedADItem>());
                        adapter.clearList();
                        adapter.setList(forwarded_ADList.data);
                        forwardedAdList.setAdapter(adapter);
                    }
                }
            });
        }

    }
}
