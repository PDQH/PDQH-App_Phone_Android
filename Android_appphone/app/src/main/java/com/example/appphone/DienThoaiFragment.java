package com.example.appphone;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Base64;

public class DienThoaiFragment extends Fragment {
    DecimalFormat formatter = new DecimalFormat("###,###,###");
    ArrayList<DienThoai> dsls = new ArrayList<DienThoai>();
    RecyclerView recyclerView;
    int sosaone;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public DienThoaiFragment() {
        // Required empty public constructor
    }

    public static DienThoaiFragment newInstance(String param1, String param2) {
        DienThoaiFragment fragment = new DienThoaiFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dsls.clear();

        recyclerView = view.findViewById(R.id.reviewdt);
        LinearLayoutManager layoutManager= new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);




    }

    @Override
    public void onStart() {
        FirebaseRecyclerOptions<DienThoai> options =
                new FirebaseRecyclerOptions.Builder<DienThoai>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("DienThoai")
                                , DienThoai.class)
                        .build();


        FirebaseRecyclerAdapter<DienThoai, DienThoaiFragment.DienThoaiViewHolder> adapter =
                new FirebaseRecyclerAdapter<DienThoai, DienThoaiFragment.DienThoaiViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull DienThoaiFragment.DienThoaiViewHolder holder, @SuppressLint("RecyclerView") int i, @NonNull DienThoai dienThoai) {


                        holder.tendt.setText("" + dienThoai.getTen());
                        holder.giadt.setText("Giá : " + formatter.format(dienThoai.getGiaTien()));
                        holder.ct.setText("" + dienThoai.getChiTiet());
                        holder.solike.setText("" + dienThoai.getSoLike());

                        byte[] manghinh = Base64.getDecoder().decode(dienThoai.getLinkAnh());
                        Bitmap bm = BitmapFactory.decodeByteArray(manghinh, 0, manghinh.length);
                        holder.anhdt.setImageBitmap(bm);
                        holder.anhdt.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Fragment fragment = new ChiTietDienThoaiFragment();
                                FragmentManager fmgr = getActivity().getSupportFragmentManager();
                                Bundle bundle = new Bundle();
                                bundle.putString("name", dienThoai.getTen());
                                bundle.putInt("gia", dienThoai.getGiaTien());
                                bundle.putString("chitiet", dienThoai.getChiTiet());
                                bundle.putString("anh", dienThoai.getLinkAnh());
                                bundle.putFloat("tim", dienThoai.getSoLike());
                                bundle.putInt("daban", dienThoai.getDaBan());
                                bundle.putString("keydt",dienThoai.getId());
                                fragment.setArguments(bundle);
                                FragmentTransaction ft = fmgr.beginTransaction();
                                ft.replace(R.id.nav_host_fragment_content_main, fragment);
                                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                                ft.addToBackStack(null);
                                ft.commit();
                            }
                        });
                        holder.tendt.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Fragment fragment = new ChiTietDienThoaiFragment();
                                FragmentManager fmgr = getActivity().getSupportFragmentManager();
                                Bundle bundle = new Bundle();
                                bundle.putString("name", dienThoai.getTen());
                                bundle.putInt("gia", dienThoai.getGiaTien());
                                bundle.putString("chitiet", dienThoai.getChiTiet());
                                bundle.putString("anh", dienThoai.getLinkAnh());
                                bundle.putFloat("tim", dienThoai.getSoLike());
                                bundle.putInt("daban", dienThoai.getDaBan());
                                bundle.putString("keydt",dienThoai.getId());
                                fragment.setArguments(bundle);
                                FragmentTransaction ft = fmgr.beginTransaction();
                                ft.replace(R.id.nav_host_fragment_content_main, fragment);
                                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                                ft.addToBackStack(null);
                                ft.commit();
                            }
                        });

                    }

                    @NonNull
                    @Override
                    public DienThoaiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dienthoai, parent, false);
                        return new DienThoaiViewHolder(view);
                    }
                };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
        super.onStart();
    }

    public static class DienThoaiViewHolder extends RecyclerView.ViewHolder {
        TextView tendt,giadt,ct,solike;
        ImageView anhdt,tim;

        public DienThoaiViewHolder(View view) {
            super(view);

            tendt =view.findViewById(R.id.itemten);
            ct =view.findViewById(R.id.itemct);
            giadt = view.findViewById(R.id.itemgia);
            anhdt = view.findViewById(R.id.itemsp);
            solike = view.findViewById(R.id.sosaodt);
            tim = view.findViewById(R.id.tim);


        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dien_thoai, container, false);
    }
}