package com.auroratechdevelopment.common.webservice.request;

import com.auroratechdevelopment.common.webservice.WebServiceConstants;
import com.auroratechdevelopment.common.webservice.response.CurrentIncomeResponse;
import com.auroratechdevelopment.common.webservice.response.ForwardedAdHistoryResponse;
import com.auroratechdevelopment.common.webservice.util.WebUtils;

/**
 * Created by Edward liu on 1/14/2015.
 */
public class ForwardedAdHistoryRequest extends RequestBase<ForwardedAdHistoryResponse> {

    public String email;
    public String deviceID;
    public int startNumber;
    public int count;

    public ForwardedAdHistoryRequest(String email, String token, String deviceID, int startNumber, int count) {
        this.email = email;
        this.token = token;
        this.deviceID = deviceID;
        this.startNumber = startNumber;
        this.count = count;
    }

    @Override
    public String getUri() {
        return WebUtils.getURI(String.format(WebServiceConstants.statsForwardedAdHistory));
    }

    @Override
    public String getRequestMethodType() {
        return "POST";
    }


    @Override
    public ForwardedAdHistoryResponse getResponse() {
        return new ForwardedAdHistoryResponse();
    }

}
