package com.auroratechdevelopment.common.webservice.request;

import com.auroratechdevelopment.common.webservice.WebServiceConstants;
import com.auroratechdevelopment.common.webservice.response.UpdateGCMResponse;
import com.auroratechdevelopment.common.webservice.response.UpdatePasswordResponse;
import com.auroratechdevelopment.common.webservice.util.WebUtils;

/**
 * Created by Raymond Zhuang 2016/6/2
 */
public class UpdateGCMRequest extends RequestBase<UpdateGCMResponse> {

    public String GCM_token;

    public String topic;


    public UpdateGCMRequest(String token, String topic) {
        this.GCM_token = token;
        this.topic = topic;

    }

    @Override
    public String getUri() {
        return WebUtils.getURI(String.format(WebServiceConstants.updateGCMToken));
    }

    @Override
    public String getRequestMethodType() {
        return "POST";
    }

    //same format with update profile
    @Override
    public UpdateGCMResponse getResponse() {
        return new UpdateGCMResponse();
    }

}
