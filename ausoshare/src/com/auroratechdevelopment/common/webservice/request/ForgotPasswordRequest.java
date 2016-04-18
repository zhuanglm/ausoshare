package com.auroratechdevelopment.common.webservice.request;

import com.auroratechdevelopment.common.webservice.WebServiceConstants;
import com.auroratechdevelopment.common.webservice.response.ForgotPasswordResponse;
import com.auroratechdevelopment.common.webservice.response.RegisterResponse;
import com.auroratechdevelopment.common.webservice.util.WebUtils;

/**
 * Created by Edward liu on 1/14/2015.
 */
public class ForgotPasswordRequest extends RequestBase<ForgotPasswordResponse> {

    public String email;
    public String deviceID;

    public ForgotPasswordRequest(String email, String deviceID) {
        this.email = email;
        this.deviceID = deviceID;
    }

    @Override
    public String getUri() {
        return WebUtils.getURI(String.format(WebServiceConstants.forgotPassword));
    }

    @Override
    public String getRequestMethodType() {
        return "POST";
    }


    @Override
    public ForgotPasswordResponse getResponse() {
        return new ForgotPasswordResponse();
    }

}
