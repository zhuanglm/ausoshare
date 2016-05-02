package com.auroratechdevelopment.common.webservice.request;

import com.auroratechdevelopment.common.webservice.WebServiceConstants;
import com.auroratechdevelopment.common.webservice.response.UpdateUserProfileResponse;
import com.auroratechdevelopment.common.webservice.util.WebUtils;

/**
 * Created by Raymond Zhuang 2016/5/2
 */
public class UpdateSharedTimeRequest extends RequestBase<UpdateUserProfileResponse> {

    public String deviceID;
    public String adID;
    public String email;
    

    public UpdateSharedTimeRequest(String token, String deviceID,String email,String adId) {
        this.token = token;
        this.deviceID = deviceID;
        adID = adId;
        this.email = email;
        
    }

    @Override
    public String getUri() {
        return WebUtils.getURI(String.format(WebServiceConstants.updateSharedTime));
    }

    @Override
    public String getRequestMethodType() {
        return "POST";
    }


    @Override
    public UpdateUserProfileResponse getResponse() {
        return new UpdateUserProfileResponse();
    }

}
