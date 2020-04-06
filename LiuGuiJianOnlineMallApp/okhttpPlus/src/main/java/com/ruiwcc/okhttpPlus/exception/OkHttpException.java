package com.ruiwcc.okhttpPlus.exception;

/**
 *@Title:OkHttpException
 *@Desc: 通用异常
 *@Author: Pengwc
 *@Date: 2019-6-26 15:38
 */
public class OkHttpException extends Exception {

  private static final long serialVersionUID = 1L;

  private String code; //错误码
  private String msg; //错误消息

  public OkHttpException(String ecode, String emsg) {
    this.code = ecode;
    this.msg = emsg;
  }

  public String getCode() {
    return code;
  }

  public String getMsg() {
    return msg;
  }

}
