package com.marius.valeyou.ui.fragment.myjob.jobdetails.invoice.pyment;

import com.google.android.gms.common.config.GservicesValue;
import com.marius.valeyou.data.beans.base.ApiResponse;
import com.marius.valeyou.data.beans.base.SimpleApiResponse;
import com.marius.valeyou.data.beans.respbean.GetCardBean;
import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.data.remote.helper.NetworkErrorHandler;
import com.marius.valeyou.data.remote.helper.Resource;
import com.marius.valeyou.data.repo.WelcomeRepo;
import com.marius.valeyou.di.base.viewmodel.BaseActivityViewModel;
import com.marius.valeyou.util.event.SingleRequestEvent;

import java.net.HttpURLConnection;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

public class PaymentActivityVM extends BaseActivityViewModel{
    private final SharedPref sharedPref;
    private final NetworkErrorHandler networkErrorHandler;
    private final WelcomeRepo welcomeRepo;
    SingleRequestEvent<List<GetCardBean>> singleRequestEvent = new SingleRequestEvent<>();
    SingleRequestEvent<SimpleApiResponse> paymentEvent = new SingleRequestEvent<>();
    SingleRequestEvent<SimpleApiResponse> deleteCardRequest = new SingleRequestEvent<>();
    @Inject
    public PaymentActivityVM(SharedPref sharedPref, NetworkErrorHandler networkErrorHandler, WelcomeRepo welcomeRepo) {
        this.sharedPref = sharedPref;
        this.networkErrorHandler = networkErrorHandler;
        this.welcomeRepo = welcomeRepo;
    }

    public void getUserCard() {
        welcomeRepo.getUserCard(sharedPref.getUserData().getAuthKey())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ApiResponse<List<GetCardBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                        singleRequestEvent.setValue(Resource.loading(null));
                    }

                    @Override
                    public void onSuccess(ApiResponse<List<GetCardBean>> listApiResponse) {
                        if (listApiResponse.getStatus() == HttpURLConnection.HTTP_OK){
                            singleRequestEvent.setValue(Resource.success(listApiResponse.getData(),listApiResponse.getMsg()));
                        } else {
                            singleRequestEvent.setValue(Resource.warn(null,listApiResponse.getMsg()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        singleRequestEvent.setValue(Resource.error(null,networkErrorHandler.getErrMsg(e)));
                    }
                });
    }


    public void stripePaymentApi(int orderId, String cardId, String amount,String servicefee, String cvv){
        welcomeRepo.stripePaymentApi(sharedPref.getUserData().getAuthKey(),orderId,cardId,amount, servicefee,cvv)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<SimpleApiResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                        paymentEvent.setValue(Resource.loading(null));
                    }

                    @Override
                    public void onSuccess(SimpleApiResponse listApiResponse) {
                        if (listApiResponse.getStatus() == HttpURLConnection.HTTP_OK){
                            paymentEvent.setValue(Resource.success(null,listApiResponse.getMsg()));
                        } else {
                            paymentEvent.setValue(Resource.warn(null,listApiResponse.getMsg()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        if (e instanceof HttpException) {
                            HttpException exception = (HttpException) e;
                            if (exception.code() == 401){
                                paymentEvent.setValue(Resource.error(null, "Provider Stripe account not connected."));
                            } else {
                                paymentEvent.setValue(Resource.error(null, networkErrorHandler.getErrMsg(e)));
                            }
                        } else {
                            paymentEvent.setValue(Resource.error(null, networkErrorHandler.getErrMsg(e)));
                        }

                    }
                });
    }


    public void deleteCard(String card_id) {
        welcomeRepo.deleteCard(sharedPref.getUserData().getAuthKey(),card_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<SimpleApiResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                        deleteCardRequest.setValue(Resource.loading(null));
                    }

                    @Override
                    public void onSuccess(SimpleApiResponse listApiResponse) {
                        if (listApiResponse.getStatus() == HttpURLConnection.HTTP_OK){
                            deleteCardRequest.setValue(Resource.success(null,listApiResponse.getMsg()));
                        } else {
                            deleteCardRequest.setValue(Resource.warn(null,listApiResponse.getMsg()));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        deleteCardRequest.setValue(Resource.error(null,networkErrorHandler.getErrMsg(e)));
                    }
                });
    }
}
