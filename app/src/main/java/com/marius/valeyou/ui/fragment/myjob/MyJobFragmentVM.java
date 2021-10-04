package com.marius.valeyou.ui.fragment.myjob;

import com.marius.valeyou.data.beans.NotificationBadgeModel;
import com.marius.valeyou.data.beans.base.ApiResponse;
import com.marius.valeyou.data.beans.respbean.GetAllJobBean;
import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.data.remote.helper.NetworkErrorHandler;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.data.repo.WelcomeRepo;
import com.marius.valeyou.di.base.viewmodel.BaseFragmentViewModel;
import com.marius.valeyou.util.event.SingleActionEvent;
import com.marius.valeyou.util.event.SingleRequestEvent;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class MyJobFragmentVM extends BaseFragmentViewModel {

    public SingleActionEvent<String> text = new SingleActionEvent<>();
    private final SharedPref sharedPref;
    private final NetworkErrorHandler networkErrorHandler;
    private final WelcomeRepo welcomeRepo;
    public SingleActionEvent<Integer> position = new SingleActionEvent<>();
    public SingleRequestEvent<List<GetAllJobBean>> jobListEvent = new SingleRequestEvent<>();
    final SingleRequestEvent<NotificationBadgeModel> notificationbadgeEventRequest = new SingleRequestEvent<>();

    @Inject
    public MyJobFragmentVM(SharedPref sharedPref, NetworkErrorHandler networkErrorHandler, WelcomeRepo welcomeRepo) {
        this.sharedPref = sharedPref;
        this.networkErrorHandler = networkErrorHandler;
        this.welcomeRepo = welcomeRepo;
    }

    public void getAllJobList(String page, String limit, String type) {
        Map<String,String> data = new HashMap<>();
        data.put("page",page);
        data.put("limit",limit);
        data.put("type",type);
        welcomeRepo.getAllJobList(sharedPref.getUserData().getAuthKey(),data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ApiResponse<List<GetAllJobBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                        jobListEvent.setValue(Resource.loading(null));
                    }

                    @Override
                    public void onSuccess(ApiResponse<List<GetAllJobBean>> listApiResponse) {
                        if (listApiResponse.getStatus() == HttpURLConnection.HTTP_OK){
                            jobListEvent.setValue(Resource.success(listApiResponse.getData(),listApiResponse.getMsg()));
                        } else {
                            jobListEvent.setValue(Resource.warn(null,listApiResponse.getMsg()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        jobListEvent.setValue(Resource.error(null,networkErrorHandler.getErrMsg(e)));
                    }
                });
    }


    public void getCount(){
        welcomeRepo.getCount(sharedPref.getUserData().getAuthKey()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<ApiResponse<NotificationBadgeModel>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
                notificationbadgeEventRequest.setValue(Resource.loading(null));
            }

            @Override
            public void onSuccess(ApiResponse<NotificationBadgeModel> faqResponse) {

                if (faqResponse.getStatus() == HttpURLConnection.HTTP_OK) {

                    notificationbadgeEventRequest.setValue(Resource.success(faqResponse.getData(), faqResponse.getMsg()));

                } else {

                    notificationbadgeEventRequest.setValue(Resource.error(null, faqResponse.getMsg()));

                }
            }

            @Override
            public void onError(Throwable e) {

                notificationbadgeEventRequest.setValue(Resource.error(null, networkErrorHandler.getErrMsg(e)));

            }
        });
    }

}
