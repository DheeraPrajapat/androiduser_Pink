package com.marius.valeyou.ui.activity.identityverfication;

import com.marius.valeyou.data.beans.base.ApiResponse;
import com.marius.valeyou.data.beans.base.SimpleApiResponse;
import com.marius.valeyou.data.beans.respbean.GetProfileBean;
import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.data.remote.helper.NetworkErrorHandler;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.data.repo.WelcomeRepo;
import com.marius.valeyou.di.base.viewmodel.BaseActivityViewModel;
import com.marius.valeyou.util.event.SingleRequestEvent;
import com.marius.valeyou.util.location.LiveLocationDetecter;

import java.net.HttpURLConnection;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class IdentityVerficationActivityVM extends BaseActivityViewModel {
    public final SharedPref sharedPref;
    public final LiveLocationDetecter liveLocationDetecter;
    private final NetworkErrorHandler networkErrorHandler;
    private final WelcomeRepo welcomeRepo;
    final SingleRequestEvent<SimpleApiResponse> deleteApiBean = new SingleRequestEvent<>();
    final SingleRequestEvent<GetProfileBean> profileBeanEvent = new SingleRequestEvent<>();


    @Inject
    public IdentityVerficationActivityVM(SharedPref sharedPref,LiveLocationDetecter liveLocationDetecter,NetworkErrorHandler networkErrorHandle,WelcomeRepo welcomeRepo) {
        this.sharedPref =sharedPref;
        this.liveLocationDetecter = liveLocationDetecter;
        this.welcomeRepo = welcomeRepo;
        this.networkErrorHandler = networkErrorHandle;

    }

    public void getProfile() {
        welcomeRepo.getProfileData(sharedPref.getUserData().getAuthKey(), sharedPref.getUserData().getId()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<ApiResponse<GetProfileBean>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
                profileBeanEvent.setValue(Resource.loading(null));
            }

            @Override
            public void onSuccess(ApiResponse<GetProfileBean> getProfileBeanApiResponse) {
                if (getProfileBeanApiResponse.getStatus() == HttpURLConnection.HTTP_OK) {
                    profileBeanEvent.setValue(Resource.success(getProfileBeanApiResponse.getData(), getProfileBeanApiResponse.getMsg()));
                } else {
                    profileBeanEvent.setValue(Resource.warn(null, getProfileBeanApiResponse.getMsg()));
                }
            }

            @Override
            public void onError(Throwable e) {
                profileBeanEvent.setValue(Resource.error(null, networkErrorHandler.getErrMsg(e)));
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
}
