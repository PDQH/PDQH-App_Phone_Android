package com.example.appphone;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ThongBaoNguoiDungAdapter extends RecyclerView.Adapter<ThongBaoNguoiDungAdapter.ThongBaoViewHolder> {
    private ArrayList<ThongBao> dsm;
    private Context c;
    public ThongBaoNguoiDungAdapter(Context c, ArrayList<ThongBao> dsm) {

        this.dsm = dsm;
        this.c = c;
    }


    @Override
    public ThongBaoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) c).getLayoutInflater();
        View view = inflater.inflate(R.layout.thongbao_nd, parent, false);
        ThongBaoViewHolder viewHolder = new ThongBaoViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ThongBaoNguoiDungAdapter.ThongBaoViewHolder holder, int position) {
        ThongBao lg = dsm.get(position);
        holder.noidung.setText(lg.getNoiDung());
        holder.tieude.setText(lg.getTieuDe());

    }

    @Override
    public int getItemCount() {
        return dsm.size();
    }

    public class ThongBaoViewHolder extends RecyclerView.ViewHolder {
        TextView ngay,noidung,tieude;
        CardView cardView;

        public ThongBaoViewHolder(View view) {
            super(view);
            tieude = view.findViewById(R.id.tieudetbnd);
            noidung = view.findViewById(R.id.noidungtbnd);
            cardView = view.findViewById(R.id.cardviewloai21nd);


        }
    }
}
