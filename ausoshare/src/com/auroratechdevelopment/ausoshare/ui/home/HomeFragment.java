package com.auroratechdevelopment.ausoshare.ui.home;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import android.view.ViewGroup;


import com.auroratechdevelopment.ausoshare.CustomApplication;
import com.auroratechdevelopment.ausoshare.R;
import com.auroratechdevelopment.ausoshare.ui.contact.ContactURLActivity;
import com.auroratechdevelopment.ausoshare.ui.ext.LineProgress;
import com.auroratechdevelopment.ausoshare.ui.home.HomeActivity.HomeAdListUpdated;
import com.auroratechdevelopment.ausoshare.ui.home.HomeActivity.HomeEntertainmentListUpdated;
import com.auroratechdevelopment.ausoshare.ui.home.HomeActivity.HomeStartNumUpdated;
import com.auroratechdevelopment.ausoshare.ui.login.LoginActivity;
import com.auroratechdevelopment.ausoshare.util.Constants;
import com.auroratechdevelopment.common.DebugLogUtil;
import com.auroratechdevelopment.common.ViewUtils;
import com.auroratechdevelopment.common.ui.ViewPagerEx;
import com.auroratechdevelopment.common.webservice.WebServiceHelper;
import com.auroratechdevelopment.common.webservice.models.AdDataItem;
import com.auroratechdevelopment.common.webservice.models.OnGoingAdItem;
import com.auroratechdevelopment.common.webservice.models.UserInfo;
import com.auroratechdevelopment.common.webservice.response.GetOnGoingAdListResponse;
import com.auroratechdevelopment.common.webservice.response.ResponseBase;
//import com.tencent.mm.sdk.platformtools.Log;

import java.util.ArrayList;

/**
 * Created by happy pan on 2015/10/30.
 * Updated by Raymond Zhuang 2016/4/26
 */
