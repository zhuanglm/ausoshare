package com.auroratechdevelopment.common.webservice.request;

import com.auroratechdevelopment.common.webservice.WebServiceConstants;
import com.auroratechdevelopment.common.webservice.response.UpdateUserProfileResponse;
import com.auroratechdevelopment.common.webservice.response.WithdrawRequestResponse;
import com.auroratechdevelopment.common.webservice.util.WebUtils;

public class WithdrawRequestRequest extends RequestBase<WithdrawRequestResponse> {

    public String deviceID;
    public String email;

    public WithdrawRequestRequest(String token, String deviceID, String email) {
        this.token = token;
        this.deviceID = deviceID;
        this.email = email;
    }

    @Override
    public String getUri() {
        return WebUtils.getURI(String.format(WebServiceConstants.withdrawRequest));
    }

    @Override
    public String getRequestMethodType() {
        return "POST";
    }


    @Override
    public WithdrawRequestResponse getResponse() {
        return new WithdrawRequestResponse();
    }

}