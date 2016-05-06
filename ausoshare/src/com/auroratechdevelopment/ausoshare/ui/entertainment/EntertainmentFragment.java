package com.auroratechdevelopment.ausoshare.ui.entertainment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;

import com.auroratechdevelopment.ausoshare.CustomApplication;
import com.auroratechdevelopment.ausoshare.R;
import com.auroratechdevelopment.ausoshare.ui.home.HomeActivity;
import com.auroratechdevelopment.ausoshare.ui.home.HomeFragmentBase;
import com.auroratechdevelopment.ausoshare.ui.home.PrepareShareAdActivity;
import com.auroratechdevelopment.ausoshare.ui.login.LoginActivity;
import com.auroratechdevelopment.ausoshare.util.Constants;
import com.auroratechdevelopment.common.ViewUtils;
import com.auroratechdevelopment.common.ui.ViewPagerEx;
import com.auroratechdevelopment.common.webservice.WebService;
import com.auroratechdevelopment.common.webservice.WebServiceConstants;
import com.auroratechdevelopment.common.webservice.WebServiceHelper;
import com.auroratechdevelopment.common.webservice.models.AdDataItem;
import com.auroratechdevelopment.common.webservice.models.UserInfo;
import com.auroratechdevelopment.common.webservice.request.GetURLRequest;
import com.auroratechdevelopment.common.webservice.response.ForgotPasswordResponse;
import com.auroratechdevelopment.common.webservice.response.GetOnGoingAdListResponse;
import com.auroratechdevelopment.common.webservice.response.GetOnGoingEntertainmentListResponse;
import com.auroratechdevelopment.common.webservice.response.GetURLResponse;
import com.auroratechdevelopment.common.webservice.response.LoginResponse;
import com.auroratechdevelopment.common.webservice.response.ResponseBase;
//import com.tencent.mm.sdk.platformtools.Log;

import java.util.ArrayList;

import org.w3c.dom.Text;

/**
 * Created by Edward Liu on 2015/11/10.
 * Updated by Raymond Zhuang 2016/4/24
 */
