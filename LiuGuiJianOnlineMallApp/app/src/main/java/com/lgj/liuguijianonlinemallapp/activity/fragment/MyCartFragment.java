package com.lgj.liuguijianonlinemallapp.activity.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lgj.liuguijianonlinemallapp.R;
import com.lgj.liuguijianonlinemallapp.activity.BalanceActivity;
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

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyCartFragment extends Fragment implements View.OnClickListener {
    private RecyclerView rv_goods;
    private CheckBox checkbox_all;
    private TextView tv_allpay,tv_tip;
    private Button btn_topay;
    private LinearLayout ll_tip;
    private RelativeLayout rl_footer;
    private CarGoodsRecyclerViewAdapter adapter;
    private List<Goods> goodsList = new ArrayList<>();
    private List<Goods> goodsChecked = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.module_fragment_mycart, container, false);
        init(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        String isReLogin = PreferencesUtils.getString(getActivity(), "isReLogin");
        if (isReLogin == null || isReLogin.isEmpty() || isReLogin.equals("yes")) {

            ll_tip.setVisibility(View.VISIBLE);
            tv_tip.setText("还未登录,请点击去登录");
            tv_tip.setClickable(true);
            tv_tip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent, 300);
                }
            });
        } else {
            tv_tip.setClickable(false);
            bind();
            loadData();
        }
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
        adapter = new CarGoodsRecyclerViewAdapter(getContext(), handler, goodsList);
        rv_goods.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_goods.setAdapter(adapter);
        checkbox_all.setChecked(false);
        checkbox_all.setOnClickListener(this);
        btn_topay.setOnClickListener(this);
        adapter.setOnitemLongClickListener(new CarGoodsRecyclerViewAdapter.OnitemLongClickListener() {
            @Override
            public void onItemLongClick(View view, final int postion) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setTitle("是否删除?");
                dialog.setMessage("");
                dialog.setCancelable(false);    //设置是否可以通过点击对话框外区域或者返回按键关闭对话框
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Map<String, String> map = new HashMap<>();
                        map.put("id", String.valueOf(goodsList.get(postion).getId()));
                        RequestParams params = new RequestParams(map);
                        RequestParamConfig.deleteCar(params, new ResponseCallback() {
                            @Override
                            public void onSuccess(Object responseObj) {
                                Gson gson = new Gson();
                                Type type = new TypeToken<ServerResult>() {
                                }.getType();
                                ServerResult result = gson.fromJson(responseObj.toString(), type);
                                Toast.makeText(getActivity(), result.getMessage(), Toast.LENGTH_SHORT).show();
                                if (result.getRetCode() == 0) {
                                    goodsList.remove(postion);
                                    adapter.notifyDataSetChanged();
                                    if (goodsList.size() > 0) {
                                        rl_footer.setVisibility(View.VISIBLE);
                                        ll_tip.setVisibility(View.GONE);
                                    }else {
                                        rl_footer.setVisibility(View.GONE);
                                        ll_tip.setVisibility(View.VISIBLE);
                                        tv_tip.setText("购物车还是空的");
                                        tv_tip.setClickable(false);
                                    }
                                }
                            }

                            @Override
                            public void onFailure(OkHttpException failuer) {
                                Toast.makeText(getActivity(), failuer.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        });

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
        });
    }

    private void init(View view) {
        rv_goods = view.findViewById(R.id.rv_goods);
        checkbox_all = view.findViewById(R.id.checkbox_all);
        tv_allpay = view.findViewById(R.id.tv_allpay);
        btn_topay = view.findViewById(R.id.btn_topay);
        rl_footer = view.findViewById(R.id.rl_footer);
        ll_tip = view.findViewById(R.id.ll_tip);
        tv_tip = view.findViewById(R.id.tv_tip);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 300) {
            if (resultCode == 100) {
                bind();
                loadData();
            }

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
                        if (goodsList.size() > 0) {
                            rl_footer.setVisibility(View.VISIBLE);
                            ll_tip.setVisibility(View.GONE);
                        }else {
                            rl_footer.setVisibility(View.GONE);
                            ll_tip.setVisibility(View.VISIBLE);
                            tv_tip.setText("购物车还是空的");
                            tv_tip.setClickable(false);
                        }
                    }
                    break;
                case 1:
                    pay();
                    break;
            }
        }
    };

    private void pay() {
        int buy_count = 0;
        double money = 0;
        boolean flag = true;
        for (int i = 0; i < goodsList.size(); i++) {
            if (goodsList.get(i).isChecked()) {
                buy_count++;
                money = money + goodsList.get(i).getGprice() * goodsList.get(i).getCount();
            } else {
                flag = false;
            }
        }
        checkbox_all.setChecked(flag);
        if (buy_count == 0) {
            tv_allpay.setText("0.00");
            btn_topay.setText("去结算");
        } else {
            tv_allpay.setText(money + "");
            btn_topay.setText("去结算(" + buy_count + ")");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_topay:
                Intent intent = new Intent(getActivity(), BalanceActivity.class);
                intent.putExtra("from", "mycar");
                goodsChecked.clear();
                for (int i = 0; i < goodsList.size(); i++) {
                    if (goodsList.get(i).isChecked()) {
                        goodsChecked.add(goodsList.get(i));
                    }
                }
                intent.putExtra("list", (Serializable) goodsChecked);
                startActivity(intent);
                break;
            case R.id.checkbox_all:
                if (checkbox_all.isChecked()) {
                    for (int i = 0; i < goodsList.size(); i++) {
                        goodsList.get(i).setChecked(true);
                    }
                } else {
                    for (int i = 0; i < goodsList.size(); i++) {
                        goodsList.get(i).setChecked(false);
                    }
                }
                pay();
                adapter.notifyDataSetChanged();
                break;
        }
    }
}
