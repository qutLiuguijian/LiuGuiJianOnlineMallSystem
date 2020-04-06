package com.ruiwcc.okhttpPlus.request;

import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.ruiwcc.okhttpPlus.OkHttpPlus;
import com.ruiwcc.okhttpPlus.config.Common;
import com.ruiwcc.okhttpPlus.response.CommonJsonCallback;
import com.ruiwcc.okhttpPlus.response.ResponseByteCallback;
import com.ruiwcc.okhttpPlus.response.ResponseCallback;
import com.ruiwcc.okhttpPlus.response.ResposeDataHandle;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 *@Title:RequestMode
 *@Desc:
 *@Author: Pengwc
 *@Date: 2019-6-26 15:44
 */
public class OkRequestBuilder {
    private static final String DEFAUTL_SAVE_FILE_DIR="ruiwcc";
    /**
     * 发送具体的HTTP以及Https请求
     */
    public static Call sendRequest(Request request, CommonJsonCallback commonCallback) {
        Call call = OkHttpPlus.getOkHttpClient().newCall(request);
        call.enqueue(commonCallback);
        return call;
    }

    /**
     * GET请求
     */
    public static Call get(Request request, ResposeDataHandle handle) {
        Call call = OkHttpPlus.getOkHttpClient().newCall(request);
        call.enqueue(new CommonJsonCallback(handle));
        return call;
    }

    /**
     * POST请求
     */
    public static Call post(Request request, ResposeDataHandle handle) {
        Call call = OkHttpPlus.getOkHttpClient().newCall(request);
        call.enqueue(new CommonJsonCallback(handle));
        return call;
    }

    /**
     * POST请求图片
     */
    public static Call postImage(Request request, final String imgPath,
                                 final ResponseByteCallback callback) {
        Call call = OkHttpPlus.getOkHttpClient().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                Log.e(Common.LOG_TAG, "下载图片失败=" + e.getMessage());
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFailure(e.getMessage());
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e(Common.LOG_TAG, "下载图片成功=" + response);
                File file = null;
                try {
                    InputStream is = response.body().byteStream();
                    int len = 0;
                    // 文件夹路径
                    String pathUrl =
                            Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator
                                    + DEFAUTL_SAVE_FILE_DIR;
                    File filepath = new File(pathUrl);
                    if (!filepath.exists()) {
                        filepath.mkdirs();// 创建文件夹
                    }
                    file = new File(pathUrl, imgPath);

                    FileOutputStream fos = new FileOutputStream(file);

                    byte[] buf = new byte[2048];
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                    }
                    fos.flush();
                    fos.close();
                    is.close();
                    final File finalFile = file;
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onSuccess(finalFile);
                        }
                    });
                } catch (final Exception e) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onFailure(e.getMessage());
                        }
                    });

                }

            }
        });
        return call;
    }

}
