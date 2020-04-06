package com.lgj.liuguijianonlinemallapp.utils;

import com.ruiwcc.okhttpPlus.OkHttpPlus;
import com.ruiwcc.okhttpPlus.request.RequestParams;
import com.ruiwcc.okhttpPlus.response.ResponseCallback;

public class RequestParamConfig {
    private final static  String host="http://localhost:8081/app/";
    public static void isReLogin(RequestParams params,
                                 ResponseCallback callback){
        OkHttpPlus.post(host+"/isReLogin",params,callback);
    }
}
