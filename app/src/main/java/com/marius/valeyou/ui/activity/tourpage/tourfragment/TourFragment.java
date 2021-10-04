package com.marius.valeyou.ui.activity.tourpage.tourfragment;

import android.os.Bundle;

import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.base.TourBean;
import com.marius.valeyou.databinding.FragmentTourBinding;
import com.marius.valeyou.di.base.view.AppFragment;
import com.marius.valeyou.di.base.view.BaseFragment;

import androidx.fragment.app.Fragment;

public class TourFragment extends AppFragment<FragmentTourBinding, TourFragmentVM> {

    private static final String TOUR_BEAN = "tour_bean";

    public static Fragment newInatance(TourBean tourBean) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(TOUR_BEAN, tourBean);
        TourFragment tourFragment = new TourFragment();
        tourFragment.setArguments(bundle);
        return tourFragment;
    }

    @Override
    protected BaseFragment.BindingFragment<TourFragmentVM> getBindingFragment() {
        return new BaseFragment.BindingFragment<>(R.layout.fragment_tour, TourFragmentVM.class);
    }

    @Override
    protected void subscribeToEvents(TourFragmentVM vm) {
        if (getArguments() != null) {
            binding.setBean(getArguments().getParcelable(TOUR_BEAN));
        }
    }
}
