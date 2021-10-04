package com.marius.valeyou.di.base.viewmodel;

import android.view.View;

import androidx.lifecycle.ViewModel;
import androidx.annotation.CallSuper;

import com.marius.valeyou.util.event.SingleActionEvent;
import com.marius.valeyou.util.event.SingleMessageEvent;

import io.reactivex.disposables.CompositeDisposable;

/**
 * This will serve as the base class for all Fragment ViewModels
 * <p>
 */
public abstract class BaseFragmentViewModel extends ViewModel {
    protected final CompositeDisposable compositeDisposable = new CompositeDisposable();
    public final SingleActionEvent<View> base_click = new SingleActionEvent<>();
    public final SingleMessageEvent normal = new SingleMessageEvent();
    public final SingleMessageEvent success = new SingleMessageEvent();
    public final SingleMessageEvent error = new SingleMessageEvent();
    public final SingleMessageEvent info = new SingleMessageEvent();
    public final SingleMessageEvent warn = new SingleMessageEvent();

    public void onClick(View view) {
        base_click.setValue(view);
    }


    @CallSuper
    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
