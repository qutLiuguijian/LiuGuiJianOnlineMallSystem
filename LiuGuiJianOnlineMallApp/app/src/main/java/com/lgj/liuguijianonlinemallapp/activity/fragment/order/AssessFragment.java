package com.lgj.liuguijianonlinemallapp.activity.fragment.order;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lgj.liuguijianonlinemallapp.R;
import com.lgj.liuguijianonlinemallapp.activity.GoodsDetailActivity;
import com.lgj.liuguijianonlinemallapp.adapter.MyGoodsRecyclerViewAdapter;
import com.lgj.liuguijianonlinemallapp.adapter.OrderGoodsRecyclerViewAdapter;
import com.lgj.liuguijianonlinemallapp.bean.Goods;
import com.lgj.liuguijianonlinemallapp.bean.ServerResult;
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

public class AssessFragment extends Fragment {
    private RecyclerView rv_assess;
    private List<Goods> goods=new ArrayList();
    private OrderGoodsRecyclerViewAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.module_fragment_assess, container, false);
        init(view);
        return view;
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            handler.sendEmptyMessage(1);
        }
    }
    private void init(View view){
        rv_assess=view.findViewById(R.id.rv_assess);
        adapter=new OrderGoodsRecyclerViewAdapter(getContext(),goods,handler);
        rv_assess.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_assess.setAdapter(adapter);

    }
    public void loadData(){
        Map<String, String> map = new HashMap<>();
        map.put("uname",  PreferencesUtils.getString(getActivity(), "username"));
        map.put("state",  "3");
        RequestParams params = new RequestParams(map);
        RequestParamConfig.getAllOrderByUAS(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                handler.obtainMessage(0,responseObj).sendToTarget();
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                Toast.makeText(getContext(),failuer.getMsg(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void updateData(int position){
        Map<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(goods.get(position).getId()));
        map.put("state", String.valueOf(goods.get(position).getState()+1));
        RequestParams params = new RequestParams(map);
        RequestParamConfig.updateOrder(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                handler.sendEmptyMessage(1);
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                Toast.makeText(getContext(),failuer.getMsg(),Toast.LENGTH_SHORT).show();
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
                    Type type = new TypeToken<ServerResult<List<Goods>>>() {}.getType();
                    ServerResult<List<Goods>> result = gson.fromJson(msg.obj.toString(), type);
                    if (result.getRetCode()==0){
                        if (result.getData()!=null){
                            goods.clear();
                            goods.addAll(result.getData());
                            adapter.notifyDataSetChanged();
                        }
                    }
                    break;
                case 1:
                    loadData();
                    break;
                case 2:
                    updateData(Integer.parseInt(msg.obj.toString()));
                    break;
            }
        }
    };
}
