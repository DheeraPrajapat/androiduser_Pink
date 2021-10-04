package com.marius.valeyou.di.base.adapter;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.Collections;
import java.util.List;

/**
 * Use for dynamic number of fragments
 * Fragments are destroyed
 */
public class StatePagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragmentList;

    public StatePagerAdapter(FragmentManager fm, @Nullable List<Fragment> fragmentList) {
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
