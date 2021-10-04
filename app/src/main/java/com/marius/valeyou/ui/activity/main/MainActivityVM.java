package com.marius.valeyou.ui.activity.main;

import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.marius.valeyou.data.beans.NotificationBadgeModel;
import com.marius.valeyou.data.beans.base.ApiResponse;
import com.marius.valeyou.data.beans.base.SimpleApiResponse;
import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.data.remote.helper.NetworkErrorHandler;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.data.repo.WelcomeRepo;
import com.marius.valeyou.di.base.viewmodel.BaseActivityViewModel;
import com.marius.valeyou.util.event.SingleActionEvent;
import com.marius.valeyou.util.event.SingleRequestEvent;

import java.net.HttpURLConnection;

import javax.inject.Inject;

import dagger.android.support.DaggerApplication;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivityVM extends BaseActivityViewModel {

    private final SharedPref sharedPref;
    private final NetworkErrorHandler networkErrorHandler;
    final SingleActionEvent<MenuItem> obrNavClick = new SingleActionEvent<>();
    final SingleRequestEvent<Integer> score = new SingleRequestEvent<>();
    final SingleRequestEvent<Void> playerResponse = new SingleRequestEvent<>();
    private WelcomeRepo welcomeRepo;
    private final DaggerApplication context;
    final SingleActionEvent<long[]> saveData = new SingleActionEvent<>();


    @Inject
    public MainActivityVM(DaggerApplication context, SharedPref sharedPref, NetworkErrorHandler networkErrorHandler,WelcomeRepo welcomeRepo) {
        this.context = context;
        this.sharedPref = sharedPref;
        this.networkErrorHandler = networkErrorHandler;
        this.welcomeRepo = welcomeRepo;
    }


    public BottomNavigationView.OnNavigationItemSelectedListener getNavListioner() {
        return item -> {
            obrNavClick.setValue(item);
            return true;
        };
    }





}
