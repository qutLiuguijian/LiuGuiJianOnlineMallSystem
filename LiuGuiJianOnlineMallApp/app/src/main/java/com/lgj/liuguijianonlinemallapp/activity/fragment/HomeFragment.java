package com.lgj.liuguijianonlinemallapp.activity.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lgj.liuguijianonlinemallapp.R;
import com.lgj.liuguijianonlinemallapp.activity.fragment.home.DailyFragment;
import com.lgj.liuguijianonlinemallapp.activity.fragment.home.ManFragment;
import com.lgj.liuguijianonlinemallapp.activity.fragment.home.MobileFragment;
import com.lgj.liuguijianonlinemallapp.activity.fragment.home.SnackFragment;
import com.lgj.liuguijianonlinemallapp.activity.fragment.home.WomanFragment;
import com.lgj.liuguijianonlinemallapp.adapter.MyFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private ViewPager vp_content;
    private TabLayout tl_daohang;
    private int[] tabTitle = {R.string.module_main_home_mobile, R.string.module_main_home_man,
            R.string.module_main_home_woman, R.string.module_main_home_daily, R.string.module_main_home_snack};
    private FragmentManager fragmentManager;
    private MyFragmentAdapter myFragmentAdapter;
    private List<Fragment> fragments = new ArrayList<>();
    private MobileFragment mobileFragment;
    private ManFragment manFragment;
    private WomanFragment womanFragment;
    private DailyFragment dailyFragment;
    private SnackFragment snackFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.module_fragment_home, container, false);
        tl_daohang = view.findViewById(R.id.tl_daohang);
        vp_content = view.findViewById(R.id.vp_content);
        if (mobileFragment == null) {
            mobileFragment = new MobileFragment();
            fragments.add(mobileFragment);
        }
        if (manFragment == null) {
            manFragment = new ManFragment();
            fragments.add(manFragment);
        }
        if (womanFragment == null) {
            womanFragment = new WomanFragment();
            fragments.add(womanFragment);
        }
        if (dailyFragment == null) {
            dailyFragment = new DailyFragment();
            fragments.add(dailyFragment);
        }
        if (snackFragment == null) {
            snackFragment = new SnackFragment();
            fragments.add(snackFragment);
        }
        for (int i = 0; i < tabTitle.length; i++) {
            TabLayout.Tab tab = tl_daohang.newTab();
            tab.setText(tabTitle[i]).setTag(i);
        }
        fragmentManager = getChildFragmentManager();
        myFragmentAdapter = new MyFragmentAdapter(fragmentManager, fragments);
        vp_content.setAdapter(myFragmentAdapter);
        tl_daohang.setupWithViewPager(vp_content);

        for (int i = 0; i < tabTitle.length; i++) {
            tl_daohang.getTabAt(i).setText(tabTitle[i]);
        }
        return view;
    }

}
