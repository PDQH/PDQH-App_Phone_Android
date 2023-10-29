package com.example.appphone;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appphone.databinding.ActivityMain2Binding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class Main2Activity extends AppCompatActivity{

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMain2Binding binding;
    ArrayList<DienThoai> ds = new ArrayList<DienThoai>();
    ArrayList<DangKy> dstv = new ArrayList<DangKy>();
    FirebaseAuth firebaseAuth;
    TextView tend,quyenadmin;
    CircleImageView avtlogo;
    int count;
    int countgio;
    int countdon;
    EditText text;
    int vitri=0;
    String getten;
    NavigationView navigationView;
    String tenne;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    Context context = Main2Activity.this;
    TextToSpeech textToSpeech;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();

        String getuid = firebaseAuth.getInstance().getCurrentUser().getUid();
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
         navigationView = binding.navView;
         text = binding.appBarMain.timkiemappbar;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_qlnguoidung,R.id.nav_dienthoai, R.id.nav_qldienthoai,R.id.nav_qldonhang,
                R.id.nav_thongke,R.id.nav_thongbao)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        Menu nav_Menu = navigationView.getMenu();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(vitri);
         tend = (TextView) headerView.findViewById(R.id.tennd);
         avtlogo = headerView.findViewById(R.id.avtlogo);
        TextView quyend = (TextView) headerView.findViewById(R.id.quyennd);
         quyenadmin = (TextView) headerView.findViewById(R.id.quyenadmin);
        ImageView avtlogo = (ImageView) headerView.findViewById(R.id.avtlogo);
        String getemail = firebaseAuth.getInstance().getCurrentUser().getEmail();
        getNguoiDung(getemail);

        quyend.setText(""+getemail);
        nav_Menu.findItem(R.id.nav_qldienthoai).setVisible(false);
        nav_Menu.findItem(R.id.nav_qldonhang).setVisible(false);
        nav_Menu.findItem(R.id.nav_qlnguoidung).setVisible(false);
        nav_Menu.findItem(R.id.nav_thongbao).setVisible(false);
        nav_Menu.findItem(R.id.nav_thongke).setVisible(false);

        Fragment home = new HomeFragment();
        Fragment gio = new GioHangFragment();
        Fragment sp = new DienThoaiFragment();
        Fragment dh = new DonHangCuaToiFragment();
        Fragment tk = new TaiKhoanFragment();

        AHBottomNavigation bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);

// Create items
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("Trang Chủ", R.drawable.trangchu, R.color.white);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("Sản Phẩm", R.drawable.smartphone, R.color.white);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("Giỏ Hàng", R.drawable.giohang, R.color.white);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem("Đơn Hàng", R.drawable.lichsu, R.color.white);
        AHBottomNavigationItem item5 = new AHBottomNavigationItem("Profile", R.drawable.toi, R.color.white);

// Add items
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.addItem(item4);
        bottomNavigation.addItem(item5);
        bottomNavigation.setAccentColor(Color.parseColor("#DD82A1"));
        bottomNavigation.setInactiveColor(Color.parseColor("#F8F8F8"));
// Manage titles
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_HIDE);

