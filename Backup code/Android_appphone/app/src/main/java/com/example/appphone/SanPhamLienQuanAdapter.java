package com.example.appphone;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class SanPhamLienQuanAdapter extends RecyclerView.Adapter<SanPhamLienQuanAdapter.SanPhamLienQuanViewHolder>{
    private List<DienThoai> dsm;
    private Context c;
    DecimalFormat formatter = new DecimalFormat("###,###,###");
    DienThoai dienThoai;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;

    private chuyen chuyen;
    public interface chuyen{
        void ChuyenFragment(DienThoai dienThoai);
    }
    public SanPhamLienQuanAdapter(Context c, ArrayList<DienThoai> dsm, chuyen chuyen) {
        this.dsm = dsm;
        this.chuyen = chuyen;
        this.c = c;
    }


    @Override
    public SanPhamLienQuanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) c).getLayoutInflater();
        View view = inflater.inflate(R.layout.sanphamlienquan_item, parent, false);
        SanPhamLienQuanViewHolder viewHolder = new SanPhamLienQuanViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SanPhamLienQuanAdapter.SanPhamLienQuanViewHolder holder, int position) {
        DienThoai lg = dsm.get(position);
        holder.tendt.setText(""+lg.getTen());
        holder.giadt.setText("Giá: "+formatter.format(lg.getGiaTien()));
        byte[] manghinh = Base64.getDecoder().decode(lg.getLinkAnh());
        Bitmap bm = BitmapFactory.decodeByteArray(manghinh,0, manghinh.length);
        holder.anhdt.setImageBitmap(bm);
        holder.anhdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chuyen.ChuyenFragment(lg);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dsm.size();
    }




    public class SanPhamLienQuanViewHolder extends RecyclerView.ViewHolder {
        TextView tendt,giadt;
        ImageView anhdt;

        public SanPhamLienQuanViewHolder(View view) {
            super(view);

            tendt =view.findViewById(R.id.tendtlq);
            giadt = view.findViewById(R.id.giadtlq);
            anhdt = view.findViewById(R.id.anhdtlq);

        }
    }

}
