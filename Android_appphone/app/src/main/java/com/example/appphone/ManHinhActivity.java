package com.example.appphone;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import me.relex.circleindicator.CircleIndicator;

public class ManHinhActivity extends AppCompatActivity {
    private TextView boqua;
    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private LinearLayout lllnext,lllback;
    private ManHinhAdapter manHinhAdapter;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh);
        boqua = findViewById(R.id.tv_skip);
        viewPager = findViewById(R.id.vp_duoi);
        circleIndicator = findViewById(R.id.cd_next);
        lllnext = findViewById(R.id.ll_next);
        lllback = findViewById(R.id.aaa);
        lllback.setVisibility(View.INVISIBLE);
        manHinhAdapter = new ManHinhAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_SET_USER_VISIBLE_HINT);
        viewPager.setAdapter(manHinhAdapter);
        circleIndicator.setViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position ==2){
                    boqua.setVisibility(View.INVISIBLE);
                    lllnext.setVisibility(View.INVISIBLE);
                    lllback.setVisibility(View.VISIBLE);
                }if (position==1){
                    lllback.setVisibility(View.VISIBLE);
                    lllnext.setVisibility(View.VISIBLE);
                    boqua.setVisibility(View.INVISIBLE);
                }if (position==0){
                    lllback.setVisibility(View.INVISIBLE);
                    lllnext.setVisibility(View.VISIBLE);
                    boqua.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        boqua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(2);
            }
        });
        lllnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewPager.getCurrentItem() < 2) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() +1);
                }
            }
        });
        lllback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewPager.getCurrentItem() <= 2) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() -1);
                }
            }
        });
    }
}