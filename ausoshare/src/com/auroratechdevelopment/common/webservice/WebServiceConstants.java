package com.auroratechdevelopment.common.webservice;


import com.auroratechdevelopment.ausoshare.BuildConfig;

public class WebServiceConstants {
    public static final float WebServiceConnectionTimeout = 45F;
    public static final float WebServiceReadTimeout = 45F;

    public static final String PLAY_STORE = "https://play.google.com/store/apps/details?id=com.auroratechdevelopment.ausoshare";
    
    public static final boolean DoDebug = BuildConfig.DEBUG;
 
    //public static final String WebHost = "http://auroratech.applinzi.com";     //"http://api.ausomedia.com";
    public static final String WebHost = "http://api.ausoshare.com";
    //public static final String WebHost = "http://192.168.0.30:8888";
    
    public static final String splashAdImageURL = "http://api.ausomedia.com/splash_ad_image.jpg";
    public static final String splashAdImageURL_EN = "http://api.ausomedia.com/splash_ad_image_en.jpg";
    public static final String top_banner_EN = "http://api.ausomedia.com/top-banner-en.png";
    public static final String top_banner_ZH = "http://api.ausomedia.com/top-banner-zh.png";

    public static int ClientCertResId = -1;
    public static final String ClientCertPassword = "";

    public static String userRegister = "user/register";
    public static String userLogin = "user/login";
    public static String userUpdatePassword = "user/password/update";
    public static String forgotPassword = "user/password/forgot";
    public static String fetchUserProfile = "user/profile/get";
    public static String updateUserProfile = "user/profile/update";
    public static String updateUserPassword = "user/password/update";
    public static String updateGCMToken = "user/gcm/update";
    public static String withdrawRequest = "company/withdraw_request";
    public static String updateSharedTime = "advert/addShare";
    public static String ADupdate = "advert/updateItems";

    public static String statsUserIncomeToplist = "stats/user_income_toplist";
    public static String uploadAvatar = "stats/upload_avatar";

    public static String statsForwardedAdHistory = "stats/forwarded_ad_history";
    public static String statsWithdrawHistory= "stats/withdraw_history";
    public static String advertOngoingList = "advert/ongoing_list";
    public static String advertFinishedList = "advert/finished_list";
    public static String advertOngoingDetail = "advert/ongoing_detail";
    public static String companyTermsConditions = "company/terms_conditions";
    public static String companyIncomeRule = "company/income_rule";
    public static String companyWithdrawRule="company/withdraw_rule";
    public static String companyIntroduction = "company/introduction";
    public static String companyQuestionsAnswers = "company/questions_answers";
    
    public static String entertainmentRequest = "life_news/get_information";

}
