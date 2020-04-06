package com.lgj.liuguijianonlinemallapp.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.lgj.liuguijianonlinemallapp.R;
import com.lgj.liuguijianonlinemallapp.activity.fragment.ClassifyFragment;
import com.lgj.liuguijianonlinemallapp.activity.fragment.HomeFragment;
import com.lgj.liuguijianonlinemallapp.activity.fragment.MineFragment;
import com.lgj.liuguijianonlinemallapp.activity.fragment.MyCartFragment;

public class MainActivity extends FragmentActivity {

    private FrameLayout fl_main;
    private FragmentTabHost ftb_daohang;
    private int[] tabTag = {R.string.module_main_home, R.string.module_main_classify, R.string.module_main_mycart, R.string.module_main_mine};
    private int[] tabImage={R.drawable.module_fragment_item_home,R.drawable.module_fragment_item_classify,R.drawable.module_fragment_item_mycart,R.drawable.module_fragment_item_mine};
    private Class[] tabFragment = {HomeFragment.class, ClassifyFragment.class, MyCartFragment.class, MineFragment.class};
    private ImageView iv_image;
    private TextView tv_mark;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_activity_main);

        init();
    }

    void init() {
        ftb_daohang = findViewById(R.id.ftb_daohang);
        ftb_daohang.setup(this, getSupportFragmentManager(), R.id.fl_main);
        ftb_daohang.getTabWidget().setDividerDrawable(null);
        for (int i = 0; i < tabTag.length; i++) {
            ftb_daohang.addTab(ftb_daohang.newTabSpec(String.valueOf(tabTag[i])).setIndicator(getView(i, tabImage[i])), tabFragment[i], null);
        }
        ftb_daohang.setCurrentTab(0);
    }

    public View getView(int tag, int id) {
        view = getLayoutInflater().inflate(R.layout.module_fragmenttabhost_item, null);
        iv_image = view.findViewById(R.id.iv_image);
        tv_mark = view.findViewById(R.id.tv_mark);
        iv_image.setImageResource(id);
        if (tag == 2) {
            tv_mark.setVisibility(View.VISIBLE);
            tv_mark.setText("2");
        }
        return view;
    }
}
