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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lgj.liuguijianonlinemallapp.R;
import com.lgj.liuguijianonlinemallapp.activity.AssessActivity;
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

public class AllFragment extends Fragment {
    private RecyclerView rv_all;
    private List<Goods> goods = new ArrayList();
    private OrderGoodsRecyclerViewAdapter adapter;
    private LinearLayout ll_tip;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.module_fragment_all, container, false);
        init(view);
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            handler.sendEmptyMessage(1);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == 200) {
                if (data.getStringExtra("update") != null) {
                    updateData(data.getIntExtra("pos", -1), 4);
                }
            }
        }
    }

    private void init(View view) {
        rv_all = view.findViewById(R.id.rv_all);
        ll_tip = view.findViewById(R.id.ll_tip);
        adapter = new OrderGoodsRecyclerViewAdapter(getContext(), goods, handler);
        rv_all.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_all.setAdapter(adapter);

    }

    public void loadData() {
        Map<String, String> map = new HashMap<>();
        map.put("uname", PreferencesUtils.getString(getActivity(), "username"));
        map.put("state", "-1");
        RequestParams params = new RequestParams(map);
        RequestParamConfig.getAllOrderByUAS(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                handler.obtainMessage(0, responseObj).sendToTarget();
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                Toast.makeText(getContext(), failuer.getMsg(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateData(int position, int state) {
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
                Toast.makeText(getContext(), failuer.getMsg(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteData(int position) {
        Map<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(goods.get(position).getId()));
        RequestParams params = new RequestParams(map);
        RequestParamConfig.deleteOrder(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                handler.sendEmptyMessage(1);
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                Toast.makeText(getContext(), failuer.getMsg(), Toast.LENGTH_SHORT).show();
            }
        });
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
                    updateData(Integer.parseInt(msg.obj.toString()), goods.get(Integer.parseInt(msg.obj.toString())).getState() + 1);
                    break;
                case 3:
                    if (goods.get(Integer.parseInt(msg.obj.toString())).getState() == 0) {
                        updateData(Integer.parseInt(msg.obj.toString()), 4);
                    } else {
                        deleteData(Integer.parseInt(msg.obj.toString()));
                    }
                case 4:
                    Intent intent = new Intent(getContext(), AssessActivity.class);
                    intent.putExtra("pos", Integer.parseInt(msg.obj.toString()));
                    intent.putExtra("gid", goods.get(Integer.parseInt(msg.obj.toString())).getGid());
                    intent.putExtra("img", goods.get(Integer.parseInt(msg.obj.toString())).getGimage());
                    intent.putExtra("name", goods.get(Integer.parseInt(msg.obj.toString())).getGname());
                    intent.putExtra("desc", goods.get(Integer.parseInt(msg.obj.toString())).getGdesc());
                    startActivityForResult(intent, 100);
                    break;
            }
        }
    };
}
