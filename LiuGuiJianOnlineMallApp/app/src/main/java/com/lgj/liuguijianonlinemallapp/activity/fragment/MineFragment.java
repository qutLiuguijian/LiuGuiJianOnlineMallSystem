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

import com.lgj.liuguijianonlinemallapp.R;
import com.lgj.liuguijianonlinemallapp.activity.ExitActivity;
import com.lgj.liuguijianonlinemallapp.activity.LoginActivity;
import com.lgj.liuguijianonlinemallapp.activity.OrderActivity;
import com.lgj.liuguijianonlinemallapp.utils.PreferencesUtils;
import com.rwcc.common.utils.SharedHelper;

public class MineFragment extends Fragment implements View.OnClickListener{
    private ImageView iv_toLogin;
    private CollapsingToolbarLayout ctl_loginName;
    private LinearLayout ll_all,ll_pay,ll_send,ll_takeover,ll_assess;

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
        if (requestCode==100){
            if (resultCode==100){
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

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        final String isReLogin = PreferencesUtils.getString(getContext(), "isReLogin");
        if (isReLogin != null && !isReLogin.isEmpty() && isReLogin.equals("no")) {
            String name = PreferencesUtils.getString(getContext(), "username");
            ctl_loginName.setTitle(name);
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
                    startActivityForResult(intent,100);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_all:
                Intent intent=new Intent(getActivity(),OrderActivity.class);
                intent.putExtra("tag",0);
                startActivity(intent);
                break;
            case R.id.ll_pay:
                Intent intent1=new Intent(getActivity(),OrderActivity.class);
                intent1.putExtra("tag",1);
                startActivity(intent1);
                break;
            case R.id.ll_send:
                Intent intent2=new Intent(getActivity(),OrderActivity.class);
                intent2.putExtra("tag",2);
                startActivity(intent2);
                break;
            case R.id.ll_assess:
                Intent intent3=new Intent(getActivity(),OrderActivity.class);
                intent3.putExtra("tag",4);
                startActivity(intent3);
                break;
            case R.id.ll_takeover:
                Intent intent4=new Intent(getActivity(),OrderActivity.class);
                intent4.putExtra("tag",3);
                startActivity(intent4);
                break;
        }
    }
}
