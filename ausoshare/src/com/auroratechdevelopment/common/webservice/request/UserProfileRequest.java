package com.auroratechdevelopment.common.webservice.request;

import com.auroratechdevelopment.common.webservice.response.UserProfileResponse;
import com.auroratechdevelopment.common.webservice.util.WebUtils;

/**
 * Created by Edward on 2015-10-28.
 */
public class UserProfileRequest extends RequestBase<UserProfileResponse>  {

    public long id;

    public UserProfileRequest(long id) {
        this.id = id;
    }

    @Override
    public String getUri() {
        return WebUtils.getURI(String.format("User?Id=%s&token=%s", id, token));

    }

    @Override
    public String getRequestMethodType() {
        return "GET";
    }

    @Override
    public UserProfileResponse getResponse() {
        return new UserProfileResponse();
    }
}
