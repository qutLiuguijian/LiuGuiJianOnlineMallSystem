package com.lgj.liuguijianonlinemallapp.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.lgj.liuguijianonlinemallapp.R;
import com.lgj.liuguijianonlinemallapp.bean.Classify;

import java.util.List;

public class ClassifyListViewAdapter extends BaseExpandableListAdapter {

    private List<Classify>list;
    private Context context;
    public ClassifyListViewAdapter(Context context, List<Classify>list) {
        this.context=context;
        this.list=list;
    }

    @Override
    public int getGroupCount() {
        return list==null||list.size()==0?0:list.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return list.get(i).getChildName()==null||list.get(i).getChildName().size()==0?0:list.get(i).getChildName().size();
    }

    @Override
    public Object getGroup(int i) {
        return list.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return list.get(i).getChildName().get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        view= LayoutInflater.from(context).inflate(R.layout.module_listview_item_classify,viewGroup,false);
        TextView tv_classify=view.findViewById(R.id.tv_classify);
        tv_classify.setText(list.get(i).getName());
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        view= LayoutInflater.from(context).inflate(R.layout.module_listview_item_classify,viewGroup,false);
        TextView tv_classify=view.findViewById(R.id.tv_classify);
        tv_classify.setText(list.get(i).getChildName().get(i1).getName());
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
