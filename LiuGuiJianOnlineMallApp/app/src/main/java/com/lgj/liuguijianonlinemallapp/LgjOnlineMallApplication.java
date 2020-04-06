package com.lgj.liuguijianonlinemallapp;

import android.app.Application;

import com.ruiwcc.okhttpPlus.OkHttpPlus;

public class LgjOnlineMallApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        OkHttpPlus.init(this);
    }
}
