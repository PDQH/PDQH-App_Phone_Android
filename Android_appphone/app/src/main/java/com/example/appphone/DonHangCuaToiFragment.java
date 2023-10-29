package com.example.appphone;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.util.Base64;

public class DonHangCuaToiFragment extends Fragment {
    DecimalFormat formatter = new DecimalFormat("###,###,###");
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DonHangCuaToiFragment() {
        // Required empty public constructor
    }

    public static DonHangCuaToiFragment newInstance(String param1, String param2) {
        DonHangCuaToiFragment fragment = new DonHangCuaToiFragment();
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
         recyclerView = view.findViewById(R.id.lv_dhct);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);


    }

    @Override
    public void onStart() {
        super.onStart();
        String uid = firebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseRecyclerOptions<ThongTinDonHang> options =
                new FirebaseRecyclerOptions.Builder<ThongTinDonHang>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("ThongTinDonHang").child(uid)
                                , ThongTinDonHang.class)
                        .build();
        FirebaseRecyclerAdapter<ThongTinDonHang,DonHangCuaToiViewHolder> adapter = new
                FirebaseRecyclerAdapter<ThongTinDonHang, DonHangCuaToiViewHolder>(options) {
            @Override
            protected void onBindViewHolder(DonHangCuaToiViewHolder holder, @SuppressLint("RecyclerView") int i, @NonNull ThongTinDonHang donHang) {
                holder.tendt.setText(""+donHang.getTenSP());
                holder.gia.setText("Số Tiền: "+formatter.format(donHang.getGiaSP()));
                holder.trangthai.setText(""+donHang.getTrangThai());
                holder.tongtien.setText("Tổng Tiền("+donHang.getSoLuong()+" Sản Phẩm): "+donHang.getGiaSP());
                byte[] manghinh = Base64.getDecoder().decode(donHang.getAnhSP());
                Bitmap bm = BitmapFactory.decodeByteArray(manghinh,0, manghinh.length);
                holder.anh.setImageBitmap(bm);
                if(holder.trangthai.getText().toString().equalsIgnoreCase("Chờ Xác Nhận")) {
                    holder.huy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(view.getContext());
                            builder1.setMessage("Bạn Có Chắc Chắn Muốn Hủy?");
                            builder1.setCancelable(true);
                            builder1.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    holder.trangthai.setText("Đã Hủy");
                                    String tt = holder.trangthai.getText().toString();
                                    databaseReference = FirebaseDatabase.getInstance().getReference();
                                    databaseReference.child("ThongTinDonHang").child(uid).child(getRef(i).getKey()).child("trangThai").setValue(tt);

                                }
                            });
                            builder1.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            AlertDialog alertDialog = builder1.create();
                            alertDialog.show();
                        }
                    });
                } else if (holder.trangthai.getText().toString().equalsIgnoreCase("Đã Nhận Hàng")){
                    holder.huy.setText("Đánh Giá");
                    holder.huy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Fragment fragment = new DanhGiaFragment();
                            FragmentManager fmgr = getActivity().getSupportFragmentManager();
                            Bundle bundle = new Bundle();
                            bundle.putString("tendtbl",donHang.getTenSP());
                            bundle.putString("anhdtbl", donHang.getAnhSP());
                            bundle.putString("keydtbl", donHang.getKeyDT());
                            fragment.setArguments(bundle);
                            FragmentTransaction ft = fmgr.beginTransaction();
                            ft.replace(R.id.nav_host_fragment_content_main, fragment);
                            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                            ft.addToBackStack(null);
                            ft.commit();

                        }
                    });
                } else {
                    holder.huy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(holder.itemView.getContext(), "Không Thể Hủy Do Đơn Hàng Này "+holder.trangthai.getText().toString(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }

                holder.anh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Fragment fragment = new ChiTietDonHangFragment();
                        FragmentManager fmgr = getActivity().getSupportFragmentManager();
                        Bundle bundle = new Bundle();
                        bundle.putString("madonhang", getRef(i).getKey());
                        bundle.putString("tennguoinhanhang", donHang.getTenNguoiNhan());
                        bundle.putString("ngaydathang", String.valueOf(donHang.getNgay() +""+ donHang.getThang()+""+ donHang.getNam()));
                        bundle.putString("diachidonhang", donHang.getDiaChi());
                        bundle.putInt("sdtdonhang", donHang.getSdt());
                        bundle.putInt("giadtdonhang", donHang.getGiaDT());
                        bundle.putString("tenspdonhang", donHang.getTenSP());
                        bundle.putInt("giaspdonhang", donHang.getGiaSP());
                        bundle.putInt("soluongdonhang", donHang.getSoLuong());
                        bundle.putString("anhdonhang",donHang.getAnhSP());
                        bundle.putString("trangthaidonhang",donHang.getTrangThai());
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
            public DonHangCuaToiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_donhang, parent, false);
                return new DonHangCuaToiViewHolder(view);
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }
    public static class DonHangCuaToiViewHolder extends RecyclerView.ViewHolder {
        ImageView anh;
        TextView tendt , gia , trangthai ,tongtien;
        TextView huy;


        public DonHangCuaToiViewHolder(View view) {
            super(view);
            anh = view.findViewById(R.id.itemdh);
            tendt = view.findViewById(R.id.itemtendh);
            gia = view.findViewById(R.id.itemgiadh);
            trangthai = view.findViewById(R.id.itemtrangthai);
            tongtien = view.findViewById(R.id.itemtongtien);
            huy = view.findViewById(R.id.btn_huy);



        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_don_hang_cua_toi, container, false);
    }
}