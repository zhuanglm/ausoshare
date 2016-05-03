package com.auroratechdevelopment.common.webservice.request;

import com.auroratechdevelopment.common.webservice.WebServiceConstants;
import com.auroratechdevelopment.common.webservice.response.GetURLResponse;
import com.auroratechdevelopment.common.webservice.response.RegisterResponse;
import com.auroratechdevelopment.common.webservice.response.ResponseBase;
import com.auroratechdevelopment.common.webservice.response.UploadAvatarResponse;
import com.auroratechdevelopment.common.webservice.util.WebUtils;

/**
 * Created by Raymond zhuang on 5/3/2016.
 */
public class UploadAvatarRequest extends RequestBase<UploadAvatarResponse> {
    public String deviceID;
    public String email;
    public String file_format;
    public String img_base64;

    public UploadAvatarRequest(String deviceID, String email, String token, String uploadContent, String format) {
        this.deviceID = deviceID;
        this.email = email;
        this.token = token;
        file_format = format;
        this.img_base64 = uploadContent;
    }

    @Override
    public String getUri() {
        return WebUtils.getURI(String.format(WebServiceConstants.uploadAvatar));
    }

    @Override
    public String getRequestMethodType() {
        return "POST";
    }


    @Override
    public UploadAvatarResponse getResponse() {
        return new UploadAvatarResponse();
    }

}
