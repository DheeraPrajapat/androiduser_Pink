package com.marius.valeyou.market_place.Drawer.MyAds;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class Vp_Adp extends FragmentPagerAdapter {

    public List<Fragment> fragments = new ArrayList<>();
    public List<String> titles = new ArrayList<>();

    public Vp_Adp(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    public void addfragment(Fragment f, String title){
        fragments.add(f);
        titles.add(title);
    }

}
