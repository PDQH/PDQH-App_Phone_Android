package com.example.appphone;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ManHinhAdapter extends FragmentStatePagerAdapter {
    public ManHinhAdapter(FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new GioiThieu1Fragment();
            case 1:
                return new GioiThieu2Fragment();
            case 2:
                return new GioiThieu3Fragment();
            default:
                return new GioiThieu1Fragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
