package com.marius.valeyou.di.base.view;

import android.app.ProgressDialog;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import android.os.Bundle;
import androidx.annotation.CallSuper;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marius.valeyou.BR;
import com.marius.valeyou.BuildConfig;
import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.signup.SignupData;
import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.di.base.viewmodel.BaseFragmentViewModel;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public abstract class BaseFragment<B extends ViewDataBinding, V extends BaseFragmentViewModel> extends DaggerFragment  {

    @Inject
    SharedPref sharedPref;
    protected SignupData userBean;
    @Inject
    public ViewModelProvider.Factory viewModelFactory;

    protected V viewModel;
    protected B binding;
    protected Context baseContext;

    private ProgressDialog progressDialog;

    protected abstract BindingFragment<V> getBindingFragment();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.baseContext = context;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dismissProgressDialog();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @CallSuper
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        BindingFragment<V> bindingFragment = getBindingFragment();
        if (bindingFragment == null) {
            throw new NullPointerException("Binding fragment cannot be null");
        }

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(bindingFragment.getClazz());

        binding = DataBindingUtil.inflate(inflater, bindingFragment.getLayoutResId(), container, false);
        binding.setVariable(BR.vm, viewModel);
        userBean = sharedPref.getUserData();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        subscribeToEvents(viewModel);
    }



    protected abstract void subscribeToEvents(V vm);

    /**
     * Show progress dialog
     *
     * @param titleResId
     * @param msgResId
     */
    protected final void showProgressDialog(@StringRes int titleResId, @StringRes int msgResId) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
        }

        progressDialog.setTitle(titleResId);
        progressDialog.setMessage(getString(msgResId));
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    /**
     * Show progress dialog
     *
     * @param msgResId
     */
    protected final void showProgressDialog(@StringRes int msgResId) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
        }

        progressDialog.setMessage(getString(msgResId));
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    /**
     * Dismiss progress dialog
     */
    protected final void dismissProgressDialog() {
        if (progressDialog != null && this.progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        progressDialog = null;
    }

    protected void startNewActivity(Intent intent, boolean finishExisting) {
        try {
            startActivity(intent);
            if (finishExisting)
                getActivity().finish();
            if (BuildConfig.EnableAnim)
                getActivity().overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void startNewActivity(Intent intent, boolean ainmate, boolean finishExisting) {
        try {
            startActivity(intent);
            if (finishExisting)
                getActivity().finish();
            if (BuildConfig.EnableAnim && ainmate)
                getActivity().overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void animateActivity() {
        if (BuildConfig.EnableAnim)
            getActivity().overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
    }

    protected void startNewActivity(Intent intent) {
        startNewActivity(intent, false);
    }

    protected static class BindingFragment<V extends BaseFragmentViewModel> {
        @LayoutRes
        private int layoutResId;
        private Class<V> clazz;

        public BindingFragment(@LayoutRes int layoutResId, Class<V> clazz) {
            this.layoutResId = layoutResId;
            this.clazz = clazz;
        }

        public int getLayoutResId() {
            return layoutResId;
        }

        public Class<V> getClazz() {
            return clazz;
        }
    }

}
