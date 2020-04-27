package com.lgj.liuguijianonlinemallapp.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lgj.liuguijianonlinemallapp.R;
import com.lgj.liuguijianonlinemallapp.bean.Goods;
import com.lgj.liuguijianonlinemallapp.bean.ServerResult;
import com.lgj.liuguijianonlinemallapp.utils.PreferencesUtils;
import com.lgj.liuguijianonlinemallapp.utils.RequestParamConfig;
import com.ruiwcc.okhttpPlus.exception.OkHttpException;
import com.ruiwcc.okhttpPlus.request.RequestParams;
import com.ruiwcc.okhttpPlus.response.ResponseCallback;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssessActivity extends Activity {
    private ImageView iv_assess_goods;
    private TextView tv_assess_name, tv_assess_desc;
    private EditText et_assess;
    private Button btn_assess;
    private TextView tv_title;
    private ImageView iv_order_back;
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_activity_assess);
        init();
    }

    private void init() {
        iv_assess_goods = findViewById(R.id.iv_assess_goods);
        tv_assess_desc = findViewById(R.id.tv_assess_desc);
        tv_assess_name = findViewById(R.id.tv_assess_name);
        btn_assess = findViewById(R.id.btn_assess);
        et_assess = findViewById(R.id.et_assess);
        tv_assess_name.setText(getIntent().getStringExtra("name"));
        tv_assess_desc.setText(getIntent().getStringExtra("desc"));
        Glide.with(AssessActivity.this).load(getIntent().getStringExtra("img")).into(iv_assess_goods);
        pos=getIntent().getIntExtra("pos",-1);
        tv_title = findViewById(R.id.tv_title);
        tv_title.setText("评价");
        iv_order_back=findViewById(R.id.iv_order_back);
        iv_order_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_assess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAssessDialog();
            }
        });
    }

    private void submit() {
        Map<String, String> map = new HashMap<>();
        map.put("gid", String.valueOf(getIntent().getIntExtra("gid", -1)));
        map.put("uname",  PreferencesUtils.getString(AssessActivity.this, "username"));
        map.put("assess", et_assess.getText().toString());
        RequestParams params = new RequestParams(map);
        RequestParamConfig.addAssess(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                Gson gson = new Gson();
                Type type = new TypeToken<ServerResult>() {
                }.getType();
                ServerResult result = gson.fromJson(responseObj.toString(), type);
                if (result.getRetCode() == 0) {
                    Toast.makeText(AssessActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent();
                    intent.putExtra("pos",pos);
                    intent.putExtra("update","yes");
                    setResult(200,intent);
                    finish();
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                Toast.makeText(AssessActivity.this, failuer.getMsg(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    void showAssessDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(AssessActivity.this);
        dialog.setTitle("是否提交评价?");
        dialog.setMessage("");
        dialog.setCancelable(false);    //设置是否可以通过点击对话框外区域或者返回按键关闭对话框
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                submit();
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }
}
