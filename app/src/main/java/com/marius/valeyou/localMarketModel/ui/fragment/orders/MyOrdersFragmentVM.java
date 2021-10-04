package com.marius.valeyou.localMarketModel.ui.fragment.orders;

import android.util.Log;

import com.marius.valeyou.data.beans.base.SimpleApiResponse;
import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.data.remote.helper.NetworkErrorHandler;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.data.repo.WelcomeRepo;
import com.marius.valeyou.di.base.viewmodel.BaseFragmentViewModel;
import com.marius.valeyou.localMarketModel.responseModel.MarketShopProfile;
import com.marius.valeyou.localMarketModel.responseModel.marketHome.MarketHomeModel;
import com.marius.valeyou.util.Constants;
import com.marius.valeyou.util.event.SingleRequestEvent;
import com.marius.valeyou.util.location.LiveLocationDetecter;

import java.net.HttpURLConnection;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MyOrdersFragmentVM extends BaseFragmentViewModel {

    public final SharedPref sharedPref;
    private final NetworkErrorHandler networkErrorHandler;
    public final LiveLocationDetecter liveLocationDetecter;
    private final WelcomeRepo welcomeRepo;
    final SingleRequestEvent<MarketHomeModel> homeModelEvent = new SingleRequestEvent<>();
    final SingleRequestEvent<SimpleApiResponse> soldEvent = new SingleRequestEvent<>();
    final SingleRequestEvent<MarketShopProfile> marketShopProfileEvent = new SingleRequestEvent<>();
    final SingleRequestEvent<MarketHomeModel> productFilterEvent = new SingleRequestEvent<>();


    @Inject
    public MyOrdersFragmentVM(SharedPref sharedPref, NetworkErrorHandler networkErrorHandler, LiveLocationDetecter liveLocationDetecter, WelcomeRepo welcomeRepo) {
        this.sharedPref = sharedPref;
        this.networkErrorHandler = networkErrorHandler;
        this.liveLocationDetecter = liveLocationDetecter;
        this.welcomeRepo = welcomeRepo;
    }


    public void getMyPost() {
         welcomeRepo.getMyPost(sharedPref.getUserData().getAuthKey()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<MarketHomeModel>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
                homeModelEvent.setValue(Resource.loading(null));
            }

            @Override
            public void onSuccess(MarketHomeModel apiResponse) {
                if (apiResponse.getCode() == HttpURLConnection.HTTP_OK) {
                    homeModelEvent.setValue(Resource.success(apiResponse, apiResponse.getMsg()));
                } else {
                    homeModelEvent.setValue(Resource.warn(null, apiResponse.getMsg()));
                }
            }

            @Override
            public void onError(Throwable e) {
                homeModelEvent.setValue(Resource.error(null, networkErrorHandler.getErrMsg(e)));
            }
        });
    }

    public void markAsSold(String postId, String sold) {
        welcomeRepo.markAsSold(sharedPref.getUserData().getAuthKey(), postId, sold).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<SimpleApiResponse>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
                soldEvent.setValue(Resource.loading(null));
            }

            @Override
            public void onSuccess(SimpleApiResponse apiResponse) {
                if (apiResponse.getStatus() == HttpURLConnection.HTTP_OK) {
                    soldEvent.setValue(Resource.success(apiResponse, apiResponse.getMsg()));
                } else {
                    soldEvent.setValue(Resource.warn(null, apiResponse.getMsg()));
                }
            }

            @Override
            public void onError(Throwable e) {
                soldEvent.setValue(Resource.error(null, networkErrorHandler.getErrMsg(e)));
            }
        });
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

 public void getFilterProducts(String shop_name, String owner_type) {

        welcomeRepo.getFilterProducts(sharedPref.getUserData().getAuthKey(),shop_name,owner_type).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<MarketHomeModel>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
                productFilterEvent.setValue(Resource.loading(null));
            }

            @Override
            public void onSuccess(MarketHomeModel apiResponse) {
                if (apiResponse.getCode() == HttpURLConnection.HTTP_OK) {
                    productFilterEvent.setValue(Resource.success(apiResponse, apiResponse.getMsg()));
                } else {
                    productFilterEvent.setValue(Resource.warn(null, apiResponse.getMsg()));
                }
            }

            @Override
            public void onError(Throwable e) {
                productFilterEvent.setValue(Resource.error(null, networkErrorHandler.getErrMsg(e)));
            }
        });
    }

}
