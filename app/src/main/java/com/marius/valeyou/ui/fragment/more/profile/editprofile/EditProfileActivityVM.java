package com.marius.valeyou.ui.fragment.more.profile.editprofile;

import com.marius.valeyou.data.beans.CitiesModel;
import com.marius.valeyou.data.beans.StatesModel;
import com.marius.valeyou.data.beans.base.ApiResponse;
import com.marius.valeyou.data.beans.signup.SignupData;
import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.data.remote.helper.NetworkErrorHandler;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.data.repo.WelcomeRepo;
import com.marius.valeyou.di.base.viewmodel.BaseActivityViewModel;
import com.marius.valeyou.util.event.SingleRequestEvent;

import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class EditProfileActivityVM extends BaseActivityViewModel {
    private final WelcomeRepo welcomeRepo;
    public final SharedPref sharedPref;
    private final NetworkErrorHandler networkErrorHandler;
    public SingleRequestEvent<SignupData> singleRequestEvent = new SingleRequestEvent<>();

    public SingleRequestEvent<List<StatesModel>> statesEventRequest = new SingleRequestEvent<>();
    public SingleRequestEvent<List<CitiesModel>> citiesEventRequest = new SingleRequestEvent<>();


    @Inject
    public EditProfileActivityVM(SharedPref sharedPref, NetworkErrorHandler networkErrorHandler, WelcomeRepo welcomeRepo) {
        this.sharedPref = sharedPref;
        this.networkErrorHandler = networkErrorHandler;
        this.welcomeRepo = welcomeRepo;
    }

    public void editProfile(Map<String, RequestBody> bodyMap, MultipartBody.Part body) {
        welcomeRepo.updateProfile(sharedPref.getUserData().getAuthKey(),bodyMap,body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ApiResponse<SignupData>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                        singleRequestEvent.setValue(Resource.loading(null));
                    }

                    @Override
                    public void onSuccess(ApiResponse<SignupData> apiResponse) {
                        if (apiResponse.getStatus()== HttpURLConnection.HTTP_OK){
                            singleRequestEvent.setValue(Resource.success(apiResponse.getData(),apiResponse.getMsg()));
                        } else {
                            singleRequestEvent.setValue(Resource.warn(null,apiResponse.getMsg()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        singleRequestEvent.setValue(Resource.error(null,networkErrorHandler.getErrMsg(e)));
                    }
                });
    }

    public void getStates(){
        welcomeRepo.getStates(sharedPref.getUserData().getAuthKey(),"").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<ApiResponse<List<StatesModel>>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
                statesEventRequest.setValue(Resource.loading(null));
            }

            @Override
            public void onSuccess(ApiResponse<List<StatesModel>> faqResponse) {

                if (faqResponse.getStatus() == HttpURLConnection.HTTP_OK) {

                    statesEventRequest.setValue(Resource.success(faqResponse.getData(), faqResponse.getMsg()));

                } else {

                    statesEventRequest.setValue(Resource.error(null, faqResponse.getMsg()));

                }
            }

            @Override
            public void onError(Throwable e) {

                statesEventRequest.setValue(Resource.error(null, networkErrorHandler.getErrMsg(e)));

            }
        });
    }

    public void getCities(int id){
        welcomeRepo.getCitites(sharedPref.getUserData().getAuthKey(),id,"").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<ApiResponse<List<CitiesModel>>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
                citiesEventRequest.setValue(Resource.loading(null));
            }

            @Override
            public void onSuccess(ApiResponse<List<CitiesModel>> faqResponse) {

                if (faqResponse.getStatus() == HttpURLConnection.HTTP_OK) {

                    citiesEventRequest.setValue(Resource.success(faqResponse.getData(), faqResponse.getMsg()));

                } else {

                    citiesEventRequest.setValue(Resource.error(null, faqResponse.getMsg()));

                }
            }

            @Override
            public void onError(Throwable e) {

                citiesEventRequest.setValue(Resource.error(null, networkErrorHandler.getErrMsg(e)));

            }
        });
    }

}
