package com.marius.valeyou.di.base.view;

import android.app.Activity;
import android.app.ProgressDialog;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import androidx.annotation.CallSuper;
import androidx.annotation.LayoutRes;
import androidx.annotation.StringRes;
import android.view.MenuItem;

import com.marius.valeyou.BuildConfig;
import com.marius.valeyou.R;
import com.marius.valeyou.data.beans.signup.SignupData;
import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.di.base.viewmodel.BaseActivityViewModel;
import com.marius.valeyou.util.theme.ThemeUtils;

import java.util.Locale;

import javax.inject.Inject;
import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseActivity<B extends ViewDataBinding, V extends BaseActivityViewModel> extends DaggerAppCompatActivity {
    @Inject
    SharedPref sharedPref;
    protected SignupData userBean;
    @Inject
    public ViewModelProvider.Factory viewModelFactory;
    protected V viewModel;
    protected B binding;

    private ProgressDialog progressDialog;
    private int floadStyle;

    // place this here so that it will be on top of the activity
    protected abstract BindingActivity<V> getBindingActivity();


    @CallSuper
    @Override
    protected void onResume() {
        super.onResume();
        String lang = sharedPref.get("language","en");
        setLocale(this,lang);
    }



    public static void setLocale(Activity activity, String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Resources resources = activity.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());


    }

    @CallSuper
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeUtils.getInstance(this).apply();
        BindingActivity<V> bindingActivity = getBindingActivity();
        if (bindingActivity == null) {
            throw new NullPointerException("Binding activity cannot be null");
        }
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(bindingActivity.getClazz());
        binding = DataBindingUtil.setContentView(this, bindingActivity.getLayoutResId());
        binding.setVariable(com.marius.valeyou.BR.vm, viewModel);
        userBean = sharedPref.getUserData();
        subscribeToEvents(viewModel);

    }

    protected void changeAppTheme(ThemeUtils.Theme theme){
        if(ThemeUtils.getInstance(this).setCurrentTheme(theme)){
            recreate();
        }
    }


    protected abstract void subscribeToEvents(V vm);

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * Show progress dialog
     *
     * @param titleResId
     * @param msgResId
     */
    protected final void showProgressDialog(@StringRes int titleResId, @StringRes int msgResId) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
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
            progressDialog = new ProgressDialog(this);
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


    /**
     * Activity build
     *
     * @param <V>
     */
    protected static class BindingActivity<V extends BaseActivityViewModel> {
        @LayoutRes
        private int layoutResId;
        private Class<V> clazz;

        public BindingActivity(@LayoutRes int layoutResId, Class<V> clazz) {
            this.layoutResId = layoutResId;
            this.clazz = clazz;
        }

        int getLayoutResId() {
            return layoutResId;
        }

        Class<V> getClazz() {
            return clazz;
        }
    }


    protected void startNewActivity(Intent intent, boolean finishExisting) {
        startNewActivity(intent, finishExisting, true);
    }

    protected void startNewActivity(Intent intent, boolean finishExisting, boolean animate) {
        startActivity(intent);
        if (finishExisting)
            finish();
        if (animate)
            animateActivity();
    }

    protected void startNewActivity(Intent intent) {
        startNewActivity(intent, false, true);
    }


    protected void onBackPressed(boolean animate) {
        super.onBackPressed();
        if (BuildConfig.EnableAnim && animate)
            overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);

    }

    public void animateActivity() {
        if (BuildConfig.EnableAnim)
            overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
    }

    protected void finish(boolean animate) {
        finish();
        if (BuildConfig.EnableAnim && animate)
            overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
    }
}