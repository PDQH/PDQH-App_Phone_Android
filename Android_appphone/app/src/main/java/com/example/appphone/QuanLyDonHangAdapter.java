package com.example.appphone;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Base64;

public class QuanLyDonHangAdapter extends RecyclerView.Adapter<QuanLyDonHangAdapter.QuanLyDonHangViewHolder> {
    private ArrayList<ThongTinDonHang> dsm;
    private Context c;
    DecimalFormat formatter = new DecimalFormat("###,###,###");
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;

    public QuanLyDonHangAdapter(Context c, ArrayList<ThongTinDonHang> dsm) {
        this.dsm = dsm;
        this.c = c;
    }
    @Override
    public QuanLyDonHangAdapter.QuanLyDonHangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quanlydonhang, parent, false);
        return new QuanLyDonHangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuanLyDonHangAdapter.QuanLyDonHangViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ThongTinDonHang donHang = dsm.get(position);
        holder.tendh.setText("" + donHang.getTenSP());
        holder.giadh.setText("Giá : " + donHang.getGiaSP());
        holder.tragthai.setText("" + donHang.getTrangThai());
        byte[] manghinh = Base64.getDecoder().decode(donHang.getAnhSP());
        Bitmap bm = BitmapFactory.decodeByteArray(manghinh,0, manghinh.length);
        holder.anh.setImageBitmap(bm);

        holder.box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                LayoutInflater layoutInflater = ((Activity) view.getContext()).getLayoutInflater();
                View v1 = layoutInflater.inflate(R.layout.chinhsuadonhang, null);
                builder.setView(v1);
                AlertDialog dialog = builder.create();
                dialog.show();

                // Ánh Xạ Phần
                Spinner nhaptrangthai;
                Button sua, huy;
                nhaptrangthai = v1.findViewById(R.id.nhaptrangthaidh);
                sua = v1.findViewById(R.id.capnhatdh);
                huy = v1.findViewById(R.id.huydh);
                String[] arrTT = {"Chờ Xác Nhận","Đã Xác Nhận","Chờ Giao Hàng","Đang Giao Hàng","Đã Nhận Hàng"};
                ArrayAdapter<String> spnadapter = new ArrayAdapter<>(c, android.R.layout.simple_spinner_dropdown_item,arrTT);
                nhaptrangthai.setAdapter(spnadapter);

                sua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String trangthai = (String)nhaptrangthai.getSelectedItem();
                        databaseReference = FirebaseDatabase.getInstance().getReference();
                        databaseReference.child("ThongTinDonHang").child(donHang.getUID()).child(donHang.getKeyDH()).child("trangThai").setValue(trangthai);
                        dialog.dismiss();
                        Toast.makeText(v1.getContext(), "Cập Nhật Thành Công", Toast.LENGTH_SHORT).show();
                    }
                });
                huy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });


    }

    @Override
    public int getItemCount() {
        return dsm.size();
    }
    public static class QuanLyDonHangViewHolder extends RecyclerView.ViewHolder {
        TextView tendh,giadh,tragthai;
        ImageView anh;
        Button box;


        public QuanLyDonHangViewHolder(View view) {
            super(view);

            tendh =view.findViewById(R.id.itemtendh);
            giadh = view.findViewById(R.id.itemgiadh);
            anh = view.findViewById(R.id.itemqldh);
            tragthai = view.findViewById(R.id.itemtrangthai);
            box = view.findViewById(R.id.btn_suane);
        }
    }

}
