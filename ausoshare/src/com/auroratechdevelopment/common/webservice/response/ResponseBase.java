package com.auroratechdevelopment.common.webservice.response;

import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;

import android.util.Log;

import com.auroratechdevelopment.common.webservice.util.WebUtils;

import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;

/**
*
* @author Edward Liu
*/
public class ResponseBase {
	private ResponseErrorNumber errorNumber;

	@SerializedName("success")
	public boolean success;
	
	@SerializedName("reasonCode")
	public long reasonCode;
	
	@SerializedName("responseMessage")
	public String responseMessage;
	
//	@SerializedName("Ext")
//	public String Extension;
	
	public ResponseBase(){
		this.success = false;
		this.responseMessage = "Failed";
		this.reasonCode = -1;
	}
	
    public <Y extends ResponseBase> Y Parse(InputStream stream)
            throws ParserConfigurationException, SAXException, IOException {

        String theString = convertStreamToString(stream);
        String filtered;
        if (theString.startsWith("{\"reply\"")) {
            filtered = theString.substring(9, theString.length() - 1); //filter out root element, need customized parser or wrapper class
        }else {
            filtered = theString;
        }

    	JsonReader reader = new JsonReader(new InputStreamReader(new ByteArrayInputStream(filtered.getBytes("UTF-8")), "UTF-8"));

    	return WebUtils.getGsonInstance().fromJson(reader, this.getClass());
    }
	
    public void setErrorNumber(ResponseErrorNumber responseErrorNo) {
    	
    	errorNumber = responseErrorNo;
    }
    
    public void setSuccess(boolean result) {

    	success = result;
    }

	public void setReasonCode(int reason_code){
		reasonCode = reason_code;
	}
   
    public void setMessage(String msg) {

		responseMessage = msg;
    }
    
    public ResponseErrorNumber errorNumber() {
    	
    	return errorNumber;
    }
    
    public void parseServerCode(long code) {    	
    	ResponseErrorNumber errorNo = ResponseErrorNumber.Undefined;
    	
    	
    	Log.e("edward", "code = " + code);
    	
    	if(code == 0){
    		errorNo = ResponseErrorNumber.Success;
    	}
    	else if (code == 400 ) {
    		errorNo = ResponseErrorNumber.UnknownRequest;
        }
    	else if (code == 401 ) {
    		errorNo = ResponseErrorNumber.UnknownParameter;
        }
    	else if (code == 402) {
    		errorNo = ResponseErrorNumber.UnauthorizedUser;
    	}
    	else if (code == 500 ) {
    		errorNo = ResponseErrorNumber.ErrorNickname;
        }
    	else if (code == 501) {
    		errorNo = ResponseErrorNumber.ErrorEmail;
    	}
		else if(code == 502){
			errorNo = ResponseErrorNumber.ErrorPassword;
		}
		else if (code == 503) {
    		errorNo = ResponseErrorNumber.ErrorUsername;
    	}
		else if(code == 504){
			errorNo = ResponseErrorNumber.ErrorCapcha;
		}
		else if(code == 505){
			errorNo = ResponseErrorNumber.ErrorSendEmail;
		}
		else if(code == 506){
			errorNo = ResponseErrorNumber.ErrorExistEmail;
		}
    	Log.e("edward", "errorNo = " + errorNo);
    	
   		setErrorNumber(errorNo);
    }

    public static String convertStreamToString(InputStream is) {
        Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

}
