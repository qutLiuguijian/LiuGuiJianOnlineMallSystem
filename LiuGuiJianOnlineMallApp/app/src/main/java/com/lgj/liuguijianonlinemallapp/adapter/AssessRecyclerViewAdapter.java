package com.lgj.liuguijianonlinemallapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lgj.liuguijianonlinemallapp.R;
import com.lgj.liuguijianonlinemallapp.bean.Gassess;
import com.lgj.liuguijianonlinemallapp.bean.Goods;

import java.util.List;

public class AssessRecyclerViewAdapter extends RecyclerView.Adapter {
    Context context;
    List<Gassess> list;
    public AssessRecyclerViewAdapter(Context context, List<Gassess> goodsList) {
        this.context = context;
        this.list = goodsList;

    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.module_recycle_item_assess, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        Gassess gassess=list.get(i);
        myViewHolder.tv_uname.setText(gassess.getUname());
        myViewHolder.tv_assess.setText(gassess.getAssess());
    }

    @Override
    public int getItemCount() {
        return list==null||list.size()==0?0:list.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_assess,tv_uname;
        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            tv_assess=itemView.findViewById(R.id.tv_assess);
            tv_uname=itemView.findViewById(R.id.tv_uname);
        }
    }
}
