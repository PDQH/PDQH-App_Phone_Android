package com.example.appphone;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

public class ChinhSuaThongTinFragment extends Fragment {
    EditText dtcu , dtmoi, lsm , diachimoi;
    Button suaa ,xoaa;
    ImageView themanh;
    DangKy dangKy;
    DatabaseReference databaseReference;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public ChinhSuaThongTinFragment() {
        // Required empty public constructor
    }

    public static ChinhSuaThongTinFragment newInstance(String param1, String param2) {
        ChinhSuaThongTinFragment fragment = new ChinhSuaThongTinFragment();
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
    public void onViewCreated(@NonNull View v2, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v2, savedInstanceState);
        dtcu =v2.findViewById(R.id.sdtcu);
        dtmoi = v2.findViewById(R.id.sdtmoi);
        lsm = v2.findViewById(R.id.nhaplaisdt);
        diachimoi = v2.findViewById(R.id.doiiachi);
        suaa = v2.findViewById(R.id.capnhatls);
        xoaa = v2.findViewById(R.id.huyls);
        themanh = v2.findViewById(R.id.udanhnd);

        Bundle bundle = this.getArguments();
        String email = bundle.getString("emailndne");
        int sdt = bundle.getInt("sdtnd");
        String ten = bundle.getString("hotennd");
        String diachi = bundle.getString("diachind");
        String anh = bundle.getString("anhnd");
        String id = bundle.getString("idnd");
        String pass = bundle.getString("passnd");
        String quyen = bundle.getString("quyennd");
        dtcu.setText(email);
        dtmoi.setText(""+sdt);
        lsm.setText(ten);
        diachimoi.setText(diachi);
        themanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pick=new Intent(Intent.ACTION_GET_CONTENT);
                pick.setType("image/*");
                Intent pho=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Intent chosser=Intent.createChooser(pick, "Lựa Chọn");
                chosser.putExtra(Intent.EXTRA_INITIAL_INTENTS,new Intent[]{pho});
                startActivityForResult(chosser, 999);
            }
        });
        suaa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int sdtmoi = Integer.parseInt(dtmoi.getText().toString());
                String tenmoi = lsm.getText().toString();
                String diachimoine = diachimoi.getText().toString();
                byte[] anh1=ImageView_To_Byte(themanh);
                String anhmoi = Base64.getEncoder().encodeToString(anh1);
                String layemail = dtcu.getText().toString();

                DangKy dangKy1 = new DangKy(id,tenmoi,layemail,diachimoine,sdtmoi,pass,quyen,anhmoi);
                databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child("NguoiDung").child(id).setValue(dangKy1, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        user.updateEmail(layemail);
                        Toast.makeText(getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        Fragment fragment = new TaiKhoanFragment();
                        FragmentManager fmgr = getActivity().getSupportFragmentManager();
                        FragmentTransaction ft = fmgr.beginTransaction();
                        ft.replace(R.id.nav_host_fragment_content_main, fragment);
                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                        ft.addToBackStack(null);
                        ft.commit();
                    }
                });


            }
        });
        xoaa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new TaiKhoanFragment();
                FragmentManager fmgr = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fmgr.beginTransaction();
                ft.replace(R.id.nav_host_fragment_content_main, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    }

    public byte[] ImageView_To_Byte(ImageView imgv){

        BitmapDrawable drawable = (BitmapDrawable) imgv.getDrawable();
        Bitmap bmp = drawable.getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999 && resultCode == RESULT_OK) {

            if (data.getExtras() != null) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                themanh.setImageBitmap(imageBitmap);
            } else {
                Uri uri = data.getData();
                themanh.setImageURI(uri);
            }

        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.doisdt, container, false);
    }
}