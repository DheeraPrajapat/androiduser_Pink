package com.marius.valeyou.ui.fragment.loadtype.removaljob;

import com.marius.valeyou.data.beans.base.ApiResponse;
import com.marius.valeyou.data.beans.respbean.AddJobBean;
import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.data.remote.helper.NetworkErrorHandler;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.data.repo.WelcomeRepo;
import com.marius.valeyou.di.base.viewmodel.BaseActivityViewModel;
import com.marius.valeyou.util.event.SingleRequestEvent;

import java.net.HttpURLConnection;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class JunkRemovalJobFragmentVM extends BaseActivityViewModel {
    private final WelcomeRepo welcomeRepo;
    private final SharedPref sharedPref;
    private final NetworkErrorHandler networkErrorHandler;
    public SingleRequestEvent<AddJobBean> requestEvent = new SingleRequestEvent<>();

    @Inject
    public JunkRemovalJobFragmentVM(SharedPref sharedPref, NetworkErrorHandler networkErrorHandler, WelcomeRepo welcomeRepo) {
        this.sharedPref = sharedPref;
        this.networkErrorHandler = networkErrorHandler;
        this.welcomeRepo = welcomeRepo;
    }

    public void createJob(String auth, RequestBody provider_id, RequestBody title, RequestBody description,
                          RequestBody estimationTime, RequestBody estimationPrice, RequestBody location1,
                          RequestBody latitude, RequestBody longitude, RequestBody state1, RequestBody zipCode,
                          RequestBody city1, RequestBody street, RequestBody appartment, RequestBody date, RequestBody time,
                          RequestBody selected_data, RequestBody type, RequestBody start_price, RequestBody end_price,
                          MultipartBody.Part body, RequestBody jobType,RequestBody number) {
        welcomeRepo.createJob(auth, provider_id, title, description, estimationTime, estimationPrice, location1,
                latitude, longitude, state1, zipCode, city1, street, appartment, date, time, selected_data, type,start_price,end_price, body,jobType,number)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ApiResponse<AddJobBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                        requestEvent.setValue(Resource.loading(null));
                    }

                    @Override
                    public void onSuccess(ApiResponse<AddJobBean> apiResponse) {
                        if (apiResponse.getStatus() == HttpURLConnection.HTTP_OK) {
                            requestEvent.setValue(Resource.success(apiResponse.getData(), apiResponse.getMsg()));
                        } else {
                            requestEvent.setValue(Resource.warn(null, apiResponse.getMsg()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        requestEvent.setValue(Resource.error(null, networkErrorHandler.getErrMsg(e)));
                    }
                });
    }
}
