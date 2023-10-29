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

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    private List<DienThoai> dsm;
    DecimalFormat formatter = new DecimalFormat("###,###,###");
    private List<DienThoai> dsm1;
    private Context c;
    DienThoai dienThoai;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    private chuyenFrag chuyenFrag;
    public interface chuyenFrag{
        void chuyenFrag(DienThoai dienThoai);
    }

    public SearchAdapter(Context c, ArrayList<DienThoai> dsm,chuyenFrag chuyenFrag) {
        this.chuyenFrag = chuyenFrag;
        this.dsm = dsm;
        this.dsm1 = dsm;
        this.c = c;
    }


    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) c).getLayoutInflater();
        View view = inflater.inflate(R.layout.search_item, parent, false);
        SearchViewHolder viewHolder = new SearchViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SearchAdapter.SearchViewHolder holder, int position) {
        DienThoai lg = dsm.get(position);
        holder.tendt.setText(""+lg.getTen());
        holder.giadt.setText("Giá : "+formatter.format(lg.getGiaTien()));
        holder.chitiet.setText(""+lg.getChiTiet());
        byte[] manghinh = Base64.getDecoder().decode(lg.getLinkAnh());
        Bitmap bm = BitmapFactory.decodeByteArray(manghinh,0, manghinh.length);
        holder.anhdt.setImageBitmap(bm);
        holder.anhdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chuyenFrag.chuyenFrag(lg);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dsm.size();
    }




    public class SearchViewHolder extends RecyclerView.ViewHolder {
        TextView tendt,giadt,chitiet;
        ImageView anhdt;

        public SearchViewHolder(View view) {
            super(view);

            tendt =view.findViewById(R.id.tencim);
            giadt = view.findViewById(R.id.tiencim);
            chitiet =view.findViewById(R.id.ctcim);
            anhdt = view.findViewById(R.id.cim);


        }
    }
}
