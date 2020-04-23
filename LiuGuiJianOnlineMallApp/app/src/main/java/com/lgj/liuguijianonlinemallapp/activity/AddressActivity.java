package com.lgj.liuguijianonlinemallapp.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lgj.liuguijianonlinemallapp.R;
import com.lgj.liuguijianonlinemallapp.bean.Gaddress;
import com.lgj.liuguijianonlinemallapp.bean.ServerResult;
import com.lgj.liuguijianonlinemallapp.utils.PreferencesUtils;
import com.lgj.liuguijianonlinemallapp.utils.RequestParamConfig;
import com.ruiwcc.okhttpPlus.exception.OkHttpException;
import com.ruiwcc.okhttpPlus.request.RequestParams;
import com.ruiwcc.okhttpPlus.response.ResponseCallback;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class AddressActivity extends AppCompatActivity {
    private ImageView iv_receiver_back;
    private EditText et_name,et_phone,et_address;
    private TextView tv_save;
    private Gaddress gaddress=new Gaddress();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_activity_address);
        init();
    }
    private void init() {
        iv_receiver_back=findViewById(R.id.iv_receiver_back);
        iv_receiver_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_save=findViewById(R.id.tv_save);
        et_name=findViewById(R.id.et_name);
        et_phone=findViewById(R.id.et_phone);
        et_address=findViewById(R.id.et_address);
        if (getIntent().getSerializableExtra("address")!=null){
            gaddress= (Gaddress) getIntent().getSerializableExtra("address");
            et_name.setText(gaddress.getReceiver());
            et_phone.setText(gaddress.getPhone());
            et_address.setText(gaddress.getAddress());
        }
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
    }

    private void save() {
        if (et_name.getText().toString()==null||et_name.getText().toString().isEmpty()){
            Toast.makeText(AddressActivity.this,"收货人不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if (et_phone.getText().toString()==null||et_phone.getText().toString().isEmpty()){
            Toast.makeText(AddressActivity.this,"收货人联系方式不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if (et_address.getText().toString()==null||et_address.getText().toString().isEmpty()){
            Toast.makeText(AddressActivity.this,"收货人地址不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(gaddress.getId()));
        map.put("uname", PreferencesUtils.getString(AddressActivity.this, "username"));
        map.put("receiver", et_name.getText().toString());
        map.put("address", et_address.getText().toString());
        map.put("phone",et_phone.getText().toString() );
        RequestParams params = new RequestParams(map);
        RequestParamConfig.saveReceiver(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                Gson gson = new Gson();
                Type type = new TypeToken<ServerResult>() {}.getType();
                ServerResult result = gson.fromJson(responseObj.toString(), type);
                if (result.getRetCode()==0){
                   finish();
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                Toast.makeText(AddressActivity.this,failuer.getMsg(),Toast.LENGTH_SHORT).show();
            }
        });
    }

}
