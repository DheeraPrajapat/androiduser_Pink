package com.marius.valeyou.localMarketModel.ui.fragment.myShop;

import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.data.remote.helper.NetworkErrorHandler;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.data.repo.WelcomeRepo;
import com.marius.valeyou.di.base.viewmodel.BaseFragmentViewModel;
import com.marius.valeyou.localMarketModel.responseModel.MarketShopProfile;
import com.marius.valeyou.util.event.SingleRequestEvent;
import com.marius.valeyou.util.location.LiveLocationDetecter;

import java.net.HttpURLConnection;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MyShopDetailFragmentVM extends BaseFragmentViewModel {

    public final SharedPref sharedPref;
    private final NetworkErrorHandler networkErrorHandler;
    public final LiveLocationDetecter liveLocationDetecter;
    private final WelcomeRepo welcomeRepo;
    final SingleRequestEvent<MarketShopProfile> marketShopProfileEvent = new SingleRequestEvent<>();

    @Inject
    public MyShopDetailFragmentVM(SharedPref sharedPref, NetworkErrorHandler networkErrorHandler, LiveLocationDetecter liveLocationDetecter, WelcomeRepo welcomeRepo) {
        this.sharedPref = sharedPref;
        this.networkErrorHandler = networkErrorHandler;
        this.liveLocationDetecter = liveLocationDetecter;
        this.welcomeRepo = welcomeRepo;
    }

    public void getMarketShopProfile() {
        welcomeRepo.getMarketShopProfile(sharedPref.getUserData().getAuthKey()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<MarketShopProfile>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
                marketShopProfileEvent.setValue(Resource.loading(null));
            }

            @Override
            public void onSuccess(MarketShopProfile apiResponse) {
                if (apiResponse.getCode() == HttpURLConnection.HTTP_OK) {
                    marketShopProfileEvent.setValue(Resource.success(apiResponse, apiResponse.getMsg()));
                } else {
                    marketShopProfileEvent.setValue(Resource.warn(null, apiResponse.getMsg()));
                }
            }

            @Override
            public void onError(Throwable e) {
                marketShopProfileEvent.setValue(Resource.error(null, networkErrorHandler.getErrMsg(e)));
            }
        });
    }



}

