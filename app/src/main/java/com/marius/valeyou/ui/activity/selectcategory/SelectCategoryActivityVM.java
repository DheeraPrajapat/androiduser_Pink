package com.marius.valeyou.ui.activity.selectcategory;

import android.widget.RadioGroup;

import com.marius.valeyou.data.beans.base.ApiResponse;
import com.marius.valeyou.data.beans.respbean.CategoryListBean;
import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.data.remote.helper.NetworkErrorHandler;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.data.repo.WelcomeRepo;
import com.marius.valeyou.di.base.viewmodel.BaseActivityViewModel;
import com.marius.valeyou.util.event.SingleActionEvent;
import com.marius.valeyou.util.event.SingleRequestEvent;

import java.net.HttpURLConnection;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SelectCategoryActivityVM extends BaseActivityViewModel {

    private final SharedPref sharedPref;
    private final NetworkErrorHandler networkErrorHandler;
    private final WelcomeRepo welcomeRepo;
    final SingleRequestEvent<List<CategoryListBean>> categoryList = new SingleRequestEvent<>();
    public SingleActionEvent<Integer> rButtonEvent = new SingleActionEvent<>();

    @Inject
    public SelectCategoryActivityVM(SharedPref sharedPref, NetworkErrorHandler networkErrorHandler, WelcomeRepo welcomeRepo) {
        this.sharedPref = sharedPref;
        this.networkErrorHandler = networkErrorHandler;
        this.welcomeRepo = welcomeRepo;
    }

    public void onSplitTypeChanged(RadioGroup radioGroup, int id) {
        rButtonEvent.setValue(id);
    }

    public void getListOfCateegory(int type, String search) {
        welcomeRepo.getCategory(sharedPref.getUserData().getAuthKey(), type,search).subscribeOn(Schedulers.io())
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

    public void getListOfProviderCategories(int provider_id) {
        welcomeRepo.getProviderCategories(sharedPref.getUserData().getAuthKey(),provider_id).subscribeOn(Schedulers.io())
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

}
