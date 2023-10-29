package com.example.appphone;

import static com.google.android.material.color.MaterialColors.getColor;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    DatabaseReference databaseReference;
    ArrayList<DienThoai> dsls = new ArrayList<DienThoai>();
    ArrayList<DienThoai> ds = new ArrayList<DienThoai>();
    HomeAdapter adapter;
    BanChayAdapter banChayAdapter;
    ImageView samsung,oppo,lenovo,ios,vivo,xiaomi;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ImageView anhslide = view.findViewById(R.id.anhslide);
        anhslide.setBackgroundResource(R.drawable.slide);

        TextView xem = view.findViewById(R.id.xemtatca);
        samsung = view.findViewById(R.id.samsung);
        ios = view.findViewById(R.id.ios);
        vivo = view.findViewById(R.id.vivo);
        lenovo = view.findViewById(R.id.lenovo);
        xiaomi = view.findViewById(R.id.xiaomi);
        oppo = view.findViewById(R.id.oppo);
        RecyclerView recyclerView = view.findViewById(R.id.rvtop);
        RecyclerView recyclerView1 = view.findViewById(R.id.rvbanchay);
        LinearLayoutManager layoutManager= new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);
        recyclerView1.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setLayoutManager(layoutManager);
        dsls.clear();
        ds.clear();
        thinhhanh();
        yeuthich();

        samsung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new HangDienThoaiFragment();
                FragmentManager fmgr = getActivity().getSupportFragmentManager();
                Bundle bundle = new Bundle();
                bundle.putString("keyword", "Samsung");
                fragment.setArguments(bundle);
                FragmentTransaction ft = fmgr.beginTransaction();
                ft.replace(R.id.nav_host_fragment_content_main, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        oppo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new HangDienThoaiFragment();
                FragmentManager fmgr = getActivity().getSupportFragmentManager();
                Bundle bundle = new Bundle();
                bundle.putString("keyword", "Oppo");
                fragment.setArguments(bundle);
                FragmentTransaction ft = fmgr.beginTransaction();
                ft.replace(R.id.nav_host_fragment_content_main, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        ios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new HangDienThoaiFragment();
                FragmentManager fmgr = getActivity().getSupportFragmentManager();
                Bundle bundle = new Bundle();
                bundle.putString("keyword", "IPhone");
                fragment.setArguments(bundle);
                FragmentTransaction ft = fmgr.beginTransaction();
                ft.replace(R.id.nav_host_fragment_content_main, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        lenovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new HangDienThoaiFragment();
                FragmentManager fmgr = getActivity().getSupportFragmentManager();
                Bundle bundle = new Bundle();
                bundle.putString("keyword", "Lenovo");
                fragment.setArguments(bundle);
                FragmentTransaction ft = fmgr.beginTransaction();
                ft.replace(R.id.nav_host_fragment_content_main, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        xiaomi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new HangDienThoaiFragment();
                FragmentManager fmgr = getActivity().getSupportFragmentManager();
                Bundle bundle = new Bundle();
                bundle.putString("keyword", "Xiaomi");
                fragment.setArguments(bundle);
                FragmentTransaction ft = fmgr.beginTransaction();
                ft.replace(R.id.nav_host_fragment_content_main, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        vivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new HangDienThoaiFragment();
                FragmentManager fmgr = getActivity().getSupportFragmentManager();
                Bundle bundle = new Bundle();
                bundle.putString("keyword", "Vivo");
                fragment.setArguments(bundle);
                FragmentTransaction ft = fmgr.beginTransaction();
                ft.replace(R.id.nav_host_fragment_content_main, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        xem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new DienThoaiFragment();
                FragmentManager fmgr = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fmgr.beginTransaction();
                ft.replace(R.id.nav_host_fragment_content_main, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        banChayAdapter =new BanChayAdapter(getContext(), ds, new BanChayAdapter.banchaynhat() {
            @Override
            public void banchay(DienThoai dienThoai) {
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
        adapter = new HomeAdapter(getContext(), dsls, new HomeAdapter.thinhhanh() {
            @Override
            public void thinhhanh(DienThoai dienThoai) {
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
        recyclerView.setAdapter(adapter);
        recyclerView1.setAdapter(banChayAdapter);

        AnimationDrawable drawable1 = (AnimationDrawable) anhslide.getBackground();
            drawable1.start();


        // tạo nofication

        super.onViewCreated(view, savedInstanceState);
    }


    private void yeuthich(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("DienThoai");
        Query query = databaseReference.orderByChild("soLike").equalTo(5);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    DienThoai dienThoai = dataSnapshot.getValue(DienThoai.class);
                        ds.add(dienThoai);
                        banChayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void thinhhanh(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("DienThoai");
        Query query = databaseReference.orderByChild("daBan").startAt(10);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    DienThoai dienThoai = dataSnapshot.getValue(DienThoai.class);
                    if(dienThoai!=null){
                        dsls.add(dienThoai);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home2, container, false);
    }

}
