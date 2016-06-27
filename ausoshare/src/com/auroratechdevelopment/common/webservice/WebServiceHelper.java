package com.auroratechdevelopment.common.webservice;

import com.auroratechdevelopment.ausoshare.CustomApplication;
import com.auroratechdevelopment.ausoshare.R;
import com.auroratechdevelopment.ausoshare.util.Constants;
import com.auroratechdevelopment.common.webservice.models.UserInfo;
import com.auroratechdevelopment.common.webservice.request.AcquireUpdateRequest;
import com.auroratechdevelopment.common.webservice.request.CurrentIncomeRequest;
import com.auroratechdevelopment.common.webservice.request.ForgotPasswordRequest;
import com.auroratechdevelopment.common.webservice.request.ForwardedAdHistoryRequest;
import com.auroratechdevelopment.common.webservice.request.GetFinishedAdListRequest;
import com.auroratechdevelopment.common.webservice.request.GetOnGoingAdListRequest;
import com.auroratechdevelopment.common.webservice.request.GetOnGoingEntertainmentListRequest;
import com.auroratechdevelopment.common.webservice.request.GetURLRequest;
import com.auroratechdevelopment.common.webservice.request.LoginRequest;
import com.auroratechdevelopment.common.webservice.request.OnGoingAdDetailRequest;
import com.auroratechdevelopment.common.webservice.request.RegisterRequest;
import com.auroratechdevelopment.common.webservice.request.RequestBase;
import com.auroratechdevelopment.common.webservice.request.UpdateGCMRequest;
import com.auroratechdevelopment.common.webservice.request.UpdateSharedTimeRequest;
import com.auroratechdevelopment.common.webservice.request.UpdateUserPasswordRequest;
import com.auroratechdevelopment.common.webservice.request.UpdateUserProfileRequest;
import com.auroratechdevelopment.common.webservice.request.UploadAvatarRequest;
import com.auroratechdevelopment.common.webservice.request.UserProfileRequest;
import com.auroratechdevelopment.common.webservice.request.WithdrawRequestRequest;
import com.auroratechdevelopment.common.webservice.response.AcquireUpdateResponse;
import com.auroratechdevelopment.common.webservice.response.CurrentIncomeResponse;
import com.auroratechdevelopment.common.webservice.response.ForgotPasswordResponse;
import com.auroratechdevelopment.common.webservice.response.ForwardedAdHistoryResponse;
import com.auroratechdevelopment.common.webservice.response.GetOnGoingAdListResponse;
import com.auroratechdevelopment.common.webservice.response.GetOnGoingEntertainmentListResponse;
import com.auroratechdevelopment.common.webservice.response.GetURLResponse;
import com.auroratechdevelopment.common.webservice.response.LoginResponse;
import com.auroratechdevelopment.common.webservice.response.OnGoingAdDetailResponse;
import com.auroratechdevelopment.common.webservice.response.RegisterResponse;
import com.auroratechdevelopment.common.webservice.response.ResponseBase;
import com.auroratechdevelopment.common.webservice.response.ResponseErrorNumber;
import com.auroratechdevelopment.common.webservice.response.UpdateGCMResponse;
import com.auroratechdevelopment.common.webservice.response.UpdatePasswordResponse;
import com.auroratechdevelopment.common.webservice.response.UpdateUserProfileResponse;
import com.auroratechdevelopment.common.webservice.response.UploadAvatarResponse;
import com.auroratechdevelopment.common.webservice.response.UserProfileResponse;
import com.auroratechdevelopment.common.webservice.response.WithdrawRequestResponse;

import android.util.Log;


/**
*
* @author Edward Liu
* Updated by Raymond Zhuang 2016/5/2
*/
public class WebServiceHelper {

	private static WebServiceHelper instanceObj = null;
	protected WebServiceHelper() {}
	public static WebServiceHelper getInstance() {
		if(instanceObj == null) {
			instanceObj = new WebServiceHelper();
		}
		return instanceObj;
	}
	private static String token = null;

    public interface WebServiceListener {
		void ResponseSuccessCallBack(int tag, ResponseBase response);
		void ResponseFailedCallBack(int tag, ResponseBase response);
		void ResponseConnectionError(int tag, ResponseBase response);
	}
	
	private WebServiceListener listener;
	private WebServiceListener Servicelistener;
	
	public void setListener(WebServiceListener listener) {
		this.listener = listener;
	}
	
	public void setServiceListener(WebServiceListener listener) {
		this.Servicelistener = listener;
	}
	
