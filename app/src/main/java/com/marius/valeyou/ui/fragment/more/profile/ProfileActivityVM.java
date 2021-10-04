package com.marius.valeyou.ui.fragment.more.profile;

import com.marius.valeyou.data.beans.base.ApiResponse;
import com.marius.valeyou.data.beans.base.SimpleApiResponse;
import com.marius.valeyou.data.beans.respbean.GetProfileBean;
 import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.data.remote.helper.NetworkErrorHandler;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.data.repo.WelcomeRepo;
import com.marius.valeyou.di.base.viewmodel.BaseActivityViewModel;
import com.marius.valeyou.util.event.SingleRequestEvent;

import java.net.HttpURLConnection;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ProfileActivityVM extends BaseActivityViewModel {
    public final SharedPref sharedPref;
    private final NetworkErrorHandler networkErrorHandler;
    private final WelcomeRepo welcomeRepo;
    final SingleRequestEvent<GetProfileBean> getProfile = new SingleRequestEvent<>();
    final SingleRequestEvent<SimpleApiResponse> successEvent = new SingleRequestEvent<>();
    final SingleRequestEvent<SimpleApiResponse> deleteApiBean = new SingleRequestEvent<>();

    @Inject
    public ProfileActivityVM(SharedPref sharedPref, NetworkErrorHandler networkErrorHandler, WelcomeRepo welcomeRepo) {
        this.sharedPref = sharedPref;
        this.networkErrorHandler = networkErrorHandler;
        this.welcomeRepo = welcomeRepo;
    }

    public void getProfile() {
        welcomeRepo.getProfileData(sharedPref.getUserData().getAuthKey(), sharedPref.getUserData().getId()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<ApiResponse<GetProfileBean>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
                getProfile.setValue(Resource.loading(null));
            }

            @Override
            public void onSuccess(ApiResponse<GetProfileBean> getProfileBeanApiResponse) {
                if (getProfileBeanApiResponse.getStatus() == HttpURLConnection.HTTP_OK) {
                    getProfile.setValue(Resource.success(getProfileBeanApiResponse.getData(), getProfileBeanApiResponse.getMsg()));
                } else {
                    getProfile.setValue(Resource.warn(null, getProfileBeanApiResponse.getMsg()));
                }
            }

            @Override
            public void onError(Throwable e) {
                getProfile.setValue(Resource.error(null, networkErrorHandler.getErrMsg(e)));
            }
        });
    }


    public void deleteIdentity(String authKey,int id){
        welcomeRepo.deleteIdentity(authKey,id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<SimpleApiResponse>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
                deleteApiBean.setValue(Resource.loading(null));
            }

            @Override
            public void onSuccess(SimpleApiResponse apiResponse) {

                if (apiResponse.getStatus() == HttpURLConnection.HTTP_OK) {

                    deleteApiBean.setValue(Resource.success(null, apiResponse.getMsg()));

                } else {

                    deleteApiBean.setValue(Resource.error(null, apiResponse.getMsg()));

                }
            }

            @Override
            public void onError(Throwable e) {

                deleteApiBean.setValue(Resource.error(null, networkErrorHandler.getErrMsg(e)));

            }
        });
    }

    public void deleteOrDeactivate(String authKey, String type) {
        welcomeRepo.accountSettings(authKey, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<SimpleApiResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                        successEvent.setValue(Resource.loading(null));
                    }

                    @Override
                    public void onSuccess(SimpleApiResponse simpleApiResponse) {
                        if (simpleApiResponse.getStatus() == HttpURLConnection.HTTP_OK) {
                            successEvent.setValue(Resource.success(simpleApiResponse, simpleApiResponse.getMsg()));
                        } else {
                            successEvent.setValue(Resource.warn(null, simpleApiResponse.getMsg()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        successEvent.setValue(Resource.error(null, networkErrorHandler.getErrMsg(e)));
                    }
                });
    }
}
