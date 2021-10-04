package com.marius.valeyou.ui.activity.notification;

import com.marius.valeyou.data.beans.base.ApiResponse;
import com.marius.valeyou.data.beans.base.SimpleApiResponse;
import com.marius.valeyou.data.beans.respbean.GetNotificationList;
import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.data.remote.helper.NetworkErrorHandler;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.data.repo.WelcomeRepo;
import com.marius.valeyou.di.base.viewmodel.BaseActivityViewModel;
import com.marius.valeyou.util.event.SingleRequestEvent;

import java.net.HttpURLConnection;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NotificationActivityVM extends BaseActivityViewModel {
    private final SharedPref sharedPref;
    private final NetworkErrorHandler networkErrorHandler;
    private final WelcomeRepo welcomeRepo;
    SingleRequestEvent<List<GetNotificationList>> singleRequestEvent = new SingleRequestEvent<>();
    SingleRequestEvent<SimpleApiResponse> readNotification = new SingleRequestEvent<>();

    SingleRequestEvent<SimpleApiResponse> rescheduleconfirmationEvent = new SingleRequestEvent<>();
    SingleRequestEvent<SimpleApiResponse> hireAnotherProviderEvent = new SingleRequestEvent<>();

    @Inject
    public NotificationActivityVM(SharedPref sharedPref, NetworkErrorHandler networkErrorHandler, WelcomeRepo welcomeRepo) {
        this.sharedPref = sharedPref;
        this.networkErrorHandler = networkErrorHandler;
        this.welcomeRepo = welcomeRepo;
    }

    public void getNotification() {
        welcomeRepo.getNotificationList(sharedPref.getUserData().getAuthKey(),sharedPref.get("language","en"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ApiResponse<List<GetNotificationList>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                        singleRequestEvent.setValue(Resource.loading(null));
                    }

                    @Override
                    public void onSuccess(ApiResponse<List<GetNotificationList>> listApiResponse) {
                        if (listApiResponse.getStatus() == HttpURLConnection.HTTP_OK) {
                            singleRequestEvent.setValue(Resource.success(listApiResponse.getData(), listApiResponse.getMsg()));
                        } else {
                            singleRequestEvent.setValue(Resource.warn(null, listApiResponse.getMsg()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        singleRequestEvent.setValue(Resource.error(null, networkErrorHandler.getErrMsg(e)));
                    }
                });
    }

    public void readNotification(String type, int notification_id) {
        welcomeRepo.readNotification(sharedPref.getUserData().getAuthKey(), type, String.valueOf(notification_id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<SimpleApiResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                        readNotification.setValue(Resource.loading(null));
                    }

                    @Override
                    public void onSuccess(SimpleApiResponse simpleApiResponse) {
                        if (simpleApiResponse.getStatus() == HttpURLConnection.HTTP_OK) {
                            readNotification.setValue(Resource.success(simpleApiResponse, simpleApiResponse.getMsg()));
                        } else {
                            readNotification.setValue(Resource.warn(null, simpleApiResponse.getMsg()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        readNotification.setValue(Resource.error(null, networkErrorHandler.getErrMsg(e)));
                    }
                });
    }

    public void rescheduleConfirmationApi(String order_id) {
        welcomeRepo.confirmReschedule(sharedPref.getUserData().getAuthKey(),order_id,"0")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<SimpleApiResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                        rescheduleconfirmationEvent.setValue(Resource.loading(null));
                    }

                    @Override
                    public void onSuccess(SimpleApiResponse simpleApiResponse) {
                        if (simpleApiResponse.getStatus() == HttpURLConnection.HTTP_OK) {
                            rescheduleconfirmationEvent.setValue(Resource.success(simpleApiResponse, simpleApiResponse.getMsg()));
                        } else {
                            rescheduleconfirmationEvent.setValue(Resource.warn(null, simpleApiResponse.getMsg()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        rescheduleconfirmationEvent.setValue(Resource.error(null, networkErrorHandler.getErrMsg(e)));
                    }
                });
    }

    public void hireAnotherProvider(String order_id) {
        welcomeRepo.hireAnotherProvider(sharedPref.getUserData().getAuthKey(),order_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<SimpleApiResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                        hireAnotherProviderEvent.setValue(Resource.loading(null));
                    }

                    @Override
                    public void onSuccess(SimpleApiResponse simpleApiResponse) {
                        if (simpleApiResponse.getStatus() == HttpURLConnection.HTTP_OK) {
                            hireAnotherProviderEvent.setValue(Resource.success(simpleApiResponse, simpleApiResponse.getMsg()));
                        } else {
                            hireAnotherProviderEvent.setValue(Resource.warn(null, simpleApiResponse.getMsg()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        hireAnotherProviderEvent.setValue(Resource.error(null, networkErrorHandler.getErrMsg(e)));
                    }
                });
    }
}
