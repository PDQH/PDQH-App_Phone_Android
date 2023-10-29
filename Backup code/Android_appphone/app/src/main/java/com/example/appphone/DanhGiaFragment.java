package com.example.appphone;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Locale;


public class DanhGiaFragment extends Fragment {

    Button cmt;
    DatabaseReference databaseReference;
    ImageView anh,sao1,sao2,sao3,sao4,sao5;
    TextView ten,sosao;
    EditText nhanxet;
    float saobandau;
    String uid;
    String tennd;
    String key;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DanhGiaFragment() {
        // Required empty public constructor
    }


    public static DanhGiaFragment newInstance(String param1, String param2) {
        DanhGiaFragment fragment = new DanhGiaFragment();
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
        cmt = view.findViewById(R.id.btnbinhluan);
        anh = view.findViewById(R.id.anhdtbl);
        sao1 = view.findViewById(R.id.sao1);
        sao2 = view.findViewById(R.id.sao2);
        sao3 = view.findViewById(R.id.sao3);
        sao4 = view.findViewById(R.id.sao4);
        sao5 = view.findViewById(R.id.sao5);
        sosao = view.findViewById(R.id.ketquasao);
        ten = view.findViewById(R.id.tendtbl);
        nhanxet = view.findViewById(R.id.nhapnoidung);
        Bundle bundle = this.getArguments();
        String ten1 = bundle.getString("tendtbl");
        String anh1 = bundle.getString("anhdtbl");
        key = bundle.getString("keydtbl");
        byte[] manghinh = Base64.getDecoder().decode(anh1);
        Bitmap bm = BitmapFactory.decodeByteArray(manghinh,0,manghinh.length);
        ten.setText(ten1);
        anh.setImageBitmap(bm);

        getSao(key);

        sao1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sao1.setImageResource(R.drawable.saosau);
                sao2.setImageResource(R.drawable.saodau);
                sao3.setImageResource(R.drawable.saodau);
                sao4.setImageResource(R.drawable.saodau);
                sao5.setImageResource(R.drawable.saodau);
                sosao.setText("1");
            }
        });
        sao2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sao1.setImageResource(R.drawable.saosau);
                sao2.setImageResource(R.drawable.saosau);
                sao3.setImageResource(R.drawable.saodau);
                sao4.setImageResource(R.drawable.saodau);
                sao5.setImageResource(R.drawable.saodau);
                sosao.setText("2");
            }
        });
        sao3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sao1.setImageResource(R.drawable.saosau);
                sao2.setImageResource(R.drawable.saosau);
                sao3.setImageResource(R.drawable.saosau);
                sao4.setImageResource(R.drawable.saodau);
                sao5.setImageResource(R.drawable.saodau);
                sosao.setText("3");
            }
        });
        sao4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sao1.setImageResource(R.drawable.saosau);
                sao2.setImageResource(R.drawable.saosau);
                sao3.setImageResource(R.drawable.saosau);
                sao4.setImageResource(R.drawable.saosau);
                sao5.setImageResource(R.drawable.saodau);
                sosao.setText("4");
            }
        });
        sao5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sao1.setImageResource(R.drawable.saosau);
                sao2.setImageResource(R.drawable.saosau);
                sao3.setImageResource(R.drawable.saosau);
                sao4.setImageResource(R.drawable.saosau);
                sao5.setImageResource(R.drawable.saosau);
                sosao.setText("5");
            }
        });
        cmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                databaseReference = FirebaseDatabase.getInstance().getReference();
                uid = databaseReference.push().getKey();
                SharedPreferences sharedPref = getActivity().getSharedPreferences("ThongTin", MODE_PRIVATE);
                String email = sharedPref.getString("email", "");
                databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child("NguoiDung").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            DangKy dangKy = dataSnapshot.getValue(DangKy.class);
                            if (dangKy.getEmail().contains(email)) {
                                tennd = dangKy.getHoTen();
                            }
                            String noidung = nhanxet.getText().toString();
                            String ngay = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                            if (saobandau != 0.0) {
                                float sosaosau = Float.parseFloat(sosao.getText().toString());
                                float tongsao = saobandau + sosaosau;
                                float tongsao1 = tongsao / 2;
                                BinhLuan binhLuan = new BinhLuan(uid, tennd, noidung, ngay, tongsao1);
                                databaseReference.child("DienThoai").child(key).child("BinhLuan").child(uid).setValue(binhLuan);
                                databaseReference.child("DienThoai").child(key).child("soLike").setValue(tongsao1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Fragment fragment = new DonHangCuaToiFragment();
                                        FragmentManager fmgr = getActivity().getSupportFragmentManager();
                                        FragmentTransaction ft = fmgr.beginTransaction();
                                        ft.replace(R.id.nav_host_fragment_content_main, fragment);
                                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                                        ft.addToBackStack(null);
                                        ft.commit();
                                    }
                                });
                            } else {
                                float sosaosau = Float.parseFloat(sosao.getText().toString());
                                BinhLuan binhLuan = new BinhLuan(uid, tennd, noidung, ngay, sosaosau);
                                databaseReference.child("DienThoai").child(key).child("BinhLuan").child(uid).setValue(binhLuan);
                                databaseReference.child("DienThoai").child(key).child("soLike").setValue(sosaosau).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Fragment fragment = new DonHangCuaToiFragment();
                                        FragmentManager fmgr = getActivity().getSupportFragmentManager();
                                        FragmentTransaction ft = fmgr.beginTransaction();
                                        ft.replace(R.id.nav_host_fragment_content_main, fragment);
                                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                                        ft.addToBackStack(null);
                                        ft.commit();
                                    }

                                });

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
                });




    }
    private void getSao(String key){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("DienThoai").child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DienThoai thoai = snapshot.getValue(DienThoai.class);
                saobandau =   thoai.getSoLike();
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
        return inflater.inflate(R.layout.danhgia_item, container, false);
    }
}