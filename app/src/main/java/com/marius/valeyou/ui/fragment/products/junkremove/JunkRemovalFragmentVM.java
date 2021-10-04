package com.marius.valeyou.ui.fragment.products.junkremove;

import com.marius.valeyou.data.beans.base.ApiResponse;
import com.marius.valeyou.data.beans.respbean.SubCategoryBean;
import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.data.remote.helper.NetworkErrorHandler;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.data.repo.WelcomeRepo;
import com.marius.valeyou.di.base.viewmodel.BaseFragmentViewModel;
import com.marius.valeyou.util.event.SingleRequestEvent;

import java.net.HttpURLConnection;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class JunkRemovalFragmentVM extends BaseFragmentViewModel {

    private final SharedPref sharedPref;
    private final NetworkErrorHandler networkErrorHandler;
    private final WelcomeRepo welcomeRepo;
    final SingleRequestEvent<List<SubCategoryBean>> categoryList = new SingleRequestEvent<>();

    @Inject
    public JunkRemovalFragmentVM(SharedPref sharedPref, NetworkErrorHandler networkErrorHandler, WelcomeRepo welcomeRepo) {
        this.sharedPref = sharedPref;
        this.networkErrorHandler = networkErrorHandler;
        this.welcomeRepo = welcomeRepo;
    }

    public void getListOfCateegory(int cat_id) {
        welcomeRepo.getSubCategory(String.valueOf(sharedPref.getUserData().getAuthKey()),cat_id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<ApiResponse<List<SubCategoryBean>>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
                categoryList.setValue(Resource.loading(null));
            }

            @Override
            public void onSuccess(ApiResponse<List<SubCategoryBean>> apiResponse) {
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
