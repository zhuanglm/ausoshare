/**
 * 
 */
package com.auroratechdevelopment.ausoshare;

import android.app.Application;
import android.content.Context;

import com.auroratechdevelopment.ausoshare.ui.ActivityBase;
import com.auroratechdevelopment.ausoshare.ui.home.HomeActivity;
import com.auroratechdevelopment.ausoshare.util.Constants;
import com.auroratechdevelopment.common.persistent.SharedPreferenceManager;

import java.util.Locale;

/**
 * @author Edward
 * Updated by Raymond Zhuang May 6 2016
 * 
 */
public class CustomApplication extends Application {

    private static CustomApplication instance;
    private String mUsername;
    private HomeActivity mHomeActivity;
    private String mUserToken;

    public CustomApplication() {
        instance = this;
    }

    public static Context getContext() {
        return instance;
    }

    public static CustomApplication getInstance() {
        return instance;
    }

    public static Locale appLocale = Locale.getDefault();
    public static final String TIMEZONE = "America/New_York";

    private ActivityBase mCurrentActivity = null;

    public ActivityBase getCurrentActivity() {
        return mCurrentActivity;
    }

    public void setCurrentActivity(ActivityBase mCurrentActivity) {
        this.mCurrentActivity = mCurrentActivity;
    }
    public static int activityCount;

    public void setHomeActivity(HomeActivity homeActivity) {
        mHomeActivity = homeActivity;
    }

    public HomeActivity getHomeActivity() {
        return mHomeActivity;
    }

    public void setUsername(String username) {
        SharedPreferenceManager.setString(Constants.PREF_USERNAME, username);
    }
    public String getUsername() {
        return  SharedPreferenceManager.getString(Constants.PREF_USERNAME);
    }

    public void setEmail(String email) {
        SharedPreferenceManager.setString(Constants.PREF_EMAIL, email);
    }
    public String getEmail() {
        return  SharedPreferenceManager.getString(Constants.PREF_EMAIL);
    }
    
    public void setPaypal(String email) {
        SharedPreferenceManager.setString(Constants.PREF_PAYPAL, email);
    }
    public String getPaypal() {
        return  SharedPreferenceManager.getString(Constants.PREF_PAYPAL);
    }
    
    public void setLoginOutStatus(boolean loginOutFlag){
    	SharedPreferenceManager.setBoolean(Constants.PREF_LOGINOUT_STATUS, loginOutFlag);
    }
    public boolean getLoginOutStatus(){
    	return SharedPreferenceManager.getBoolean(Constants.PREF_LOGINOUT_STATUS);
    }
    
    public void setSharedAdUrl(String url){
    	SharedPreferenceManager.setString(Constants.SHARED_AD_URL, url);
    }
    public String getSharedAdUrl(){
    	return SharedPreferenceManager.getString(Constants.SHARED_AD_URL);
    }
    
    public void setSharedAdThumb(String thumb){
    	SharedPreferenceManager.setString(Constants.SHARED_AD_THUMB, thumb);
    }
    public String getSharedAdThumb(){
    	return SharedPreferenceManager.getString(Constants.SHARED_AD_THUMB);
    }
    
    public void setSharedAdReviewURL(String reviewURL){
    	SharedPreferenceManager.setString(Constants.SHARED_AD_REVIEW_URL, reviewURL);
    }
    public String getSharedAdReviewURL(){
    	return SharedPreferenceManager.getString(Constants.SHARED_AD_REVIEW_URL);
    }
    
    public void setSharedAdDescription(String description){
    	SharedPreferenceManager.setString(Constants.SHARED_AD_DESCRIPTION, description);
    }
    public String getSharedAdDescription(){
    	return SharedPreferenceManager.getString(Constants.SHARED_AD_DESCRIPTION);
    }

    public void setRememberMeChecked(boolean rememberMe) {
        SharedPreferenceManager.setBoolean(Constants.PREF_REMEMBERME, rememberMe);
    }
    public boolean getRememberMeChecked() {
        return  SharedPreferenceManager.getBoolean(Constants.PREF_REMEMBERME);
    }

    public void setRememberPasswordChecked(boolean rememberPassword) {
        SharedPreferenceManager.setBoolean(Constants.PREF_REMEMBEPASSWORD, rememberPassword);
    }
    public boolean getRememberPasswordChecked() {
        return  SharedPreferenceManager.getBoolean(Constants.PREF_REMEMBEPASSWORD);
    }

    public void setUserToken(String userToken) {
        SharedPreferenceManager.setString(Constants.PREF_USER_TOKEN, userToken);
    }
    public String getUserToken() {
        return SharedPreferenceManager.getString(Constants.PREF_USER_TOKEN);
    }

    public void setAvailableFund(String availableFund){
        SharedPreferenceManager.setString(Constants.AVAILABLE_FUND, availableFund);
    }

    public String getAvailableFund(){
        return SharedPreferenceManager.getString(Constants.AVAILABLE_FUND);
    }

