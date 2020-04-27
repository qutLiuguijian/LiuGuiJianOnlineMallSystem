package com.lgj.liuguijianonlinemallapp.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lgj.liuguijianonlinemallapp.R;
import com.lgj.liuguijianonlinemallapp.bean.Goods;

import java.util.List;

public class CarGoodsRecyclerViewAdapter extends RecyclerView.Adapter {
    Context context;
    List<Goods> list;
    Handler handler;
    private OnitemLongClickListener onitemLongClickListener;
    public CarGoodsRecyclerViewAdapter(Context context, Handler handler, List<Goods> goodsList) {
        this.context = context;
        this.list = goodsList;
        this.handler = handler;

    }
    public void setOnitemLongClickListener(OnitemLongClickListener onitemLongClickListener) {
        this.onitemLongClickListener = onitemLongClickListener;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.module_recycle_item_car_goods, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        final Goods goods = list.get(i);
        if (goods.isChecked()) {
            myViewHolder.checkbox_car_goods.setChecked(true);
        } else {
            myViewHolder.checkbox_car_goods.setChecked(false);
        }
        myViewHolder.checkbox_car_goods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myViewHolder.checkbox_car_goods.isChecked()) {
                    myViewHolder.checkbox_car_goods.setChecked(true);
                    goods.setChecked(true);
                } else {
                    myViewHolder.checkbox_car_goods.setChecked(false);
                    goods.setChecked(false);
                }
                handler.sendEmptyMessage(1);
            }
        });
        myViewHolder.iv_car_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = Integer.parseInt(myViewHolder.tv_car_count.getText().toString());
                count++;
                goods.setCount(count);
                myViewHolder.tv_car_count.setText(count + "");
                if (count > 1) {
                    myViewHolder.iv_car_decrease.setClickable(true);
                }
                handler.sendEmptyMessage(1);
            }
        });
        myViewHolder.iv_car_decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = Integer.parseInt(myViewHolder.tv_car_count.getText().toString());
                count--;
                goods.setCount(count);
                myViewHolder.tv_car_count.setText(count + "");
                if (count <= 1) {
                    myViewHolder.iv_car_decrease.setClickable(false);
                } else {
                    myViewHolder.iv_car_decrease.setClickable(true);
                }
                handler.sendEmptyMessage(1);
            }
        });
        Glide.with(context).load(goods.getGimage()).into(myViewHolder.iv_car_goods);
        myViewHolder.tv_car_name.setText(goods.getGname());
        myViewHolder.tv_car_price.setText(goods.getGprice() + "");
        myViewHolder.tv_car_count.setText(goods.getCount() + "");
    }

    @Override
    public int getItemCount() {
        return list == null || list.size() == 0 ? 0 : list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_car_name, tv_car_price, tv_car_count;
        private ImageView iv_car_goods, iv_car_add, iv_car_decrease;
        private CheckBox checkbox_car_goods;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            tv_car_name = itemView.findViewById(R.id.tv_car_name);
            tv_car_price = itemView.findViewById(R.id.tv_car_price);
            iv_car_goods = itemView.findViewById(R.id.iv_car_goods);
            tv_car_count = itemView.findViewById(R.id.tv_car_count);
            iv_car_add = itemView.findViewById(R.id.iv_car_add);
            iv_car_decrease = itemView.findViewById(R.id.iv_car_decrease);
            checkbox_car_goods = itemView.findViewById(R.id.checkbox_car_goods);
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
        }
    }
    public interface OnitemLongClickListener {
        void onItemLongClick(View view, int postion);
    }
}
