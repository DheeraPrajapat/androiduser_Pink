package com.marius.valeyou.di.base.adapter;

import com.marius.valeyou.data.beans.base.TourBean;
import com.marius.valeyou.ui.activity.tourpage.tourfragment.TourFragment;

import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PagerAdapterList extends FragmentPagerAdapter {

    private List<TourBean> tourList;

    public PagerAdapterList(@NonNull FragmentManager fm, List<TourBean> tourBeans) {
        super(fm);
        if (tourList == null) {
            tourList = Collections.emptyList();
        }
        this.tourList = tourBeans;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return TourFragment.newInatance(tourList.get(position));
    }

    @Override
    public int getCount() {
        return tourList.size();
    }
}