// Set current item programmatically
        bottomNavigation.setCurrentItem(0);

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        // get count bảng dienthoai
        database.child("DienThoai").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                count = (int) dataSnapshot.getChildrenCount();
                bottomNavigation.setNotification(count +"", 1);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        //get count bảng giỏ hàng
        database.child("GioHang").child(getuid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot1) {

                countgio =(int)dataSnapshot1.getChildrenCount();
                bottomNavigation.setNotification(countgio +"", 2);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        //get count bảng đơn hang
        database.child("ThongTinDonHang").child(getuid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot2) {

                countdon = (int)dataSnapshot2.getChildrenCount();
                bottomNavigation.setNotification(countdon +"", 3);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
// Add or remove notificati


// Set listeners
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                switch (position) {
                    case 0:
                        chuyenFragment(home);
                        break;
                    case 1:
                        chuyenFragment(sp);
                        break;
                    case 2:
                        chuyenFragment(gio);
                        break;
                    case 3:
                        chuyenFragment(dh);
                        break;
                    case 4:
                        chuyenFragment(tk);
                        break;
                }
                return true;
            }
        });
    }
    private void chuyenFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main, fragment).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        switch (item.getItemId()) {
            case R.id.timkiem:
                String danhap= text.getText().toString();
                Fragment fragment = new SearchFragment();
                FragmentManager fmgr = Main2Activity.this.getSupportFragmentManager();
                Bundle bundle = new Bundle();
                bundle.putString("keyw",danhap);
                fragment.setArguments(bundle);
                FragmentTransaction ft = fmgr.beginTransaction();
                ft.replace(R.id.nav_host_fragment_content_main, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.commit();
                text.setText("");
                break;

            case R.id.mic:
                    ChuyenAmThanhSangVanBan();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void ChuyenAmThanhSangVanBan(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Đang Nghe...");
        try {
            startActivityForResult(intent,REQ_CODE_SPEECH_INPUT);
        }catch (ActivityNotFoundException e){
            Toast.makeText(context, "Thiết bị của bạn không hỗ trợ", Toast.LENGTH_SHORT).show();
        }

    }
    public void  tts(String text){
        textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onInit(int status) {
                if ( status != TextToSpeech.ERROR ){
                    textToSpeech.setLanguage( new Locale("vi_VN") );
                    textToSpeech.setSpeechRate((float) 1.5);
                    textToSpeech.speak(text , TextToSpeech.QUEUE_FLUSH , null);

                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case REQ_CODE_SPEECH_INPUT:{
                if(resultCode == RESULT_OK && data != null){
                    List<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String text1 = result.get(0);
                    text.setText(text1);
                    tts("Đây Là Sản Phẩm "+text1+"Mà Bạn Tìm Kiếm Cảm Ơn Bạn");
                    String danhap= text.getText().toString();
                    Fragment fragment = new SearchFragment();
                    FragmentManager fmgr = Main2Activity.this.getSupportFragmentManager();
                    Bundle bundle = new Bundle();
                    bundle.putString("keyw",danhap);
                    fragment.setArguments(bundle);
                    FragmentTransaction ft = fmgr.beginTransaction();
                    ft.replace(R.id.nav_host_fragment_content_main, fragment);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft.addToBackStack(null);
                    ft.commit();
                    text.setText("");
                }
            }
            break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void getNguoiDung(String mail){
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("NguoiDung").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    DangKy dangKy = dataSnapshot.getValue(DangKy.class);
                    if (dangKy.getEmail().contains(mail)){
                        tend.setText("Xin chào : "+dangKy.getHoTen());
                        quyenadmin.setText(dangKy.getPhanQuyen());
                        byte[] manghinh = Base64.getDecoder().decode(dangKy.getAnh());
                        Bitmap bm = BitmapFactory.decodeByteArray(manghinh, 0, manghinh.length);
                        avtlogo.setImageBitmap(bm);
                        String admin = quyenadmin.getText().toString();
                        if (admin.equalsIgnoreCase("Admin")){
                            navigationView = (NavigationView) findViewById(R.id.nav_view);
                            Menu nav_Menu = navigationView.getMenu();
                            nav_Menu.findItem(R.id.nav_qldienthoai).setVisible(true);
                            nav_Menu.findItem(R.id.nav_qldonhang).setVisible(true);
                            nav_Menu.findItem(R.id.nav_qlnguoidung).setVisible(true);
                            nav_Menu.findItem(R.id.nav_thongbao).setVisible(true);
                            nav_Menu.findItem(R.id.nav_thongke).setVisible(true);
                        }if (!admin.equalsIgnoreCase("Admin")){
                            navigationView = (NavigationView) findViewById(R.id.nav_view);
                            Menu nav_Menu = navigationView.getMenu();
                            nav_Menu.findItem(R.id.nav_qldienthoai).setVisible(false);
                            nav_Menu.findItem(R.id.nav_qldonhang).setVisible(false);
                            nav_Menu.findItem(R.id.nav_qlnguoidung).setVisible(false);
                            nav_Menu.findItem(R.id.nav_thongbao).setVisible(false);
                            nav_Menu.findItem(R.id.nav_thongke).setVisible(false);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}