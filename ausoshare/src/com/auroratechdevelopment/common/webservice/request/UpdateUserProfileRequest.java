package com.auroratechdevelopment.common.webservice.request;

import com.auroratechdevelopment.common.webservice.WebServiceConstants;
import com.auroratechdevelopment.common.webservice.response.UpdateUserProfileResponse;
import com.auroratechdevelopment.common.webservice.util.WebUtils;

/**
 * Created by Edward liu on 1/14/2015.
 */
public class UpdateUserProfileRequest extends RequestBase<UpdateUserProfileResponse> {

    public String deviceID;
    public String nickname;
    public String email;

    public UpdateUserProfileRequest(String token, String deviceID, String nickName, String email) {
        this.token = token;
        this.deviceID = deviceID;
        this.nickname = nickName;
        this.email = email;
    }

    @Override
    public String getUri() {
        return WebUtils.getURI(String.format(WebServiceConstants.updateUserProfile));
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
