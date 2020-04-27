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
    public static void getGoodsDetail(RequestParams params,
                                ResponseCallback callback){
        OkHttpPlus.post(host+"/getGoodsDetail",params,callback);
    }
    public static void addCar(RequestParams params,
                                      ResponseCallback callback){
        OkHttpPlus.post(host+"/addCar",params,callback);
    }
    public static void getCar(RequestParams params,
                              ResponseCallback callback){
        OkHttpPlus.post(host+"/getCar",params,callback);
    }
    public static void getAllOrderByUAS(RequestParams params,
                              ResponseCallback callback){
        OkHttpPlus.post(host+"/getAllOrderByUAS",params,callback);
    }
    public static void updateOrder(RequestParams params,
                                        ResponseCallback callback){
        OkHttpPlus.post(host+"/updateOrder",params,callback);
    }
    public static void deleteOrder(RequestParams params,
                                   ResponseCallback callback){
        OkHttpPlus.post(host+"/deleteOrder",params,callback);
    }
    public static void saveReceiver(RequestParams params,
                                   ResponseCallback callback){
        OkHttpPlus.post(host+"/saveReceiver",params,callback);
    }
    public static void getReceiver(RequestParams params,
                                   ResponseCallback callback){
        OkHttpPlus.post(host+"/getReceiver",params,callback);
    }
    public static void deleteReceiver(RequestParams params,
                                   ResponseCallback callback){
        OkHttpPlus.post(host+"/deleteReceiver",params,callback);
    }
    public static void buyFromCar(RequestParams params,
                                   ResponseCallback callback){
        OkHttpPlus.post(host+"/buyFromCar",params,callback);
    }
    public static void buyFromDetail(RequestParams params,
                                   ResponseCallback callback){
        OkHttpPlus.post(host+"/buyFromDetail",params,callback);
    }
    public static void exitLogin(RequestParams params,
                                     ResponseCallback callback){
        OkHttpPlus.post(host+"/exitLogin",params,callback);
    }
    public static void classify(RequestParams params,
                                 ResponseCallback callback){
        OkHttpPlus.post(host+"/classify",params,callback);
    }
    public static void getGoodsByTClassify(RequestParams params,
                                ResponseCallback callback){
        OkHttpPlus.post(host+"/getGoodsByTClassify",params,callback);
    }
    public static void getGoodsById(RequestParams params,
                                           ResponseCallback callback){
        OkHttpPlus.post(host+"/getGoodsById",params,callback);
    }
    public static void addAssess(RequestParams params,
                                    ResponseCallback callback){
        OkHttpPlus.post(host+"/addAssess",params,callback);
    }
    public static void getAssess(RequestParams params,
                                 ResponseCallback callback){
        OkHttpPlus.post(host+"/getAssess",params,callback);
    }
    public static void deleteCar(RequestParams params,
                                 ResponseCallback callback){
        OkHttpPlus.post(host+"/deleteCar",params,callback);
    }
    public static void isShowMark(RequestParams params,
                                 ResponseCallback callback){
        OkHttpPlus.post(host+"/isShowMark",params,callback);
    }
    public static void getAllOrder(RequestParams params,
                                  ResponseCallback callback){
        OkHttpPlus.post(host+"/getAllOrder",params,callback);
    }
}
