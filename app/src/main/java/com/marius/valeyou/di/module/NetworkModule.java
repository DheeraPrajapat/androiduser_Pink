package com.marius.valeyou.di.module;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.marius.valeyou.di.certificate.SSLCertificate;
import com.marius.valeyou.data.remote.intersepter.RequestInterceptor;
import com.marius.valeyou.util.misc.ConnectionHandler;
import com.marius.valeyou.data.remote.helper.NetworkErrorHandler;
import com.marius.valeyou.di.qualifier.ApiDateFormat;
import com.marius.valeyou.di.qualifier.ApiEndpoint;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.android.support.DaggerApplication;
import io.reactivex.schedulers.Schedulers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


@Module
public class NetworkModule {
   /* @Singleton
    @Provides
    static Cache cache(DaggerApplication daggerApplication) {
        Cache cache = null;

        try {
            int cacheSize = 10 * 1024 * 1024; // 10mb cache
            cache = new Cache(new File(daggerApplication.getCacheDir(), "http-cache"), cacheSize);
        } catch (Exception e) {

        }

        return cache;
    }
*/

    @Singleton
    @Provides
    static Gson gson(@ApiDateFormat String apiDateFormat) {
        return new GsonBuilder()
                .setDateFormat(apiDateFormat)
                .create();
    }

    @Singleton
    @Provides
    static Converter.Factory converterFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Singleton
    @Provides
    static CallAdapter.Factory callAdapterFactory() {
        return RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io());
    }

    /* @Singleton
     @Provides
     static Retrofit retrofit(@ApiEndpoint HttpUrl apiEndpoint, OkHttpClient okHttpClient, Converter.Factory converterFactory, CallAdapter.Factory callAdapterFactory) {
         return new Retrofit.Builder()
                 .baseUrl(apiEndpoint)
                 .client(okHttpClient)
                 .addConverterFactory(converterFactory)
                 .addCallAdapterFactory(callAdapterFactory)
                 .build();
     }


     @Singleton
     @Provides
     static OkHttpClient provideOkHttp(HttpLoggingInterceptor loggingInterceptor) {
         return new OkHttpClient.Builder()
                 .addInterceptor(loggingInterceptor)
                 .connectTimeout(30, TimeUnit.SECONDS)
                 .readTimeout(30, TimeUnit.SECONDS)
                 .writeTimeout(30, TimeUnit.SECONDS)
                 .build();
     }
 */
    @Singleton
    @Provides
    static NetworkErrorHandler networkErrorHandler(DaggerApplication context) {
        return new NetworkErrorHandler(context);
    }




/*    @Provides
    @Singleton
    @NonNull
    static HttpLoggingInterceptor httpLoggingInterceptor() {
        return new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(@NonNull String message) {
                Timber.d(message);
            }
        }).setLevel(BuildConfig.DEBUG ? BODY : NONE);

    }*/

    @ApiEndpoint
    @Singleton
    @Provides
    static Retrofit retrofit(@ApiEndpoint HttpUrl apiEndpoint, @ApiEndpoint OkHttpClient okHttpClient, Converter.Factory converterFactory, CallAdapter.Factory callAdapterFactory) {
        return new Retrofit.Builder()
                .baseUrl(apiEndpoint)
                .client(SSLCertificate.getUnsafeOkHttpClient())
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(callAdapterFactory)
                .build();
    }

    @Singleton
    @Provides
    static Retrofit retrofit2(HttpUrl apiEndpoint, @ApiEndpoint OkHttpClient okHttpClient, Converter.Factory converterFactory, CallAdapter.Factory callAdapterFactory) {
        return new Retrofit.Builder()
                .baseUrl(apiEndpoint)
                .client(SSLCertificate.getUnsafeOkHttpClient())
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(callAdapterFactory)
                .build();
    }


    @Singleton
    @Provides
    static Retrofit retrofit3(HttpUrl apiEndpoint, OkHttpClient okHttpClient, Converter.Factory converterFactory, CallAdapter.Factory callAdapterFactory) {
        return new Retrofit.Builder()
                .baseUrl(apiEndpoint)
                .client(SSLCertificate.getUnsafeOkHttpClient())
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(callAdapterFactory)
                .build();
    }

    @ApiEndpoint
    @Singleton
    @Provides
    static OkHttpClient okHttpClient(RequestInterceptor interceptor) {

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES)
                /*.addInterceptor(new RequestInterceptor())*/
                .build();


                return client;
    }



    @Singleton
    @Provides
    static OkHttpClient okHttpClient2() {
        return new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES)
                .build();
    }

    @Singleton
    @Provides
    static ConnectionHandler connectionUtils(DaggerApplication daggerApplication) {
        return new ConnectionHandler(daggerApplication.getApplicationContext());
    }

    @Provides
    @Singleton
    @NonNull
    static RequestInterceptor providesRequestInterceptor() {
        return new RequestInterceptor();
    }




}
