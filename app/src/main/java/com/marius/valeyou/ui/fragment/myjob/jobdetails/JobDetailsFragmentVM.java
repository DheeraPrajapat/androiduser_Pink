package com.marius.valeyou.ui.fragment.myjob.jobdetails;

import com.marius.valeyou.data.beans.base.ApiResponse;
import com.marius.valeyou.data.beans.base.SimpleApiResponse;
import com.marius.valeyou.data.beans.respbean.JobDetailsBean;
import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.data.remote.helper.NetworkErrorHandler;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.data.repo.WelcomeRepo;
import com.marius.valeyou.di.base.viewmodel.BaseFragmentViewModel;
import com.marius.valeyou.util.event.SingleRequestEvent;

import java.net.HttpURLConnection;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class JobDetailsFragmentVM extends BaseFragmentViewModel {
    public final SharedPref sharedPref;
    private final NetworkErrorHandler networkErrorHandler;
    private final WelcomeRepo welcomeRepo;
    public SingleRequestEvent<JobDetailsBean> jobDetailsEvent = new SingleRequestEvent<>();
    public SingleRequestEvent<SimpleApiResponse> acceptRejectEvent = new SingleRequestEvent<>();
    public SingleRequestEvent<SimpleApiResponse> cancelJobEvent = new SingleRequestEvent<>();
    public SingleRequestEvent<SimpleApiResponse> deleteJobEvent = new SingleRequestEvent<>();

    @Inject
    public JobDetailsFragmentVM(SharedPref sharedPref, NetworkErrorHandler networkErrorHandler, WelcomeRepo welcomeRepo) {
        this.sharedPref = sharedPref;
        this.networkErrorHandler = networkErrorHandler;
        this.welcomeRepo = welcomeRepo;
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

    public void acceptRejectBid(Map<String, Integer> data) {
        welcomeRepo.acceptRejectBid(sharedPref.getUserData().getAuthKey(),data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<SimpleApiResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                        acceptRejectEvent.setValue(Resource.loading(null));
                    }

                    @Override
                    public void onSuccess(SimpleApiResponse simpleApiResponse) {
                        if (simpleApiResponse.getStatus()==HttpURLConnection.HTTP_OK){
                            acceptRejectEvent.setValue(Resource.success(simpleApiResponse,simpleApiResponse.getMsg()));
                        } else {
                            acceptRejectEvent.setValue(Resource.warn(null,simpleApiResponse.getMsg()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        acceptRejectEvent.setValue(Resource.error(null,networkErrorHandler.getErrMsg(e)));
                    }
                });
    }



    public void cancelJob(String id, String status) {
        welcomeRepo.completeJob(sharedPref.getUserData().getAuthKey(),id,status)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                .subscribe(new SingleObserver<SimpleApiResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                        cancelJobEvent.setValue(Resource.loading(null));
                    }

                    @Override
                    public void onSuccess(SimpleApiResponse simpleApiResponse) {
                        if (simpleApiResponse.getStatus()==HttpURLConnection.HTTP_OK){
                            cancelJobEvent.setValue(Resource.success(simpleApiResponse,simpleApiResponse.getMsg()));
                        } else {
                            cancelJobEvent.setValue(Resource.warn(null,simpleApiResponse.getMsg()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        cancelJobEvent.setValue(Resource.error(null,networkErrorHandler.getErrMsg(e)));
                    }
                });
    }


    public void deleteJob(String id) {
        welcomeRepo.deleteJob(sharedPref.getUserData().getAuthKey(),id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                .subscribe(new SingleObserver<SimpleApiResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                        deleteJobEvent.setValue(Resource.loading(null));
                    }

                    @Override
                    public void onSuccess(SimpleApiResponse simpleApiResponse) {
                        if (simpleApiResponse.getStatus()==HttpURLConnection.HTTP_OK){
                            deleteJobEvent.setValue(Resource.success(simpleApiResponse,simpleApiResponse.getMsg()));
                        } else {
                            deleteJobEvent.setValue(Resource.warn(null,simpleApiResponse.getMsg()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        deleteJobEvent.setValue(Resource.error(null,networkErrorHandler.getErrMsg(e)));
                    }
                });
    }



}
