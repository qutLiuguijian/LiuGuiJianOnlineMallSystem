package com.lgj.liuguijianonlinemallapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lgj.liuguijianonlinemallapp.R;
import com.lgj.liuguijianonlinemallapp.bean.ServerResult;
import com.lgj.liuguijianonlinemallapp.bean.User;
import com.lgj.liuguijianonlinemallapp.utils.PreferencesUtils;
import com.lgj.liuguijianonlinemallapp.utils.RequestParamConfig;
import com.mob.MobSDK;
import com.ruiwcc.okhttpPlus.exception.OkHttpException;
import com.ruiwcc.okhttpPlus.request.RequestParams;
import com.ruiwcc.okhttpPlus.response.ResponseCallback;
import com.rwcc.common.utils.LogUtil;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class RegisterActivity extends Activity implements View.OnClickListener {
    private EditText et_phone, et_usename, et_password, et_code;
    private Button btn_register, btn_sendCode;
    private ImageView iv_back;
    private CountDownTimer countDownTimer;
    private boolean flag=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_activity_register);
        init();
        SMSSDK.registerEventHandler(eh);
    }

    private void init() {
        et_usename = findViewById(R.id.et_usename);
        et_phone = findViewById(R.id.et_phone);
        et_password = findViewById(R.id.et_password);
        btn_register = findViewById(R.id.btn_register);
        iv_back = findViewById(R.id.iv_back);
        et_code = findViewById(R.id.et_code);
        btn_sendCode = findViewById(R.id.btn_sendCode);
        btn_sendCode.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        btn_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_sendCode:
                if (et_phone.getText().toString().trim() == null || et_phone.getText().toString().trim().isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "手机号不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                flag=true;
                SMSSDK.getVerificationCode("86", et_phone.getText().toString().trim());

                break;
            case R.id.btn_register:
                if (et_code.getText().toString().trim() == null || et_phone.getText().toString().trim().isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "请填写验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                flag=false;
                SMSSDK.submitVerificationCode("86", et_phone.getText().toString().trim(), et_code.getText().toString().trim());

                break;
        }
    }

    private void register() {
        Map<String, String> map = new HashMap<>();
        map.put("username", et_usename.getText().toString());
        map.put("password", et_password.getText().toString());
        map.put("phone", et_phone.getText().toString());
        RequestParams params = new RequestParams(map);
        RequestParamConfig.register(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                handler.obtainMessage(0, responseObj).sendToTarget();
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                Toast.makeText(RegisterActivity.this, failuer.getMsg(), Toast.LENGTH_SHORT).show();
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
                    Type type = new TypeToken<ServerResult>() {
                    }.getType();
                    ServerResult result = gson.fromJson(msg.obj.toString(), type);
                    Toast.makeText(RegisterActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                    if (result.getRetCode() == 0) {
                        Intent intent = new Intent();
                        intent.putExtra("uname", et_usename.getText().toString());
                        setResult(100, intent);
                        finish();
                    }
                    break;
                case 1:
                    Toast.makeText(RegisterActivity.this, "验证码发送成功", Toast.LENGTH_SHORT).show();
                    countDownTimer = new CountDownTimer(60 * 1000, 1000) {
                        @Override
                        public void onTick(long l) {
                            btn_sendCode.setClickable(false);
                            btn_sendCode.setText("重新发送(" + l / 1000 + "s)");
                            btn_sendCode.setTextColor(Color.parseColor("#999999"));
                            btn_sendCode.setBackgroundColor(Color.parseColor("#F6F6F6"));
                        }

                        @Override
                        public void onFinish() {
                            if (!"".equals(et_code.getText().toString())) {
                                btn_sendCode.setClickable(true);
                                btn_sendCode.setText("发送验证码");
                                btn_sendCode.setTextColor(Color.parseColor("#000000"));
                                btn_sendCode.setBackgroundColor(Color.parseColor("#F6F6F6"));
                            } else {
                                btn_sendCode.setClickable(true);
                                btn_sendCode.setText("发送验证码");
                                btn_sendCode.setBackgroundColor(Color.parseColor("#ff0000"));
                                btn_sendCode.setTextColor(Color.parseColor("#ffffff"));
                            }

                        }
                    }.start();
                    break;
                case 2:
                    if (flag){
                        Toast.makeText(RegisterActivity.this, "请确认手机号是否输入正确", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(RegisterActivity.this, "请确认验证码是否输入正确", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };
    EventHandler eh = new EventHandler() {
        @Override
        public void afterEvent(int i, int i1, Object o) {
            super.afterEvent(i, i1, o);
            Log.i("hhhhhhhhh",i+"="+i1+"="+o.toString());
            if (i1 == SMSSDK.RESULT_COMPLETE) {
                if (i == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    //提交验证码成功
                    register();
                } else if (i == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    //获取验证码成功
                    handler.sendEmptyMessage(1);
                }
            } else {
                handler.sendEmptyMessage(2);
            }


        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }
}
