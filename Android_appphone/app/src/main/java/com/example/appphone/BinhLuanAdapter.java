package com.example.appphone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;

import androidx.recyclerview.widget.LinearLayoutManager;


public class BinhLuanAdapter extends FirebaseRecyclerAdapter<BinhLuan, BinhLuanAdapter.BinhLuanViewHolder> {
    float sosaodau;
    FirebaseAuth firebaseAuth;
    public BinhLuanAdapter(@NonNull FirebaseRecyclerOptions<BinhLuan> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull BinhLuanViewHolder holder, int i, @NonNull BinhLuan binhLuan) {

        holder.ten.setText(""+binhLuan.getUsername());
        holder.nd.setText(""+binhLuan.getNoiDung());
        holder.ngay.setText(""+binhLuan.getNgay());
        sosaodau = binhLuan.getSao();
        if (sosaodau == 1){
            holder.sao.setImageResource(R.drawable.motsao);
        }if (sosaodau == 2){
            holder.sao.setImageResource(R.drawable.haisao);
        }if (sosaodau == 3){
            holder.sao.setImageResource(R.drawable.bonsao);
        }if (sosaodau == 4){
            holder.sao.setImageResource(R.drawable.bonsaoreal);
        }if (sosaodau == 5){
            holder.sao.setImageResource(R.drawable.namsao);
        }


    }

    @NonNull
    @Override
    public BinhLuanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat, parent, false);
        return new BinhLuanAdapter.BinhLuanViewHolder(view);
    }

    class BinhLuanViewHolder extends RecyclerView.ViewHolder {
        TextView ten,nd,ngay;
        ImageView sao;

        public BinhLuanViewHolder(View view) {
            super(view);

            ten =view.findViewById(R.id.tvUser);
            nd = view.findViewById(R.id.tvText);
            ngay = view.findViewById(R.id.tvTime);
            sao = view.findViewById(R.id.hiensao);
        }
    }


}
