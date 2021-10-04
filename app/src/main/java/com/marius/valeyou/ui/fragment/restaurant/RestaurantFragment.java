package com.marius.valeyou.ui.fragment.restaurant;

import androidx.fragment.app.Fragment;

import com.marius.valeyou.R;
import com.marius.valeyou.databinding.FragmentRestaurantBinding;
import com.marius.valeyou.di.base.view.AppFragment;
import com.marius.valeyou.ui.activity.main.MainActivity;

public class RestaurantFragment extends AppFragment<FragmentRestaurantBinding, RestaurantFragmentVM> {
    public static final String TAG = "RestaurantFragment";
    private MainActivity mainActivity;

    public static Fragment newInstance() {
        return new RestaurantFragment();
    }

    @Override
    protected BindingFragment<RestaurantFragmentVM> getBindingFragment() {
        return new BindingFragment<>(R.layout.fragment_restaurant, RestaurantFragmentVM.class);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void subscribeToEvents(final RestaurantFragmentVM vm) {
        mainActivity = (MainActivity) getActivity();
        mainActivity.hideBottomNav(false);
        // Crashlytics.getInstance().crash(); // Force a crash
        vm.base_click.observe(this, view -> {
            switch (view != null ? view.getId() : 0) {

            }
        });
    }
}
