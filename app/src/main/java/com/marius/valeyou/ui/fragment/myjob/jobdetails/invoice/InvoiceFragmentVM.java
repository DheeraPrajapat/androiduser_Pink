package com.marius.valeyou.ui.fragment.myjob.jobdetails.invoice;

import com.marius.valeyou.data.beans.base.ApiResponse;
import com.marius.valeyou.data.beans.comission.ComissionModel;
import com.marius.valeyou.data.beans.respbean.GetCardBean;
import com.marius.valeyou.data.beans.respbean.JobDetailsBean;
import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.data.remote.helper.NetworkErrorHandler;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.data.repo.WelcomeRepo;
import com.marius.valeyou.di.base.viewmodel.BaseFragmentViewModel;
import com.marius.valeyou.util.event.SingleRequestEvent;

import java.net.HttpURLConnection;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class InvoiceFragmentVM extends BaseFragmentViewModel {
    private final SharedPref sharedPref;
    private final NetworkErrorHandler networkErrorHandler;
    private final WelcomeRepo welcomeRepo;
    SingleRequestEvent<ComissionModel> singleRequestEvent = new SingleRequestEvent<>();
    public SingleRequestEvent<JobDetailsBean> jobDetailsEvent = new SingleRequestEvent<>();
    @Inject
    public InvoiceFragmentVM(SharedPref sharedPref, NetworkErrorHandler networkErrorHandler, WelcomeRepo welcomeRepo) {
        this.sharedPref = sharedPref;
        this.networkErrorHandler = networkErrorHandler;
        this.welcomeRepo = welcomeRepo;
    }

    public void getComission() {
        welcomeRepo.getComission()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ApiResponse<ComissionModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                        singleRequestEvent.setValue(Resource.loading(null));
                    }

                    @Override
                    public void onSuccess(ApiResponse<ComissionModel> listApiResponse) {
                        if (listApiResponse.getStatus() == HttpURLConnection.HTTP_OK){
                            singleRequestEvent.setValue(Resource.success(listApiResponse.getData(),listApiResponse.getMsg()));
                        } else {
                            singleRequestEvent.setValue(Resource.warn(null,listApiResponse.getMsg()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        singleRequestEvent.setValue(Resource.error(null,networkErrorHandler.getErrMsg(e)));
                    }
                });
    }


    public void getJobDetails(int job_id) {
        welcomeRepo.getJobDetails(sharedPref.getUserData().getAuthKey(), String.valueOf(job_id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ApiResponse<JobDetailsBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                        jobDetailsEvent.setValue(Resource.loading(null));
                    }

                    @Override
                    public void onSuccess(ApiResponse<JobDetailsBean> apiResponse) {
                        if (apiResponse.getStatus() == HttpURLConnection.HTTP_OK) {
                            jobDetailsEvent.setValue(Resource.success(apiResponse.getData(), apiResponse.getMsg()));
                        } else {
                            jobDetailsEvent.setValue(Resource.warn(null, apiResponse.getMsg()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        jobDetailsEvent.setValue(Resource.error(null, networkErrorHandler.getErrMsg(e)));
                    }
                });
    }

}
