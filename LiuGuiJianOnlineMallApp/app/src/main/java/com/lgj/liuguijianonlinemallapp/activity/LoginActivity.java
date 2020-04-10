package com.lgj.liuguijianonlinemallapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.rwcc.common.utils.SharedHelper;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends Activity {
    private EditText et_name,et_pwd;
    private TextView tv_toregister;
    private Button btn_login;
    private ImageView iv_back;
    static int REQ_FLAG=100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_activity_login);
        bind();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQ_FLAG){
            et_name.setText(data.getStringExtra("uname"));
        }
    }

    private void bind(){
        et_name=findViewById(R.id.et_name);
        et_pwd=findViewById(R.id.et_pwd);
        tv_toregister=findViewById(R.id.tv_toregister);
        iv_back=findViewById(R.id.iv_back);
        btn_login=findViewById(R.id.btn_login);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_toregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivityForResult(intent,REQ_FLAG);
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = et_name.getText().toString();
                String password = et_pwd.getText().toString();
                if (username==null||password==null||username.isEmpty()||password.isEmpty()){
                    Toast.makeText(LoginActivity.this,"请填写用户名或密码",Toast.LENGTH_SHORT).show();
                    return;
                }
                Map<String, String> map = new HashMap<>();
                map.put("username", username);
                map.put("password",password );
                RequestParams params = new RequestParams(map);
                RequestParamConfig.login(params, new ResponseCallback() {
                    @Override
                    public void onSuccess(Object responseObj) {
                        handler.obtainMessage(0,responseObj).sendToTarget();
                    }
                    @Override
                    public void onFailure(OkHttpException failuer) {
                        Toast.makeText(LoginActivity.this,failuer.getMsg(),Toast.LENGTH_SHORT).show();
                    }
                });
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
                    Type type = new TypeToken<ServerResult<User>>() {}.getType();
                    ServerResult<User> result = gson.fromJson(msg.obj.toString(), type);
                    if (result.getRetCode()==0){
                        User user=result.getData();
                        PreferencesUtils.putString(LoginActivity.this,"isReLogin","no");
                        PreferencesUtils.putString(LoginActivity.this,"username",user.getUsername());
                        PreferencesUtils.putString(LoginActivity.this,"phone",user.getPhone());
                        PreferencesUtils.putString(LoginActivity.this,"level",user.getLevel());
                        Intent intent=new Intent();
                        intent.putExtra("uname", PreferencesUtils.getString(LoginActivity.this,"username"));
                        setResult(100,intent);
                        finish();
                    }else {
                        PreferencesUtils.putString(LoginActivity.this,"isReLogin","yes");
                    }
                    break;
            }
        }
    };
}
