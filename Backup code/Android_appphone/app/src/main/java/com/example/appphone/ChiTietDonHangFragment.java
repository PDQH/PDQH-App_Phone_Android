package com.example.appphone;

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
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Base64;

public class ChiTietDonHangFragment extends Fragment {
    DecimalFormat formatter = new DecimalFormat("###,###,###");
    TextView ma,ngay,tenkh,diachi,sdt,sosp,tongtien,trangthai,tensp,giasp;
    ImageView anh;
    Button back;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChiTietDonHangFragment() {
        // Required empty public constructor
    }

    public static ChiTietDonHangFragment newInstance(String param1, String param2) {
        ChiTietDonHangFragment fragment = new ChiTietDonHangFragment();
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
        ma = view.findViewById(R.id.machitietdonhang);
        ngay = view.findViewById(R.id.ngaydathang);
        tenkh = view.findViewById(R.id.tenkhachhang);
        diachi = view.findViewById(R.id.diachigiaohang);
        sdt = view.findViewById(R.id.sdtnguoinhan);
        sosp = view.findViewById(R.id.soluonghang);
        tongtien = view.findViewById(R.id.tongtienhang);
        trangthai = view.findViewById(R.id.trangthaidonhang);
        tensp = view.findViewById(R.id.tenspdh);
        giasp = view.findViewById(R.id.giaspdh);
        anh = view.findViewById(R.id.anhspdh);
        back = view.findViewById(R.id.btn_back);

        Bundle bundle = this.getArguments();
        String madh = bundle.getString("madonhang");
        String tenngnhan = bundle.getString("tennguoinhanhang");
        String diachidh = bundle.getString("diachidonhang");
        int sdtdh = bundle.getInt("sdtdonhang");
        String tenspdh = bundle.getString("tenspdonhang");
        int gia = bundle.getInt("giaspdonhang");
        int soluong = bundle.getInt("soluongdonhang");
        int giadt = bundle.getInt("giadtdonhang");
        String anhdh = bundle.getString("anhdonhang");
        String ngaydh = bundle.getString("ngaydathang");
        String trangthaidh = bundle.getString("trangthaidonhang");
        byte[] manghinh = Base64.getDecoder().decode(anhdh);
        Bitmap bm = BitmapFactory.decodeByteArray(manghinh,0, manghinh.length);

        ma.setText(madh+"");
        ngay.setText(ngaydh+"");
        tenkh.setText(tenngnhan+"");
        diachi.setText(diachidh+"");
        sdt.setText(sdtdh+"");
        sosp.setText(soluong+"");
        tongtien.setText(formatter.format(gia)+" VNĐ");
        trangthai.setText(trangthaidh+"");
        anh.setImageBitmap(bm);
        tensp.setText(tenspdh+"");
        giasp.setText(formatter.format(giadt)+"");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_info, container, false);
    }
}