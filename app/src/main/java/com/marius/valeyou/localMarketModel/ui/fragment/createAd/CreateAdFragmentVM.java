package com.marius.valeyou.localMarketModel.ui.fragment.createAd;

import android.util.Log;

import com.marius.valeyou.data.beans.base.SimpleApiResponse;
import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.data.remote.helper.NetworkErrorHandler;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.data.repo.WelcomeRepo;
import com.marius.valeyou.di.base.viewmodel.BaseFragmentViewModel;
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

public class CreateAdFragmentVM extends BaseFragmentViewModel {

    public final SharedPref sharedPref;
    private final NetworkErrorHandler networkErrorHandler;
    public final LiveLocationDetecter liveLocationDetecter;
    private final WelcomeRepo welcomeRepo;
    final SingleRequestEvent<SimpleApiResponse> addPostEvent = new SingleRequestEvent<>();
    final SingleRequestEvent<SimpleApiResponse> deletePostImageEvent = new SingleRequestEvent<>();
    final SingleRequestEvent<MarketShopProfile> marketShopProfileEvent = new SingleRequestEvent<>();

    @Inject
    public CreateAdFragmentVM(SharedPref sharedPref, NetworkErrorHandler networkErrorHandler, LiveLocationDetecter liveLocationDetecter, WelcomeRepo welcomeRepo) {
        this.sharedPref = sharedPref;
        this.networkErrorHandler = networkErrorHandler;
        this.liveLocationDetecter = liveLocationDetecter;
        this.welcomeRepo = welcomeRepo;
    }

    public void marketAddPost(boolean isEdit,
                              RequestBody postId,
                              RequestBody title,
                              RequestBody description,
                              RequestBody category,
                              RequestBody product_type,
                              RequestBody post_type,
                              RequestBody shipping,
                              RequestBody price,
                              RequestBody fixed_price,
                              RequestBody tag,
                              RequestBody owner_type,
                              RequestBody location,
                              RequestBody latitude,
                              RequestBody longitude,
                              RequestBody search_keyword,
                              RequestBody phone,
                              RequestBody is_phone_show ,
                              RequestBody country_code,
                              RequestBody shop_id,
                              MultipartBody.Part[] image) {
        welcomeRepo.marketPost(isEdit, sharedPref.getUserData().getAuthKey(),
                postId, title, description, category, product_type,post_type, shipping, price, fixed_price, tag, owner_type,
                location, latitude, longitude, search_keyword, phone,is_phone_show,country_code,shop_id, image).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<SimpleApiResponse>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
                addPostEvent.setValue(Resource.loading(null));
            }

            @Override
            public void onSuccess(SimpleApiResponse apiResponse) {
                if (apiResponse.getStatus() == HttpURLConnection.HTTP_OK) {
                    addPostEvent.setValue(Resource.success(apiResponse, apiResponse.getMsg()));
                } else {
                    addPostEvent.setValue(Resource.warn(null, apiResponse.getMsg()));
                }
            }

            @Override
            public void onError(Throwable e) {
                addPostEvent.setValue(Resource.error(null, networkErrorHandler.getErrMsg(e)));
            }
        });
    }

    public void deletePostImage(String postImageId) {
        welcomeRepo.deletePostImage(sharedPref.getUserData().getAuthKey(), postImageId).subscribeOn(Schedulers.io())
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

    public void getMarketShopProfile() {
        welcomeRepo.getMarketShopProfile(sharedPref.getUserData().getAuthKey()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<MarketShopProfile>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
                marketShopProfileEvent.setValue(Resource.loading(null));
            }

            @Override
            public void onSuccess(MarketShopProfile apiResponse) {
                if (apiResponse.getCode() == HttpURLConnection.HTTP_OK) {
                    marketShopProfileEvent.setValue(Resource.success(apiResponse, apiResponse.getMsg()));
                 } else {
                    marketShopProfileEvent.setValue(Resource.warn(null, apiResponse.getMsg()));
                }
            }

            @Override
            public void onError(Throwable e) {
                marketShopProfileEvent.setValue(Resource.error(null, networkErrorHandler.getErrMsg(e)));
            }
        });
    }



}
