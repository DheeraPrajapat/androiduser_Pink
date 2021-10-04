package com.marius.valeyou.di.base.view;

import android.os.Bundle;
import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;

import android.text.TextUtils;


import com.marius.valeyou.data.beans.ConnectionBean;
import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.di.base.viewmodel.BaseActivityViewModel;
import com.marius.valeyou.util.event.SingleMessageEvent;
import com.marius.valeyou.util.misc.ConnectionHandler;
import com.marius.valeyou.util.view.MessageUtils;
import com.marius.valeyou.util.view.NoInternetSheet;

import javax.inject.Inject;

public abstract class AppActivity<B extends ViewDataBinding, V extends BaseActivityViewModel> extends BaseActivity<B, V> {
    @Inject
    public SharedPref sharedPref;
    @Inject
    ConnectionHandler connectionHandler;
    private boolean showInternet = true;
    @Nullable
    private NoInternetSheet noInternetSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subscribeBaseEvents();
    }

    protected void setShowInternet(boolean showInternet) {
        this.showInternet = showInternet;
    }

    protected void onConnectionRefresh(boolean connected) {

    }

    @CallSuper
    public void onConnected() {
        if (noInternetSheet != null) {
            noInternetSheet.dismiss();
        }
    }

    private void subscribeBaseEvents() {

       /* connectionHandler.observe(this, connectionBean -> {
            if (connectionBean == null) return;
            if (showInternet) {
                if (noInternetSheet == null) {
                    noInternetSheet = new NoInternetSheet();
                    noInternetSheet.setCancelable(false);
                }
                if (connectionBean.getType() == ConnectionBean.State.NONE) {
                    noInternetSheet.show(getSupportFragmentManager(), noInternetSheet.getTag());
                } else {
                    onConnected();

                }
            }
        });*/

        connectionHandler.observe(this, connectionBean -> {
            if (connectionBean == null) return;
            if (showInternet) {
                if (noInternetSheet == null) {
                    noInternetSheet = new NoInternetSheet();
                    noInternetSheet.setCancelable(false);
                }
                if (connectionBean.getType() == ConnectionBean.State.NONE) {
                    noInternetSheet.show(getSupportFragmentManager(), noInternetSheet.getTag());
                } else {
                    if (noInternetSheet != null&&noInternetSheet.isVisible()) {
                        noInternetSheet.dismiss();
                    }
                    onConnectionRefresh(true);
                }
            }
        });

        viewModel.normal.observe(this, new SingleMessageEvent.MessageObserver() {
            @Override
            public void onMessageReceived(int msgResId) {
                MessageUtils.normal(AppActivity.this, getString(msgResId));
            }

            @Override
            public void onMessageReceived(String msg) {
                if (msg != null && !TextUtils.isEmpty(msg))
                    MessageUtils.normal(AppActivity.this, msg);
            }
        });
        viewModel.success.observe(this, new SingleMessageEvent.MessageObserver() {
            @Override
            public void onMessageReceived(int msgResId) {
                MessageUtils.success(AppActivity.this, getString(msgResId));
            }

            @Override
            public void onMessageReceived(String msg) {
                if (msg != null && !TextUtils.isEmpty(msg))
                    MessageUtils.success(AppActivity.this, msg);

            }
        });
        viewModel.error.observe(this, new SingleMessageEvent.MessageObserver() {
            @Override
            public void onMessageReceived(int msgResId) {
                MessageUtils.error(AppActivity.this, getString(msgResId));
            }

            @Override
            public void onMessageReceived(String msg) {
                if (msg != null && !TextUtils.isEmpty(msg))
                    MessageUtils.error(AppActivity.this, msg);
            }
        });
        viewModel.warn.observe(this, new SingleMessageEvent.MessageObserver() {
            @Override
            public void onMessageReceived(int msgResId) {
                MessageUtils.warning(AppActivity.this, getString(msgResId));
            }

            @Override
            public void onMessageReceived(String msg) {
                if (msg != null && !TextUtils.isEmpty(msg))
                    MessageUtils.warning(AppActivity.this, msg);
            }
        });
        viewModel.info.observe(this, new SingleMessageEvent.MessageObserver() {
            @Override
            public void onMessageReceived(int msgResId) {
                MessageUtils.info(AppActivity.this, getString(msgResId));
            }

            @Override
            public void onMessageReceived(String msg) {
                if (msg != null && !TextUtils.isEmpty(msg))
                    MessageUtils.info(AppActivity.this, msg);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed(true);
    }
}
