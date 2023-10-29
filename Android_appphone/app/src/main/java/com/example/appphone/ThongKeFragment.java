package com.example.appphone;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;


public class ThongKeFragment extends Fragment {
    BarChart mBarChart;
    DatabaseReference databaseReference;
    DecimalFormat formatter = new DecimalFormat("###,###,###");
    TextView tongdoanhthu,tongdonhang,ngaybd,ngaykt,tongtv,tongsp,tinhtong,sobl;
    Spinner chonthang;
    SimpleDateFormat spfm1 = new SimpleDateFormat("ddMMyyyy");
    int mYear;
    int mMonth;
    int mDay;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ThongKeFragment() {
        // Required empty public constructor
    }

    public static ThongKeFragment newInstance(String param1, String param2) {
        ThongKeFragment fragment = new ThongKeFragment();
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
        tongdoanhthu = view.findViewById(R.id.tongdoanhthu);
        tongdonhang = view.findViewById(R.id.tongdonhang);
        tongtv = view.findViewById(R.id.tongthanhvien);
        sobl = view.findViewById(R.id.sobinhluan);
        tinhtong = view.findViewById(R.id.tinhtong);
        tongsp = view.findViewById(R.id.tongsanpham);
        ngaybd = view.findViewById(R.id.thang);
        chonthang = view.findViewById(R.id.chonthang);
        getDuLieu();
        getthanhvien();
        getsanpham();
        getbinhluan();
        String[] arrTT = {"0","2021","2022","2023","2024","2025","2026","2027","2028","2029","2030"};
        ArrayAdapter<String> spnadapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,arrTT);
        chonthang.setAdapter(spnadapter);
        chonthang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ngaybd.setText(""+arrTT[i]);
                int namlay = Integer.parseInt(ngaybd.getText().toString());
                if (namlay == 0){
                    getDuLieu();
                }
                    tinhTheoNgay(namlay);
                    mBarChart.clearChart();
                    tinhTheoThang(1,namlay);
                    tinhTheoThang(2,namlay);
                    tinhTheoThang(3,namlay);
                    tinhTheoThang(4,namlay);
                    tinhTheoThang(5,namlay);
                    tinhTheoThang(6,namlay);
                    tinhTheoThang(7,namlay);
                    tinhTheoThang(8,namlay);
                    tinhTheoThang(9,namlay);
                    tinhTheoThang(10,namlay);
                    tinhTheoThang(11,namlay);
                    tinhTheoThang(12,namlay);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        tinhtong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDuLieu();
            }
        });




         mBarChart = (BarChart) view.findViewById(R.id.barchart);
    }


    private void getDuLieu(){
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("ThongTinDonHang").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int tong = 0;
                for (DataSnapshot dataSnapshot:snapshot.getChildren() ){
                    tongdonhang.setText(""+dataSnapshot.getChildrenCount()+" Đơn Hàng");
                    for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren() ) {
                        ThongTinDonHang donHang = dataSnapshot1.getValue(ThongTinDonHang.class);
                        tong += donHang.getGiaSP();
                        Log.d("Tong Doanh Thu", String.valueOf(tong));
                        tongdoanhthu.setText("" + formatter.format(tong) + " VNĐ");

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void tinhTheoNgay(int namne){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("ThongKe");
        Query query = databaseReference.orderByChild("nam").equalTo(namne);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int tong1 = 0;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        ThongTinDonHang donHang = dataSnapshot.getValue(ThongTinDonHang.class);
                        tong1 += donHang.getGiaSP();
                        Log.d("Tong Doanh Thu Theo Nam", String.valueOf(tong1));
                        tongdoanhthu.setText("" + formatter.format(tong1) + " VNĐ");
                    }
                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void tinhTheoThang(int thang,int nam){

        databaseReference = FirebaseDatabase.getInstance().getReference().child("ThongKe");
        Query query = databaseReference.orderByChild("thang").equalTo(thang);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int tongthang12=0;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ThongTinDonHang donHang = dataSnapshot.getValue(ThongTinDonHang.class);
                    if (nam == donHang.getNam()){
                        tongthang12 += donHang.getGiaSP();

                    }else {
                        tongthang12 = 0;
                    }
                }
                if (thang==1){
                    mBarChart.addBar(new BarModel("1", tongthang12, 0xff663397));
                    Log.d("Tong Doanh Thu Theo Thang 1", String.valueOf(tongthang12));
                }
                else if (thang==2){
                    mBarChart.addBar(new BarModel("2", tongthang12, 0xff4183d7));
                    Log.d("Tong Doanh Thu Theo Thang 2", String.valueOf(tongthang12));
                }
                else if (thang==3){
                    mBarChart.addBar(new BarModel("3", tongthang12, 0xff19b5fe));
                    Log.d("Tong Doanh Thu Theo Thang 3", String.valueOf(tongthang12));
                }
                else if (thang==4){
                    mBarChart.addBar(new BarModel("4", tongthang12, 0xff1e8bc3));
                    Log.d("Tong Doanh Thu Theo Thang 4", String.valueOf(tongthang12));
                }
                else if (thang==5){
                    mBarChart.addBar(new BarModel("5", tongthang12, 0xff36d7b7));
                    Log.d("Tong Doanh Thu Theo Thang 5", String.valueOf(tongthang12));
                }
                else if (thang==6){
                    mBarChart.addBar(new BarModel("6", tongthang12, 0xff663397));
                    Log.d("Tong Doanh Thu Theo Thang 6", String.valueOf(tongthang12));
                }
                else if (thang==7){
                    mBarChart.addBar(new BarModel("7", tongthang12, 0xff4183d7));
                    Log.d("Tong Doanh Thu Theo Thang 7", String.valueOf(tongthang12));
                }
                else if (thang==8){
                    mBarChart.addBar(new BarModel("8", tongthang12, 0xff19b5fe));
                    Log.d("Tong Doanh Thu Theo Thang 8", String.valueOf(tongthang12));
                }
                else if (thang==9){
                    mBarChart.addBar(new BarModel("9", tongthang12, 0xff1e8bc3));
                    Log.d("Tong Doanh Thu Theo Thang 9", String.valueOf(tongthang12));
                }
                else if (thang==10){
                    mBarChart.addBar(new BarModel("10", tongthang12, 0xff36d7b7));
                    Log.d("Tong Doanh Thu Theo Thang 10", String.valueOf(tongthang12));
                }
                else if (thang==11){
                    mBarChart.addBar(new BarModel("11", tongthang12, 0xff1e8bc3));
                    Log.d("Tong Doanh Thu Theo Thang 11", String.valueOf(tongthang12));
                }
                else{
                    mBarChart.addBar(new BarModel("12", tongthang12, 0xff19b5fe));
                    Log.d("Tong Doanh Thu Theo Thang 12", String.valueOf(tongthang12));
                }
                mBarChart.startAnimation();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getthanhvien(){
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("NguoiDung").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tongtv.setText(""+snapshot.getChildrenCount()+" Thành Viên");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void getsanpham(){
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("DienThoai").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tongsp.setText(""+snapshot.getChildrenCount()+" Sản Phẩm");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getbinhluan(){
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("DienThoai").child("BinhLuan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    sobl.setText("Lượt Đánh Giá: "+snapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_thongke1, container, false);
    }
}