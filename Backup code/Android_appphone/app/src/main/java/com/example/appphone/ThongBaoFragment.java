package com.example.appphone;

import static com.facebook.FacebookSdk.getApplicationContext;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ThongBaoFragment extends Fragment {
    ThongBaoAdapter adapter;
    DatabaseReference databaseReference;
    ArrayList<ThongBao> list = new ArrayList<>();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ThongBaoFragment() {
        // Required empty public constructor
    }


    public static ThongBaoFragment newInstance(String param1, String param2) {
        ThongBaoFragment fragment = new ThongBaoFragment();
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

        RecyclerView recyclerView = view.findViewById(R.id.rvtb);
        FloatingActionButton add = view.findViewById(R.id.themtb);
        LinearLayoutManager layoutManager= new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        list.clear();
        getList();
        adapter = new ThongBaoAdapter(getContext(),list);
        recyclerView.setAdapter(adapter);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater layoutInflater = ((Activity)getContext()).getLayoutInflater();
                View v2 = layoutInflater.inflate(R.layout.addtb,null);
                builder.setView(v2);
                AlertDialog dialog = builder.create();
                dialog.show();
                EditText nhaptd = v2.findViewById(R.id.nhaptdtb);
                EditText nhap = v2.findViewById(R.id.nhaptb);
                Button addvao = v2.findViewById(R.id.addtb);
                Button huy = v2.findViewById(R.id.huytb);
                addvao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ThongBao thongBao = new ThongBao(nhaptd.getText().toString(),nhap.getText().toString());
                        databaseReference.child("ThongBao").push().setValue(thongBao);
                        list.clear();
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();
                        thongBao();
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
        super.onViewCreated(view, savedInstanceState);
    }

    private void getList(){
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("ThongBao").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    ThongBao thongBao = dataSnapshot.getValue(ThongBao.class);
                    list.add(thongBao);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void thongBao(){
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("ThongBao").limitToLast(1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ThongBao thongBao = dataSnapshot.getValue(ThongBao.class);
                    String CHANNEL_ID = "channel_id";
                    CharSequence name = "chanel_name";
                    int importance = NotificationManager.IMPORTANCE_DEFAULT;

                    Context context = getApplicationContext();
                    Intent j= new Intent(String.valueOf(context));
                    PendingIntent pe=PendingIntent.getActivity(getApplicationContext(),
                            0, j, PendingIntent.FLAG_CANCEL_CURRENT);

                    Notification builder = new NotificationCompat.Builder(context)
                            .setSmallIcon(R.drawable.logovip2)
                            .setContentTitle(""+thongBao.getTieuDe())
                            .setContentText(""+thongBao.getNoiDung())
                            .setChannelId(CHANNEL_ID)
                            .setContentIntent(pe)
                            .addAction(R.drawable.giohang, "Điện Thoại",pe)
                            .addAction(R.drawable.lichsu, "Lịch Sử",pe)
                            .build();
                    NotificationManager manager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        NotificationChannel mchanel = new NotificationChannel(CHANNEL_ID,name,importance);
                        manager.createNotificationChannel(mchanel);
                    }
                    manager.notify(0,builder);
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
        return inflater.inflate(R.layout.fragment_thong_bao, container, false);
    }
}