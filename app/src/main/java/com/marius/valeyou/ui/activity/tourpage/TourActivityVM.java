package com.marius.valeyou.ui.activity.tourpage;

import android.util.Log;

import com.marius.valeyou.data.beans.base.SimpleApiResponse;
import com.marius.valeyou.data.repo.WelcomeRepo;
import com.marius.valeyou.di.base.viewmodel.BaseActivityViewModel;

import java.net.HttpURLConnection;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TourActivityVM extends BaseActivityViewModel {
    private WelcomeRepo welcomeRepo;
    @Inject
    public TourActivityVM(WelcomeRepo welcomeRepo) {
        this.welcomeRepo = welcomeRepo;
    }

    public void deleteLanguage() {
        welcomeRepo.deleteLanguage("1ca7d6f742855cf4506dd294e13b1485788bb91e747f8b8beb",14)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<SimpleApiResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(SimpleApiResponse simpleApiResponse) {
                        if (simpleApiResponse.getStatus()== HttpURLConnection.HTTP_OK){
                            Log.i("TAG","SUCCESS");
                        } else {
                            Log.i("TAG","WARN");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("TAG","ERROR");
                    }
                });
    }
}
