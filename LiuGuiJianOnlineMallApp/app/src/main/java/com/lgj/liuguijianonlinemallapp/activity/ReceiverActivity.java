package com.lgj.liuguijianonlinemallapp.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lgj.liuguijianonlinemallapp.R;
import com.lgj.liuguijianonlinemallapp.adapter.AddressRecyclerViewAdapter;
import com.lgj.liuguijianonlinemallapp.adapter.AssessRecyclerViewAdapter;
import com.lgj.liuguijianonlinemallapp.bean.Gaddress;
import com.lgj.liuguijianonlinemallapp.bean.ServerResult;
import com.lgj.liuguijianonlinemallapp.utils.PreferencesUtils;
import com.lgj.liuguijianonlinemallapp.utils.RequestParamConfig;
import com.ruiwcc.okhttpPlus.exception.OkHttpException;
import com.ruiwcc.okhttpPlus.request.RequestParams;
import com.ruiwcc.okhttpPlus.response.ResponseCallback;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReceiverActivity extends Activity {

    private TextView tv_toadd;
    private ImageView iv_receiver_back;
    private RecyclerView rv_receiver;
    private List<Gaddress> gaddresses = new ArrayList<>();
    private AddressRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_activity_receiver);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }
    private void showDeleteDialog(final int postion) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("是否删除这条收货人信息?");
        dialog.setMessage("");
        dialog.setCancelable(false);    //设置是否可以通过点击对话框外区域或者返回按键关闭对话框
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                delete(postion);
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

    private void delete(final int postion) {
        Map<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(gaddresses.get(postion).getId()));
        RequestParams params = new RequestParams(map);
        RequestParamConfig.deleteReceiver(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                Gson gson = new Gson();
                Type type = new TypeToken<ServerResult>() {
                }.getType();
                ServerResult result = gson.fromJson(responseObj.toString(), type);
                if (result.getRetCode() == 0) {
                    gaddresses.remove(postion);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(ReceiverActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                Toast.makeText(ReceiverActivity.this, failuer.getMsg(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadData() {
        Map<String, String> map = new HashMap<>();
        map.put("uname", PreferencesUtils.getString(ReceiverActivity.this, "username"));
        RequestParams params = new RequestParams(map);
        RequestParamConfig.getReceiver(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                Gson gson = new Gson();
                Type type = new TypeToken<ServerResult<List<Gaddress>>>() {
                }.getType();
                ServerResult<List<Gaddress>> result = gson.fromJson(responseObj.toString(), type);
                if (result.getRetCode() == 0) {
                    if (result.getData() != null && result.getData().size() > 0) {
                        gaddresses.clear();
                        gaddresses.addAll(result.getData());
                        adapter.notifyDataSetChanged();
                    }

                }
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                Toast.makeText(ReceiverActivity.this, failuer.getMsg(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init() {
        iv_receiver_back = findViewById(R.id.iv_receiver_back);
        iv_receiver_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_toadd = findViewById(R.id.tv_toadd);
        tv_toadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReceiverActivity.this, AddressActivity.class);
                startActivity(intent);
            }
        });
        rv_receiver = findViewById(R.id.rv_receiver);
        adapter = new AddressRecyclerViewAdapter(ReceiverActivity.this, gaddresses);
        rv_receiver.setLayoutManager(new LinearLayoutManager(ReceiverActivity.this));
        rv_receiver.setAdapter(adapter);
        adapter.setOnitemClickListener(new AddressRecyclerViewAdapter.OnitemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                if (getIntent().getStringExtra("from")==null||
                        getIntent().getStringExtra("from").isEmpty()){
                    Intent intent = new Intent(ReceiverActivity.this, AddressActivity.class);
                    intent.putExtra("address", (Serializable) gaddresses.get(postion));
                    startActivity(intent);
                }else {
                    Intent intent = new Intent();
                    intent.putExtra("address", (Serializable) gaddresses.get(postion));
                    setResult(200,intent);
                    finish();
                }

            }
        });
        adapter.setOnitemLongClickListener(new AddressRecyclerViewAdapter.OnitemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int postion) {
                showDeleteDialog(postion);
            }
        });
    }
}
