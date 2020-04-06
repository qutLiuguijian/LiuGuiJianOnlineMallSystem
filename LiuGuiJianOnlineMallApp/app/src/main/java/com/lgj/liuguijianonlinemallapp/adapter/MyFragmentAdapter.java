package com.lgj.liuguijianonlinemallapp.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.List;

public  class MyFragmentAdapter extends FragmentStatePagerAdapter {
    List<Fragment> fragmentss;
    public MyFragmentAdapter(FragmentManager fm, List<Fragment> fragmentss) {
        super(fm);
        this.fragmentss=fragmentss;
    }

    @Override
    public Fragment getItem(int i) {
        return fragmentss.get(i);
    }

    @Override
    public void finishUpdate(@NonNull ViewGroup container) {
        super.finishUpdate(container);
    }

    @Override
    public int getCount() {
        return fragmentss.size();
    }


}
