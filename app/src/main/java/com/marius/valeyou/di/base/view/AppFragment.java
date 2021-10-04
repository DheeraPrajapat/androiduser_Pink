package com.marius.valeyou.di.base.view;

import androidx.databinding.ViewDataBinding;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;

import com.marius.valeyou.util.event.SingleMessageEvent;
import com.marius.valeyou.util.view.MessageUtils;
import com.marius.valeyou.di.base.viewmodel.BaseFragmentViewModel;


public abstract class AppFragment<B extends ViewDataBinding, V extends BaseFragmentViewModel>
        extends BaseFragment<B, V> {



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        subscriveBaseEvents();

    }


    private void subscriveBaseEvents() {
        BaseFragmentViewModel model = viewModel;
        model.normal.observe(this, new SingleMessageEvent.MessageObserver() {
            @Override
            public void onMessageReceived(int msgResId) {
                MessageUtils.normal(baseContext, getString(msgResId));
            }

            @Override
            public void onMessageReceived(String msg) {
                MessageUtils.normal(baseContext, msg);
            }
        });
        model.success.observe(this, new SingleMessageEvent.MessageObserver() {
            @Override
            public void onMessageReceived(int msgResId) {
                MessageUtils.success(baseContext, getString(msgResId));
            }

            @Override
            public void onMessageReceived(String msg) {
                MessageUtils.success(baseContext, msg);

            }
        });
        model.error.observe(this, new SingleMessageEvent.MessageObserver() {
            @Override
            public void onMessageReceived(int msgResId) {
                MessageUtils.error(baseContext, getString(msgResId));
            }

            @Override
            public void onMessageReceived(String msg) {
                MessageUtils.error(baseContext, msg);
            }
        });
        model.warn.observe(this, new SingleMessageEvent.MessageObserver() {
            @Override
            public void onMessageReceived(int msgResId) {
                MessageUtils.warning(baseContext, getString(msgResId));
            }

            @Override
            public void onMessageReceived(String msg) {
                MessageUtils.warning(baseContext, msg);
            }
        });
        model.info.observe(this, new SingleMessageEvent.MessageObserver() {
            @Override
            public void onMessageReceived(int msgResId) {
                MessageUtils.info(baseContext, getString(msgResId));
            }

            @Override
            public void onMessageReceived(String msg) {
                MessageUtils.info(baseContext, msg);
            }
        });

    }

}
