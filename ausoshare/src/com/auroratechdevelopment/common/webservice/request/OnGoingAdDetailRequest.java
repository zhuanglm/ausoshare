package com.auroratechdevelopment.common.webservice.request;

import com.auroratechdevelopment.common.webservice.WebServiceConstants;
import com.auroratechdevelopment.common.webservice.response.OnGoingAdDetailResponse;
import com.auroratechdevelopment.common.webservice.util.WebUtils;

//Updated by Raymond Zhuang May 10 2016

public class OnGoingAdDetailRequest extends RequestBase<OnGoingAdDetailResponse> {

    public String email;
    public String deviceID;
    public String tag;
    public String adID;

    public OnGoingAdDetailRequest( String email, String Token, String deviceId, String tag, String adID ) {
        this.email = email;
        this.deviceID = deviceId;
        this.tag = tag;
        this.adID = adID;
        this.token = Token;
    }

    @Override
    public String getUri() {
        return WebUtils.getURI(String.format(WebServiceConstants.advertOngoingDetail));
    }

    @Override
    public String getRequestMethodType() {
        return "POST";
    }

    @Override
    public OnGoingAdDetailResponse getResponse() {
        return new OnGoingAdDetailResponse();
    }

}