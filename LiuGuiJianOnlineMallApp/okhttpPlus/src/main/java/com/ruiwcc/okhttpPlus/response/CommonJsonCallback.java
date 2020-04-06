package com.ruiwcc.okhttpPlus.response;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ruiwcc.okhttpPlus.OkHttpPlus;
import com.ruiwcc.okhttpPlus.config.Common;
import com.ruiwcc.okhttpPlus.exception.OkHttpException;
import com.ruiwcc.okhttpPlus.utils.NetUtil;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.json.JSONObject;

/**
 *@Title:CommonJsonCallback
 *@Desc: json请求
 *@Author: Pengwc
 *@Date: 2019-6-26 16:04
 */
public class CommonJsonCallback implements Callback {

  /**
   * errorCode是根据接口返回的标识 实际根据自己接口返回为准
   */
  protected final String RESULT_CODE = "code";
  protected final int RESULT_CODE_VALUE = 200;

  /**
   * errorMsg字段提示信息，实际根据自己接口返回为准
   */
  protected final String ERROR_MSG = "errorMsg";

  protected final String NETWORK_MSG = "请求失败";
  protected final String JSON_MSG = "解析失败";

  /**
   * 自定义异常类型
   */
  protected final String NETWORK_ERROR = "-1"; //网络失败
  protected final String JSON_ERROR = "-2"; //解析失败
  protected final String OTHER_ERROR = "-3"; //未知错误
  protected final String TIMEOUT_ERROR = "-4"; //请求超时

  private ResponseCallback mListener;

  public CommonJsonCallback(ResposeDataHandle handle) {
    this.mListener = handle.mListener;
  }

  /**
   * 请求失败的处理
   * @param call
   * @param e
   */
  @Override
  public void onFailure(@NonNull Call call, @NonNull final IOException e) {
    Log.e(Common.LOG_TAG,"请求失败=" + e.getMessage());
    OkHttpPlus.getHander().post(new Runnable() {
      @Override
      public void run() {
        if (!NetUtil.isConnected(Common.context)) {
          mListener.onFailure(new OkHttpException(NETWORK_ERROR, "请检查网络"));
        } else if (e instanceof SocketTimeoutException) {
          //判断超时异常
          mListener.onFailure(new OkHttpException(TIMEOUT_ERROR, "请求超时"));
        } else if (e instanceof ConnectException) {
          //判断超时异常
          mListener.onFailure(new OkHttpException(OTHER_ERROR, "请求服务器失败"));
        } else {
          mListener.onFailure(new OkHttpException(NETWORK_ERROR, e.getMessage()));
        }

      }
    });
  }

  /**
   * 请求成功的处理
   * 回调在主线程
   */
  @Override
  public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
    final String result = response.body().string();
    OkHttpPlus.getHander().post(new Runnable() {
      @Override
      public void run() {
        handleResponse(result);
      }
    });
  }

  /**
   * 处理Http成功的响应
   * @param responseObj
   */
  private void handleResponse(Object responseObj) {
    if (responseObj == null && responseObj.toString().trim().equals("")) {
      mListener.onFailure(new OkHttpException(NETWORK_ERROR, NETWORK_MSG));
      return;
    }
    try {
      mListener.onSuccess(responseObj);
    } catch (Exception e) {
      e.printStackTrace();
            mListener.onFailure(new OkHttpException(OTHER_ERROR, e.getMessage()));
      Log.e(Common.LOG_TAG,"onResponse处理失败=" + e.getMessage());
    }
  }

}
