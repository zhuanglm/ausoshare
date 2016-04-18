package com.auroratechdevelopment.common.webservice.request;

import com.auroratechdevelopment.common.webservice.WebServiceConstants;
import com.auroratechdevelopment.common.webservice.response.LoginResponse;
import com.auroratechdevelopment.common.webservice.util.WebUtils;

/**
 * Created by Edward on 2015-04-14.
 */
public class LoginRequest extends RequestBase<LoginResponse> {

    public String email;
    public String password;
    public String deviceID;

    public LoginRequest(String email, String password, String deviceId) {
        this.email = email;
        this.password = password;
        this.deviceID = deviceId;
    }

    @Override
    public String getUri() {

        return WebUtils.getURI(WebServiceConstants.userLogin);
    }

    @Override
    public LoginResponse getResponse() {

        return new LoginResponse();
    }

    @Override
    public String getRequestMethodType() {

        return "POST";
    }

}
