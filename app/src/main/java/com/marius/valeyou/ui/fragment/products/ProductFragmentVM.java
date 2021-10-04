package com.marius.valeyou.ui.fragment.products;

import androidx.databinding.ObservableField;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import com.marius.valeyou.data.beans.base.ApiResponse;
import com.marius.valeyou.data.beans.respbean.CategoryListBean;
import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.data.remote.helper.NetworkErrorHandler;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.data.repo.WelcomeRepo;
import com.marius.valeyou.di.base.viewmodel.BaseFragmentViewModel;
import com.marius.valeyou.util.event.SingleActionEvent;
import com.marius.valeyou.util.event.SingleRequestEvent;

import java.net.HttpURLConnection;
import java.util.List;

import javax.inject.Inject;

public class ProductFragmentVM extends BaseFragmentViewModel {

    final SingleActionEvent<Integer> passIntent = new SingleActionEvent<>();
    public final ObservableField<String> field_Player1 = new ObservableField<>();

    private final SharedPref sharedPref;
    private final NetworkErrorHandler networkErrorHandler;
    private final WelcomeRepo welcomeRepo;
    final SingleRequestEvent<List<CategoryListBean>> categoryList = new SingleRequestEvent<>();

    @Inject
    public ProductFragmentVM(SharedPref sharedPref, NetworkErrorHandler networkErrorHandler, WelcomeRepo welcomeRepo) {
        this.sharedPref = sharedPref;
        this.networkErrorHandler = networkErrorHandler;
        this.welcomeRepo = welcomeRepo;
    }

    public void getListOfCateegory() {
        welcomeRepo.getCategory(sharedPref.getUserData().getAuthKey(),1,"").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<ApiResponse<List<CategoryListBean>>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
                categoryList.setValue(Resource.loading(null));
            }

            @Override
            public void onSuccess(ApiResponse<List<CategoryListBean>> apiResponse) {
                if (apiResponse.getStatus() == HttpURLConnection.HTTP_OK) {
                    categoryList.setValue(Resource.success(apiResponse.getData(),apiResponse.getMsg()));
                } else {
                    categoryList.setValue(Resource.warn(null,apiResponse.getMsg()));
                }
            }

            @Override
            public void onError(Throwable e) {
                categoryList.setValue(Resource.error(null,networkErrorHandler.getErrMsg(e)));
            }
        });
    }
}
