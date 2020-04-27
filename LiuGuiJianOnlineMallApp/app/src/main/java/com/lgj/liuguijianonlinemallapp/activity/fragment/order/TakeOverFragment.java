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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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

public class TakeOverFragment extends Fragment {
    private RecyclerView rv_takeover;
    private List<Goods> goods=new ArrayList();
    private OrderGoodsRecyclerViewAdapter adapter;
    private LinearLayout ll_tip;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.module_fragment_takeover, container, false);
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
        rv_takeover=view.findViewById(R.id.rv_takeover);
        ll_tip=view.findViewById(R.id.ll_tip);
        adapter=new OrderGoodsRecyclerViewAdapter(getContext(),goods,handler);
        rv_takeover.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_takeover.setAdapter(adapter);
    }
    public void loadData(){
        Log.i("kkkkkkkk","kk");
        Map<String, String> map = new HashMap<>();
        map.put("uname",  PreferencesUtils.getString(getActivity(), "username"));
        map.put("state",  "2");
        RequestParams params = new RequestParams(map);
        RequestParamConfig.getAllOrderByUAS(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                Log.i("kkkkkkkk",responseObj.toString());
                handler.obtainMessage(0,responseObj).sendToTarget();
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                Toast.makeText(getContext(),failuer.getMsg(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void updateData(int position,int state){
        Map<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(goods.get(position).getId()));
        map.put("state", String.valueOf(state));
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
                    Log.i("kkkkkkkk",msg.obj.toString());
                    Gson gson = new Gson();
                    Type type = new TypeToken<ServerResult<List<Goods>>>() {}.getType();
                    ServerResult<List<Goods>> result = gson.fromJson(msg.obj.toString(), type);
                    if (result.getRetCode()==0){
                        goods.clear();
                        goods.addAll(result.getData());
                        adapter.notifyDataSetChanged();
                        if (goods.size() > 0) {
                            ll_tip.setVisibility(View.GONE);
                        } else {
                            ll_tip.setVisibility(View.VISIBLE);
                        }
                    }
                    break;
                case 1:
                    loadData();
                    break;
                case 2:
                    updateData(Integer.parseInt(msg.obj.toString()),goods.get(Integer.parseInt(msg.obj.toString())).getState()+1);
                    break;
            }
        }
    };
}
