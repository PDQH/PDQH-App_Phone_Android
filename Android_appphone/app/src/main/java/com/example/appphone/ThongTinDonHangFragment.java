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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Base64;
import java.util.Calendar;

public class ThongTinDonHangFragment extends Fragment {
    EditText nhapten,nhapdiachi,nhapsdt;
    RadioButton thanhtoan;
    Button xacnhan,trove;
    ImageView anhdt;
    TextView tendt,giadttt;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public ThongTinDonHangFragment() {
        // Required empty public constructor
    }

    public static ThongTinDonHangFragment newInstance(String param1, String param2) {
        ThongTinDonHangFragment fragment = new ThongTinDonHangFragment();
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
        nhapten = view.findViewById(R.id.nhaptenngmua);
        nhapdiachi = view.findViewById(R.id.nhapdiachi);
        nhapsdt = view.findViewById(R.id.nhapsodt);
        xacnhan = view.findViewById(R.id.btn_xacnhan);
        anhdt = view.findViewById(R.id.anhdtthongtin);
        tendt = view.findViewById(R.id.tendtthongtin);
        giadttt = view.findViewById(R.id.tongtienthongtin);
        String getemail = firebaseAuth.getInstance().getCurrentUser().getEmail();
        getNguoiDung(getemail);

        //lấy thông tin từ bundle
        Bundle bundle = this.getArguments();
        String tensp = bundle.getString("tengh");
        int giasp = bundle.getInt("giagh");
        String key = bundle.getString("keygio");
        int daban = bundle.getInt("dabangio");
        int giadt = bundle.getInt("giaspgio");
        int soluong = Integer.parseInt(bundle.getString("soluong"));
        String anhsp = bundle.getString("anhgh");
        tendt.setText(tensp);
        giadttt.setText(""+giasp);
        byte[] manghinh = Base64.getDecoder().decode(anhsp);
        Bitmap bm = BitmapFactory.decodeByteArray(manghinh,0, manghinh.length);
        anhdt.setImageBitmap(bm);

        xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference = FirebaseDatabase.getInstance().getReference();
                String keydh = databaseReference.push().getKey();
                String tenngnhan = nhapten.getText().toString();
                String diachi = nhapdiachi.getText().toString();
                int sdt = Integer.parseInt(nhapsdt.getText().toString());
                String trangthai = "Chờ Xác Nhận";
                String uid = firebaseAuth.getInstance().getCurrentUser().getUid();
                Calendar c = Calendar.getInstance();
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                int mMonth = c.get(Calendar.MONTH)+1;
                int mYear = c.get(Calendar.YEAR);

                ThongTinDonHang donHang = new ThongTinDonHang(keydh,uid,key,tenngnhan,diachi,sdt,tensp,giasp,soluong,anhsp,trangthai,mDay,mMonth,mYear,giadt);

                databaseReference.child("DienThoai").child(key).child("daBan").setValue(daban+1);
                databaseReference.child("ThongTinDonHang").child(uid).child(keydh).setValue(donHang);
                databaseReference.child("ThongKe").child(keydh).setValue(donHang);
                databaseReference.child("ThongTinDonHang").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Toast.makeText(getContext(), "Thanh Toán Thành Công", Toast.LENGTH_SHORT).show();
                        Fragment fragment = new DonHangCuaToiFragment();
                        FragmentManager fmgr =getActivity().getSupportFragmentManager();
                        FragmentTransaction ft = fmgr.beginTransaction();
                        ft.replace(R.id.nav_host_fragment_content_main, fragment);
                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                        ft.addToBackStack(null);
                        ft.commit();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });





        super.onViewCreated(view, savedInstanceState);

    }

    public void getNguoiDung(String mail){
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("NguoiDung").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    DangKy dangKy = dataSnapshot.getValue(DangKy.class);
                    if (dangKy.getEmail().contains(mail)){
                        nhapten.setText(""+dangKy.getHoTen());
                        nhapdiachi.setText(dangKy.getDiaChi());
                        nhapsdt.setText(""+dangKy.getSDT());
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
        return inflater.inflate(R.layout.fragment_thongtin, container, false);
    }
}