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

import com.lgj.liuguijianonlinemallapp.R;
import com.lgj.liuguijianonlinemallapp.activity.LoginActivity;
import com.lgj.liuguijianonlinemallapp.utils.PreferencesUtils;
import com.rwcc.common.utils.SharedHelper;

public class MineFragment extends Fragment {
    private ImageView iv_toLogin;
    private CollapsingToolbarLayout ctl_loginName;

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
            ctl_loginName.setTitle(data.getStringExtra("uname"));
        }
    }

    private void bind(View view) {
        iv_toLogin = view.findViewById(R.id.iv_toLogin);
        ctl_loginName = view.findViewById(R.id.ctl_loginName);
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

                } else {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent,100);
                }
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
