package com.auroratechdevelopment.common.webservice.request;

import com.auroratechdevelopment.common.webservice.WebServiceConstants;
import com.auroratechdevelopment.common.webservice.response.RegisterResponse;
import com.auroratechdevelopment.common.webservice.util.WebUtils;

/**
 * Created by Edward liu on 1/14/2015.
 * updated by Raymond Zhuang May 4 2016
 */
public class RegisterRequest extends RequestBase<RegisterResponse> {

    public String nickname;
    public String email;
    public String password;
    public String deviceID;
    public String promotion_code;

    public RegisterRequest(String nickName, String email, String password, String deviceID, String code) {
        this.nickname = nickName;
        this.email = email;
        this.password = password;
        this.deviceID = deviceID;
        promotion_code = code;
    }

    @Override
    public String getUri() {
        return WebUtils.getURI(String.format(WebServiceConstants.userRegister));
    }

    @Override
    public String getRequestMethodType() {
        return "POST";
    }


    @Override
    public RegisterResponse getResponse() {
        return new RegisterResponse();
    }

}
