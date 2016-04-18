package com.auroratechdevelopment.common.webservice.response;

/**
 *
 * @author Edward Liu
 */
public enum ResponseErrorNumber {
    Undefined,
	Success,
    
    // web service communication errors
    NoInternetConnection,
    Timeout,
    InvalidURL,
    InvalidResponse,
    IOError,
    UnknownCommunicationError,
    BlockedByFirewall,
    ServerResponseError,
    ClientNotAuthorized,
    
    //ausoshare reason code
    UnknownRequest,   //400
    UnknownParameter, //401
    UnauthorizedUser, //402
    ErrorNickname,    //500
    ErrorEmail,       //501
    ErrorPassword,    //502
    ErrorUsername,    //503
    ErrorCapcha,      //504
    ErrorSendEmail,   //505
    ErrorExistEmail,  //506
    
    //Genric 
    SessionExpired,
    
    
    //card validation
    NoAccountOnDPS, //15030002 
    
    //card holder identification
    PasswordLoginExceedLimits, // 10000013
    
    //Authentication/Registration/Identity    
    InvalidPassword, //15010001
    AccountBlocked, //15010005
    CardStateBlocked, //15010006

    SocialIdNotfound,
    UserEmailNotFound
}
