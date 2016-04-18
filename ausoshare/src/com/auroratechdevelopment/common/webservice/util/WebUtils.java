package com.auroratechdevelopment.common.webservice.util;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.auroratechdevelopment.common.DebugLogUtil;
import com.auroratechdevelopment.common.webservice.WebServiceConstants;

public class WebUtils {
	
	public static String getURI(String contextRoot){
		
		String uri = String.format("%s/%s", WebServiceConstants.WebHost, contextRoot);
		DebugLogUtil.LogD("Request URI >>> " + uri);

		return uri;
	}
	
	public static Gson getGsonInstance(){
		return new GsonBuilder()
		.serializeNulls()
				.setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
		.create();
	}

}


