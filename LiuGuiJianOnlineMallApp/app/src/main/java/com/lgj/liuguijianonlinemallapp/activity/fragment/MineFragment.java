package com.lgj.liuguijianonlinemallapp.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lgj.liuguijianonlinemallapp.R;
import com.lgj.liuguijianonlinemallapp.activity.ExitActivity;
import com.lgj.liuguijianonlinemallapp.activity.LoginActivity;
import com.lgj.liuguijianonlinemallapp.activity.MainActivity;
import com.lgj.liuguijianonlinemallapp.activity.OrderActivity;
import com.lgj.liuguijianonlinemallapp.activity.TransitActivity;
import com.lgj.liuguijianonlinemallapp.bean.Goods;
import com.lgj.liuguijianonlinemallapp.bean.ServerResult;
import com.lgj.liuguijianonlinemallapp.utils.PreferencesUtils;
import com.lgj.liuguijianonlinemallapp.utils.RequestParamConfig;
import com.ruiwcc.okhttpPlus.exception.OkHttpException;
import com.ruiwcc.okhttpPlus.request.RequestParams;
import com.ruiwcc.okhttpPlus.response.ResponseCallback;
import com.rwcc.common.utils.SharedHelper;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MineFragment extends Fragment implements View.OnClickListener {
    private ImageView iv_toLogin;
    private CollapsingToolbarLayout ctl_loginName;
    private LinearLayout ll_all, ll_pay, ll_send, ll_takeover, ll_assess;
    private ImageView iv_pay_mark, iv_send_mark, iv_takeover_mark, iv_assess_mark;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.module_fragment_mine, container, false);
        bind(view);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == 100) {
                ctl_loginName.setTitle(data.getStringExtra("uname"));
            }

        }
    }

    private void bind(View view) {
        iv_toLogin = view.findViewById(R.id.iv_toLogin);
        ctl_loginName = view.findViewById(R.id.ctl_loginName);
        ll_all = view.findViewById(R.id.ll_all);
        ll_pay = view.findViewById(R.id.ll_pay);
        ll_assess = view.findViewById(R.id.ll_assess);
        ll_send = view.findViewById(R.id.ll_send);
        ll_takeover = view.findViewById(R.id.ll_takeover);
        ll_all.setOnClickListener(this);
        ll_pay.setOnClickListener(this);
        ll_assess.setOnClickListener(this);
        ll_send.setOnClickListener(this);
        ll_takeover.setOnClickListener(this);
        iv_assess_mark = view.findViewById(R.id.iv_assess_mark);
        iv_pay_mark = view.findViewById(R.id.iv_pay_mark);
        iv_send_mark = view.findViewById(R.id.iv_send_mark);
        iv_takeover_mark = view.findViewById(R.id.iv_takeover_mark);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        PreferencesUtils.putBoolean(getContext(), "isShowPayMark", false);
        PreferencesUtils.putBoolean(getContext(), "isShowSendMark", false);
        PreferencesUtils.putBoolean(getContext(), "isShowTekeMark", false);
        PreferencesUtils.putBoolean(getContext(), "isShowAssessMark", false);
        final String isReLogin = PreferencesUtils.getString(getContext(), "isReLogin");
        if (isReLogin != null && !isReLogin.isEmpty() && isReLogin.equals("no")) {
            String name = PreferencesUtils.getString(getContext(), "username");
            ctl_loginName.setTitle(name);
            loadMark();
        } else {
            ctl_loginName.setTitle("登录/注册");
        }
        iv_toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isReLogin != null && !isReLogin.isEmpty() && isReLogin.equals("no")) {
                    Intent intent = new Intent(getActivity(), ExitActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent, 100);
                }
            }
        });
    }

    private void loadMark() {
        Map<String, String> map = new HashMap<>();
        map.put("uname", PreferencesUtils.getString(getContext(), "username"));
        RequestParams params = new RequestParams(map);
        RequestParamConfig.getAllOrder(params, new ResponseCallback() {
            @Override
            public void onSuccess(Object responseObj) {
                Gson gson = new Gson();
                Type type = new TypeToken<ServerResult<List<Goods>>>() {
                }.getType();
                ServerResult<List<Goods>> result = gson.fromJson(responseObj.toString(), type);
                if (result.getRetCode() == 0) {
                    for (int i = 0; i < result.getData().size(); i++) {
                        if (result.getData().get(i).getState() == 0) {
                            PreferencesUtils.putBoolean(getContext(), "isShowPayMark", true);
                        }
                        if (result.getData().get(i).getState() == 1) {
                            PreferencesUtils.putBoolean(getContext(), "isShowSendMark", true);
                        }
                        if (result.getData().get(i).getState() == 2) {
                            PreferencesUtils.putBoolean(getContext(), "isShowTekeMark", true);
                        }
                        if (result.getData().get(i).getState() == 3) {
                            PreferencesUtils.putBoolean(getContext(), "isShowAssessMark", true);
                        }
                    }
                    if (PreferencesUtils.getBoolean(getContext(), "isShowPayMark", false)) {
                        iv_pay_mark.setVisibility(View.VISIBLE);
                    } else {
                        iv_pay_mark.setVisibility(View.GONE);
                    }
                    if (PreferencesUtils.getBoolean(getContext(), "isShowSendMark", false)) {
                        iv_send_mark.setVisibility(View.VISIBLE);
                    } else {
                        iv_send_mark.setVisibility(View.GONE);
                    }
                    if (PreferencesUtils.getBoolean(getContext(), "isShowTekeMark", false)) {
                        iv_takeover_mark.setVisibility(View.VISIBLE);
                    } else {
                        iv_takeover_mark.setVisibility(View.GONE);
                    }
                    if (PreferencesUtils.getBoolean(getContext(), "isShowAssessMark", false)) {
                        iv_assess_mark.setVisibility(View.VISIBLE);
                    } else {
                        iv_assess_mark.setVisibility(View.GONE);
                    }
                }

            }

            @Override
            public void onFailure(OkHttpException failuer) {
                Log.i("hhhhhhhhhhh",failuer.getMsg());
                Toast.makeText(getContext(), failuer.getMsg(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_all:
                Intent intent = new Intent(getActivity(), OrderActivity.class);
                intent.putExtra("tag", 0);
                startActivity(intent);
                break;
            case R.id.ll_pay:
                Intent intent1 = new Intent(getActivity(), OrderActivity.class);
                intent1.putExtra("tag", 1);
                startActivity(intent1);
                break;
            case R.id.ll_send:
                Intent intent2 = new Intent(getActivity(), OrderActivity.class);
                intent2.putExtra("tag", 2);
                startActivity(intent2);
                break;
            case R.id.ll_assess:
                Intent intent3 = new Intent(getActivity(), OrderActivity.class);
                intent3.putExtra("tag", 4);
                startActivity(intent3);
                break;
            case R.id.ll_takeover:
                Intent intent4 = new Intent(getActivity(), OrderActivity.class);
                intent4.putExtra("tag", 3);
                startActivity(intent4);
                break;
        }
    }
}
