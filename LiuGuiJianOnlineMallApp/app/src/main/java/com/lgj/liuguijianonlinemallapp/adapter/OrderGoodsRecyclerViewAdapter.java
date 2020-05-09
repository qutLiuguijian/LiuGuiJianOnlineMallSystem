package com.lgj.liuguijianonlinemallapp.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lgj.liuguijianonlinemallapp.R;
import com.lgj.liuguijianonlinemallapp.bean.Goods;

import java.util.List;


public class OrderGoodsRecyclerViewAdapter extends RecyclerView.Adapter {
    Context context;
    List<Goods> list;
    Handler handler;
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

    public OrderGoodsRecyclerViewAdapter(Context context, List<Goods> goodsList, Handler handler) {
        this.context = context;
        this.list = goodsList;
        this.handler = handler;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.module_recycle_item_order_goods, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        final Goods goods = list.get(i);
        Glide.with(context).load(goods.getGimage().replace("localhost","10.0.2.2")).into(myViewHolder.iv_order_goods);
        myViewHolder.tv_order_name.setText(goods.getGname());
        myViewHolder.tv_order_price.setText(goods.getGprice() + "");
        myViewHolder.tv_order_count.setText("x " + goods.getCount() + "");
        myViewHolder.tv_order_allpay.setText("总计:¥" + goods.getGprice() * goods.getCount() + "");

        if (goods.getState() == null) {
            myViewHolder.btn_delete.setVisibility(View.GONE);
            myViewHolder.btn_update.setVisibility(View.GONE);
        } else if (goods.getState() == 0) {
            myViewHolder.btn_delete.setVisibility(View.VISIBLE);
            myViewHolder.btn_update.setText("付款");
        } else if (goods.getState() == 1) {
            myViewHolder.btn_delete.setVisibility(View.GONE);
            myViewHolder.btn_update.setVisibility(View.GONE);
        } else if (goods.getState() == 2) {
            myViewHolder.btn_delete.setVisibility(View.GONE);
            myViewHolder.btn_update.setText("确认收货");
        } else if (goods.getState() == 3) {
            myViewHolder.btn_delete.setVisibility(View.GONE);
            myViewHolder.btn_update.setText("立即评价");
        } else if (goods.getState() == 4) {
            myViewHolder.btn_delete.setVisibility(View.VISIBLE);
            myViewHolder.btn_update.setVisibility(View.GONE);
        }
        myViewHolder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog("确认删除订单?", "", 1, i);
            }
        });
        myViewHolder.btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (goods.getState() == 3) {
                    handler.obtainMessage(4, i).sendToTarget();
                } else {
                    String title = "";
                    if (goods.getState() == 0) {
                        title = "是否进行付款?";
                    } else if (goods.getState() == 2) {
                        title = "是否确认收货?";
                    }
                    showDialog(title, "", 0, i);
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return list == null || list.size() == 0 ? 0 : list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_order_name, tv_order_price, tv_order_count, tv_order_allpay;
        private ImageView iv_order_goods;
        private Button btn_delete, btn_update;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            tv_order_name = itemView.findViewById(R.id.tv_order_name);
            tv_order_price = itemView.findViewById(R.id.tv_order_price);
            tv_order_count = itemView.findViewById(R.id.tv_order_count);
            tv_order_allpay = itemView.findViewById(R.id.tv_order_allpay);
            iv_order_goods = itemView.findViewById(R.id.iv_order_goods);
            btn_delete = itemView.findViewById(R.id.btn_delete);
            btn_update = itemView.findViewById(R.id.btn_update);
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

    void showDialog(String title, String message, final int flag, final int postion) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setCancelable(false);    //设置是否可以通过点击对话框外区域或者返回按键关闭对话框
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (flag == 0) {
                    handler.obtainMessage(2, postion).sendToTarget();
                } else {
                    handler.obtainMessage(3, postion).sendToTarget();
                }

            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }
}

