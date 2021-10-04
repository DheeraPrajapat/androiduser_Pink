package com.marius.valeyou.localMarketModel.ui.activity.createShopProfile;

import com.marius.valeyou.data.beans.base.SimpleApiResponse;
import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.data.remote.helper.NetworkErrorHandler;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.data.repo.WelcomeRepo;
import com.marius.valeyou.di.base.viewmodel.BaseActivityViewModel;
import com.marius.valeyou.localMarketModel.responseModel.MarketShopProfile;
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

public class CreateShopProfileActivityVM extends BaseActivityViewModel {

    public final SharedPref sharedPref;
    private final NetworkErrorHandler networkErrorHandler;
    public final LiveLocationDetecter liveLocationDetecter;
    private final WelcomeRepo welcomeRepo;
    final SingleRequestEvent<SimpleApiResponse> shopProfileCreateEvent = new SingleRequestEvent<>();
    final SingleRequestEvent<SimpleApiResponse> deletePostImageEvent = new SingleRequestEvent<>();
    final SingleRequestEvent<MarketShopProfile> marketShopProfileEvent = new SingleRequestEvent<>();

    @Inject
    public CreateShopProfileActivityVM(SharedPref sharedPref, NetworkErrorHandler networkErrorHandler, LiveLocationDetecter liveLocationDetecter, WelcomeRepo welcomeRepo) {
        this.sharedPref = sharedPref;
        this.networkErrorHandler = networkErrorHandler;
        this.liveLocationDetecter = liveLocationDetecter;
        this.welcomeRepo = welcomeRepo;
    }

    public void shopCreateProfile(boolean isEdit,
                                  RequestBody shop_id,
                                  RequestBody company_name,
                                  RequestBody register_number,
                                  RequestBody category,
                                  RequestBody shipping,
                                  RequestBody business_hours,
                                  RequestBody phone,
                                  RequestBody countryCodeBody,
                                  RequestBody address,
                                  RequestBody latitude,
                                  RequestBody longitude,
                                  MultipartBody.Part[] image) {
        welcomeRepo.shopCreateProfile(isEdit, sharedPref.getUserData().getAuthKey(),
                shop_id, company_name, register_number, category, shipping, business_hours, phone,countryCodeBody, address, latitude, longitude, image).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<SimpleApiResponse>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
                shopProfileCreateEvent.setValue(Resource.loading(null));
            }

            @Override
            public void onSuccess(SimpleApiResponse apiResponse) {
                if (apiResponse.getStatus() == HttpURLConnection.HTTP_OK) {
                    shopProfileCreateEvent.setValue(Resource.success(apiResponse, apiResponse.getMsg()));
                } else {
                    shopProfileCreateEvent.setValue(Resource.warn(null, apiResponse.getMsg()));
                }
            }

            @Override
            public void onError(Throwable e) {
                shopProfileCreateEvent.setValue(Resource.error(null, networkErrorHandler.getErrMsg(e)));
            }
        });
    }

    public void deleteShopImage(String shopImageId) {
        welcomeRepo.deleteShopImage(sharedPref.getUserData().getAuthKey(), shopImageId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<SimpleApiResponse>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
                deletePostImageEvent.setValue(Resource.loading(null));
            }

            @Override
            public void onSuccess(SimpleApiResponse apiResponse) {
                if (apiResponse.getStatus() == HttpURLConnection.HTTP_OK) {
                    deletePostImageEvent.setValue(Resource.success(apiResponse, apiResponse.getMsg()));
                } else {
                    deletePostImageEvent.setValue(Resource.warn(null, apiResponse.getMsg()));
                }
            }

            @Override
            public void onError(Throwable e) {
                deletePostImageEvent.setValue(Resource.error(null, networkErrorHandler.getErrMsg(e)));
            }
        });
    }

//    public void getMarketShopProfile() {
//        welcomeRepo.getMarketShopProfile(sharedPref.getUserData().getAuthKey()).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<MarketShopProfile>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//                compositeDisposable.add(d);
//                marketShopProfileEvent.setValue(Resource.loading(null));
//            }
//
//            @Override
//            public void onSuccess(MarketShopProfile apiResponse) {
//                if (apiResponse.getCode() == HttpURLConnection.HTTP_OK) {
//                    marketShopProfileEvent.setValue(Resource.success(apiResponse, apiResponse.getMsg()));
//                } else {
//                    marketShopProfileEvent.setValue(Resource.warn(null, apiResponse.getMsg()));
//                }
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                marketShopProfileEvent.setValue(Resource.error(null, networkErrorHandler.getErrMsg(e)));
//            }
//        });
//    }


}
