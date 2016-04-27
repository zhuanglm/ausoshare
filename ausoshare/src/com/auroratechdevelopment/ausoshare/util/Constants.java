package com.auroratechdevelopment.ausoshare.util;

//import com.tencent.mm.sdk.modelbase.BaseResp;

import com.tencent.mm.sdk.openapi.BaseResp;

public class Constants {

    /**
     * Integer conversion error codes
     */
	
    public static final String APP_ID = "wx6bb3add97b7da9f9";
    public static final String WX_APP_SECRET = "wx6bb3add97b7da9f9";
    public static BaseResp	WXresp;
    
    public static class ShowMsgActivity {
		public static final String STitle = "showmsg_title";
		public static final String SMessage = "showmsg_message";
		public static final String BAThumbData = "showmsg_thumb_data";
	}
	
    public static final int ERROR_CODE = -1;
    public static final String CHARSET_BINARY = "UTF-8";

    public static final String ACTIVITY_TAG = "com.auroratechdevelopment.common.Activity";
    public static final String ADDTOCART_TAG = "com.auroratechdevelopment.common.AddToCart";
    public static final String ADDTOCART_PRODUCT_DETAIL_TAG = "com.auroratechdevelopment.common.AddToCartProduectDetalQuantity";

    public static final String TopBarBackButtonTag = "com.auroratechdevelopment.common.TopBarBackButtonTitle";
    public static final String TitleBarTitleTag = "com.auroratechdevelopment.common.TilteBarTitle";

    public static final String AndroidPushId = "";
    public static final String BUNDLE_ITEM_DETAIL = "com.app.productDetail";

    public static final int PRODUCT_PAGE_SIZE = 20;
    public static final int ADS_PAGE_SIZE = 10;
    public static final int ORDER_PAGE_SIZE = 10;
    public static final int MAX_ITEM_TO_ORDER = 9999;
    public static final int PAYMENT_METHOD = 4; //COD

    public static final String PREF_USER_ID = "com.app.userid";

    public static final String PREF_USER_TOKEN = "com.app.usertoken";
    public static final String PREF_USERNAME = "com.app.username";

    public static final String PREF_EMAIL = "com.app.email";
    public static final String PREF_LOGINOUT_STATUS = "com.app.loginout_status";

    public static final String PREF_USER_SCREENWIDTH = "com.app.screenwidth";

    public static final String PREF_REMEMBERME = "com.app.rememberme";
    public static final String PREF_REMEMBEPASSWORD = "com.app.rememberpassword";

    public static final String BUNDLE_ORDER_DETAIL = "com.app.orderDetail";
    public static final String BUNDLE_ORDER_ITEM_PRICE = "com.app.regularPrice";
    public static final String BUNDLE_ORDER_ITEM_NUMBER = "com.app.selectedQuantity";

    public static final String BUNDLE_AD_DETAIL = "com.app.AdDetail";
    public static final String BUNDLE_AD_ITEM = "com.app.ad_item";
    public static final String BUNDLE_AD_DETAIL_REVIEWURL = "com.app.AdDetail.reviewurl";

    public static final double DEFAULT_MAP_LAT = 43.8466676;
    public static final double DEFAULT_MAP_LON = -79.3486446;
    public static final String STORE_ADDRESS ="290 Yorktech Drive, Markham, Ontario";
    public static final String STORE_EMAIL ="infinitycellphone@gmail.com";
    public static final int ZOOM_LEVEL = 14;

    public static final int ORDER_TYPE_ALL = 0; //ALL
    public static final int ORDER_TYPE_PENDING = 1; //PENDING
    public static final int ORDER_TYPE_COMPLETED = 1; //COMPLETED

    public static final String PREF_ITEMLIST ="com.app.itemlist";

    public static final String HOME_TO_FACE_BODY_CARE ="com.xinyuhengtai.hometoface_body";

    public static final int HOME_TO_FACE = 1;
    public static final int HOME_TO_BODY = 2;

    public static final String USER_COUNTRY ="com.app.user_country";
    public static final String USER_PROVINCE="com.app.user_province";
    public static final String USER_CITY="com.app.user_city";
    public static final String USER_LATITUDE ="com.app.user_latitude";
    public static final String USER_LONGITUDE="com.app.user_longitude";

    public static final String NICK_NAME ="com.app.nick_name";
    public static final String USER_EMAIL = "com.app.user_email";
    public static final String ANDROID_ID = "com.app.android_id";

    public static final String USER_LOGIN ="com.app.user_login";

    public static final String CONTACT_URL="com.app.contact.url";
    public static final String ADID_URL="com.app.home.ad.url";

    public static final String AVAILABLE_FUND ="com.app.available.fund";

    public static final int CURRENT_INCOME_TOP_USER_MAXNUMBER =  10;

    public static final String LAST_PAGE ="com.app.last_page";
    public static final String LOGIN_STATUS = "login_status";

    public static final String HOME_PAGE = "home";
    public static final String YELLOW_PAGE = "yellow_page";
    public static final String COUPON_PAGE = "coupon";
    public static final String CONTACT_PAGE = "contact";
    public static final String MINE_PAGE = "mine";
    public static final String ENTERTAINMENT_PAGE = "entertainment";
    public static final String PROMOTION_PAGE = "promotion";
    
    
    public static final String LOGIN_PAGE_FROM_AD_PREPARE_SHARE="ad_prepare_share";
    public static final String LOGIN_PAGE_FROM_LOGIN="my_login";
       
    public static final String FIRST_TIME_USE ="com.app.first_time_use";
    
    public static final String SHARED_AD_ID = "com.app.shared_ad_id";
    public static final String SHARED_AD_URL = "com.app.shared_ad_url";
    public static final String SHARED_AD_TIME = "com.app.shared_ad_time";
    public static final String SHARED_AD_THUMB = "com.app.shared_ad_thumb";
    public static final String SHARED_AD_DESCRIPTION = "com.app.shared_ad_description";
    public static final String SHARED_AD_REVIEW_URL = "com.app.shared_ad_review_url";
//    public static final String FRAG_NEED_WAITING = "com.app.frag_need_waiting";
    
    //fragment definition
    public static final int FRAG_HOME = 0;
    public static final int FRAG_ENTERTAINMENT = 1;
    public static final int FRAG_CONTACT = 2;
    public static final int FRAG_PROFILE = 3;
    public static final int FRAG_YELLOW_PAGE = 4;
    public static final int FRAG_PROMOTION = 5;
    

    //advertisement or entertainment
    public static final String TAG_ADVERT = "advert";
    public static final String TAG_LIFE = "life";
    public static final String TAG_FUN = "fun";
    public static final String TAG_NEWS = "news";
    public static final String TAG_TIPS = "tips";
    public static final String TAG_VIDEO = "video";
    
    public static final String SHARE_TO_FRIENDS ="friends";
    public static final String SHARE_TO_MOMENTS="moments";
    
}
