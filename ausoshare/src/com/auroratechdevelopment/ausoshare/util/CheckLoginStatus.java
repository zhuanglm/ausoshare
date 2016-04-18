package com.auroratechdevelopment.ausoshare.util;

import com.auroratechdevelopment.ausoshare.CustomApplication;

/**
 * Created by happy pan on 2015/11/5.
 */
public class CheckLoginStatus {

    public CheckLoginStatus(){
        if(!CustomApplication.getInstance().getUserLogin()){

        }
    }
}
