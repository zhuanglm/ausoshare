package com.auroratechdevelopment.ausoshare.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by happy pan on 2015/11/5.
 */
public class CheckNetworkStatus {

    private boolean networkStatus = false;

    public CheckNetworkStatus(Context context){
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected())
            {
                // the network is available
                if (info.getState() == NetworkInfo.State.CONNECTED)
                {
                    // return true
                    setNetworkStatus(true);
                }
            }
        }else {
            setNetworkStatus(false);
        }
    }

    public void isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected())
            {
                // the network is available
                if (info.getState() == NetworkInfo.State.CONNECTED)
                {
                    // return true
                    setNetworkStatus(true);
                }
            }
        }
        setNetworkStatus(false);
    }

    public void setNetworkStatus(boolean networkStatus){
        this.networkStatus = networkStatus;
    }

    public boolean getNetworkStatus(){
        return networkStatus;
    }

}