    public void setUserId(long userId) {
        SharedPreferenceManager.setLong(Constants.PREF_USER_ID, userId);
    }
    public long getUserId() {
        return SharedPreferenceManager.getLong(Constants.PREF_USER_ID);
    }
    
    
    public void setNotFirstTimeUse(boolean first_time){
    	SharedPreferenceManager.setBoolean(Constants.FIRST_TIME_USE, first_time);
    }
    public Boolean getNotFirstTimeUse(){
    	return SharedPreferenceManager.getBoolean(Constants.FIRST_TIME_USE);
    }

    public void setCategoryID(String categoryID){
        SharedPreferenceManager.setString(Constants.PREF_ITEMLIST, categoryID);
    }

    public String getCategoryID(){
        return SharedPreferenceManager.getString(Constants.PREF_ITEMLIST);
    }

    public void setScreenWidth(int screenWidth) {
        SharedPreferenceManager.setInt(Constants.PREF_USER_SCREENWIDTH, screenWidth);
    }
    public int getScreenWidth() {
        return SharedPreferenceManager.getInt(Constants.PREF_USER_SCREENWIDTH);
    }

    public void setUserCountry(String country){
        SharedPreferenceManager.setString(Constants.USER_COUNTRY, country);
    }

    public String getUserCountry(){
        return SharedPreferenceManager.getString(Constants.USER_COUNTRY);
    }

    public void setUserProvince(String province){
        SharedPreferenceManager.setString(Constants.USER_PROVINCE, province);
    }

    public String getUserProvince(){
        return SharedPreferenceManager.getString(Constants.USER_PROVINCE);
    }

    public void setUserCity(String city){
        SharedPreferenceManager.setString(Constants.USER_CITY, city);
    }

    public String getUserCity(){
        return SharedPreferenceManager.getString(Constants.USER_CITY);
    }

    public void setUserLatitude(String latitude){
        SharedPreferenceManager.setString(Constants.USER_LATITUDE, latitude);
    }

    public String getUserLatitude(){
        return SharedPreferenceManager.getString(Constants.USER_LATITUDE);
    }

    public void setUserLongitude(String longitude){
        SharedPreferenceManager.setString(Constants.USER_LONGITUDE, longitude);
    }

    public String getUserLongitude(){
        return SharedPreferenceManager.getString(Constants.USER_LONGITUDE);
    }
    
    public void setSharedADTime(int sharedTime){
    	SharedPreferenceManager.setInt(Constants.SHARED_AD_TIME, sharedTime);
    }
    
    public int getSharedADTime(){
    	return SharedPreferenceManager.getInt(Constants.SHARED_AD_TIME);
    }
//    
//    public void setFragNeedWaiting(boolean waitingFlag){
//    	SharedPreferenceManager.setBoolean(Constants.FRAG_NEED_WAITING, waitingFlag);
//    }
//    
//    public boolean getFragNeedWaiting(){
//    	return SharedPreferenceManager.getBoolean(Constants.FRAG_NEED_WAITING);
//    }
    
    

//    public void setNickName(String nickName){
//        SharedPreferenceManager.setString(Constants.NICK_NAME, nickName);
//    }

//    public String getNickName(){
//        return SharedPreferenceManager.getString(Constants.NICK_NAME);
//    }

    public void setUserEmail(String email){
        SharedPreferenceManager.setString(Constants.USER_EMAIL, email);
    }

    public String getUserEmail(){
        return SharedPreferenceManager.getString(Constants.USER_EMAIL);
    }

    public void setAndroidID(String androidID){
        SharedPreferenceManager.setString(Constants.ANDROID_ID, androidID);
    }

    public String getAndroidID(){
        return SharedPreferenceManager.getString(Constants.ANDROID_ID);
    }

    public void setUserLogin(boolean login_flag){
        SharedPreferenceManager.setBoolean(Constants.USER_LOGIN,login_flag);
    }

    public boolean getUserLogin(){
        return SharedPreferenceManager.getBoolean(Constants.USER_LOGIN);
    }

//    private DatabaseReader databaseReader;
//    private DatabaseWriter databaseWriter;
//    private CartReader cartReader;
//    private CartWriter cartWriter;

//    private DatabaseReader getDatabaseReader() {
//        if (databaseReader == null) {
//            databaseReader = new DatabaseReader(getContentResolver());
//        }
//        return databaseReader;
//    }
//
//    private DatabaseWriter getDatabaseWriter() {
//        if (databaseWriter == null) {
//            databaseWriter = new DatabaseWriter(getContentResolver());
//        }
//        return databaseWriter;
//    }
//
//    public final CartReader getCartReader() {
//        if (cartReader == null) {
//            cartReader = new CartReader(getDatabaseReader());
//        }
//        return cartReader;
//    }
//
//    public final CartWriter getCartWriter() {
//        if (cartWriter == null) {
//            cartWriter = new CartWriter(getDatabaseWriter());
//        }
//        return cartWriter;
//    }



}
