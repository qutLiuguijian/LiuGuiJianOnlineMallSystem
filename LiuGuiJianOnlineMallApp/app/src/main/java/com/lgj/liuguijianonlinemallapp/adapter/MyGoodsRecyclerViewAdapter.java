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

public class MyGoodsRecyclerViewAdapter extends RecyclerView.Adapter {
    Context context;
    List<Goods> list;
    private OnitemClickListener onitemClickListener;
    private OnitemLongClickListener onitemLongClickListener;
    private OnitemFocusChangeClickListener onitemFocusChangeClickListener;


    public void setOnitemClickListener(OnitemClickListener onitemClickListener) {
        this.onitemClickListener = onitemClickListener;
    }

    public void setOnitemLongClickListener(OnitemLongClickListener onitemLongClickListener) {
        this.onitemLongClickListener = onitemLongClickListener;
    }

    public void setOnitemFocusChangeClickListener(OnitemFocusChangeClickListener onitemFocusChangeClickListener) {
        this.onitemFocusChangeClickListener = onitemFocusChangeClickListener;
    }

    public MyGoodsRecyclerViewAdapter(Context context, List<Goods> goodsList) {
        this.context = context;
        this.list = goodsList;

    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.module_recycle_item_goods, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        Goods goods = list.get(i);
        Glide.with(context).load(goods.getGimage().replace("localhost","10.0.2.2")).into(myViewHolder.iv_img);
        myViewHolder.tv_desc.setText(goods.getGdesc());
        myViewHolder.tv_price.setText(goods.getGprice()+"");
    }

    @Override
    public int getItemCount() {
        return list==null||list.size()==0?0:list.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_desc,tv_price;
        private ImageView iv_img;
        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            tv_desc=itemView.findViewById(R.id.tv_desc);
            tv_price=itemView.findViewById(R.id.tv_price);
            iv_img=itemView.findViewById(R.id.iv_img);
            if (onitemClickListener != null) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int pos = getAdapterPosition();
                        onitemClickListener.onItemClick(itemView, pos);
                    }
                });
            }
            if (onitemLongClickListener != null) {
                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        int pos = getAdapterPosition();
                        onitemLongClickListener.onItemLongClick(itemView, pos);
                        return true;
                    }
                });
            }
            if (onitemFocusChangeClickListener != null) {
                itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean b) {
                        int pos = getAdapterPosition();
                        onitemFocusChangeClickListener.onItemFocusChangeListener(itemView, b, pos);
                    }
                });
            }
        }
    }
    public interface OnitemClickListener {
        void onItemClick(View view, int postion);
    }

    public interface OnitemLongClickListener {
        void onItemLongClick(View view, int postion);
    }

    public interface OnitemFocusChangeClickListener {
        void onItemFocusChangeListener(View view, boolean hasFocus, int postion);
    }
}
