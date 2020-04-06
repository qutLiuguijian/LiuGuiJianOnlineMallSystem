package com.ruiwcc.okhttpPlus.response;

import java.io.File;


/**
 *@Title:ResponseByteCallback
 *@Desc: 请求回调
 *@Author: Pengwc
 *@Date: 2019-6-26 15:47
 */
public interface ResponseByteCallback {

  //请求成功回调事件处理
  void onSuccess(File file);

  //请求失败回调事件处理
  void onFailure(String failureMsg);

}
