package com.auroratechdevelopment.ausoshare.ui.profile;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.auroratechdevelopment.ausoshare.CustomApplication;
import com.auroratechdevelopment.ausoshare.R;
import com.auroratechdevelopment.ausoshare.ui.ActivityBase;
import com.auroratechdevelopment.ausoshare.ui.home.HomeActivity;
import com.auroratechdevelopment.ausoshare.util.Constants;
import com.auroratechdevelopment.common.ViewUtils;
import com.auroratechdevelopment.common.webservice.WebServiceHelper;
import com.auroratechdevelopment.common.webservice.models.Top10Item;
import com.auroratechdevelopment.common.webservice.response.CurrentIncomeResponse;
import com.auroratechdevelopment.common.webservice.response.ResponseBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by happy pan on 2015/11/12.
 */
public class CurrentIncomeActivity extends ActivityBase {

    private ListView top10List;
    private Top10IncomeAdapter adapter;

    private TextView currentIncomeText;
    
    private TextView textTitle;
    private ImageView headerTitleImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setView() {
        setContentView(R.layout.activity_current_income);
        top10List = (ListView)findViewById(R.id.top10_list);
        textTitle = (TextView)findViewById(R.id.text_title);
        headerTitleImage = (ImageView)findViewById(R.id.header_title_image);
        
        textTitle.setVisibility(View.VISIBLE);
        textTitle.setText(getResources().getString(R.string.my_current_income));
        headerTitleImage.setVisibility(View.GONE);
        
        currentIncomeText = (TextView)findViewById(R.id.current_income_text);

        fetchCurrentIncome();

    }

    public void fetchCurrentIncome(){
        WebServiceHelper.getInstance().getCurrentIncome(CustomApplication.getInstance().getEmail(),
                        CustomApplication.getInstance().getAndroidID(),
                        Constants.CURRENT_INCOME_TOP_USER_MAXNUMBER);
        showWaiting();
    }

    @Override
    public void viewInitialized(){

    }

    @Override
    public void onBackPressed() {
    	final Bundle bundle = new Bundle(); 
        bundle.putString(Constants.LAST_PAGE, Constants.MINE_PAGE);
        
		ViewUtils.startPageWithClearStack(bundle, CurrentIncomeActivity.this, HomeActivity.class);
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

        if (response instanceof CurrentIncomeResponse) {
            final CurrentIncomeResponse currentIncome = (CurrentIncomeResponse) response;

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //display user current income and top 10 income

                    currentIncomeText.setText(getResources().getString(R.string.profile_current_income_text)+
                            currentIncome.data.currentIncome);

                    List<Top10Item> top10ArrayList = Arrays.asList(currentIncome.data.top10);

                    adapter = new Top10IncomeAdapter(CurrentIncomeActivity.this, new ArrayList<Top10Item>());
                    adapter.clearList();
                    adapter.setList(top10ArrayList);
                    top10List.setAdapter(adapter);
                }
            });
        }

    }
}
