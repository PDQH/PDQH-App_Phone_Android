package com.example.appphone;

import static android.service.controls.ControlsProviderService.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class DangKyActivity extends AppCompatActivity {
    TextInputLayout tendk,maildk,passdk;
    CircularProgressButton login;
    DatabaseReference databaseReference;
    CallbackManager mCallbackManage;
    ArrayList<DangKy> dsls = new ArrayList<DangKy>();
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        mAuth = FirebaseAuth.getInstance();
        tendk = findViewById(R.id.textInputName);
        maildk = findViewById(R.id.textInputEmail);
        passdk = findViewById(R.id.textInputPassword);
        login = findViewById(R.id.cirRegisterButton);
        mAuth = FirebaseAuth.getInstance();
        isOnline();
        getlist();
        changeStatusBarColor();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DangKy();

            }
        });
                mCallbackManage = CallbackManager.Factory.create();
                mCallbackManage = CallbackManager.Factory.create();
                LoginButton loginButton = findViewById(R.id.login_button);
                loginButton.setReadPermissions("email", "public_profile");
                loginButton.registerCallback(mCallbackManage, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d(TAG, "facebook:onSuccess:" + loginResult);
                        handleFacebookAccessToken(loginResult.getAccessToken());
                        mAuth.getInstance().signOut();
                    }

                    @Override
                    public void onCancel() {
                        Log.d(TAG, "facebook:onCancel");
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d(TAG, "facebook:onError", error);
                    }
                });

    }
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = mAuth.getCurrentUser();
                            String email = user.getUid();
                            String name = user.getDisplayName();
                            String mk = "";
                            String quyen = "Khách Hàng";
                            String id = String.valueOf(dsls.size()+1);
                            DangKy dangKy = new DangKy(id,name,email,"",0,mk,quyen,"");
                            databaseReference = FirebaseDatabase.getInstance().getReference();
                            databaseReference.child("NguoiDung").push().setValue(dangKy);
                            Toast.makeText(DangKyActivity.this, "Đăng Ký Thành Công", Toast.LENGTH_SHORT).show();
                            mAuth.getInstance().signOut();
                            startActivity(new Intent(DangKyActivity.this,LoginActivity.class));
                            overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
                            mAuth.getInstance().signOut();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(DangKyActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManage.onActivityResult(requestCode, resultCode, data);
        mAuth.getInstance().signOut();
    }
    private void DangKy(){
        databaseReference = FirebaseDatabase.getInstance().getReference();
        String tk = maildk.getEditText().getText().toString();
        String mk = passdk.getEditText().getText().toString();
        String hoten = tendk.getEditText().getText().toString();
        String quyen = "Khách Hàng";
        String uid = databaseReference.push().getKey();
        String id = uid;
        DangKy dangKy = new DangKy(id,hoten,tk,"",0,mk,quyen,"");

        databaseReference.child("NguoiDung").child(uid).setValue(dangKy);
        //Authentication
        mAuth.createUserWithEmailAndPassword(tk, mk)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(DangKyActivity.this, "Đăng Ký Thành Công, Vui Lòng Đăng Nhập", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(DangKyActivity.this,LoginActivity.class));
                            overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
                        } else {
                            Toast.makeText(DangKyActivity.this, "Đăng Ký Thất Bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void getlist(){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("NguoiDung");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    DangKy dangKy = dataSnapshot.getValue(DangKy.class);
                    dsls.add(dangKy);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));
        }
    }
    public void onLoginClick(View view){
        startActivity(new Intent(this,LoginActivity.class));
        overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
    }
    private Boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if(ni != null && ni.isConnected()) {
            return true;
        }
        AlertDialog.Builder builder1 = new AlertDialog.Builder(DangKyActivity.this);
        builder1.setMessage("Thiết bị chưa kết nối Internet.");
        builder1.setTitle("Lỗi");
        AlertDialog alert11 = builder1.create();
        alert11.show();
        return false;
    }
}