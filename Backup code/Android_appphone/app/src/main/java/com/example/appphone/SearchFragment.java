package com.example.appphone;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.appphone.databinding.ActivityMain2Binding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    SearchAdapter adapter;
    DatabaseReference databaseReference;
    SearchView searchView;
    ActivityMain2Binding binding;

    ArrayList<DienThoai> dsls = new ArrayList<DienThoai>();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.rvserch);
        LinearLayoutManager layoutManager= new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        dsls.clear();
        Bundle bundle = this.getArguments();
        String ten1 = bundle.getString("keyw");
        getlist(ten1);
        adapter = new SearchAdapter(getContext(), dsls, new SearchAdapter.chuyenFrag() {
            @Override
            public void chuyenFrag(DienThoai dienThoai) {
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



    }

    private void getlist(String key){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("DienThoai");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    DienThoai dienThoai = dataSnapshot.getValue(DienThoai.class);

                    if(dienThoai.getTen().toLowerCase().contains(key.toLowerCase())){
                        dsls.add(dienThoai);
                    }
                    adapter.notifyDataSetChanged();

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
        return inflater.inflate(R.layout.fragment_search, container, false);
    }
}