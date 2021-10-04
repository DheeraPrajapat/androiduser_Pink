package com.marius.valeyou.ui.fragment.more.changepassword;

import com.marius.valeyou.data.beans.base.ApiResponse;
import com.marius.valeyou.data.beans.signup.SignupData;
import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.data.remote.helper.NetworkErrorHandler;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.data.repo.WelcomeRepo;
import com.marius.valeyou.di.base.viewmodel.BaseActivityViewModel;
import com.marius.valeyou.di.base.viewmodel.BaseFragmentViewModel;
import com.marius.valeyou.util.event.SingleRequestEvent;

import java.net.HttpURLConnection;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ChangePasswordFragmentVM extends BaseActivityViewModel {

    private final SharedPref sharedPref;
    private final NetworkErrorHandler networkErrorHandler;
    private final WelcomeRepo welcomeRepo;
    public final SingleRequestEvent<SignupData> changedPassword = new SingleRequestEvent<>();


    @Inject
    public ChangePasswordFragmentVM(SharedPref sharedPref, NetworkErrorHandler networkErrorHandler, WelcomeRepo welcomeRepo) {
        this.sharedPref = sharedPref;
        this.networkErrorHandler = networkErrorHandler;
        this.welcomeRepo = welcomeRepo;
    }

    public void changedPassword(String old_password,String new_password) {
        welcomeRepo.userChangePassword(sharedPref.getUserData().getAuthKey(),old_password,new_password).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<ApiResponse>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
                changedPassword.setValue(Resource.loading(null));
            }

            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if (apiResponse.getStatus() == HttpURLConnection.HTTP_OK) {
                    changedPassword.setValue(Resource.success(null,apiResponse.getMsg()));
                } else {
                    changedPassword.setValue(Resource.warn(null,apiResponse.getMsg()));
                }
            }

            @Override
            public void onError(Throwable e) {
                changedPassword.setValue(Resource.error(null,networkErrorHandler.getErrMsg(e)));
            }
        });
    }

}
