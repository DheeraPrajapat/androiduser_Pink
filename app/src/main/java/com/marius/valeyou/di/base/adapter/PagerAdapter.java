package com.marius.valeyou.di.base.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.Collections;
import java.util.List;

/**
 * Use for static number of fragments
 * Fragments are kept in memory
 */
public class PagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;

    public PagerAdapter(FragmentManager fm, @Nullable List<Fragment> fragmentList) {
        super(fm);
        if (fragmentList == null) {
            fragmentList = Collections.emptyList();
        }

        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}

