package com.auroratechdevelopment.ausoshare.wxapi;

import java.util.logging.LogManager;

//import com.auroratechdevelopment.ausoshare.Constants;
import com.auroratechdevelopment.ausoshare.R;
import com.tencent.mm.sdk.openapi.BaseReq;
import com.tencent.mm.sdk.openapi.BaseResp;
import com.tencent.mm.sdk.openapi.ConstantsAPI;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.ShowMessageFromWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXAppExtendObject;
import com.tencent.mm.sdk.openapi.WXMediaMessage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {  
    // IWXAPI 是第三方app和微信通信的openapi接口  
    private IWXAPI api;  
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        api = WXAPIFactory.createWXAPI(this, "这里替换第一步申请的APP_ID", false);  
        api.handleIntent(getIntent(), this);  
        super.onCreate(savedInstanceState);  
    }  

    
    
    @Override
	public void onReq(BaseReq req) {
		switch (req.getType()) {
		case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
//			goToGetMsg();		
			break;
		case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
//			goToShowMsg((ShowMessageFromWX.Req) req);
			break;
		default:
			break;
		}
	}

	
	@Override
	public void onResp(BaseResp resp) {
		int result = 0;
		String sR;
		
		switch (resp.errCode) {
		case BaseResp.ErrCode.ERR_OK:
			result = R.string.errcode_success;
			sR= getResources().getString(result);
			break;
		case BaseResp.ErrCode.ERR_USER_CANCEL:
			result = R.string.errcode_cancel;
			sR= getResources().getString(result);
			break;
		case BaseResp.ErrCode.ERR_AUTH_DENIED:
			result = R.string.errcode_deny;
			sR= getResources().getString(result);
			break;
		default:
			result = R.string.errcode_unknown;
			sR= String.format(getResources().getString(result),resp.errCode);
			break;
		}
		
		//String s = "[" +Integer.toString(resp.errCode)+"]";
		Toast.makeText(this, sR, Toast.LENGTH_LONG).show();
		finish();
		
		
	}
	
	
  
}  