public class EntertainmentFragment extends HomeFragmentBase  implements  
	EntertainmentItemsAdapter.GetItemSelected,
	SwipeRefreshLayout.OnRefreshListener,
	HomeActivity.HomeEntertainmentListUpdated
	{
		private ListView list1,list2,list3,list4;
		private TextView m_tVEmpty;
		
		private EntertainmentItemsAdapter adapter;
		protected SwipeRefreshLayout swipeRefreshlayout1,swipeRefreshlayout2,swipeRefreshlayout3,swipeRefreshlayout4;
		private int startNumber = 0;
		private static final String KEY_ADS = "ENTERTAINMENT";
		
		private ViewPagerEx pager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	}

	@Override
	public void onItemDetails(AdDataItem item) {
		
		final Bundle bundle = new Bundle(); 
	    bundle.putParcelable(Constants.BUNDLE_AD_ITEM, item);
	    
	    ViewUtils.startPage(bundle, homeActivity, PrepareShareEntertainmentActivity.class);
	
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) { 	
		startNumber = 0;
	    View rootView = inflater.inflate(R.layout.fragment_home_entertainment_v2, container, false);
	    m_tVEmpty = (TextView)rootView.findViewById(R.id.empty_list);
	    
	    final TabHost tabs = (TabHost) rootView.findViewById(android.R.id.tabhost);
	    TabWidget mTabWidget = tabs.getTabWidget(); 
	    tabs.setup(); 
	    
	    TabSpec tabSpec = tabs.newTabSpec("page_fun");
        tabSpec.setIndicator(getResources().getString(R.string.page_fun));
        tabSpec.setContent(R.id.tab1);
        tabs.addTab(tabSpec);
        
        tabSpec = tabs.newTabSpec("page_news");
        tabSpec.setIndicator(getResources().getString(R.string.page_news));
        tabSpec.setContent(R.id.tab2);
        tabs.addTab(tabSpec);
	   	    
        tabSpec = tabs.newTabSpec("page_tips");
        tabSpec.setIndicator(getResources().getString(R.string.page_tips));
        tabSpec.setContent(R.id.tab3);
        tabs.addTab(tabSpec);
        
        tabSpec = tabs.newTabSpec("page_video");
        tabSpec.setIndicator(getResources().getString(R.string.page_video));
        tabSpec.setContent(R.id.tab4);
        tabs.addTab(tabSpec);
        
        /*View v = mTabWidget.getChildTabViewAt(2);  
        v.setOnClickListener(new OnClickListener(){  
            @Override  
            public void onClick(View v) {  
                // TODO Auto-generated method stub  
                
            }  
              
        });  */
        tabs.setCurrentTab(0);
        updateTab(tabs);
        tabs.setOnTabChangedListener(new OnTabChangeListener(){  
            @Override  
            public void onTabChanged(String tabId){  
               //Log.d("Raymond", tabId);	//page2  ....
            	if(tabId.equalsIgnoreCase("page_news")){
            		startNumber = 0;
            		adapter.clearList();
            		m_tVEmpty.setVisibility(View.VISIBLE);
            		getNewEntertainments(startNumber,Constants.TAG_NEWS);
            		
            		updateTab(tabs);
            	}else if(tabId.equalsIgnoreCase("page_tips")){
            		startNumber = 0;
            		adapter.clearList();
            		m_tVEmpty.setVisibility(View.VISIBLE);
            		getNewEntertainments(startNumber,Constants.TAG_TIPS);
            		
            		updateTab(tabs);
            	}else if(tabId.equalsIgnoreCase("page_video")){
            		startNumber = 0;
            		adapter.clearList();
            		m_tVEmpty.setVisibility(View.VISIBLE);
            		getNewEntertainments(startNumber,Constants.TAG_VIDEO);
            		updateTab(tabs);
            		
            	}else if(tabId.equalsIgnoreCase("page_fun")){
            		startNumber = 0;
            		adapter.clearList();
            		m_tVEmpty.setVisibility(View.VISIBLE);
            		m_tVEmpty.setVisibility(View.VISIBLE);
            		getNewEntertainments(startNumber,Constants.TAG_LIFE);
            		
            		updateTab(tabs);
            	}
            }  
        }); 
        
	    int abt = CustomApplication.getInstance().getSharedADTime();
	
	    list1 = (ListView) rootView.findViewById(R.id.valid_entertainment_list1);
        list2 = (ListView) rootView.findViewById(R.id.valid_entertainment_list2);
        list3 = (ListView) rootView.findViewById(R.id.valid_entertainment_list3);
        list4 = (ListView) rootView.findViewById(R.id.valid_entertainment_list4);

	    list1.setOnScrollListener(new AbsListView.OnScrollListener() {
	    	@Override
	    	public void onScrollStateChanged(AbsListView view, int scrollState) {
	    		if (scrollState == SCROLL_STATE_IDLE) {
	    			if (list1.getLastVisiblePosition() >= list1.getCount() - 1 - 0) {

//                    startNumber = startNumber + Constants.ADS_PAGE_SIZE;
	    				if(startNumber%Constants.ADS_PAGE_SIZE == 0){
	    					getNewEntertainments(startNumber,Constants.TAG_LIFE);
	    				}
	    			}
	    		}
	    	}

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        }
    });
	    
	    list2.setOnScrollListener(new AbsListView.OnScrollListener() {
	    	@Override
	    	public void onScrollStateChanged(AbsListView view, int scrollState) {
	    		if (scrollState == SCROLL_STATE_IDLE) {
	    			if (list2.getLastVisiblePosition() >= list2.getCount() - 1 - 0) {

//                    startNumber = startNumber + Constants.ADS_PAGE_SIZE;
	    				if(startNumber%Constants.ADS_PAGE_SIZE == 0){
	    					getNewEntertainments(startNumber,Constants.TAG_NEWS);
	    				}
	    			}
	    		}
	    	}

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        }
    });
	    
	    list3.setOnScrollListener(new AbsListView.OnScrollListener() {
	    	@Override
	    	public void onScrollStateChanged(AbsListView view, int scrollState) {
	    		if (scrollState == SCROLL_STATE_IDLE) {
	    			if (list3.getLastVisiblePosition() >= list3.getCount() - 1 - 0) {

//                    startNumber = startNumber + Constants.ADS_PAGE_SIZE;
	    				if(startNumber%Constants.ADS_PAGE_SIZE == 0){
	    					getNewEntertainments(startNumber,Constants.TAG_TIPS);
	    				}
	    			}
	    		}
	    	}

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        }
    });
	    
	    list4.setOnScrollListener(new AbsListView.OnScrollListener() {
	    	@Override
	    	public void onScrollStateChanged(AbsListView view, int scrollState) {
	    		if (scrollState == SCROLL_STATE_IDLE) {
	    			if (list4.getLastVisiblePosition() >= list4.getCount() - 1 - 0) {

//                    startNumber = startNumber + Constants.ADS_PAGE_SIZE;
	    				if(startNumber%Constants.ADS_PAGE_SIZE == 0){
	    					getNewEntertainments(startNumber,Constants.TAG_VIDEO);
	    				}
	    			}
	    		}
	    	}

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        }
    });

	
	
    adapter = new EntertainmentItemsAdapter(this.getActivity(),new ArrayList<AdDataItem>());
    adapter.clearList();
    adapter.setListener(this);
    
    list1.setAdapter(adapter);
    list2.setAdapter(adapter);
    list3.setAdapter(adapter);
    list4.setAdapter(adapter);
    
    getNewEntertainments(startNumber,Constants.TAG_LIFE);	//first page

    swipeRefreshlayout1 = (SwipeRefreshLayout)rootView.findViewById(R.id.swipe_container_ad1);
    //swipeRefreshlayout.setColorScheme(android.R.color.holo_purple);
    swipeRefreshlayout1.setColorSchemeResources(android.R.color.holo_blue_bright,
            android.R.color.holo_green_light, android.R.color.holo_orange_light,
            android.R.color.holo_red_light);
    
    swipeRefreshlayout2 = (SwipeRefreshLayout)rootView.findViewById(R.id.swipe_container_ad2);
    swipeRefreshlayout2.setColorSchemeResources(android.R.color.holo_blue_bright,
            android.R.color.holo_green_light, android.R.color.holo_orange_light,
            android.R.color.holo_red_light);
    
    swipeRefreshlayout3 = (SwipeRefreshLayout)rootView.findViewById(R.id.swipe_container_ad3);
    swipeRefreshlayout3.setColorSchemeResources(android.R.color.holo_blue_bright,
            android.R.color.holo_green_light, android.R.color.holo_orange_light,
            android.R.color.holo_red_light);
    
    swipeRefreshlayout4 = (SwipeRefreshLayout)rootView.findViewById(R.id.swipe_container_ad4);
    swipeRefreshlayout4.setColorSchemeResources(android.R.color.holo_blue_bright,
            android.R.color.holo_green_light, android.R.color.holo_orange_light,
            android.R.color.holo_red_light);
    
    swipeRefreshlayout1.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
		
		@Override
		public void onRefresh() {

			swipeRefreshlayout1.setRefreshing(true);
            (new Handler()).postDelayed(new Runnable() {
                @Override
                public void run() {
                	swipeRefreshlayout1.setRefreshing(false);
                	startNumber = 0;
                    getNewEntertainments(startNumber,Constants.TAG_LIFE);
                    adapter.clearList();
                }
            }, 2000);
		}
	});
    
    swipeRefreshlayout2.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
		
		@Override
		public void onRefresh() {
			
			swipeRefreshlayout2.setRefreshing(true);
            (new Handler()).postDelayed(new Runnable() {
                @Override
                public void run() {
                	swipeRefreshlayout2.setRefreshing(false);
                	startNumber = 0;
                	adapter.clearList();
                    getNewEntertainments(startNumber,Constants.TAG_NEWS);
                    
                }
            }, 2000);
		}
	});
    
    swipeRefreshlayout3.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
		
		@Override
		public void onRefresh() {
			
			swipeRefreshlayout3.setRefreshing(true);
            (new Handler()).postDelayed(new Runnable() {
                @Override
                public void run() {
                	swipeRefreshlayout3.setRefreshing(false);
                	startNumber = 0;
                	adapter.clearList();
                    getNewEntertainments(startNumber,Constants.TAG_TIPS);
                    
                }
            }, 2000);
		}
	});

    swipeRefreshlayout4.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
	
	@Override
	public void onRefresh() {
		
		swipeRefreshlayout4.setRefreshing(true);
        (new Handler()).postDelayed(new Runnable() {
            @Override
            public void run() {
            	swipeRefreshlayout4.setRefreshing(false);
            	startNumber = 0;
            	adapter.clearList();
                getNewEntertainments(startNumber,Constants.TAG_VIDEO);
                
            }
        }, 2000);
	}
});

    return rootView;
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
	
	private void getNewEntertainments(int startNumber,String sTag ){
//		homeActivity.showWaiting();
		
		Log.e("Edward", "entertainment: startNumber = " + startNumber);
	    UserInfo user_info = setUserInfo(startNumber);
	    WebServiceHelper.getInstance().onGoingEntertainmentList(user_info, sTag);
	}
	
	public void checkLoginStatus(){
	    if(!CustomApplication.getInstance().getUserLogin()){
	        startActivity(new Intent(getActivity(), LoginActivity.class));
//	        getActivity().finish();
	    }
	}

	
	@Override
	public void onHomeEntertainmentListUpdated(final int tag, GetOnGoingEntertainmentListResponse response) {
		// TODO Auto-generated method stub
		final GetOnGoingEntertainmentListResponse adList = (GetOnGoingEntertainmentListResponse) response;
        if(homeActivity != null){
            homeActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                	if(adList.data != null &&adList.data.size() == 0 ){

                	}
                	
                	if(tag == -1){
                		adapter.clearList();
                		m_tVEmpty.setVisibility(View.VISIBLE);
                	}

                	startNumber = startNumber + adList.data.size();
                	Log.e("Edward", "at ResponseSuccessCallBack, startNumber is: " + startNumber);
                	m_tVEmpty.setVisibility(View.GONE);
                    adapter.setList(adList.data);
                    adapter.notifyDataSetChanged();
                }
            });
        }
	}
	
	/**
     * 更新Tab标签的颜色，和字体的颜色
     * @param tabHost
     */ 
    private void updateTab(final TabHost tabHost) { 
        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) { 
            View view = tabHost.getTabWidget().getChildAt(i);
            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title); 
            tv.setTextSize(16); 
            tv.setTypeface(Typeface.SERIF, 0); // 设置字体和风格  
            if (tabHost.getCurrentTab() == i) {//选中  
                //view.setBackgroundDrawable(getResources().getDrawable(R.drawable.category_current));//选中后的背景  
            	tv.setTextColor(this.getResources().getColor(R.color.blue)); 
            } else {//不选中  
                //view.setBackgroundDrawable(getResources().getDrawable(R.drawable.category_bg));//非选择的背景  
            	tv.setTextColor(this.getResources().getColor(R.color.black)); 
            } 
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

}
