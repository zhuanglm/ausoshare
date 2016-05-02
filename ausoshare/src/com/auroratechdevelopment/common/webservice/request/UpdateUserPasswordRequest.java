package com.auroratechdevelopment.common.webservice.request;

import com.auroratechdevelopment.common.webservice.WebServiceConstants;
import com.auroratechdevelopment.common.webservice.response.UpdateUserProfileResponse;
import com.auroratechdevelopment.common.webservice.util.WebUtils;

/**
 * Created by Raymond Zhuang 2016/5/2
 */
public class UpdateUserPasswordRequest extends RequestBase<UpdateUserProfileResponse> {

    public String deviceID;
    
    public String email;
    public String password;
    public String new_password;

    public UpdateUserPasswordRequest(String token, String deviceID,String email,String pwd,String new_pwd) {
        this.token = token;
        this.deviceID = deviceID;
        
        this.email = email;
        this.password = pwd;
        this.new_password = new_pwd;
    }

    @Override
    public String getUri() {
        return WebUtils.getURI(String.format(WebServiceConstants.updateUserPassword));
    }

    @Override
    public String getRequestMethodType() {
        return "POST";
    }


    @Override
    public UpdateUserProfileResponse getResponse() {
        return new UpdateUserProfileResponse();
    }

}
