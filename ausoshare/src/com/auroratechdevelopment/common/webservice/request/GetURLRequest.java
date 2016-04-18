package com.auroratechdevelopment.common.webservice.request;

import com.auroratechdevelopment.common.webservice.WebServiceConstants;
import com.auroratechdevelopment.common.webservice.response.GetURLResponse;
import com.auroratechdevelopment.common.webservice.response.RegisterResponse;
import com.auroratechdevelopment.common.webservice.util.WebUtils;

/**
 * Created by Edward liu on 1/14/2015.
 */
public class GetURLRequest extends RequestBase<GetURLResponse> {
    public String deviceID;
    public String email;

    public String requestContent;

    public GetURLRequest(String deviceID, String email, String token, String requestContent) {
        this.deviceID = deviceID;
        this.email = email;
        this.token = token;

        this.requestContent =  requestContent;
    }

    @Override
    public String getUri() {
        return WebUtils.getURI(requestContent);
    }

    @Override
    public String getRequestMethodType() {
        return "POST";
    }


    @Override
    public GetURLResponse getResponse() {
        return new GetURLResponse();
    }

}
