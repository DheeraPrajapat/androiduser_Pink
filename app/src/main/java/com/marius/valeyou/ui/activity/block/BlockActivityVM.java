package com.marius.valeyou.ui.activity.block;

import com.marius.valeyou.data.beans.base.ApiResponse;
import com.marius.valeyou.data.beans.base.SimpleApiResponse;
import com.marius.valeyou.data.beans.block.BlockModel;
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

public class BlockActivityVM extends BaseActivityViewModel {
    public final SharedPref sharedPref;
    private final NetworkErrorHandler networkErrorHandler;
    public final LiveLocationDetecter liveLocationDetecter;
    private final WelcomeRepo welcomeRepo;

    SingleRequestEvent<List<BlockModel>> blockListEvent = new SingleRequestEvent<>();
    SingleRequestEvent<SimpleApiResponse> blockUnblockEvent = new SingleRequestEvent<>();

    @Inject
    public BlockActivityVM(SharedPref sharedPref, NetworkErrorHandler networkErrorHandler, LiveLocationDetecter liveLocationDetecter, WelcomeRepo welcomeRepo) {
        this.sharedPref = sharedPref;
        this.networkErrorHandler = networkErrorHandler;
        this.liveLocationDetecter = liveLocationDetecter;
        this.welcomeRepo = welcomeRepo;
    }

    public void blockList() {
        welcomeRepo.blockList(sharedPref.getUserData().getAuthKey()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<ApiResponse<List<BlockModel>>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
                blockListEvent.setValue(Resource.loading(null));
            }

            @Override
            public void onSuccess(ApiResponse<List<BlockModel>> faqResponse) {

                if (faqResponse.getStatus() == HttpURLConnection.HTTP_OK) {

                    blockListEvent.setValue(Resource.success(faqResponse.getData(), faqResponse.getMsg()));

                } else {

                    blockListEvent.setValue(Resource.error(null, faqResponse.getMsg()));

                }
            }

            @Override
            public void onError(Throwable e) {

                blockListEvent.setValue(Resource.error(null, networkErrorHandler.getErrMsg(e)));

            }
        });

    }


    public void blockUnblock(String user2Id, String apiType) {
        welcomeRepo.blockUnblockProvider(sharedPref.getUserData().getAuthKey(), user2Id, apiType).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<SimpleApiResponse>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
                blockUnblockEvent.setValue(Resource.loading(null));
            }

            @Override
            public void onSuccess(SimpleApiResponse faqResponse) {

                if (faqResponse.getStatus() == HttpURLConnection.HTTP_OK) {

                    blockUnblockEvent.setValue(Resource.success(null, faqResponse.getMsg()));

                } else {

                    blockUnblockEvent.setValue(Resource.error(null, faqResponse.getMsg()));

                }
            }

            @Override
            public void onError(Throwable e) {

                blockUnblockEvent.setValue(Resource.error(null, networkErrorHandler.getErrMsg(e)));

            }
        });
    }

}