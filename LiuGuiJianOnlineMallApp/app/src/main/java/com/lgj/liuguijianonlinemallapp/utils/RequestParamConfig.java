package com.lgj.liuguijianonlinemallapp.utils;

import com.ruiwcc.okhttpPlus.OkHttpPlus;
import com.ruiwcc.okhttpPlus.request.RequestParams;
import com.ruiwcc.okhttpPlus.response.ResponseCallback;

public class RequestParamConfig {
    private final static  String host="http://10.0.2.2:8081/app";
    public static void isReLogin(RequestParams params,
                                  ResponseCallback callback){
        OkHttpPlus.post(host+"/isReLogin",params,callback);
    }
    public static void getGoodsByClassify(RequestParams params,
                                 ResponseCallback callback){
        OkHttpPlus.post(host+"/getGoodsByClassify",params,callback);
    }
    public static void login(RequestParams params,
                                          ResponseCallback callback){
        OkHttpPlus.post(host+"/login",params,callback);
    }
    public static void register(RequestParams params,
                             ResponseCallback callback){
        OkHttpPlus.post(host+"/register",params,callback);
    }
}
