package com.example.appphone;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.util.Base64;

public class DonHangAdapter extends FirebaseRecyclerAdapter<GioHang, DonHangAdapter.DonHangViewHolder> {

    FirebaseAuth firebaseAuth;
    public DonHangAdapter(@NonNull FirebaseRecyclerOptions<GioHang> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull DonHangViewHolder holder, int i, @NonNull GioHang gioHang) {
        String uid = firebaseAuth.getInstance().getCurrentUser().getUid();
        int tinhtong = gioHang.getGiaGioHang() * gioHang.getSoLuong();
        holder.ten.setText(""+gioHang.getTenGioHang());
        holder.gia.setText("Giá : "+tinhtong);
        holder.soluong.setText(""+gioHang.getSoLuong());
        byte[] manghinh = Base64.getDecoder().decode(gioHang.getAnhGioHang());
        Bitmap bm = BitmapFactory.decodeByteArray(manghinh,0, manghinh.length);
        holder.anh.setImageBitmap(bm);
    }

    @NonNull
    @Override
    public DonHangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_giohang, parent, false);
        return new DonHangAdapter.DonHangViewHolder(view);
    }

    class DonHangViewHolder extends RecyclerView.ViewHolder {
        TextView ten,gia,soluong;
        ImageView anh;
        ImageView cong,tru;
        int so=1;

        public DonHangViewHolder(View view) {
            super(view);

            ten =view.findViewById(R.id.tv_name_giohang);
            gia = view.findViewById(R.id.tv_gia_giohang);
            anh = view.findViewById(R.id.img_giohang);
            soluong = view.findViewById(R.id.soluonggio);
            cong = view.findViewById(R.id.conggio);
            tru = view.findViewById(R.id.trugio);

            cong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    so = so+1;
                    soluong.setText(so+"");
                }
            });
            tru.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    so = so-1;
                    soluong.setText(so+"");
                }
            });




        }
    }
}
