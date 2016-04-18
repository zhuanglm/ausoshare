package com.auroratechdevelopment.common.webservice.request;

import com.auroratechdevelopment.common.webservice.WebServiceConstants;
import com.auroratechdevelopment.common.webservice.response.RegisterResponse;
import com.auroratechdevelopment.common.webservice.util.WebUtils;

/**
 * Created by Edward liu on 1/14/2015.
 */
public class RegisterRequest extends RequestBase<RegisterResponse> {

    public String nickname;
    public String email;
    public String password;
    public String deviceID;

    public RegisterRequest(String nickName, String email, String password, String deviceID) {
        this.nickname = nickName;
        this.email = email;
        this.password = password;
        this.deviceID = deviceID;
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
