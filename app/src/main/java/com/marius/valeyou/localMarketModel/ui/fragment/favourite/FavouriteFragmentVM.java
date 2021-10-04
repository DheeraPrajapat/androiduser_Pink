package com.marius.valeyou.localMarketModel.ui.fragment.favourite;

import com.marius.valeyou.data.beans.base.SimpleApiResponse;
import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.data.remote.helper.NetworkErrorHandler;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.data.repo.WelcomeRepo;
import com.marius.valeyou.di.base.viewmodel.BaseFragmentViewModel;
import com.marius.valeyou.localMarketModel.responseModel.FavouriteModel;
import com.marius.valeyou.util.event.SingleRequestEvent;
import com.marius.valeyou.util.location.LiveLocationDetecter;

import java.net.HttpURLConnection;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FavouriteFragmentVM extends BaseFragmentViewModel {

    public final SharedPref sharedPref;
    private final NetworkErrorHandler networkErrorHandler;
    public final LiveLocationDetecter liveLocationDetecter;
    private final WelcomeRepo welcomeRepo;
    final SingleRequestEvent<FavouriteModel> favouriteEvent = new SingleRequestEvent<>();
    final SingleRequestEvent<SimpleApiResponse> addFavouriteEvent = new SingleRequestEvent<>();

    @Inject
    public FavouriteFragmentVM(SharedPref sharedPref, NetworkErrorHandler networkErrorHandler, LiveLocationDetecter liveLocationDetecter, WelcomeRepo welcomeRepo) {
        this.sharedPref = sharedPref;
        this.networkErrorHandler = networkErrorHandler;
        this.liveLocationDetecter = liveLocationDetecter;
        this.welcomeRepo = welcomeRepo;
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

    public void getFavorite() {
        welcomeRepo.getFavourite(sharedPref.getUserData().getAuthKey()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<FavouriteModel>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
                favouriteEvent.setValue(Resource.loading(null));
            }

            @Override
            public void onSuccess(FavouriteModel apiResponse) {
                if (apiResponse.getCode() == HttpURLConnection.HTTP_OK) {
                    favouriteEvent.setValue(Resource.success(apiResponse, apiResponse.getMsg()));
                } else {
                    favouriteEvent.setValue(Resource.warn(null, apiResponse.getMsg()));
                }
            }

            @Override
            public void onError(Throwable e) {
                favouriteEvent.setValue(Resource.error(null, networkErrorHandler.getErrMsg(e)));
            }
        });
    }


}
