package com.lgj.liuguijianonlinemallapp.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lgj.liuguijianonlinemallapp.R;
import com.lgj.liuguijianonlinemallapp.activity.GoodsDetailActivity;
import com.lgj.liuguijianonlinemallapp.activity.LoginActivity;
import com.lgj.liuguijianonlinemallapp.adapter.CarGoodsRecyclerViewAdapter;
import com.lgj.liuguijianonlinemallapp.bean.Goods;
import com.lgj.liuguijianonlinemallapp.bean.ServerResult;
import com.lgj.liuguijianonlinemallapp.utils.GlideImageLoader;
import com.lgj.liuguijianonlinemallapp.utils.PreferencesUtils;
import com.lgj.liuguijianonlinemallapp.utils.RequestParamConfig;
import com.ruiwcc.okhttpPlus.exception.OkHttpException;
import com.ruiwcc.okhttpPlus.request.RequestParams;
import com.ruiwcc.okhttpPlus.response.ResponseCallback;
import com.youth.banner.BannerConfig;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyCartFragment extends Fragment implements View.OnClickListener{
    private RecyclerView rv_goods;
    private CheckBox checkbox_all;
    private TextView tv_allpay;
    private Button btn_topay;
    private CarGoodsRecyclerViewAdapter adapter;
    private List<Goods> goodsList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.module_fragment_mycart, container, false);
        init(view);
        String isReLogin = PreferencesUtils.getString(getActivity(), "isReLogin");
        if (isReLogin == null || isReLogin.isEmpty() || isReLogin.equals("yes")) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivityForResult(intent, 300);
        } else {
            bind();
            loadData();
        }
        return view;
    }

    private void loadData() {
        Map<String, String> map = new HashMap<>();
        map.put("username", PreferencesUtils.getString(getActivity(), "username"));
        RequestParams params = new RequestParams(map);
        RequestParamConfig.getCar(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                handler.obtainMessage(0, responseObj).sendToTarget();
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                Toast.makeText(getActivity(), failuer.getMsg(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void bind() {
        adapter = new CarGoodsRecyclerViewAdapter(getContext(), goodsList);
        rv_goods.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_goods.setAdapter(adapter);
        checkbox_all.setOnClickListener(this);
        btn_topay.setOnClickListener(this);
    }

    private void init(View view) {
        rv_goods = view.findViewById(R.id.rv_goods);
        checkbox_all = view.findViewById(R.id.checkbox_all);
        tv_allpay = view.findViewById(R.id.tv_allpay);
        btn_topay = view.findViewById(R.id.btn_topay);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 300) {
            bind();
            loadData();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    Gson gson = new Gson();
                    Type type = new TypeToken<ServerResult<List<Goods>>>() {
                    }.getType();
                    ServerResult<List<Goods>> result = gson.fromJson(msg.obj.toString(), type);
                    if (result.getRetCode() == 0) {
                        goodsList.clear();
                        goodsList.addAll(result.getData());
                        adapter.notifyDataSetChanged();
                    }
                    break;

            }
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_topay:
                break;
            case R.id.checkbox_all:
                if (checkbox_all.isChecked()){

                }else {

                }
                break;
        }
    }
}
