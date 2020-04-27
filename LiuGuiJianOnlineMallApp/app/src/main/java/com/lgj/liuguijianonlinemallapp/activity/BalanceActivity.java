package com.lgj.liuguijianonlinemallapp.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
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
import com.lgj.liuguijianonlinemallapp.adapter.OrderGoodsRecyclerViewAdapter;
import com.lgj.liuguijianonlinemallapp.bean.Gaddress;
import com.lgj.liuguijianonlinemallapp.bean.Goods;
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

public class BalanceActivity extends Activity implements View.OnClickListener {
    private TextView tv_receiver, tv_receiver_phone,
            tv_receiver_address, tv_receiver_tip, tv_maoney, tv_title;
    private RecyclerView rv_balance;
    private ImageView iv_order_back;
    private CardView cv_takeoverpeerson;
    private List<Goods> goods = new ArrayList();
    private Button btn_submit;
    private OrderGoodsRecyclerViewAdapter adapter;
    private int count = 0;
    private Double money = 0d;
    private Gaddress gaddress;
    private int submitCount = 0;
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_activity_balance);
        loadData();
        init();
    }

    private void loadData() {
        Map<String, String> map = new HashMap<>();
        map.put("uname", PreferencesUtils.getString(BalanceActivity.this, "username"));
        RequestParams params = new RequestParams(map);
        RequestParamConfig.getReceiver(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                handler.obtainMessage(0, responseObj).sendToTarget();
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                Toast.makeText(BalanceActivity.this, failuer.getMsg(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init() {
        tv_receiver = findViewById(R.id.tv_receiver);
        tv_receiver_phone = findViewById(R.id.tv_receiver_phone);
        tv_receiver_address = findViewById(R.id.tv_receiver_address);
        tv_receiver_tip = findViewById(R.id.tv_receiver_tip);
        rv_balance = findViewById(R.id.rv_balance);
        btn_submit = findViewById(R.id.btn_submit);
        tv_maoney = findViewById(R.id.tv_money);
        goods = (List<Goods>) getIntent().getSerializableExtra("list");
        adapter = new OrderGoodsRecyclerViewAdapter(BalanceActivity.this, goods, handler);
        rv_balance.setLayoutManager(new LinearLayoutManager(BalanceActivity.this));
        rv_balance.setAdapter(adapter);
        tv_title = findViewById(R.id.tv_title);
        tv_title.setText("确认订单");
        iv_order_back = findViewById(R.id.iv_order_back);
        updateFooter();
        btn_submit.setOnClickListener(this);
        iv_order_back.setOnClickListener(this);
        cv_takeoverpeerson = findViewById(R.id.cv_takeoverpeerson);
        cv_takeoverpeerson.setOnClickListener(this);
    }

    private void updateFooter() {
        for (int i = 0; i < goods.size(); i++) {
            count = count + goods.get(i).getCount();
            money = money + goods.get(i).getCount() * goods.get(i).getGprice();
        }
        tv_maoney.setText("共" + count + "件,总计:¥ " + money);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    Gson gson = new Gson();
                    Type type = new TypeToken<ServerResult<List<Gaddress>>>() {
                    }.getType();
                    ServerResult<List<Gaddress>> result = gson.fromJson(msg.obj.toString(), type);
                    if (result.getRetCode() == 0) {
                        if (result.getData() == null || result.getData().size() == 0) {
                            tv_receiver_tip.setVisibility(View.VISIBLE);
                        } else {
                            tv_receiver_tip.setVisibility(View.GONE);
                            gaddress = result.getData().get(0);
                            tv_receiver.setText(gaddress.getReceiver());
                            tv_receiver_address.setText(gaddress.getAddress());
                            tv_receiver_phone.setText(gaddress.getPhone());
                        }
                    }
                    break;
                case 1:
                    Gson gson2 = new Gson();
                    Type type2 = new TypeToken<ServerResult>() {
                    }.getType();
                    ServerResult result2 = gson2.fromJson(msg.obj.toString(), type2);
                    if (result2.getRetCode() == 0) {
                        submitCount++;
                        if (submitCount != goods.size()) {
                            submitFromCar(0);
                        }else {
                            finish();
                        }
                    }
                    break;
                case 2:
                    Gson gson3 = new Gson();
                    Type type3 = new TypeToken<ServerResult>() {
                    }.getType();
                    ServerResult result3 = gson3.fromJson(msg.obj.toString(), type3);
                    if (result3.getRetCode() == 0) {
                        finish();
                    }
                    break;
            }

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == 200) {
                gaddress = (Gaddress) data.getSerializableExtra("address");
                tv_receiver.setText(gaddress.getReceiver());
                tv_receiver_address.setText(gaddress.getAddress());
                tv_receiver_phone.setText(gaddress.getPhone());
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_order_back:
                finish();
                break;
            case R.id.cv_takeoverpeerson:
                Intent intent = new Intent(BalanceActivity.this, ReceiverActivity.class);
                intent.putExtra("from", "balance");
                startActivityForResult(intent, 100);
                break;
            case R.id.btn_submit:
                showPopView();
                break;
        }
    }

    private void showPopView() {
        View view = LayoutInflater.from(BalanceActivity.this).inflate(R.layout.module_popupwindow_balance, null);
        popupWindow = new PopupWindow(this);
        popupWindow.setContentView(view);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.update();
        TextView tv_pop_price = view.findViewById(R.id.tv_pop_price);

        tv_pop_price.setText("¥" + money);
        ImageView iv_pop_img = view.findViewById(R.id.iv_pop_img);
        iv_pop_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(BalanceActivity.this);
                dialog.setTitle("是否离开?");
                dialog.setMessage("");
                dialog.setCancelable(false);    //设置是否可以通过点击对话框外区域或者返回按键关闭对话框
                dialog.setPositiveButton("继续付款", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });
                dialog.setNegativeButton("狠心离开", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        popupWindow.dismiss();
                        if (getIntent().getStringExtra("from").equals("detail")) {
                            submitFromDetail(0);
                        } else {
                            submitFromCar(0);
                        }
                    }
                });
                dialog.show();
            }
        });
        Button btn_pay = view.findViewById(R.id.btn_pay);
        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(BalanceActivity.this);
                dialog.setTitle("是否付款?");
                dialog.setMessage("");
                dialog.setCancelable(false);    //设置是否可以通过点击对话框外区域或者返回按键关闭对话框
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        popupWindow.dismiss();
                        if (getIntent().getStringExtra("from").equals("detail")) {
                            submitFromDetail(1);
                        } else {
                            submitFromCar(1);
                        }

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
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_BACK){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(BalanceActivity.this);
                    dialog.setTitle("是否离开?");
                    dialog.setMessage("");
                    dialog.setCancelable(false);    //设置是否可以通过点击对话框外区域或者返回按键关闭对话框
                    dialog.setPositiveButton("继续付款", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    });
                    dialog.setNegativeButton("狠心离开", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            popupWindow.dismiss();
                            if (getIntent().getStringExtra("from").equals("detail")) {
                                submitFromDetail(0);
                            } else {
                                submitFromCar(0);
                            }
                        }
                    });
                    dialog.show();
                }
                return false;
            }
        });
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    private void submitFromCar(int state) {
        Map<String, String> map = new HashMap<>();
        map.put("username", PreferencesUtils.getString(BalanceActivity.this, "username"));
        map.put("id", String.valueOf(goods.get(submitCount).getId()));
        map.put("g_id", String.valueOf(goods.get(submitCount).getGid()));
        map.put("edTime", "3");
        map.put("userAddress", gaddress.getAddress());
        map.put("count", String.valueOf(goods.get(submitCount).getCount()));
        map.put("state", String.valueOf(state));
        map.put("rid", String.valueOf(gaddress.getId()));
        RequestParams params = new RequestParams(map);
        RequestParamConfig.buyFromCar(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                handler.obtainMessage(1, responseObj).sendToTarget();
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                Toast.makeText(BalanceActivity.this, failuer.getMsg(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void submitFromDetail(int state) {
        Map<String, String> map = new HashMap<>();
        map.put("username", PreferencesUtils.getString(BalanceActivity.this, "username"));
        map.put("g_id", String.valueOf(goods.get(0).getId()));
        map.put("edTime", "3");
        map.put("userAddress", gaddress.getAddress());
        map.put("count", String.valueOf(goods.get(0).getCount()));
        map.put("state", String.valueOf(state));
        map.put("rid", String.valueOf(gaddress.getId()));
        RequestParams params = new RequestParams(map);
        RequestParamConfig.buyFromDetail(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                handler.obtainMessage(2, responseObj).sendToTarget();
            }

            @Override
            public void onFailure(OkHttpException failuer) {
                Toast.makeText(BalanceActivity.this, failuer.getMsg(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
