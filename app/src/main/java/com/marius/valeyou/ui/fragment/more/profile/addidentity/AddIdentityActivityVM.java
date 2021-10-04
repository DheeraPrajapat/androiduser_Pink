package com.marius.valeyou.ui.fragment.more.profile.addidentity;

import android.view.View;

import com.marius.valeyou.data.beans.base.SimpleApiResponse;
import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.data.remote.helper.NetworkErrorHandler;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.data.repo.WelcomeRepo;
import com.marius.valeyou.di.base.viewmodel.BaseActivityViewModel;
import com.marius.valeyou.util.event.SingleActionEvent;
import com.marius.valeyou.util.event.SingleRequestEvent;
import com.marius.valeyou.util.location.LiveLocationDetecter;

import java.net.HttpURLConnection;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AddIdentityActivityVM extends BaseActivityViewModel {
    public final SharedPref sharedPref;
    public final LiveLocationDetecter liveLocationDetecter;
    private final NetworkErrorHandler networkErrorHandler;
    private final WelcomeRepo welcomeRepo;
    final SingleRequestEvent<SimpleApiResponse> addIdentityBean = new SingleRequestEvent<>();

    public final SingleActionEvent<View> base_click = new SingleActionEvent<>();

    @Inject
    public AddIdentityActivityVM(SharedPref sharedPref,LiveLocationDetecter liveLocationDetecter,NetworkErrorHandler networkErrorHandle,WelcomeRepo welcomeRepo) {
        this.sharedPref =sharedPref;
        this.liveLocationDetecter =liveLocationDetecter;
        this.welcomeRepo =welcomeRepo;
        this.networkErrorHandler = networkErrorHandle;
    }

    public void onClick(View view) {
        base_click.call(view);
    }


    public void addIdentity(String auth_key, RequestBody type,RequestBody api_type, MultipartBody.Part frontImage, MultipartBody.Part backImage, MultipartBody.Part selfieImage){
        welcomeRepo.addIdentity(auth_key,type,api_type,frontImage,backImage, selfieImage).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<SimpleApiResponse>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
                addIdentityBean.setValue(Resource.loading(null));
            }

            @Override
            public void onSuccess(SimpleApiResponse addCertificateApiResponse) {

                if (addCertificateApiResponse.getStatus() == HttpURLConnection.HTTP_OK) {

                    addIdentityBean.setValue(Resource.success(null, addCertificateApiResponse.getMsg()));

                } else {

                    addIdentityBean.setValue(Resource.error(null, addCertificateApiResponse.getMsg()));

                }
            }

            @Override
            public void onError(Throwable e) {

                addIdentityBean.setValue(Resource.error(null, networkErrorHandler.getErrMsg(e)));

            }
        });
    }

    public void editIdentity(String auth_key,  RequestBody identity_id,RequestBody type,RequestBody api_type, MultipartBody.Part frontImage, MultipartBody.Part backImage, MultipartBody.Part selfieImage){
        welcomeRepo.editIdentity(auth_key,identity_id,type,api_type,frontImage,backImage,selfieImage).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<SimpleApiResponse>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
                addIdentityBean.setValue(Resource.loading(null));
            }

            @Override
            public void onSuccess(SimpleApiResponse addCertificateApiResponse) {

                if (addCertificateApiResponse.getStatus() == HttpURLConnection.HTTP_OK) {

                    addIdentityBean.setValue(Resource.success(null, addCertificateApiResponse.getMsg()));

                } else {

                    addIdentityBean.setValue(Resource.error(null, addCertificateApiResponse.getMsg()));

                }
            }

            @Override
            public void onError(Throwable e) {

                addIdentityBean.setValue(Resource.error(null, networkErrorHandler.getErrMsg(e)));

            }
        });
    }
}
