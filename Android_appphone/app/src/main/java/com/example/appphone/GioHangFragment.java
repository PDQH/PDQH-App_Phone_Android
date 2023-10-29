package com.example.appphone;

import android.annotation.SuppressLint;
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
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.util.Base64;
import java.util.List;

public class GioHangFragment extends Fragment {
    DecimalFormat formatter = new DecimalFormat("###,###,###");

    RecyclerView recyclerView;
    Button btn_thanhtoan;
    int tongtiengh;
    TextView xoa;
    TextView emty;
    List<GioHang> gioHangList;
    int sohang1;
    RadioButton chontatca;
    FirebaseAuth firebaseAuth;
    TextView vnd;
    int giadtchitiet;
    private LinearLayoutManager manager;
    //GioHangAdapter adapter;
    DatabaseReference databaseReference;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GioHangFragment() {
        // Required empty public constructor
    }

    public static GioHangFragment newInstance(String param1, String param2) {
        GioHangFragment fragment = new GioHangFragment();
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
         btn_thanhtoan = view.findViewById(R.id.btn_thanhtoan);
         vnd = view.findViewById(R.id.vnd);
         chontatca = view.findViewById(R.id.chontatca);
         xoa = view.findViewById(R.id.xoagiohang);
         recyclerView = view.findViewById(R.id.lv_giohang);
         emty = view.findViewById(R.id.emptygio);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    public void onStart() {
        super.onStart();
        String uid = firebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseRecyclerOptions<GioHang> options =
                new FirebaseRecyclerOptions.Builder<GioHang>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("GioHang").child(uid), GioHang.class)
                        .build();

            FirebaseRecyclerAdapter<GioHang, GioHangViewHolder> adapter =
                    new FirebaseRecyclerAdapter<GioHang, GioHangViewHolder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull GioHangViewHolder holder, @SuppressLint("RecyclerView") int i, @NonNull GioHang gioHang) {

                            holder.ten.setText("" + gioHang.getTenGioHang());
                            holder.gia.setText("" + formatter.format(gioHang.getGiaGioHang()));
                            holder.soluong.setText("" + gioHang.getSoLuong());
                            byte[] manghinh = Base64.getDecoder().decode(gioHang.getAnhGioHang());
                            Bitmap bm = BitmapFactory.decodeByteArray(manghinh, 0, manghinh.length);
                            holder.anh.setImageBitmap(bm);

                            holder.select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                    if (compoundButton.isChecked()) {
                                        vnd.setText(gioHang.getGiaGioHang() + " Đ");
                                        Toast.makeText(getContext(), "Đã Chọn", Toast.LENGTH_SHORT).show();
                                        Fragment fragment = new ThongTinDonHangFragment();
                                        FragmentManager fmgr = getActivity().getSupportFragmentManager();
                                        Bundle bundle = new Bundle();
                                        bundle.putString("tengh", gioHang.getTenGioHang());
                                        bundle.putInt("giagh", gioHang.getGiaGioHang());
                                        bundle.putString("keygio", gioHang.getKeyDT());
                                        bundle.putInt("giaspgio", gioHang.getGiaDT());
                                        bundle.putInt("dabangio", gioHang.getDaBan());
                                        bundle.putString("soluong", String.valueOf(gioHang.getSoLuong()));
                                        bundle.putString("anhgh", gioHang.getAnhGioHang());
                                        fragment.setArguments(bundle);

                                        xoa.setEnabled(true);
                                        chontatca.setText("Bỏ Chọn");
                                        btn_thanhtoan.setEnabled(true);
                                        chontatca.setChecked(false);

                                        btn_thanhtoan.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                //Xóa dữ liệu đã chọn trong bảng đã thanh toán
                                                databaseReference = FirebaseDatabase.getInstance().getReference();
                                                databaseReference.child("GioHang").child(uid).child(getRef(i).getKey()).removeValue();
                                                FragmentTransaction ft = fmgr.beginTransaction();
                                                ft.replace(R.id.nav_host_fragment_content_main, fragment);
                                                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                                                ft.addToBackStack(null);
                                                ft.commit();
                                            }
                                        });

                                        xoa.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                databaseReference = FirebaseDatabase.getInstance().getReference();
                                                databaseReference.child("GioHang").child(uid).child(getRef(i).getKey()).removeValue();
                                                Toast.makeText(getContext(), "Đã Xóa Thành Công", Toast.LENGTH_SHORT).show();
                                                chontatca.setText("Chọn tất cả");
                                                chontatca.setChecked(false);
                                            }
                                        });

                                        chontatca.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                            @Override
                                            public void onCheckedChanged(CompoundButton compoundButton1, boolean b) {
                                                if (compoundButton1.isChecked()) {
                                                    compoundButton.setChecked(false);
                                                    xoa.setEnabled(false);
                                                    chontatca.setText("Chọn tất cả");
                                                    chontatca.setChecked(false);
                                                }
                                            }
                                        });

                                    }


                                }
                            });


                            holder.cong.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    int sl1 = Integer.parseInt(holder.soluong.getText().toString());
                                    holder.so = sl1 + 1;
                                    holder.soluong.setText(holder.so + "");
                                    int tinhtong = gioHang.getGiaDT() * holder.so;
                                    holder.gia.setText(formatter.format(tinhtong) + "");
                                }
                            });
                            holder.tru.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    int sl1 = Integer.parseInt(holder.soluong.getText().toString());
                                    holder.so = sl1 - 1;
                                    holder.soluong.setText(holder.so + "");
                                    int tinhtong = gioHang.getGiaDT() * holder.so;
                                    holder.gia.setText(formatter.format(tinhtong) + "");
                                }
                            });


                        }

                        @NonNull
                        @Override
                        public GioHangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_giohang, parent, false);
                            GioHangViewHolder viewHolder = new GioHangViewHolder(view);
                            return viewHolder;
                        }
                    };

            recyclerView.setAdapter(adapter);
            adapter.startListening();
//        if (adapter.getItemCount()==0) {
//            recyclerView.setVisibility(View.GONE);
//            emty.setVisibility(View.VISIBLE);
//        }else{
//            recyclerView.setVisibility(View.VISIBLE);
//            emty.setVisibility(View.GONE);
//        }
//        if (adapter.getItemCount() == 0 || recyclerView.getItemDecorationCount()==0){
//            recyclerView.setVisibility(View.GONE);
//            emty.setVisibility(View.VISIBLE);
//        }
    }

    public static class GioHangViewHolder extends RecyclerView.ViewHolder {
        TextView ten,gia,soluong;
        ImageView anh;
        RadioButton select;

        ImageView cong,tru;
        int so;

        public GioHangViewHolder(View view) {
            super(view);

            ten =view.findViewById(R.id.tv_name_giohang);
            gia = view.findViewById(R.id.tv_gia_giohang);
            anh = view.findViewById(R.id.img_giohang);
            soluong = view.findViewById(R.id.soluonggio);
            cong = view.findViewById(R.id.conggio);
            tru = view.findViewById(R.id.trugio);
            select = view.findViewById(R.id.select111);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gio_hang, container, false);
    }
}