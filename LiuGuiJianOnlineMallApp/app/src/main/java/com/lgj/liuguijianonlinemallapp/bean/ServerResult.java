package com.lgj.liuguijianonlinemallapp.bean;

public class ServerResult<T> {
    //等于0时表示无错误，其余值表示有错误，错误时，errMsg有值，否则无值
    private int retCode;
    //出错时的信息
    private String message;
    //真正返回的数据，其类型由参数T决定
    private T data;

    public ServerResult(int retCode) {
        this.retCode = retCode;
    }

    public ServerResult(int retCode, String message) {
        this.retCode = retCode;
        this.message = message;
    }

    public ServerResult(int retCode, String message, T data) {
        this.retCode = retCode;
        this.message = message;
        this.data = data;
    }

    public int getRetCode() {
        return retCode;
    }

    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
