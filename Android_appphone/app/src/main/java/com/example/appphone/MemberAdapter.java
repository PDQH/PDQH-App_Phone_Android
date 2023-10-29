package com.example.appphone;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

public class MemberAdapter extends FirebaseRecyclerAdapter<DangKy,MemberAdapter.ThanhVienViewHolder> {
    DatabaseReference databaseReference;

    public MemberAdapter(FirebaseRecyclerOptions<DangKy> options) {
        super(options);
    }

    class ThanhVienViewHolder extends RecyclerView.ViewHolder {
        TextView usern, pquyen;
        ImageView edit, delete;
        CardView cardView;

        public ThanhVienViewHolder(View view) {
            super(view);

            usern = view.findViewById(R.id.idls);
            pquyen = view.findViewById(R.id.tenls);
            edit = view.findViewById(R.id.editls);
            delete = view.findViewById(R.id.deletels);
            cardView = view.findViewById(R.id.cardviewloai2);


        }
    }

    @Override
    protected void onBindViewHolder(ThanhVienViewHolder holder, @SuppressLint("RecyclerView") int i, DangKy dangKy) {
        holder.usern.setText("Tên : " + dangKy.getHoTen());
        holder.pquyen.setText("Email : " + dangKy.getEmail());

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogPlus dialogPlus = DialogPlus.newDialog(v.getContext())
                        .setContentHolder(new ViewHolder(R.layout.updatetv))
                        .setExpanded(true, 850)
                        .create();
                View v1 = dialogPlus.getHolderView();
                dialogPlus.show();
                TextView tvls;
                EditText nhaptenls, nhapemail, nhapmk;

                Button capnhat, huy;
                tvls = v1.findViewById(R.id.tvudls);
                nhaptenls = v1.findViewById(R.id.nhaptenls);
                nhapemail = v1.findViewById(R.id.nhapemaills);
                nhapmk = v1.findViewById(R.id.nhapmk);
                capnhat = v1.findViewById(R.id.capnhatls);
                huy = v1.findViewById(R.id.huyls);
                nhaptenls.setText(dangKy.getHoTen());
                nhapemail.setText(dangKy.getEmail());
                nhapmk.setText(dangKy.getPassword());

                //update du lieu tu dialog
                capnhat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dangKy.setHoTen(nhaptenls.getText().toString());
                        dangKy.setEmail(nhapemail.getText().toString());
                        dangKy.setPassword(nhapmk.getText().toString());

                        databaseReference = FirebaseDatabase.getInstance().getReference();
                        databaseReference.child("NguoiDung").child(getRef(i).getKey()).updateChildren(dangKy.toMap(), new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                Toast.makeText(v1.getContext(), "Cập Nhật Thành Công", Toast.LENGTH_SHORT).show();
                                dialogPlus.dismiss();

                            }
                        });

                    }
                });
                huy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogPlus.dismiss();
                    }
                });

            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(v.getContext());
                builder1.setMessage("Bạn Có Chắc Chắn Muốn Xóa?");
                builder1.setCancelable(true);
                builder1.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        databaseReference = FirebaseDatabase.getInstance().getReference();
                        databaseReference.child("NguoiDung").child(getRef(i).getKey()).removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                Toast.makeText(builder1.getContext(), "Xóa Thành Công", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });

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
    }

    @Override
    public ThanhVienViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.two_item, parent, false);
        return new ThanhVienViewHolder(view);
    }
}
