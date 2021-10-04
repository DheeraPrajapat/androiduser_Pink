package com.marius.valeyou.ui.fragment.restaurant;

import androidx.databinding.ObservableField;

import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.data.remote.helper.NetworkErrorHandler;
import com.marius.valeyou.di.base.viewmodel.BaseFragmentViewModel;
import com.marius.valeyou.util.event.SingleActionEvent;

import javax.inject.Inject;

public class RestaurantFragmentVM extends BaseFragmentViewModel {

    private final SharedPref sharedPref;
    private final NetworkErrorHandler networkErrorHandler;
    final SingleActionEvent<Integer> passIntent = new SingleActionEvent<>();
    public final ObservableField<String> field_Player1 = new ObservableField<>();

    @Inject
    public RestaurantFragmentVM(SharedPref sharedPref, NetworkErrorHandler networkErrorHandler) {
        this.sharedPref = sharedPref;
        this.networkErrorHandler = networkErrorHandler;
    }
}
