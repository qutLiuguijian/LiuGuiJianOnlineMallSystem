package com.lgj.liuguijianonlinemallapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lgj.liuguijianonlinemallapp.R;
import com.lgj.liuguijianonlinemallapp.adapter.AssessRecyclerViewAdapter;
import com.lgj.liuguijianonlinemallapp.bean.Goods;
import com.lgj.liuguijianonlinemallapp.bean.ServerResult;
import com.lgj.liuguijianonlinemallapp.bean.User;
import com.lgj.liuguijianonlinemallapp.utils.GlideImageLoader;
import com.lgj.liuguijianonlinemallapp.utils.PreferencesUtils;
import com.lgj.liuguijianonlinemallapp.utils.RequestParamConfig;
import com.ruiwcc.okhttpPlus.exception.OkHttpException;
import com.ruiwcc.okhttpPlus.request.RequestParams;
import com.ruiwcc.okhttpPlus.response.ResponseCallback;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoodsDetailActivity extends Activity implements View.OnClickListener {

    private Banner banner_img;
    private TextView tv_detail_price, tv_detail_dec;
    private Button btn_buy, btn_car;
    private RecyclerView rv_assess;
    private PopupWindow popupWindow;
    private int count = 1;
    private Goods goods;
    private AssessRecyclerViewAdapter adapter;
    private List<String> list = new ArrayList<>();
    private int flag = 0;//0 默认 1 购买 2 加入购物车

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_activity_goods_detail);
        init();
        loadData();
    }

    private void loadData() {
        Map<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(getIntent().getIntExtra("id", -1)));
        RequestParams params = new RequestParams(map);
        RequestParamConfig.getGoodsDetail(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                handler.obtainMessage(0, responseObj).sendToTarget();
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                Toast.makeText(GoodsDetailActivity.this, failuer.getMsg(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init() {
        banner_img = findViewById(R.id.banner_img);
        tv_detail_price = findViewById(R.id.tv_detail_price);
        tv_detail_dec = findViewById(R.id.tv_detail_dec);
        btn_buy = findViewById(R.id.btn_buy);
        btn_car = findViewById(R.id.btn_car);
        btn_buy.setOnClickListener(this);
        btn_car.setOnClickListener(this);
        for (int i = 0; i < 100; i++) {
            list.add("hhh" + i);
        }
        rv_assess = findViewById(R.id.rv_assess);
        adapter = new AssessRecyclerViewAdapter(GoodsDetailActivity.this, list);
        rv_assess.setLayoutManager(new LinearLayoutManager(GoodsDetailActivity.this));
        rv_assess.setAdapter(adapter);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    Gson gson = new Gson();
                    Type type = new TypeToken<ServerResult<Goods>>() {
                    }.getType();
                    ServerResult<Goods> result = gson.fromJson(msg.obj.toString(), type);
                    if (result.getRetCode() == 0) {
                        goods = result.getData();
                        banner_img.setImageLoader(new GlideImageLoader());
                        banner_img.setImages(goods.getImgurl());
                        banner_img.setBannerStyle(BannerConfig.NUM_INDICATOR);
                        banner_img.setIndicatorGravity(BannerConfig.RIGHT);
                        banner_img.start();
                        tv_detail_dec.setText(goods.getGdesc());
                        tv_detail_price.setText("¥" + goods.getGprice() + "");
                    }
                    break;
                case 1:
                    Gson gson1 = new Gson();
                    Type type1 = new TypeToken<ServerResult>() {
                    }.getType();
                    ServerResult result1 = gson1.fromJson(msg.obj.toString(), type1);
                    Toast.makeText(GoodsDetailActivity.this, result1.getMessage(), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==200){
            if (flag == 1) {
                Intent intent = new Intent();
                startActivity(intent);
            } else if (flag == 2) {
                Map<String, String> map = new HashMap<>();
                map.put("username", PreferencesUtils.getString(GoodsDetailActivity.this, "username"));
                map.put("g_id", String.valueOf(goods.getId()));
                map.put("count", String.valueOf(count));
                RequestParams params = new RequestParams(map);
                RequestParamConfig.addCar(params, new ResponseCallback() {
                    @Override
                    public void onSuccess(Object responseObj) {
                        handler.obtainMessage(1, responseObj).sendToTarget();
                    }

                    @Override
                    public void onFailure(OkHttpException failuer) {
                        Toast.makeText(GoodsDetailActivity.this, failuer.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_buy:
                flag = 1;
                showPopView();
                break;
            case R.id.btn_car:
                flag = 2;
                showPopView();
                break;
        }
    }

    private void showPopView() {
        View view = LayoutInflater.from(GoodsDetailActivity.this).inflate(R.layout.module_popupwindow_goods, null);
        popupWindow = new PopupWindow(this);
        popupWindow.setContentView(view);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.update();
        TextView tv_pop_desc = view.findViewById(R.id.tv_pop_desc);
        TextView tv_pop_price = view.findViewById(R.id.tv_pop_price);
        tv_pop_desc.setText(goods.getGdesc());
        tv_pop_price.setText("¥" + goods.getGprice() + "");
        final TextView tv_pop_count = view.findViewById(R.id.tv_pop_count);
        ImageView iv_pop_img = view.findViewById(R.id.iv_pop_img);
        Glide.with(this).load(goods.getGimage()).into(iv_pop_img);
        ImageView iv_pop_add = view.findViewById(R.id.iv_pop_add);
        final ImageView iv_pop_decrease = view.findViewById(R.id.iv_pop_decrease);
        iv_pop_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                tv_pop_count.setText(count + "");
                if (count > 1) {
                    iv_pop_decrease.setClickable(true);
                }
            }
        });
        iv_pop_decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count--;
                tv_pop_count.setText(count + "");
                if (count == 1) {
                    iv_pop_decrease.setClickable(false);
                }
            }
        });
        Button btn_define = view.findViewById(R.id.btn_define);
        btn_define.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (popupWindow!=null){
                    popupWindow.dismiss();
                }
                String isReLogin = PreferencesUtils.getString(GoodsDetailActivity.this, "isReLogin");
                if (isReLogin == null || isReLogin.isEmpty() || isReLogin.equals("yes")) {
                    Intent intent = new Intent(GoodsDetailActivity.this, LoginActivity.class);
                    startActivityForResult(intent,200);
                } else {
                    if (flag == 1) {
                        Intent intent = new Intent();
                        startActivity(intent);
                    } else if (flag == 2) {
                        Map<String, String> map = new HashMap<>();
                        map.put("username", PreferencesUtils.getString(GoodsDetailActivity.this, "username"));
                        map.put("g_id", String.valueOf(goods.getId()));
                        map.put("count", tv_pop_count.getText().toString());
                        RequestParams params = new RequestParams(map);
                        RequestParamConfig.addCar(params, new ResponseCallback() {
                            @Override
                            public void onSuccess(Object responseObj) {
                                handler.obtainMessage(1, responseObj).sendToTarget();
                            }

                            @Override
                            public void onFailure(OkHttpException failuer) {
                                Toast.makeText(GoodsDetailActivity.this, failuer.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                }
            }
        });
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }
}
