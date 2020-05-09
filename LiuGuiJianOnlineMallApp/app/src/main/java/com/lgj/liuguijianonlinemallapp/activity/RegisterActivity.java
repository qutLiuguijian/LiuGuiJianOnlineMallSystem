package com.lgj.liuguijianonlinemallapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.ruiwcc.okhttpPlus.exception.OkHttpException;
import com.ruiwcc.okhttpPlus.request.RequestParams;
import com.ruiwcc.okhttpPlus.response.ResponseCallback;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends Activity implements View.OnClickListener {
    private EditText et_phone, et_usename, et_password;
    private Button btn_register;
    private ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_activity_register);
        init();
    }

    private void init() {
        et_usename = findViewById(R.id.et_usename);
        et_phone = findViewById(R.id.et_phone);
        et_password = findViewById(R.id.et_password);
        btn_register = findViewById(R.id.btn_register);
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        btn_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_register:
                register();
                break;
        }
    }

    private void register() {
        Map<String, String> map = new HashMap<>();
        map.put("username", et_usename.getText().toString());
        map.put("password",et_password.getText().toString() );
        map.put("phone",et_phone.getText().toString() );
        RequestParams params = new RequestParams(map);
        RequestParamConfig.register(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                handler.obtainMessage(0,responseObj).sendToTarget();
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                Toast.makeText(RegisterActivity.this,failuer.getMsg(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    Gson gson = new Gson();
                    Type type = new TypeToken<ServerResult>() {}.getType();
                    ServerResult result = gson.fromJson(msg.obj.toString(), type);
                    Toast.makeText(RegisterActivity.this,result.getMessage(),Toast.LENGTH_SHORT).show();
                    if (result.getRetCode()==0){
                        Intent intent=new Intent();
                        intent.putExtra("uname", et_usename.getText().toString());
                        setResult(100,intent);
                        finish();
                    }
                    break;
            }
        }
    };
}
