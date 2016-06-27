package com.auroratechdevelopment.common.webservice.request;

import com.auroratechdevelopment.ausoshare.CustomApplication;
import com.auroratechdevelopment.ausoshare.util.Constants;
import com.google.gson.annotations.SerializedName;
import com.auroratechdevelopment.common.webservice.response.ResponseBase;
import com.auroratechdevelopment.common.webservice.util.WebUtils;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.OutputStream;

//updated by Raymond at Jun 7 2016

public abstract class RequestBase<T extends ResponseBase> {


	@SerializedName("token")
	public String token = null;
    public String version = Constants.VER;
    public String language = CustomApplication.getInstance().getLanguage().toUpperCase();

    @SuppressWarnings("unused")
    private transient String extension;
	
	public RequestBase(){
	}
	
	public abstract String getUri();
	public abstract String getRequestMethodType();
    public abstract T getResponse();
	
	public void writeRequest(OutputStream stream) throws XmlPullParserException, IOException {
		
        String gstring = WebUtils.getGsonInstance().toJson(this);
        stream.write(gstring.getBytes());
	}

    public String toJSON() {
        return WebUtils.getGsonInstance().toJson(this);
    }
}
