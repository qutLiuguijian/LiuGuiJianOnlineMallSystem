package com.ruiwcc.okhttpPlus.response;

import com.ruiwcc.okhttpPlus.exception.OkHttpException;

/**
 *@Title:ResponseCallback
 *@Desc: 请求回调
 *@Author: Pengwc
 *@Date: 2019-6-26 15:47
 */
public interface ResponseCallback {

  //请求成功回调事件处理
  void onSuccess(Object responseObj);

  //请求失败回调事件处理
  void onFailure(OkHttpException failuer);

}
