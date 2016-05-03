/**
 *
 */
package com.auroratechdevelopment.ausoshare.ui.home;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.auroratechdevelopment.ausoshare.CustomApplication;
import com.auroratechdevelopment.ausoshare.R;
import com.auroratechdevelopment.common.webservice.WebServiceHelper;
import com.auroratechdevelopment.common.webservice.models.AdDataItem;
import com.auroratechdevelopment.common.webservice.models.OnGoingAdItem;
import com.auroratechdevelopment.common.webservice.models.ProductItem;
import com.auroratechdevelopment.common.webservice.response.GetOnGoingAdListResponse;
import com.auroratechdevelopment.common.webservice.response.ResponseBase;
//import com.tencent.mm.sdk.platformtools.Log;

import java.util.ArrayDeque;
import java.util.ArrayList;

/**
 * @author Edward Liu
 * Updated by Raymond Zhuang 2016/5/3
 */
public class HomeFragmentBase extends Fragment
        implements
        OnGoingAdItemsAdapter.GetItemSelected,
        SwipeRefreshLayout.OnRefreshListener {

    public static String searchString = "";
    protected HomeActivity homeActivity;
    protected final String KEY_PRODUCTS = "app.products";
    protected ListView list;
    private OnGoingAdItemsAdapter adapter;
    protected SwipeRefreshLayout swipeRefreshLayout;
    protected int currentPage = 1;
    protected int startNumber = 0;
    
	public ProgressDialog myDialog;
	public FragmentManager fragmentManager;
	public String curFragmentTag = "";
	

    public void onItemDetails(AdDataItem item) {

        homeActivity.showWaiting();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideSoftBoard();
    }
    
    private void hideSoftBoard(){

    	if(getActivity().getCurrentFocus()!=null)  
    	{  
    		((InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE))  
    		.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),  
                  InputMethodManager.HIDE_NOT_ALWAYS);   
    	}  

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            if (homeActivity != null) {
            }
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    	View rootView = inflater.inflate(R.layout.fragment_home_adlist_v2, container, false);
//
//
//        CustomApplication.getInstance().setCategoryID("-1");
//        currentPage = 1;
//        startNumber = 0;
//        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
//
//        list = (ListView) rootView.findViewById(R.id.valid_ad_list);
//        list.setEmptyView(rootView.findViewById(R.id.empty_list));
//        list.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
//                if (scrollState == SCROLL_STATE_IDLE) {
//                    if (list.getLastVisiblePosition() >= list.getCount() - 1 - 0) {
//                        currentPage++;
//                        startNumber = 0;
////                        getItems(true);
//                    }
//                }
//            }
//
//            @Override
//            public void onScroll(AbsListView absListView, int i, int i2, int i3) {
//
//            }
//        });
//        adapter = new OnGoingAdItemsAdapter(this.getActivity(), new ArrayList<AdDataItem>());
//        adapter.setListener(this);
//        list.setAdapter(adapter);
//
//        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);
//        swipeRefreshLayout.setOnRefreshListener(this);
//
//        homeActivity.setListener();
//
        return rootView;
    }

//    protected void getItems(final boolean getMore) {
//        if (getSortType() == HomeMyFavoritesFragment.SORT_FAVORITE) {
//            WebServiceHelper.getInstance().getProductList((getMore ? HomeMyFavoritesFragment.PRODUCT_LIST_FAVARITE_GETMORE : HomeMyFavoritesFragment.PRODUCT_LIST_FAVARITE), getSortType(), currentPage, Constants.PRODUCT_PAGE_SIZE, searchString, "");
//        } else {
//            WebServiceHelper.getInstance().getProductList((getMore ? 0 : -1), getSortType(), currentPage, Constants.PRODUCT_PAGE_SIZE, searchString, "");
//        }
//    }
//
//    protected void getItemsWithCategory(final boolean getMore, String category) {
//        WebServiceHelper.getInstance().getProductList((getMore ? 0 : -1), getSortType(), currentPage, Constants.PRODUCT_PAGE_SIZE, searchString, category);
//    }

    protected int getSortType() {
        return 0; //default for Item List
    }

    public void setHomeActivity(HomeActivity activity) {
        this.homeActivity = activity;
    }

    public boolean ResponseSuccessCallBack(final int tag, final ResponseBase response) {


//       if (response instanceof GetOnGoingAdListResponse) {
//           final GetOnGoingAdListResponse adList = (GetOnGoingAdListResponse) response;
//           if(homeActivity != null){
//               homeActivity.runOnUiThread(new Runnable() {
//                   @Override
//                   public void run() {
//                       swipeRefreshLayout.setRefreshing(false);
//                       adapter.clearList();
//                       adapter.setList(adList.data);
//                       adapter.notifyDataSetChanged();
//                   }
//               });
//            }
//            return true;
//        }
    	
    	
    	if(response instanceof GetOnGoingAdListResponse){
            final GetOnGoingAdListResponse adList = (GetOnGoingAdListResponse) response;
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
                        adapter.setList(adList.data);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
            return true;
        }
    	else if(response instanceof GetOnGoingAdListResponse){
	        final GetOnGoingAdListResponse adList = (GetOnGoingAdListResponse) response;
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
	                    adapter.setList(adList.data);
	                    adapter.notifyDataSetChanged();
	                }
	            });
	        }
	        return true;
	    }
        return true;
    }

    public boolean ResponseFailedCallBack(int tag, ResponseBase response) {

        return false;
    }

    public void onItemDetails(AdDataItem item, int position) {
        homeActivity.showWaiting();
    }


    public static boolean isNumber(String value) {
        return isInteger(value) || isDouble(value);
    }

    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            if (value.contains("."))
                return true;
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    protected void onRegreshStartedOverride(){
        currentPage =1;
    }


    @Override
    public void onRefresh() {
        onRegreshStartedOverride();
    }
    
    @Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        android.app.Fragment f = fragmentManager.findFragmentByTag(curFragmentTag);

        f.onActivityResult(requestCode, resultCode, data);
    }


}
