package com.ruiwcc.okhttpPlus;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.ruiwcc.okhttpPlus.config.Common;
import com.ruiwcc.okhttpPlus.exception.OkHttpException;
import com.ruiwcc.okhttpPlus.request.CommonRequest;
import com.ruiwcc.okhttpPlus.request.OkRequestBuilder;
import com.ruiwcc.okhttpPlus.request.RequestParams;
import com.ruiwcc.okhttpPlus.request.RequetInterceptor;
import com.ruiwcc.okhttpPlus.response.CommonJsonCallback;
import com.ruiwcc.okhttpPlus.response.ResponseByteCallback;
import com.ruiwcc.okhttpPlus.response.ResponseCallback;
import com.ruiwcc.okhttpPlus.response.ResposeDataHandle;
import com.ruiwcc.okhttpPlus.utils.HttpsUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @Title:OkHttpPlus
 * @Desc: okHttp入口类
 * @Author: Pengwc
 * @Date: 2019-6-26 15:30
 */
public class OkHttpPlus {
    /**
     * 超时时间
     */
    private static final int DEFAULT_TIME_OUT = 30; //默认超时时间30s

    private static OkHttpClient mOkHttpClient;
    private static Handler mDeliveryHandler; //进行消息的转发

    public static void init(Context context) {
        if (mOkHttpClient == null) {
            Common.context = context;
            //获取缓存路径
            File cacheDir = context.getExternalCacheDir();
            //设置缓存的大小
            int cacheSize = 10 * 1024 * 1024;
            //创建我们Client对象的构建者
            OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
            okHttpBuilder
                    //为构建者设置超时时间
                    .connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
                    .readTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
                    .writeTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
                    ////websocket轮训间隔(单位:秒)
                    .pingInterval(20, TimeUnit.SECONDS)
                    //设置缓存
                    .cache(new Cache(cacheDir.getAbsoluteFile(), cacheSize))
                    //允许重定向
                    .followRedirects(true)
                    //设置拦截器
                    .addInterceptor(new RequetInterceptor())
                    //添加https支持
                    .hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String s, SSLSession sslSession) {
                            return true;
                        }
                    })
                    .sslSocketFactory(HttpsUtil.initSSLSocketFactory(), HttpsUtil.initTrustManager());
            mOkHttpClient = okHttpBuilder.build();
            mDeliveryHandler = new Handler(Looper.getMainLooper());
        }
    }

    public static Handler getHander() {
        return mDeliveryHandler;
    }


    public static OkHttpClient getOkHttpClient()  {
        if (mOkHttpClient == null) {
            Log.i(Common.LOG_TAG,"please init() first");
        }
        return mOkHttpClient;
    }

    /**
     * GET请求
     *
     * @param url      URL请求地址
     * @param params   入参
     * @param callback 回调接口
     */
    public static void get(String url, RequestParams params,
                           ResponseCallback callback) {
        OkRequestBuilder.get(CommonRequest.createGetRequest(url, params),
                new ResposeDataHandle(callback));
    }

    /**
     * POST请求
     *
     * @param url      URL请求地址
     * @param params   入参
     * @param callback 回调接口
     */
    public static void post(String url, RequestParams params,
                            ResponseCallback callback) {
        OkRequestBuilder.post(CommonRequest.createPostRequest(url, params),
                new ResposeDataHandle(callback));
    }

    /**
     * 下载图片 Get方式
     */
    public static void getLoadImg(String url, RequestParams params, String imgPath, ResponseByteCallback callback) {
        OkRequestBuilder.postImage(CommonRequest.createGetRequest(url, params), imgPath, callback);
    }

    /**
     * 下载图片 Post方式
     */
    public static void postLoadImg(String url, RequestParams params, String imgPath, ResponseByteCallback callback) {
        OkRequestBuilder.postImage(CommonRequest.createPostRequest(url, params), imgPath, callback);
    }

    /**
     * 表单和媒体 图文混合
     */
    public static void postMultipart(String url, RequestParams params,
                                     List<File> files, ResponseCallback callback) {
        OkRequestBuilder.post(CommonRequest.createMultipartRequest(url, params, files),
                new ResposeDataHandle(callback));
    }
}
