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
import com.lgj.liuguijianonlinemallapp.bean.Goods;

import java.util.List;

public class AssessRecyclerViewAdapter extends RecyclerView.Adapter {
    Context context;
    List<String> list;
    public AssessRecyclerViewAdapter(Context context, List<String> goodsList) {
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
        //Goods goods = list.get(i);
        myViewHolder.tv_uname.setText(list.get(i));
//        myViewHolder.tv_desc.setText(goods.getGdesc());
//        myViewHolder.tv_goodsName.setText(goods.getGprice()+"");
    }

    @Override
    public int getItemCount() {
        return list==null||list.size()==0?0:list.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_desc,tv_uname,tv_goodsName;
        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            tv_desc=itemView.findViewById(R.id.tv_desc);
            tv_uname=itemView.findViewById(R.id.tv_uname);
            tv_goodsName=itemView.findViewById(R.id.tv_goodsName);
        }
    }
}
