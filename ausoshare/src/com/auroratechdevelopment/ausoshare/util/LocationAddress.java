package com.auroratechdevelopment.ausoshare.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;

import com.auroratechdevelopment.ausoshare.CustomApplication;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationAddress {
    private static final String TAG = "LocationAddress";

    public static void getAddressFromLocation(final double latitude, final double longitude,
                                              final Context context, final Handler handler) {
        Thread thread = new Thread() {
            @Override
            public void run() {
            	//get the OS default language
//                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            	//set the language is English
            	Geocoder geocoder = new Geocoder(context,Locale.ENGLISH);
                
                String result = null;
                String result_country = null;
                String result_province = null;
                String result_city = null;
                String result_lantitude = null;
                String result_longitude = null;

                try {
                    List<Address> addressList = geocoder.getFromLocation(
                            latitude, longitude, 1);

                    if (addressList != null && addressList.size() > 0) {
                        Address address = addressList.get(0);
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                            sb.append(address.getAddressLine(i)).append("\n");
                        }
                        sb.append(address.getLocality()).append("\n");
                        sb.append(address.getAdminArea()).append("\n");
                        sb.append(address.getCountryName()).append("\n");
                        result = sb.toString();

                        result_country = address.getCountryCode();
                        result_province = address.getAdminArea();
                        result_city = address.getLocality();
                        result_lantitude = String.valueOf(latitude);
                        result_longitude = String.valueOf(longitude);
                        
                        CustomApplication.getInstance().setUserCountry(result_country);
                        CustomApplication.getInstance().setUserProvince(result_province);
                        CustomApplication.getInstance().setUserCity(result_city);
                        CustomApplication.getInstance().setUserLatitude(result_lantitude);
                        CustomApplication.getInstance().setUserLongitude(result_longitude);
                        

                        System.out.print("result_country is: " + result_country + ", AdminArea is: " + result_province + ", Locality is: " + result_city+"\n\n");

                    }
                } catch (IOException e) {
                    Log.e(TAG, "Unable connect to Geocoder", e);
                } finally {
                    Message message = Message.obtain();
                    message.setTarget(handler);
                    if (result != null) {
                        message.what = 1;
                        Bundle bundle = new Bundle();
                        result = "Latitude: " + latitude + " Longitude: " + longitude +
                                "\n\nAddress:\n" + result;
                        bundle.putString("address", result);

                        message.setData(bundle);

                        CustomApplication.getInstance().setUserCountry(result_country);
                        CustomApplication.getInstance().setUserProvince(result_province);
                        CustomApplication.getInstance().setUserCity(result_city);
                        CustomApplication.getInstance().setUserLatitude(result_lantitude);
                        CustomApplication.getInstance().setUserLongitude(result_longitude);

                    } else {
                        message.what = 1;
                        Bundle bundle = new Bundle();
                        result = "Latitude: " + latitude + " Longitude: " + longitude +
                                "\n Unable to get address for this lat-long.";
                        bundle.putString("address", result);

                        message.setData(bundle);
                    }
                    message.sendToTarget();
                }
            }
        };
        thread.start();
    }
    

}
