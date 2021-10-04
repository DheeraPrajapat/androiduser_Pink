package com.marius.valeyou.localMarketModel.ui.fragment.home;

import android.util.Log;

import com.marius.valeyou.data.beans.base.SimpleApiResponse;
import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.data.remote.helper.NetworkErrorHandler;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.data.repo.WelcomeRepo;
import com.marius.valeyou.di.base.viewmodel.BaseFragmentViewModel;
import com.marius.valeyou.localMarketModel.responseModel.MarketCategoryModel;
import com.marius.valeyou.localMarketModel.responseModel.MarketShopProfile;
import com.marius.valeyou.localMarketModel.responseModel.marketHome.MarketHomeModel;
import com.marius.valeyou.util.event.SingleRequestEvent;
import com.marius.valeyou.util.location.LiveLocationDetecter;

import java.net.HttpURLConnection;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomeFragmentMarketVM extends BaseFragmentViewModel {

    public final SharedPref sharedPref;
    private final NetworkErrorHandler networkErrorHandler;
    public final LiveLocationDetecter liveLocationDetecter;
    private final WelcomeRepo welcomeRepo;
    final SingleRequestEvent<MarketCategoryModel> categoryModelEvent = new SingleRequestEvent<>();
    final SingleRequestEvent<MarketHomeModel> homeModelEvent = new SingleRequestEvent<>();
    final SingleRequestEvent<SimpleApiResponse> addFavouriteEvent = new SingleRequestEvent<>();
    final SingleRequestEvent<MarketShopProfile> marketShopProfileEvent = new SingleRequestEvent<>();

    HomeFragmentMarket homeFragmentMarket;

    @Inject
    public HomeFragmentMarketVM(SharedPref sharedPref, NetworkErrorHandler networkErrorHandler, LiveLocationDetecter liveLocationDetecter, WelcomeRepo welcomeRepo) {
        this.sharedPref = sharedPref;
        this.networkErrorHandler = networkErrorHandler;
        this.liveLocationDetecter = liveLocationDetecter;
        this.welcomeRepo = welcomeRepo;
    }


    public void getCategoryList() {
        welcomeRepo.getMarketCategory(sharedPref.getUserData().getAuthKey()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<MarketCategoryModel>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
                categoryModelEvent.setValue(Resource.loading(null));
            }

            @Override
            public void onSuccess(MarketCategoryModel apiResponse) {
                if (apiResponse.getCode() == HttpURLConnection.HTTP_OK) {
                    categoryModelEvent.setValue(Resource.success(apiResponse, apiResponse.getMsg()));
                } else {
                    categoryModelEvent.setValue(Resource.warn(null, apiResponse.getMsg()));
                }
            }

            @Override
            public void onError(Throwable e) {
                categoryModelEvent.setValue(Resource.error(null, networkErrorHandler.getErrMsg(e)));
            }
        });
    }


    public void getHomeList(String latitude, String longitude, String search, String category,
                            String private_saller, String commercial_seller, String only_sale,
                            String only_rent, String radius1, String radius2, String price_min,
                            String price_max, String product_type) {


        if (search.length() > 0) {
            radius1 = "0";
            radius2 = "1000000000000000000000000";
        }

        welcomeRepo.getMarketHomeList(sharedPref.getUserData().getAuthKey(), latitude, longitude, search, category, private_saller, commercial_seller, only_sale, only_rent, radius1, radius2, price_min, price_max, product_type).subscribeOn(Schedulers.io())
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

    public void addToFavourite(String postId, String type) {
        welcomeRepo.addToFavourite(sharedPref.getUserData().getAuthKey(), postId, type).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<SimpleApiResponse>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
                addFavouriteEvent.setValue(Resource.loading(null));
            }

            @Override
            public void onSuccess(SimpleApiResponse apiResponse) {
                if (apiResponse.getStatus() == HttpURLConnection.HTTP_OK) {
                    addFavouriteEvent.setValue(Resource.success(apiResponse, apiResponse.getMsg()));
                } else {
                    addFavouriteEvent.setValue(Resource.warn(null, apiResponse.getMsg()));
                }
            }

            @Override
            public void onError(Throwable e) {
                addFavouriteEvent.setValue(Resource.error(null, networkErrorHandler.getErrMsg(e)));
            }
        });
    }

    public void getMarketShopProfile(HomeFragmentMarket homeFragmentMarket) {
        this.homeFragmentMarket=homeFragmentMarket;

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
                    homeFragmentMarket.marketShopResponse(Resource.success(apiResponse, apiResponse.getMsg()));
//                    marketShopProfileEvent.setValue(Resource.success(apiResponse, apiResponse.getMsg()));
                    Log.d("ViewMOdellll1", String.valueOf(apiResponse.getMsg()));

                } else {
                    marketShopProfileEvent.setValue(Resource.warn(null, apiResponse.getMsg()));
                    Log.d("ViewMOdellll2", String.valueOf(apiResponse.getMsg()));
                }
            }

            @Override
            public void onError(Throwable e) {
                marketShopProfileEvent.setValue(Resource.error(null, networkErrorHandler.getErrMsg(e)));
            }
        });
    }

}
