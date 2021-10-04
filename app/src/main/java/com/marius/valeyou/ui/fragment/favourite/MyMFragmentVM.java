package com.marius.valeyou.ui.fragment.favourite;

import androidx.databinding.ObservableField;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import com.marius.valeyou.data.beans.base.ApiResponse;
import com.marius.valeyou.data.beans.respbean.FavoriteListBean;
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

public class MyMFragmentVM extends BaseFragmentViewModel {

    private final SharedPref sharedPref;
    private final NetworkErrorHandler networkErrorHandler;
    private final WelcomeRepo welcomeRepo;
    final SingleActionEvent<Integer> passIntent = new SingleActionEvent<>();
    public final ObservableField<String> field_Player1 = new ObservableField<>();
    public SingleRequestEvent<List<FavoriteListBean>> successData = new SingleRequestEvent<>();

    @Inject
    public MyMFragmentVM(SharedPref sharedPref, NetworkErrorHandler networkErrorHandler, WelcomeRepo welcomeRepo) {
        this.sharedPref = sharedPref;
        this.networkErrorHandler = networkErrorHandler;
        this.welcomeRepo = welcomeRepo;
    }

    public void getFavouriteList(String authKey) {
        welcomeRepo.getFavoriteList(authKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ApiResponse<List<FavoriteListBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                        successData.setValue(Resource.loading(null));
                    }

                    @Override
                    public void onSuccess(ApiResponse<List<FavoriteListBean>> listApiResponse) {
                        if (listApiResponse.getStatus() == HttpURLConnection.HTTP_OK) {
                            successData.setValue(Resource.success(listApiResponse.getData(),listApiResponse.getMsg()));
                        } else {
                            successData.setValue(Resource.warn(null,listApiResponse.getMsg()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        successData.setValue(Resource.error(null,networkErrorHandler.getErrMsg(e)));
                    }
                });

    }
}
