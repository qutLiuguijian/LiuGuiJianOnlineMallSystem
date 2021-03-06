package com.lgj.liuguijianonlinemallapp.activity.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.lgj.liuguijianonlinemallapp.bean.Goods;
import com.lgj.liuguijianonlinemallapp.bean.ServerResult;
import com.lgj.liuguijianonlinemallapp.utils.RequestParamConfig;
import com.ruiwcc.okhttpPlus.exception.OkHttpException;
import com.ruiwcc.okhttpPlus.request.RequestParams;
import com.ruiwcc.okhttpPlus.response.ResponseCallback;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DailyFragment extends Fragment {
    private RecyclerView rv_daily;
    private List<Goods> goods=new ArrayList();
    private MyGoodsRecyclerViewAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.module_fragment_daily, container, false);
        init(view);
        loadData();
        return view;
    }
    private void init(View view){
        rv_daily=view.findViewById(R.id.rv_daily);
        adapter=new MyGoodsRecyclerViewAdapter(getContext(),goods);
        rv_daily.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        rv_daily.setAdapter(adapter);
        adapter.setOnitemClickListener(new MyGoodsRecyclerViewAdapter.OnitemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                Intent intent=new Intent(getActivity(), GoodsDetailActivity.class);
                intent.putExtra("id",goods.get(postion).getId());
                startActivity(intent);
            }
        });
    }
    private void loadData(){
        Map<String, String> map = new HashMap<>();
        map.put("classify",  "日用百货");
        RequestParams params = new RequestParams(map);
        RequestParamConfig.getGoodsByClassify(params, new ResponseCallback() {
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
            }
        }
    };
}
