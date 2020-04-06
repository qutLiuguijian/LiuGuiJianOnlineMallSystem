package com.ruiwcc.okhttpPlus.response;

/**
 *@Title:ResposeDataHandle
 *@Desc: 
 *@Author: Pengwc
 *@Date: 2019-6-26 15:52
 */
public class ResposeDataHandle {

    public ResponseCallback mListener = null;

    public ResposeDataHandle(ResponseCallback listener) {
        this.mListener = listener;
    }


}
