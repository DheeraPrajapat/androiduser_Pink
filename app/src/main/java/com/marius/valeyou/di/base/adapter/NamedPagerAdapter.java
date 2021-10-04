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
public class NamedPagerAdapter extends FragmentPagerAdapter {
    private List<NavViewItem> navViewItemList;

    public NamedPagerAdapter(FragmentManager fm, @Nullable List<NavViewItem> navViewItemList) {
        super(fm);
        if (navViewItemList == null) {
            navViewItemList = Collections.emptyList();
        }

        this.navViewItemList = navViewItemList;
    }

    @Override
    public Fragment getItem(int position) {
        return navViewItemList.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return navViewItemList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return navViewItemList.get(position).getTitle();
    }

    public static class NavViewItem {
        private String title;
        private Fragment fragment;

        public NavViewItem(String title, Fragment fragment) {
            this.title = title;
            this.fragment = fragment;
        }

        public String getTitle() {
            return title;
        }

        public Fragment getFragment() {
            return fragment;
        }
    }
}
