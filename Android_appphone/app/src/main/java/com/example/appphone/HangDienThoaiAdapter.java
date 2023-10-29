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

public class HangDienThoaiAdapter extends RecyclerView.Adapter<HangDienThoaiAdapter.HangDienThoaiViewHolder>{
    private List<DienThoai> dsm;
    DecimalFormat formatter = new DecimalFormat("###,###,###");
    private Context c;
    DienThoai dienThoai;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;

    private chuyen chuyen;
    public interface chuyen{
        void ChuyenFragment(DienThoai dienThoai);
    }
    public HangDienThoaiAdapter(Context c, ArrayList<DienThoai> dsm,chuyen chuyen) {
        this.dsm = dsm;
        this.chuyen = chuyen;
        this.c = c;
    }


    @Override
    public HangDienThoaiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) c).getLayoutInflater();
        View view = inflater.inflate(R.layout.hangdienthoai_item, parent, false);
        HangDienThoaiViewHolder viewHolder = new HangDienThoaiViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HangDienThoaiAdapter.HangDienThoaiViewHolder holder, int position) {
        DienThoai lg = dsm.get(position);
        holder.tendt.setText(""+lg.getTen());
        holder.giadt.setText("Giá: "+formatter.format(lg.getGiaTien()));
        holder.chitiet.setText(""+lg.getChiTiet());
        byte[] manghinh = Base64.getDecoder().decode(lg.getLinkAnh());
        Bitmap bm = BitmapFactory.decodeByteArray(manghinh,0, manghinh.length);
        holder.anhdt.setImageBitmap(bm);
        holder.sao.setText(""+lg.getSoLike());
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




    public class HangDienThoaiViewHolder extends RecyclerView.ViewHolder {
        TextView tendt,giadt,chitiet,sao;
        ImageView anhdt;

        public HangDienThoaiViewHolder(View view) {
            super(view);

            tendt =view.findViewById(R.id.tendthang);
            giadt = view.findViewById(R.id.giadthang);
            chitiet =view.findViewById(R.id.ctdthang);
            anhdt = view.findViewById(R.id.anhdthang);
            sao = view.findViewById(R.id.saodthang);


        }
    }

}
