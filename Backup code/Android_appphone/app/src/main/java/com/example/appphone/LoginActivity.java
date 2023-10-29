package com.example.appphone;

import static android.service.controls.ControlsProviderService.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class LoginActivity extends AppCompatActivity {
    TextInputLayout maildn,passdn;
    CircularProgressButton login;
    DatabaseReference databaseReference;
    ArrayList<DangKy> dsls = new ArrayList<DangKy>();
    FirebaseAuth mAuth;
    CallbackManager mCallbackManage;
    TextView quenpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        maildn = findViewById(R.id.textEmail);
        passdn = findViewById(R.id.textPassword);
        login = findViewById(R.id.cirLoginButton);
        quenpass = findViewById(R.id.quenpass);
        mAuth = FirebaseAuth.getInstance();
        mAuth.getInstance().signOut();
        isOnline();

        quenpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                LayoutInflater layoutInflater = ((Activity) LoginActivity.this).getLayoutInflater();
                View v1 = layoutInflater.inflate(R.layout.quenmatkhau, null);
                builder.setView(v1);
                AlertDialog dialog = builder.create();
                dialog.show();

                EditText nhapmail = v1.findViewById(R.id.nhapmailquenpass);
                Button gui = v1.findViewById(R.id.guipass);
                gui.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String emailAddress = nhapmail.getText().toString();

                        mAuth.sendPasswordResetEmail(emailAddress)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(LoginActivity.this, "Đã gửi liên kết đặt mật khẩu về email", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        }
                                    }
                                });
                    }
                });

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DangNhap();
            }
        });
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        mCallbackManage = CallbackManager.Factory.create();
        mCallbackManage = CallbackManager.Factory.create();
        LoginButton loginButton = findViewById(R.id.login_button1);
        loginButton.setReadPermissions("email", "public_profile", "user_friends");
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
                            String name = user.getDisplayName();
                            Intent i = new Intent(LoginActivity.this,Main2Activity.class);
                            i.putExtra("name",name);
                            Toast.makeText(LoginActivity.this, "Đăng Nhập Thành Công", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this,Main2Activity.class));
                            overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
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

    private void DangNhap(){
        String tk = maildn.getEditText().getText().toString();
        String mk = passdn.getEditText().getText().toString();
        SharedPreferences sharedPref = getSharedPreferences("ThongTin", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("email", tk);
        editor.putString("pass", mk);
        editor.apply();

        mAuth.signInWithEmailAndPassword(tk, mk)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Đăng Nhập Thành Công", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this,Main2Activity.class));
                            overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
                        } else {
                            Toast.makeText(LoginActivity.this, "Thông Tin Không Đúng", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void onLoginClick(View View){
        startActivity(new Intent(this,DangKyActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
    }

    private Boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if(ni != null && ni.isConnected()) {
            return true;
        }
        AlertDialog.Builder builder1 = new AlertDialog.Builder(LoginActivity.this);
        builder1.setMessage("Thiết bị chưa kết nối Internet.");
        builder1.setTitle("Lỗi");
        AlertDialog alert11 = builder1.create();
        alert11.show();
        return false;
    }
}