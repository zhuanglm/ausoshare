package com.auroratechdevelopment.common.webservice.request;

import com.auroratechdevelopment.common.webservice.WebServiceConstants;
import com.auroratechdevelopment.common.webservice.response.CurrentIncomeResponse;
import com.auroratechdevelopment.common.webservice.response.RegisterResponse;
import com.auroratechdevelopment.common.webservice.util.WebUtils;

/**
 * Created by Edward liu on 1/14/2015.
 */
public class CurrentIncomeRequest extends RequestBase<CurrentIncomeResponse> {

    public String email;
    public String deviceID;
    public int maxNumber;

    public CurrentIncomeRequest(String email,String deviceID, String token, int maxnumber) {
        this.email = email;
        this.deviceID = deviceID;
        this.token = token;
        this.maxNumber = maxnumber;
    }

    @Override
    public String getUri() {
        return WebUtils.getURI(String.format(WebServiceConstants.statsUserIncomeToplist));
    }

    @Override
    public String getRequestMethodType() {
        return "POST";
    }


    @Override
    public CurrentIncomeResponse getResponse() {
        return new CurrentIncomeResponse();
    }

}
