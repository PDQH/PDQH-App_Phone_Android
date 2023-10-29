package com.example.appphone;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Base64;

import de.hdodenhof.circleimageview.CircleImageView;

public class TaiKhoanFragment extends Fragment {
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    ArrayList<DangKy> list = new ArrayList<>();
    RecyclerView recyclerView;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TaiKhoanFragment() {
        // Required empty public constructor
    }

    public static TaiKhoanFragment newInstance(String param1, String param2) {
        TaiKhoanFragment fragment = new TaiKhoanFragment();
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

        recyclerView = view.findViewById(R.id.rvtk);
        LinearLayoutManager layoutManager= new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);


    }
    @Override
    public void onStart() {
        String getemail = firebaseAuth.getInstance().getCurrentUser().getEmail();
        FirebaseRecyclerOptions<DangKy> options =
                new FirebaseRecyclerOptions.Builder<DangKy>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("NguoiDung").orderByChild("email").equalTo(getemail)
                                , DangKy.class)
                        .build();


        FirebaseRecyclerAdapter<DangKy, TaiKhoanViewHolder> adapter =
                new FirebaseRecyclerAdapter<DangKy, TaiKhoanViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(TaiKhoanViewHolder holder, @SuppressLint("RecyclerView") int i, @NonNull DangKy dangKy ){

                        holder.tennd.setText(dangKy.getHoTen());
                        byte[] manghinh = Base64.getDecoder().decode(dangKy.getAnh());
                        Bitmap bm = BitmapFactory.decodeByteArray(manghinh, 0, manghinh.length);
                        holder.anh.setImageBitmap(bm);
                        holder.diadiem.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent i=new Intent(getContext(),BanDoActivity.class);
                                startActivity(i);
                            }
                        });
                        holder.tb.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Fragment fragment = new ThongBaoNguoiDungFragment();
                                FragmentManager fmgr = getActivity().getSupportFragmentManager();
                                FragmentTransaction ft = fmgr.beginTransaction();
                                ft.replace(R.id.nav_host_fragment_content_main, fragment);
                                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                                ft.addToBackStack(null);
                                ft.commit();
                            }
                        });

                        holder.lichsu.setOnClickListener(new View.OnClickListener() {
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
                        holder.editprofile.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Fragment fragment = new ChinhSuaThongTinFragment();
                                FragmentManager fmgr = getActivity().getSupportFragmentManager();
                                Bundle bundle = new Bundle();
                                bundle.putString("emailndne",dangKy.getEmail());
                                bundle.putInt("sdtnd",dangKy.getSDT());
                                bundle.putString("hotennd",dangKy.getHoTen());
                                bundle.putString("diachind",dangKy.getDiaChi());
                                bundle.putString("anhnd",dangKy.getAnh());
                                bundle.putString("idnd",dangKy.getId());
                                bundle.putString("quyennd",dangKy.getPhanQuyen());
                                bundle.putString("passnd",dangKy.getPassword());
                                fragment.setArguments(bundle);
                                FragmentTransaction ft = fmgr.beginTransaction();
                                ft.replace(R.id.nav_host_fragment_content_main, fragment);
                                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                                ft.addToBackStack(null);
                                ft.commit();
                            }
                        });

                        holder.doimk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                LayoutInflater layoutInflater = ((Activity) getContext()).getLayoutInflater();
                                View v1 = layoutInflater.inflate(R.layout.doimatkhau, null);
                                builder.setView(v1);
                                AlertDialog dialog = builder.create();
                                dialog.show();
                                EditText  matkhaucu , matkhaumoi ,nhaplaimkmoi;
                                Button capnhat , huy ;
                                matkhaumoi = v1.findViewById(R.id.mkmoi);
                                matkhaucu = v1.findViewById(R.id.mkcu);
                                nhaplaimkmoi = v1.findViewById(R.id.nhaplaimkmoi);
                                capnhat=v1.findViewById(R.id.capnhatmkmoi);
                                huy = v1.findViewById(R.id.huydoimk);
                                SharedPreferences sharedPref = getContext().getSharedPreferences("ThongTin", MODE_PRIVATE);
                                String  laymkcu=sharedPref.getString("pass","");
                                capnhat.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        String mkmoi=matkhaumoi.getText().toString();
                                        String nlmk=nhaplaimkmoi.getText().toString();
                                        if (laymkcu.equalsIgnoreCase(matkhaucu.getText().toString())){
                                            if (mkmoi.equalsIgnoreCase(nlmk)){
                                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                                user.updatePassword(mkmoi);


                                                databaseReference = FirebaseDatabase.getInstance().getReference();
                                                databaseReference.child("NguoiDung").child(dangKy.getId()).child("password").setValue(matkhaumoi.getText().toString() );
                                                databaseReference.addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        Toast.makeText(getContext() , "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                                        dialog.dismiss();
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });


                                            }
                                            else {
                                                Toast.makeText(getContext(), "Mật khẩu không trùng", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        else {
                                            Toast.makeText(getContext(), "Mật khẩu cũ không đúng", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                huy.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                    }
                                });

                            }
                        });

                        holder.logout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                firebaseAuth.getInstance().signOut();
                                Intent i = new Intent(getContext(),LoginActivity.class);
                                startActivity(i);
                            }
                        });


                    }

                    @NonNull
                    @Override
                    public TaiKhoanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.thongtin, parent, false);
                        return new TaiKhoanViewHolder(view);
                    }
                };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
        super.onStart();
    }
    public class TaiKhoanViewHolder extends RecyclerView.ViewHolder {
        TextView logout,editprofile,tennd,doimk,diadiem,tb,lichsu;
        CircleImageView anh;
        public TaiKhoanViewHolder(View view) {
            super(view);
            tennd = view.findViewById(R.id.tennd);
            logout = view.findViewById(R.id.tv_logout);
            doimk = view.findViewById(R.id.tv_doimk);
            editprofile = view.findViewById(R.id.tv_editpro);
            anh = view.findViewById(R.id.imganh);
            lichsu = view.findViewById(R.id.tv_order);
            tb = view.findViewById(R.id.tv_notifi);
            diadiem = view.findViewById(R.id.tv_shipping);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.taikhoanfragment, container, false);
    }
}