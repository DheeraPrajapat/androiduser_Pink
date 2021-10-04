package com.marius.valeyou.localMarketModel.ui.activity.searched;

import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.data.remote.helper.NetworkErrorHandler;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.data.repo.WelcomeRepo;
import com.marius.valeyou.di.base.viewmodel.BaseActivityViewModel;
import com.marius.valeyou.localMarketModel.responseModel.SearchedModel;
import com.marius.valeyou.util.event.SingleRequestEvent;

import java.net.HttpURLConnection;

import javax.inject.Inject;

import dagger.android.support.DaggerApplication;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SearchedActivityVM extends BaseActivityViewModel {

    public final SharedPref sharedPref;
    private final NetworkErrorHandler networkErrorHandler;
    private WelcomeRepo welcomeRepo;
    private final DaggerApplication context;
    final SingleRequestEvent<SearchedModel> searchedModelEvent = new SingleRequestEvent<>();

    @Inject
    public SearchedActivityVM(DaggerApplication context, SharedPref sharedPref, NetworkErrorHandler networkErrorHandler, WelcomeRepo welcomeRepo) {
        this.context = context;
        this.sharedPref = sharedPref;
        this.networkErrorHandler = networkErrorHandler;
        this.welcomeRepo = welcomeRepo;
    }



    public void getSearchedModel() {
        welcomeRepo.getSearchedModel(sharedPref.getUserData().getAuthKey()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<SearchedModel>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
                searchedModelEvent.setValue(Resource.loading(null));
            }

            @Override
            public void onSuccess(SearchedModel apiResponse) {
                if (apiResponse.getCode() == HttpURLConnection.HTTP_OK) {
                    searchedModelEvent.setValue(Resource.success(apiResponse, apiResponse.getMsg()));
                } else {
                    searchedModelEvent.setValue(Resource.warn(null, apiResponse.getMsg()));
                }
            }

            @Override
            public void onError(Throwable e) {
                searchedModelEvent.setValue(Resource.error(null, networkErrorHandler.getErrMsg(e)));
            }
        });
    }



}