	private void validateResponse(int tag, ResponseBase response) {
		
		if(listener == null) {
			Log.e("Edward", "Why the listener is null??");
			return;
		}
		if(response != null){
            if (response.reasonCode==0) { // reasonCode == 0 means success from web server
                
            	Log.e("Edward", "response.reasonCode == 0");
            	listener.ResponseSuccessCallBack(tag, response);
            }
            else {
            	Log.e("Edward", "response.reasonCode != 0");
                //web service communication error
                if(!validateWebServiceConnection(response)){
                    listener.ResponseConnectionError(tag, response);
                    return;
                }

                //webservice returned with error
                listener.ResponseFailedCallBack(tag, response);
            }
        }
	}
	
	private void validateServiceResponse(int tag, ResponseBase response) {
		
		if(Servicelistener == null) {
			Log.e("Raymond", "Service listener is null??");
			return;
		}
		if(response != null){
            if (response.reasonCode==0) { // reasonCode == 0 means success from web server
                
            	Log.e("Raymond", "response.reasonCode == 0");
            	Servicelistener.ResponseSuccessCallBack(tag, response);
            }
            else {
            	Log.e("Raymond", "response.reasonCode "+response.reasonCode);
                //web service communication error
                if(!validateWebServiceConnection(response)){
                	Servicelistener.ResponseConnectionError(tag, response);
                    return;
                }

                //webservice returned with error
                Servicelistener.ResponseFailedCallBack(tag, response);
            }
        }
	}
	
private boolean validateWebServiceConnection(ResponseBase response) {
		
		int resourceId = 0;	
		final ResponseErrorNumber errorNumber = response.errorNumber();

		
		if(errorNumber == ResponseErrorNumber.Timeout){
			resourceId = R.string.ws_error_timeout;
		}
		else if(errorNumber == ResponseErrorNumber.UnknownRequest){
			resourceId = R.string.ws_error_unknown_request;
		}
		else if(errorNumber == ResponseErrorNumber.UnknownParameter){
			resourceId = R.string.ws_error_unknown_parameter;	
		}
		else if	(errorNumber == ResponseErrorNumber.UnauthorizedUser){
			resourceId = R.string.ws_error_unauthorized_user;	
		}
		else if (errorNumber == ResponseErrorNumber.ErrorNickname) {
			resourceId = R.string.ws_error_nickname;	
		}
		else if(errorNumber == ResponseErrorNumber.ErrorEmail){
			resourceId = R.string.ws_error_email;	
		}
		else if (errorNumber == ResponseErrorNumber.ErrorPassword) {
			resourceId = R.string.ws_error_password;	
		}
		else if	(errorNumber == ResponseErrorNumber.ErrorUsername){
			resourceId = R.string.ws_error_username;	
		}
		else if (errorNumber == ResponseErrorNumber.ErrorCapcha) {
			resourceId = R.string.ws_error_capcha;	
		}
		else if(errorNumber == ResponseErrorNumber.ErrorSendEmail){
			resourceId = R.string.ws_error_send_email;	
		}
		else if (errorNumber == ResponseErrorNumber.ErrorExistEmail) {
			resourceId = R.string.ws_error_exist_email;	
		}

		if(resourceId != 0) {
			response.setMessage(response.responseMessage);
			
			return false;
		}
		
		return true;
	}


    public void login(String email, String password, String deviceId) {
        LoginRequest req = new LoginRequest(email, password, deviceId);
        WebService.sendRequestAsync(req, new WebService.WebServiceCallback<LoginResponse>() {
            @Override
            public void ResponseReady(int id, int tag, LoginResponse response) {
                validateResponse(tag, response);
            }
        });
    }

    public void register(String nickName, String email, String password, String deviceID, String PromotionCode) {
        RegisterRequest req = new RegisterRequest(nickName, email, password, deviceID, PromotionCode);
        WebService.sendRequestAsync(req, new WebService.WebServiceCallback<RegisterResponse>() {
            @Override
            public void ResponseReady(int id, int tag, RegisterResponse response) {
                validateResponse(tag, response);
            }
        });
    }

    public void forgotPassward(String email, String deviceId){
        ForgotPasswordRequest req = new ForgotPasswordRequest(email, deviceId);
        WebService.sendRequestAsync(req, new WebService.WebServiceCallback<ForgotPasswordResponse>(){
            @Override
            public void ResponseReady(int id, int tag, ForgotPasswordResponse response){
                validateResponse(tag, response);
            }
        });
    }

