package com.lgj.liuguijianonlinemallapp.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lgj.liuguijianonlinemallapp.R;
import com.lgj.liuguijianonlinemallapp.bean.ServerResult;
import com.lgj.liuguijianonlinemallapp.bean.User;
import com.lgj.liuguijianonlinemallapp.utils.PreferencesUtils;
import com.lgj.liuguijianonlinemallapp.utils.RequestParamConfig;
import com.ruiwcc.okhttpPlus.exception.OkHttpException;
import com.ruiwcc.okhttpPlus.request.RequestParams;
import com.ruiwcc.okhttpPlus.response.ResponseCallback;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransitActivity extends Activity {
    String[] permissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
    };
    // 声明一个集合，在后面的代码中用来存储用户拒绝授权的权
    List<String> mPermissionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_activity_transit);
        getQuanXian();
    }

    private void goMainAct() {
        String isFirstStart = PreferencesUtils.getString(TransitActivity.this, "isFirstStart");
        if (isFirstStart == null || isFirstStart.isEmpty()) {
            PreferencesUtils.putString(TransitActivity.this, "isFirstStart", "yes");
            Intent intent = new Intent(TransitActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            String username = PreferencesUtils.getString(TransitActivity.this, "username");
            if (username == null || username.isEmpty()) {
                PreferencesUtils.putString(TransitActivity.this, "isFirstStart", "yes");
                Intent intent = new Intent(TransitActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Map<String, String> map = new HashMap<>();
                map.put("username", username);
                RequestParams params = new RequestParams(map);
                RequestParamConfig.isReLogin(params, new ResponseCallback() {
                    @Override
                    public void onSuccess(Object responseObj) {
                        handler.obtainMessage(0, responseObj).sendToTarget();
                    }

                    @Override
                    public void onFailure(OkHttpException failuer) {
                        Toast.makeText(TransitActivity.this, failuer.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

        }
    }

    private void getQuanXian() {
        Log.i("kkkkkkkkkk", Build.VERSION.SDK_INT + "=");
        if (Build.VERSION.SDK_INT >= 24) {
            checkPermission();
        } else {
            goMainAct();
        }
    }

    /**
     * 检查权限是否已接近配置
     */
    private void checkPermission() {
        mPermissionList = new ArrayList<String>();
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(TransitActivity.this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i]);
            }
        }
        if (mPermissionList.isEmpty()) {//未授予的权限为空，表示都授予了
            goMainAct();
        } else {//请求权限方法
            String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]);//将List转为数组
            ActivityCompat.requestPermissions(TransitActivity.this, permissions, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    //判断是否勾选禁止后不再询问
                    boolean showRequestPermission = ActivityCompat.shouldShowRequestPermissionRationale(TransitActivity.this, permissions[i]);
                    if (showRequestPermission) {
                        Toast.makeText(TransitActivity.this, "权限未申请", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        goMainAct();
    }

    private void loadMark() {
        Map<String, String> map = new HashMap<>();
        map.put("username", PreferencesUtils.getString(TransitActivity.this, "username"));
        RequestParams params = new RequestParams(map);
        RequestParamConfig.isShowMark(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                Gson gson = new Gson();
                Type type = new TypeToken<ServerResult>() {
                }.getType();
                ServerResult result = gson.fromJson(responseObj.toString(), type);
                if (result.getRetCode() == 0) {
                    PreferencesUtils.putBoolean(TransitActivity.this, "isShowMark", true);
                } else {
                    PreferencesUtils.putBoolean(TransitActivity.this, "isShowMark", false);
                }
                Intent intent = new Intent(TransitActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                Toast.makeText(TransitActivity.this, failuer.getMsg(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    Gson gson = new Gson();
                    Type type = new TypeToken<ServerResult<User>>() {
                    }.getType();
                    ServerResult<User> result = gson.fromJson(msg.obj.toString(), type);
                    if (result.getRetCode() == 0) {
                        User user = result.getData();
                        PreferencesUtils.putString(TransitActivity.this, "isReLogin", "no");
                        PreferencesUtils.putString(TransitActivity.this, "username", user.getUsername());
                        PreferencesUtils.putString(TransitActivity.this, "phone", user.getPhone());
                        PreferencesUtils.putString(TransitActivity.this, "level", user.getLevel());
                        loadMark();
                    } else {
                        PreferencesUtils.putBoolean(TransitActivity.this, "isShowMark", false);
                        PreferencesUtils.putString(TransitActivity.this, "isReLogin", "yes");
                    }
                    PreferencesUtils.putString(TransitActivity.this, "isFirstStart", "yes");
                    break;
            }
        }
    };
}
