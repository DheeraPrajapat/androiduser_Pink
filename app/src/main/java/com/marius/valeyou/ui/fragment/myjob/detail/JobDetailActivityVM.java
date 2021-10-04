package com.marius.valeyou.ui.fragment.myjob.detail;

import android.view.View;

import com.marius.valeyou.data.beans.base.ApiResponse;
import com.marius.valeyou.data.beans.base.SimpleApiResponse;
import com.marius.valeyou.data.beans.respbean.JobDetailsBean;
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

public class JobDetailActivityVM extends BaseActivityViewModel {
    public final SharedPref sharedPref;
    public final LiveLocationDetecter liveLocationDetecter;
    private final NetworkErrorHandler networkErrorHandler;
    private final WelcomeRepo welcomeRepo;
    public final SingleActionEvent<View> base_click = new SingleActionEvent<>();

    final SingleRequestEvent<SimpleApiResponse> cancelJobEvent = new SingleRequestEvent<>();
    final SingleRequestEvent<SimpleApiResponse> deleteJobEvent = new SingleRequestEvent<>();
    final SingleRequestEvent<SimpleApiResponse> updateEndPriceEvent = new SingleRequestEvent<>();

    final SingleRequestEvent<JobDetailsBean> jobDetailBean = new SingleRequestEvent<>();
    final SingleRequestEvent<ApiResponse<SimpleApiResponse>> changeStatusEvent = new SingleRequestEvent<>();
    @Inject
    public JobDetailActivityVM(SharedPref sharedPref,
                                       LiveLocationDetecter liveLocationDetecter,
                                       NetworkErrorHandler networkErrorHandler,
                                       WelcomeRepo welcomeRepo) {

        this.sharedPref = sharedPref;
        this.networkErrorHandler = networkErrorHandler;
        this.liveLocationDetecter = liveLocationDetecter;
        this.welcomeRepo = welcomeRepo;

    }


    public void onClick(View view) {
        base_click.call(view);
    }


    public void getJobDetaial(String auth_key,int post_id) {
        welcomeRepo.getJobDetails(auth_key, String.valueOf(post_id)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<ApiResponse<JobDetailsBean>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
                jobDetailBean.setValue(Resource.loading(null));
            }

            @Override
            public void onSuccess(ApiResponse<JobDetailsBean> jobDetailApiResponse) {

                if (jobDetailApiResponse.getStatus() == HttpURLConnection.HTTP_OK) {

                    jobDetailBean.setValue(Resource.success(jobDetailApiResponse.getData(), jobDetailApiResponse.getMsg()));

                } else {

                    jobDetailBean.setValue(Resource.error(null, jobDetailApiResponse.getMsg()));

                }
            }

            @Override
            public void onError(Throwable e) {

                jobDetailBean.setValue(Resource.error(null, networkErrorHandler.getErrMsg(e)));

            }
        });
    }

    public void changeJobStatus(String auth_key,String order_id,String type, String status,String reason) {
        welcomeRepo.changeStatus(auth_key,order_id,type,status,reason).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<SimpleApiResponse>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
                changeStatusEvent.setValue(Resource.loading(null));
            }

            @Override
            public void onSuccess(SimpleApiResponse response) {

                if (response.getStatus() == HttpURLConnection.HTTP_OK) {

                    changeStatusEvent.setValue(Resource.success(null, response.getMsg()));

                } else {

                    changeStatusEvent.setValue(Resource.success(null,response.getMsg()));

                }
            }

            @Override
            public void onError(Throwable e) {

                changeStatusEvent.setValue(Resource.error(null, networkErrorHandler.getErrMsg(e)));

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

    public void updateEndPRice(String orderId,String endPRice) {
        welcomeRepo.updateEndPrice(sharedPref.getUserData().getAuthKey(),orderId,endPRice)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                .subscribe(new SingleObserver<SimpleApiResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                        updateEndPriceEvent.setValue(Resource.loading(null));
                    }

                    @Override
                    public void onSuccess(SimpleApiResponse simpleApiResponse) {
                        if (simpleApiResponse.getStatus()==HttpURLConnection.HTTP_OK){
                            updateEndPriceEvent.setValue(Resource.success(simpleApiResponse,simpleApiResponse.getMsg()));
                        } else {
                            updateEndPriceEvent.setValue(Resource.warn(null,simpleApiResponse.getMsg()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        updateEndPriceEvent.setValue(Resource.error(null,networkErrorHandler.getErrMsg(e)));
                    }
                });
    }

}