    public void updateGCMToken(String token){
        UpdateGCMRequest req = new UpdateGCMRequest(token, Constants.GCM_TOPIC+"global");

        WebService.sendRequestAsync(req, new WebService.WebServiceCallback<UpdateGCMResponse>(){
            @Override
            public void ResponseReady(int id, int tag, UpdateGCMResponse response){
                validateServiceResponse(tag, response);
            }
        });
    }

    public void updateUserProfile(String deviceID, String nickname, String email, String pwd, String paypalEmail){
        UpdateUserProfileRequest req = new UpdateUserProfileRequest(CustomApplication.getInstance().getUserToken(),
                deviceID, nickname,email,pwd,paypalEmail);

        WebService.sendRequestAsync(req, new WebService.WebServiceCallback<UpdateUserProfileResponse>(){
            @Override
            public void ResponseReady(int id, int tag, UpdateUserProfileResponse response){
                validateResponse(tag, response);
            }
        });
    }
    
    public void updateUserPassword(String deviceID, String email,String pwd, String new_pwd){
        UpdateUserPasswordRequest req = new UpdateUserPasswordRequest(CustomApplication.getInstance().getUserToken(),
                deviceID, email,pwd,new_pwd);

        WebService.sendRequestAsync(req, new WebService.WebServiceCallback<UpdatePasswordResponse>(){
            @Override
            public void ResponseReady(int id, int tag, UpdatePasswordResponse response){
                validateResponse(tag, response);
            }
        });
    }
    
    public void updateSharedTime(String deviceID, String email, String adID){
    	UpdateSharedTimeRequest req = new UpdateSharedTimeRequest(CustomApplication.getInstance().getUserToken(),
                deviceID, email,adID);

        WebService.sendRequestAsync(req, new WebService.WebServiceCallback<UpdateUserProfileResponse>(){
            @Override
            public void ResponseReady(int id, int tag, UpdateUserProfileResponse response){
                validateResponse(tag, response);
            }
        });
    }
    

    public void withdrawRequest(String deviceID, String email){
    	WithdrawRequestRequest req = new WithdrawRequestRequest(CustomApplication.getInstance().getUserToken(),
                deviceID, email);

        WebService.sendRequestAsync(req, new WebService.WebServiceCallback<WithdrawRequestResponse>(){
            @Override
            public void ResponseReady(int id, int tag, WithdrawRequestResponse response){
                validateResponse(tag, response);
            }
        });
    }
    

    public void getContactURL(String deviceID, String email, String ContentURL){
        GetURLRequest req = new GetURLRequest(deviceID,email,
                CustomApplication.getInstance().getUserToken(),ContentURL);


        WebService.sendRequestAsync(req, new WebService.WebServiceCallback<GetURLResponse>(){
            @Override
            public void ResponseReady(int id, int tag, GetURLResponse response){
                validateResponse(tag,response);
            }
        });
    }

    
    public void getEntertainmentRL(String deviceID, String email, String ContentURL){
        GetURLRequest req = new GetURLRequest(deviceID,email,
                CustomApplication.getInstance().getUserToken(),ContentURL);


        WebService.sendRequestAsync(req, new WebService.WebServiceCallback<GetURLResponse>(){
            @Override
            public void ResponseReady(int id, int tag, GetURLResponse response){
                validateResponse(tag,response);
            }
        });
    }
    
    
    public void getCurrentIncome(String email, String deviceID, int maxNumber ){
        CurrentIncomeRequest req = new CurrentIncomeRequest(email, deviceID,
                CustomApplication.getInstance().getUserToken(),
                maxNumber);

        WebService.sendRequestAsync(req, new WebService.WebServiceCallback<CurrentIncomeResponse>(){
            @Override
            public void ResponseReady(int id, int tag, CurrentIncomeResponse response){
                validateResponse(tag,response);
            }
        });
    }
    
    public void UploadAvatar(String email, String deviceID, String img_base64 ){
    	UploadAvatarRequest req = new UploadAvatarRequest(email, deviceID,
                CustomApplication.getInstance().getUserToken(),img_base64,"jpg");

        WebService.sendRequestAsync(req, new WebService.WebServiceCallback<UploadAvatarResponse>(){
            @Override
            public void ResponseReady(int id, int tag, UploadAvatarResponse response){
                validateResponse(tag,response);
            }
        });
    }