public class HomeFragment extends HomeFragmentBase  implements  
    OnGoingAdItemsAdapter.GetItemSelected,
    SwipeRefreshLayout.OnRefreshListener,
    HomeActivity.HomeAdListUpdated, HomeStartNumUpdated
    {
	    private ListView list;
	    private OnGoingAdItemsAdapter adapter;
	    protected SwipeRefreshLayout swipeRefreshlayout;
	    private int startNumber = 0;
	    private static final String KEY_ADS = "ADS";
	    //private TextView forwardMethodText;
	    private LinearLayout forwardMethodImg;
	    private RelativeLayout forwardMethodText;
	    
	    private ViewPagerEx pager;
	    
//	    private PullToRefreshLayout mPullToRefreshLayout;
	
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	    }
	
	    @Override
	    public void onItemDetails(AdDataItem item) {
	    	
	    	final Bundle bundle = new Bundle(); 
	        bundle.putParcelable(Constants.BUNDLE_AD_ITEM, item);
	        
	//    	if(CustomApplication.getInstance().getEmail().equalsIgnoreCase("")){
	//    		ViewUtils.startPage(bundle, getActivity(), LoginActivity.class);
	//    		getActivity().finish();
	//    	}
	//    	else{
		        ViewUtils.startPage(bundle, homeActivity, PrepareShareAdActivity.class);
	//    	}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) { 	
    	startNumber = 0;
        View rootView = inflater.inflate(R.layout.fragment_home_adlist_v2, container, false);
        //forwardMethodText = (TextView) rootView.findViewById(R.id.forward_method_tv);
        forwardMethodImg = (LinearLayout) rootView.findViewById(R.id.forward_method_tv);
        //forwardMethodText = (RelativeLayout) rootView.findViewById(R.id.description_layout);
        
        //int abt = CustomApplication.getInstance().getSharedADTime();
        
        if(CustomApplication.getInstance().getSharedADTime()>=3){
        	//forwardMethodText.setVisibility(View.GONE);
        	forwardMethodImg.setVisibility(View.GONE);
        }
        
        
        list = (ListView) rootView.findViewById(R.id.valid_ad_list); 
        list.setEmptyView(rootView.findViewById(R.id.empty_list));

        list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {
                    if (list.getLastVisiblePosition() >= list.getCount() - 1 - 0) {

//                        startNumber = startNumber + Constants.ADS_PAGE_SIZE;
                    	if(startNumber%Constants.ADS_PAGE_SIZE == 0){
                    		Log.e("Edward Debug", "111 invoke getNewAds");
                    		getNewAds(startNumber);
                    	}
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        Log.e("Edward Debug", "333 invoke getNewAds");
        getNewAds(startNumber);
        adapter = new OnGoingAdItemsAdapter(this.getActivity(),new ArrayList<AdDataItem>());
//        adapter.clearList();
        adapter.setListener(this);
        list.setAdapter(adapter);

        swipeRefreshlayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swipe_container_ad);
        swipeRefreshlayout.setColorScheme(android.R.color.holo_purple);
        swipeRefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				swipeRefreshlayout.setRefreshing(true);
	            (new Handler()).postDelayed(new Runnable() {
	                @Override
	                public void run() {
	                	swipeRefreshlayout.setRefreshing(false);
	                	startNumber = 0;
	                	Log.e("Edward Debug", "222 invoke getNewAds");
	                	adapter.clearList();
	                    getNewAds(startNumber);
	                }
	            }, 1000);
			}
		});

        hideSoftBoard();
        
        return rootView;
    }
    
    private void hideSoftBoard(){
    	if(getActivity().getCurrentFocus()!=null)  
        {  
            ((InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE))  
            .hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),  
                    InputMethodManager.HIDE_NOT_ALWAYS);   
        }  
    }

    public UserInfo setUserInfo(int startNumber){
        UserInfo userInfo = new UserInfo();
        userInfo.city = CustomApplication.getInstance().getUserCity();
        userInfo.province = CustomApplication.getInstance().getUserProvince();
        userInfo.country = CustomApplication.getInstance().getUserCountry();
        userInfo.lon = CustomApplication.getInstance().getUserLongitude();
        userInfo.lat = CustomApplication.getInstance().getUserLatitude();
        userInfo.startNumber = startNumber;
        userInfo.counts = Constants.ADS_PAGE_SIZE;
        return userInfo;
    }

    private void getNewAds(int startNumber){
//    	homeActivity.showWaiting();
    	
    	Log.e("Edward", "getNewAds: startNumber = " + startNumber);
        UserInfo user_info = setUserInfo(startNumber);
        WebServiceHelper.getInstance().onGoingAdList(user_info, Constants.TAG_ADVERT,"");
    }

    public void checkLoginStatus(){
        if(!CustomApplication.getInstance().getUserLogin()){
            startActivity(new Intent(getActivity(), LoginActivity.class));
//            getActivity().finish();
        }
    }

    private HomeFragmentBase getCurrentFragment() {
        FragmentStatePagerAdapter a = (FragmentStatePagerAdapter) pager.getAdapter();
        return (HomeFragmentBase) a.instantiateItem(pager, pager.getCurrentItem());
    }
    
    @Override
    public void onResume() {
        super.onResume();
    }

	@Override
	public void onHomeAdListUpdated(final int tag, final GetOnGoingAdListResponse adList ) {
		// TODO Auto-generated method stub
        homeActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
            	swipeRefreshlayout.setRefreshing(false);//  .setRefreshComplete();
            }
        });
		
//		final GetOnGoingAdListResponse adList = (GetOnGoingAdListResponse) response;
        if(homeActivity != null){
            homeActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                	if(adList.data != null &&adList.data.size() == 0 ){

                	}
                	
                	if(tag == -1){
                		adapter.clearList();
                	}

                	startNumber = startNumber + adList.data.size();
                	
                	Log.e("Edward", "at ResponseSuccessCallBack, startNumber is: " + startNumber);
                	CustomApplication.getInstance().setLastAD(adList.data.get(adList.data.size()-1).adID);
                    adapter.setList(adList.data);
                    adapter.notifyDataSetChanged();
                }
            });
        }
	}

	@Override
	public void onHomeStartNumUpdated(int pos) {
		startNumber = 0;
		adapter.clearList();
		
	}


}
