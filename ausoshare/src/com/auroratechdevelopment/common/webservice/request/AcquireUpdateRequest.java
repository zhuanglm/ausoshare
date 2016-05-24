package com.auroratechdevelopment.common.webservice.request;

import com.auroratechdevelopment.common.webservice.WebServiceConstants;
import com.auroratechdevelopment.common.webservice.response.AcquireUpdateResponse;
import com.auroratechdevelopment.common.webservice.util.WebUtils;

/**
 * Created by Raymond Zhuang 2016/5/4
 */
public class AcquireUpdateRequest extends RequestBase<AcquireUpdateResponse> {

    public String deviceID;
    public String last_adID;
    public String email;
    

    public AcquireUpdateRequest(String token, String deviceID,String email,String adId) {
        this.token = token;
        this.deviceID = deviceID;
        last_adID = adId;
        this.email = email;
        
    }

    @Override
    public String getUri() {
        return WebUtils.getURI(String.format(WebServiceConstants.ADupdate));
    }

    @Override
    public String getRequestMethodType() {
        return "POST";
    }


    @Override
    public AcquireUpdateResponse getResponse() {
        return new AcquireUpdateResponse();
    }

}

