package com.marius.valeyou.ui.fragment;

import android.widget.RadioGroup;

import androidx.databinding.ObservableField;

import com.marius.valeyou.data.beans.CitiesModel;
import com.marius.valeyou.data.beans.NotificationBadgeModel;
import com.marius.valeyou.data.beans.StatesModel;
import com.marius.valeyou.data.beans.SuggestionsModel;
import com.marius.valeyou.data.beans.base.ApiResponse;
import com.marius.valeyou.data.beans.base.SimpleApiResponse;
import com.marius.valeyou.data.beans.respbean.CategoryListBean;
import com.marius.valeyou.data.beans.respbean.ProviderNearMe;
import com.marius.valeyou.data.beans.signup.SignupData;
import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.data.remote.helper.NetworkErrorHandler;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.data.repo.WelcomeRepo;
import com.marius.valeyou.di.base.viewmodel.BaseFragmentViewModel;
import com.marius.valeyou.util.event.SingleActionEvent;
import com.marius.valeyou.util.event.SingleRequestEvent;
import com.marius.valeyou.util.location.LiveLocationDetecter;

import java.net.HttpURLConnection;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NewHomeFragmentVM extends BaseFragmentViewModel {
    public final SharedPref sharedPref;
    private final NetworkErrorHandler networkErrorHandler;
    public final LiveLocationDetecter liveLocationDetecter;
    private final WelcomeRepo welcomeRepo;
    final SingleRequestEvent<List<CategoryListBean>> categoryList = new SingleRequestEvent<>();
    final SingleRequestEvent<SignupData> logout = new SingleRequestEvent<>();
    public SingleActionEvent<Integer> rButtonEvent = new SingleActionEvent<>();
    public SingleRequestEvent<List<StatesModel>> statesEventRequest = new SingleRequestEvent<>();
    public SingleRequestEvent<List<SuggestionsModel>> suggestionsEvent = new SingleRequestEvent<>();
    final SingleRequestEvent<NotificationBadgeModel> notificationbadgeEventRequest = new SingleRequestEvent<>();
    final SingleRequestEvent<SimpleApiResponse> successEvent = new SingleRequestEvent<>();
    public SingleRequestEvent<List<CitiesModel>> citiesEventRequest = new SingleRequestEvent<>();

    @Inject
    public NewHomeFragmentVM(SharedPref sharedPref, NetworkErrorHandler networkErrorHandler, LiveLocationDetecter liveLocationDetecter, WelcomeRepo welcomeRepo) {
        this.sharedPref = sharedPref;
        this.networkErrorHandler = networkErrorHandler;
        this.liveLocationDetecter = liveLocationDetecter;
        this.welcomeRepo = welcomeRepo;
    }


    public void onSplitTypeChanged(RadioGroup radioGroup, int id) {
        rButtonEvent.setValue(id);
    }


    public void logout() {
        welcomeRepo.logOut(sharedPref.getUserData().getId(), sharedPref.getUserData().getAuthKey()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<ApiResponse>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
                logout.setValue(Resource.loading(null));
            }

            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if (apiResponse.getStatus() == HttpURLConnection.HTTP_OK) {
                    logout.setValue(Resource.success(null, apiResponse.getMsg()));
                } else {
                    logout.setValue(Resource.warn(null, apiResponse.getMsg()));
                }
            }

            @Override
            public void onError(Throwable e) {
                logout.setValue(Resource.error(null, networkErrorHandler.getErrMsg(e)));
            }
        });
    }

    public void getListOfCateegory(int type, String search) {
        welcomeRepo.getCategory(sharedPref.getUserData().getAuthKey(), type, search).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<ApiResponse<List<CategoryListBean>>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
                categoryList.setValue(Resource.loading(null));
            }

            @Override
            public void onSuccess(ApiResponse<List<CategoryListBean>> apiResponse) {
                if (apiResponse.getStatus() == HttpURLConnection.HTTP_OK) {
                    categoryList.setValue(Resource.success(apiResponse.getData(), apiResponse.getMsg()));
                } else {
                    categoryList.setValue(Resource.warn(null, apiResponse.getMsg()));
                }
            }

            @Override
            public void onError(Throwable e) {
                categoryList.setValue(Resource.error(null, networkErrorHandler.getErrMsg(e)));
            }
        });
    }


    public void searchSuggestions(String search) {
        welcomeRepo.getSuggestions(sharedPref.getUserData().getAuthKey(),search)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ApiResponse<List<SuggestionsModel>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                        suggestionsEvent.setValue(Resource.loading(null));
                    }

                    @Override
                    public void onSuccess(ApiResponse<List<SuggestionsModel>> simpleApiResponse) {
                        if (simpleApiResponse.getStatus() == HttpURLConnection.HTTP_OK) {
                            suggestionsEvent.setValue(Resource.success(simpleApiResponse.getData(), simpleApiResponse.getMsg()));
                        } else {
                            suggestionsEvent.setValue(Resource.warn(null, simpleApiResponse.getMsg()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                     suggestionsEvent.setValue(Resource.error(null, networkErrorHandler.getErrMsg(e)));

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


    public void deleteOrDeactivate(String authKey, String type) {
        welcomeRepo.accountSettings(authKey, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<SimpleApiResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                        successEvent.setValue(Resource.loading(null));
                    }

                    @Override
                    public void onSuccess(SimpleApiResponse simpleApiResponse) {
                        if (simpleApiResponse.getStatus() == HttpURLConnection.HTTP_OK) {
                            successEvent.setValue(Resource.success(simpleApiResponse, simpleApiResponse.getMsg()));
                        } else {
                            successEvent.setValue(Resource.warn(null, simpleApiResponse.getMsg()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        successEvent.setValue(Resource.error(null, networkErrorHandler.getErrMsg(e)));
                    }
                });
    }
    public void getStates(String input){
        welcomeRepo.getStates(sharedPref.getUserData().getAuthKey(),input).subscribeOn(Schedulers.io())
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
    public void getCities(int id,String input){
        welcomeRepo.getCitites(sharedPref.getUserData().getAuthKey(),id,input).subscribeOn(Schedulers.io())
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
