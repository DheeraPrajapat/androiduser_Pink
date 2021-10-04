package com.marius.valeyou.ui.fragment.loadtype.removaljob.when;

import com.marius.valeyou.data.beans.BookingModel;
import com.marius.valeyou.data.beans.base.ApiResponse;
import com.marius.valeyou.data.beans.respbean.ProviderDetails;
import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.data.remote.helper.NetworkErrorHandler;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.data.repo.WelcomeRepo;
import com.marius.valeyou.di.base.viewmodel.BaseActivityViewModel;
import com.marius.valeyou.util.event.SingleRequestEvent;
import com.marius.valeyou.util.location.LiveLocationDetecter;

import java.net.HttpURLConnection;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WhenActivityVM extends BaseActivityViewModel {
    public final SharedPref sharedPref;
    private final NetworkErrorHandler networkErrorHandler;
    public final LiveLocationDetecter liveLocationDetecter;
    private final WelcomeRepo welcomeRepo;
    public SingleRequestEvent<List<BookingModel>> bookingsEvent = new SingleRequestEvent<>();
    @Inject
    public WhenActivityVM(SharedPref sharedPref, NetworkErrorHandler networkErrorHandler, LiveLocationDetecter liveLocationDetecter,WelcomeRepo welcomeRepo) {
        this.sharedPref = sharedPref;
        this.networkErrorHandler = networkErrorHandler;
        this.liveLocationDetecter = liveLocationDetecter;
        this.welcomeRepo = welcomeRepo;
    }


    public void getBoolings(String date, String provider_id) {
        welcomeRepo.getBookings(sharedPref.getUserData().getAuthKey(), date,provider_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ApiResponse<List<BookingModel>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                        bookingsEvent.setValue(Resource.loading(null));
                    }

                    @Override
                    public void onSuccess(ApiResponse<List<BookingModel>> apiResponse) {
                        if (apiResponse.getStatus() == HttpURLConnection.HTTP_OK) {
                            bookingsEvent.setValue(Resource.success(apiResponse.getData(), apiResponse.getMsg()));
                        } else {
                            bookingsEvent.setValue(Resource.warn(null, apiResponse.getMsg()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        bookingsEvent.setValue(Resource.error(null, networkErrorHandler.getErrMsg(e)));
                    }
                });
    }

}
