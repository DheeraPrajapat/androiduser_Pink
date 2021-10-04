package com.marius.valeyou.ui.activity.provider_profile;

import com.marius.valeyou.data.beans.base.ApiResponse;
import com.marius.valeyou.data.beans.base.SimpleApiResponse;
import com.marius.valeyou.data.beans.respbean.ProviderDetails;
import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.data.remote.helper.NetworkErrorHandler;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.data.repo.WelcomeRepo;
import com.marius.valeyou.di.base.viewmodel.BaseActivityViewModel;
import com.marius.valeyou.util.event.SingleRequestEvent;

import java.net.HttpURLConnection;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ProviderProfileActivityVM extends BaseActivityViewModel {
    public final SharedPref sharedPref;
    private final NetworkErrorHandler networkErrorHandler;
    private final WelcomeRepo welcomeRepo;
    public SingleRequestEvent<ProviderDetails> providerDetails = new SingleRequestEvent<>();
    public SingleRequestEvent<SimpleApiResponse> favUnFavEvent = new SingleRequestEvent<>();

    @Inject
    public ProviderProfileActivityVM(SharedPref sharedPref, NetworkErrorHandler networkErrorHandler, WelcomeRepo welcomeRepo) {
        this.sharedPref = sharedPref;
        this.networkErrorHandler = networkErrorHandler;
        this.welcomeRepo = welcomeRepo;
    }

    public void getProviderDetails(int provider_id) {
        welcomeRepo.getProviderDetails(sharedPref.getUserData().getAuthKey(), String.valueOf(provider_id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ApiResponse<ProviderDetails>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                        providerDetails.setValue(Resource.loading(null));
                    }

                    @Override
                    public void onSuccess(ApiResponse<ProviderDetails> apiResponse) {
                        if (apiResponse.getStatus() == HttpURLConnection.HTTP_OK) {
                            providerDetails.setValue(Resource.success(apiResponse.getData(), apiResponse.getMsg()));
                        } else {
                            providerDetails.setValue(Resource.warn(null, apiResponse.getMsg()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        providerDetails.setValue(Resource.error(null, networkErrorHandler.getErrMsg(e)));
                    }
                });
    }

    public void addToFavUnfav(int provider_id, int status) {
        welcomeRepo.addToFav(sharedPref.getUserData().getAuthKey(), provider_id, status)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<SimpleApiResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                        favUnFavEvent.setValue(Resource.loading(null));
                    }

                    @Override
                    public void onSuccess(SimpleApiResponse simpleApiResponse) {
                        if (simpleApiResponse.getStatus() == HttpURLConnection.HTTP_OK) {
                            favUnFavEvent.setValue(Resource.success(simpleApiResponse, simpleApiResponse.getMsg()));
                        } else {
                            favUnFavEvent.setValue(Resource.warn(null, simpleApiResponse.getMsg()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        favUnFavEvent.setValue(Resource.error(null, networkErrorHandler.getErrMsg(e)));
                    }
                });
    }
}
