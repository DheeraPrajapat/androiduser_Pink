package com.marius.valeyou.localMarketModel.ui.fragment.privacyTermsAbout;

import com.marius.valeyou.data.beans.base.ApiResponse;
import com.marius.valeyou.data.beans.respbean.PrivacyResponseBean;
import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.data.remote.helper.NetworkErrorHandler;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.data.repo.WelcomeRepo;
import com.marius.valeyou.di.base.viewmodel.BaseFragmentViewModel;
import com.marius.valeyou.util.event.SingleRequestEvent;

import java.net.HttpURLConnection;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PrivacyTermsAboutFragmentVM extends BaseFragmentViewModel {

    private final SharedPref sharedPref;
    private final NetworkErrorHandler networkErrorHandler;
    private final WelcomeRepo welcomeRepo;

    final SingleRequestEvent<PrivacyResponseBean> requestEvent = new SingleRequestEvent<>();

    @Inject
    public PrivacyTermsAboutFragmentVM(SharedPref sharedPref, NetworkErrorHandler networkErrorHandler, WelcomeRepo welcomeRepo) {
        this.sharedPref = sharedPref;
        this.networkErrorHandler = networkErrorHandler;
        this.welcomeRepo = welcomeRepo;
    }

    //send type(1=term,2=privacy,3=aboutus)
    public void getAllContent(int type) {
        welcomeRepo.getAllContent(sharedPref.getUserData().getAuthKey(),type).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<ApiResponse<PrivacyResponseBean>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
                requestEvent.setValue(Resource.loading(null));
            }

            @Override
            public void onSuccess(ApiResponse<PrivacyResponseBean> apiResponse) {
                if (apiResponse.getStatus() == HttpURLConnection.HTTP_OK) {
                    requestEvent.setValue(Resource.success(apiResponse.getData(),apiResponse.getMsg()));
                } else {
                    requestEvent.setValue(Resource.warn(null,apiResponse.getMsg()));
                }
            }

            @Override
            public void onError(Throwable e) {
                requestEvent.setValue(Resource.error(null,networkErrorHandler.getErrMsg(e)));
            }
        });
    }


}
