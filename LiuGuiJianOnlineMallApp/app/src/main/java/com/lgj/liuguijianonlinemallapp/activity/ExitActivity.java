package com.lgj.liuguijianonlinemallapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ExitActivity extends Activity {
    private ImageView iv_exit_back;
    private TextView tv_uname,tv_toAddress;
    private Button btn_exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_activity_exit);
        init();
    }

    private void init() {
        iv_exit_back=findViewById(R.id.iv_exit_back);
        iv_exit_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_uname=findViewById(R.id.tv_uname);
        tv_uname.setText(PreferencesUtils.getString(ExitActivity.this, "username"));
        tv_toAddress=findViewById(R.id.tv_toAddress);
        tv_toAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ExitActivity.this,ReceiverActivity.class);
                startActivity(intent);
            }
        });
        btn_exit=findViewById(R.id.btn_exit);
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exit();
            }
        });

    }

    private void exit() {
        Map<String, String> map = new HashMap<>();
        RequestParams params = new RequestParams(map);
        RequestParamConfig.exitLogin(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                Gson gson = new Gson();
                Type type = new TypeToken<ServerResult>() {}.getType();
                ServerResult result = gson.fromJson(responseObj.toString(), type);
                if (result.getRetCode()==0){
                    PreferencesUtils.putString(ExitActivity.this,"isReLogin","yes");
                    finish();
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {

            }
        });
    }
}
