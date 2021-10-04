package com.marius.valeyou.di.module;

import android.content.Context;

import com.marius.valeyou.BuildConfig;
import com.marius.valeyou.di.qualifier.ApiDateFormat;
import com.marius.valeyou.di.qualifier.ApiEndpoint;
import com.marius.valeyou.di.qualifier.AppContext;
import com.marius.valeyou.util.Constants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.android.DaggerApplication;
import okhttp3.HttpUrl;

@Module
public class AppModule {
    @AppContext
    @Provides
    static Context context(DaggerApplication daggerApplication) {
        return daggerApplication;
    }

    @ApiEndpoint
    @Singleton
    @Provides
    static HttpUrl apiEndpoint() {
        return HttpUrl.parse(BuildConfig.BASE_URL);
    }


    @Singleton
    @Provides
    static HttpUrl placeEndpoint() {
        return HttpUrl.parse(Constants.GOOGLE_URL);
    }

    @ApiDateFormat
    @Singleton
    @Provides
    static String apiDateFormat() {
        return "yyyy-MM-dd HH:mm:ss";
    }


}
