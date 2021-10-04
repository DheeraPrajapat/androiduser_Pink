package com.marius.valeyou.localMarketModel.ui.activity.other_user_profile;

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

public class OtherUserProfileVM extends BaseActivityViewModel {
    public final SharedPref sharedPref;
    private final NetworkErrorHandler networkErrorHandler;
    private final WelcomeRepo welcomeRepo;
    final SingleRequestEvent<GetProfileBean> getProfile = new SingleRequestEvent<>();

    @Inject
    public OtherUserProfileVM(SharedPref sharedPref, NetworkErrorHandler networkErrorHandler, WelcomeRepo welcomeRepo) {
        this.sharedPref = sharedPref;
        this.networkErrorHandler = networkErrorHandler;
        this.welcomeRepo = welcomeRepo;
    }

    public void getProfile(int user_id) {
        welcomeRepo.getProfileData(sharedPref.getUserData().getAuthKey(), user_id)
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


}
