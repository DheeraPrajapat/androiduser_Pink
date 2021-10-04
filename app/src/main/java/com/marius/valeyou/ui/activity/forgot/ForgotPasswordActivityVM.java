package com.marius.valeyou.ui.activity.forgot;

import com.marius.valeyou.data.beans.base.SimpleApiResponse;
import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.data.remote.helper.NetworkErrorHandler;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.data.repo.WelcomeRepo;
import com.marius.valeyou.di.base.viewmodel.BaseActivityViewModel;
import com.marius.valeyou.util.event.SingleRequestEvent;
import com.marius.valeyou.util.location.LiveLocationDetecter;

import java.net.HttpURLConnection;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ForgotPasswordActivityVM extends BaseActivityViewModel {

    private final SharedPref sharedPref;
    public final LiveLocationDetecter liveLocationDetecter;
    private final NetworkErrorHandler networkErrorHandler;
    private final WelcomeRepo welcomeRepo;
    public SingleRequestEvent<SimpleApiResponse> requestEvent = new SingleRequestEvent<>();

    @Inject
    public ForgotPasswordActivityVM(SharedPref sharedPref, LiveLocationDetecter liveLocationDetecter,
                                    NetworkErrorHandler networkErrorHandler, WelcomeRepo welcomeRepo) {
        this.sharedPref = sharedPref;
        this.liveLocationDetecter = liveLocationDetecter;
        this.networkErrorHandler = networkErrorHandler;
        this.welcomeRepo = welcomeRepo;
    }

    public void forgetPassword(String email) {
        welcomeRepo.forgetPassword(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<SimpleApiResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                        requestEvent.setValue(Resource.loading(null));
                    }

                    @Override
                    public void onSuccess(SimpleApiResponse simpleApiResponse) {
                        if (simpleApiResponse.getStatus() == HttpURLConnection.HTTP_OK){
                            requestEvent.setValue(Resource.success(null,simpleApiResponse.getMsg()));
                        } else {
                            requestEvent.setValue(Resource.warn(null,simpleApiResponse.getMsg()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        requestEvent.setValue(Resource.error(null,networkErrorHandler.getErrMsg(e)));
                    }
                });
    }
}
