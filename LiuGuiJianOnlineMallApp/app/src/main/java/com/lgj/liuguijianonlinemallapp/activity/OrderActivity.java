package com.lgj.liuguijianonlinemallapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lgj.liuguijianonlinemallapp.R;
import com.lgj.liuguijianonlinemallapp.activity.fragment.home.DailyFragment;
import com.lgj.liuguijianonlinemallapp.activity.fragment.home.ManFragment;
import com.lgj.liuguijianonlinemallapp.activity.fragment.home.MobileFragment;
import com.lgj.liuguijianonlinemallapp.activity.fragment.home.SnackFragment;
import com.lgj.liuguijianonlinemallapp.activity.fragment.home.WomanFragment;
import com.lgj.liuguijianonlinemallapp.activity.fragment.order.AllFragment;
import com.lgj.liuguijianonlinemallapp.activity.fragment.order.AssessFragment;
import com.lgj.liuguijianonlinemallapp.activity.fragment.order.DrawbackFragment;
import com.lgj.liuguijianonlinemallapp.activity.fragment.order.PayFragment;
import com.lgj.liuguijianonlinemallapp.activity.fragment.order.SendFragment;
import com.lgj.liuguijianonlinemallapp.activity.fragment.order.TakeOverFragment;
import com.lgj.liuguijianonlinemallapp.adapter.MyFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends FragmentActivity {
    private ViewPager vp_content;
    private TabLayout tl_daohang;
    private TextView tv_tltle;
    private ImageView iv_order_back;
    private int[] tabTitle = {R.string.module_order_all, R.string.module_order_pay,
            R.string.module_order_send, R.string.module_order_takeover,
            R.string.module_order_assess, R.string.module_order_drawback};
    private FragmentManager fragmentManager;
    private MyFragmentAdapter myFragmentAdapter;
    private List<Fragment> fragments = new ArrayList<>();
    private AllFragment allFragment;
    private AssessFragment assessFragment;
    private DrawbackFragment drawbackFragment;
    private PayFragment payFragment;
    private SendFragment sendFragment;
    private TakeOverFragment takeOverFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_activity_order);
        init();
        int tag = getIntent().getIntExtra("tag", -1);
        tl_daohang.getTabAt(tag).select();
        tv_tltle.setText(tabTitle[tag]);
        tl_daohang.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tv_tltle.setText(tab.getText());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void init() {
        tl_daohang = findViewById(R.id.tl_order_daohang);
        vp_content = findViewById(R.id.vp_order_content);
        tv_tltle = findViewById(R.id.tv_tltle);
        iv_order_back=findViewById(R.id.iv_order_back);
        iv_order_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if (allFragment == null) {
            allFragment = new AllFragment();
            fragments.add(allFragment);
        }
        if (payFragment == null) {
            payFragment = new PayFragment();
            fragments.add(payFragment);
        }
        if (sendFragment == null) {
            sendFragment = new SendFragment();
            fragments.add(sendFragment);
        }
        if (takeOverFragment == null) {
            takeOverFragment = new TakeOverFragment();
            fragments.add(takeOverFragment);
        }
        if (assessFragment == null) {
            assessFragment = new AssessFragment();
            fragments.add(assessFragment);
        }
        if (drawbackFragment == null) {
            drawbackFragment = new DrawbackFragment();
            fragments.add(drawbackFragment);
        }
        for (int i = 0; i < tabTitle.length; i++) {
            TabLayout.Tab tab = tl_daohang.newTab();
            tab.setText(tabTitle[i]).setTag(i);
        }
        fragmentManager = getSupportFragmentManager();
        myFragmentAdapter = new MyFragmentAdapter(fragmentManager, fragments);
        vp_content.setAdapter(myFragmentAdapter);
        tl_daohang.setupWithViewPager(vp_content);

        for (int i = 0; i < tabTitle.length; i++) {
            tl_daohang.getTabAt(i).setText(tabTitle[i]);
        }
    }

}
