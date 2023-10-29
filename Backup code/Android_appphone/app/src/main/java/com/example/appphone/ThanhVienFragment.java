package com.example.appphone;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class ThanhVienFragment extends Fragment {

    DangKy dangKy;
    ArrayList<DangKy> dsls = new ArrayList<DangKy>();
    MemberAdapter adapter;
    DatabaseReference databaseReference;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public ThanhVienFragment() {
        // Required empty public constructor
    }
    public static ThanhVienFragment newInstance(String param1, String param2) {
        ThanhVienFragment fragment = new ThanhVienFragment();
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
        RecyclerView recyclerView = view.findViewById(R.id.reviewm);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        FirebaseRecyclerOptions<DangKy> options =
                new FirebaseRecyclerOptions.Builder<DangKy>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("NguoiDung"), DangKy.class)
                        .build();

        adapter = new MemberAdapter(options);
        recyclerView.setAdapter(adapter);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        adapter.startListening();
        super.onStart();
    }

    @Override
    public void onStop() {
        adapter.stopListening();
        super.onStop();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_thanh_vien, container, false);
    }

}