    public void getForwardedAd(int startNumber, int count){
        ForwardedAdHistoryRequest req = new ForwardedAdHistoryRequest(
                CustomApplication.getInstance().getEmail(),
                CustomApplication.getInstance().getUserToken(),
                CustomApplication.getInstance().getAndroidID(),
                startNumber,count);

        WebService.sendRequestAsync(req, new WebService.WebServiceCallback<ForwardedAdHistoryResponse>(){
            @Override
            public void ResponseReady(int id, int tag, ForwardedAdHistoryResponse response){
                validateResponse(tag,response);
            }
        });
    }

    public void offAdList(UserInfo userInfo, String tag , String key){
        GetFinishedAdListRequest req = new GetFinishedAdListRequest(CustomApplication.getInstance().getEmail(),
                CustomApplication.getInstance().getUserToken(),
                CustomApplication.getInstance().getAndroidID(),
                userInfo, tag , key);

        WebService.sendRequestAsync(req, new WebService.WebServiceCallback<GetOnGoingAdListResponse>(){//same response with ongoing list
            @Override
            public void ResponseReady(int id, int tag, GetOnGoingAdListResponse response){
                validateResponse(tag, response);
            }
        });
    }

    public void onGoingAdList(UserInfo userInfo, String tag , String key){
        GetOnGoingAdListRequest req = new GetOnGoingAdListRequest(CustomApplication.getInstance().getEmail(),
                CustomApplication.getInstance().getUserToken(),
                CustomApplication.getInstance().getAndroidID(),
                userInfo, tag , key);

        WebService.sendRequestAsync(req, new WebService.WebServiceCallback<GetOnGoingAdListResponse>(){
            @Override
            public void ResponseReady(int id, int tag, GetOnGoingAdListResponse response){
                validateResponse(tag, response);
            }
        });
    }

    public void onGoingAdList(UserInfo userInfo, String tag , String key,String lang){
        GetOnGoingAdListRequest req = new GetOnGoingAdListRequest(CustomApplication.getInstance().getEmail(),
                CustomApplication.getInstance().getUserToken(),
                CustomApplication.getInstance().getAndroidID(),
                userInfo, tag , key,lang);

        WebService.sendRequestAsync(req, new WebService.WebServiceCallback<GetOnGoingAdListResponse>(){
            @Override
            public void ResponseReady(int id, int tag, GetOnGoingAdListResponse response){
                validateResponse(tag, response);
            }
        });
    }
    
    public void onGoingEntertainmentList(UserInfo userInfo, String tag, String key){
        GetOnGoingEntertainmentListRequest req = new GetOnGoingEntertainmentListRequest(CustomApplication.getInstance().getEmail(),
                CustomApplication.getInstance().getUserToken(),
                CustomApplication.getInstance().getAndroidID(),
                userInfo, tag , key);

        WebService.sendRequestAsync(req, new WebService.WebServiceCallback<GetOnGoingEntertainmentListResponse>(){
            @Override
            public void ResponseReady(int id, int tag, GetOnGoingEntertainmentListResponse response){
                validateResponse(tag, response);
            }
        });
    }
    
    public void onGoingAdDetail(String tag, String adID){
    	OnGoingAdDetailRequest req = new OnGoingAdDetailRequest(CustomApplication.getInstance().getEmail(),
    			CustomApplication.getInstance().getUserToken(),
    			CustomApplication.getInstance().getAndroidID(),
    		    tag, adID
    			);
    	
    	WebService.sendRequestAsync(req, new WebService.WebServiceCallback<OnGoingAdDetailResponse>(){

			@Override
			public void ResponseReady(int id, int tag, OnGoingAdDetailResponse response) {
				validateResponse(tag,response);
			}
    		
    	});
    			
    }


    public void getUserProfile(long id) {
        UserProfileRequest req = new UserProfileRequest(id);
        req.token = CustomApplication.getInstance().getUserToken();
        WebService.sendRequestAsync(req, new WebService.WebServiceCallback<UserProfileResponse>() {
            @Override
            public void ResponseReady(int id, int tag, UserProfileResponse response) {
                validateResponse(tag, response);
            }
        });
    }
    
    public void acquireUpdate(String adID){
    	AcquireUpdateRequest req = new AcquireUpdateRequest(CustomApplication.getInstance().getUserToken(),
    			CustomApplication.getInstance().getAndroidID(), CustomApplication.getInstance().getEmail() ,adID);

        WebService.sendRequestAsync(req, new WebService.WebServiceCallback<AcquireUpdateResponse>(){
            @Override
            public void ResponseReady(int id, int tag, AcquireUpdateResponse response){
            	validateServiceResponse(tag, response);
            }
        });
    }

}
