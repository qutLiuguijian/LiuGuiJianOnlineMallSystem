package com.lgj.liuguijianonlinemallapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lgj.liuguijianonlinemallapp.R;
import com.lgj.liuguijianonlinemallapp.bean.Gaddress;

import java.util.List;

public class AddressRecyclerViewAdapter extends RecyclerView.Adapter {
    Context context;
    List<Gaddress> list;
    public AddressRecyclerViewAdapter(Context context, List<Gaddress> goodsList) {
        this.context = context;
        this.list = goodsList;

    }
    private OnitemClickListener onitemClickListener;
    private OnitemLongClickListener onitemLongClickListener;


    public void setOnitemClickListener(OnitemClickListener onitemClickListener) {
        this.onitemClickListener = onitemClickListener;
    }
    public void setOnitemLongClickListener(OnitemLongClickListener onitemLongClickListener) {
        this.onitemLongClickListener = onitemLongClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.module_recycle_item_address, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        Gaddress gaddress = list.get(i);
        myViewHolder.tv_receiver.setText(gaddress.getReceiver());
        myViewHolder.tv_receiver_address.setText(gaddress.getAddress());
        myViewHolder.tv_receiver_phone.setText(gaddress.getPhone());
    }

    @Override
    public int getItemCount() {
        return list==null||list.size()==0?0:list.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_receiver,tv_receiver_phone,tv_receiver_address;
        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            tv_receiver=itemView.findViewById(R.id.tv_receiver);
            tv_receiver_phone=itemView.findViewById(R.id.tv_receiver_phone);
            tv_receiver_address=itemView.findViewById(R.id.tv_receiver_address);

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
        }
    }
    public interface OnitemClickListener {
        void onItemClick(View view, int postion);
    }
    public interface OnitemLongClickListener {
        void onItemLongClick(View view, int postion);
    }
}
