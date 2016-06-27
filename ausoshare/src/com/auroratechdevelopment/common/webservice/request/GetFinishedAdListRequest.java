package com.auroratechdevelopment.common.webservice.request;

import com.auroratechdevelopment.common.webservice.WebServiceConstants;
import com.auroratechdevelopment.common.webservice.models.UserInfo;
import com.auroratechdevelopment.common.webservice.response.GetOnGoingAdListResponse;
import com.auroratechdevelopment.common.webservice.util.WebUtils;

/**
 * Created by Raymond Zhuang on 2016-06-08.
 */
public class GetFinishedAdListRequest extends RequestBase<GetOnGoingAdListResponse> {

    public String email;
    public String deviceID;
    public String tag;  //advert means advertisement; life means entertainment
    public UserInfo data;
    public String keyword;

    public GetFinishedAdListRequest(String email, String Token, String deviceId, UserInfo data, String tag, String key) {
        this.email = email;
        this.deviceID = deviceId;
        this.data = data;
        this.token = Token;
        this.tag = tag;
        keyword = key;
    }

    @Override
    public String getUri() {
        return WebUtils.getURI(String.format(WebServiceConstants.advertFinishedList));
    }

    @Override
    public String getRequestMethodType() {

        return "POST";
    }

    @Override
    public GetOnGoingAdListResponse getResponse() {
        return new GetOnGoingAdListResponse();
    }

}
