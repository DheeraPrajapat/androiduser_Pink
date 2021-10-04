package com.marius.valeyou.di.module;


import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.data.remote.api.WelcomeApi;
import com.marius.valeyou.data.remote.helper.NetworkErrorHandler;
import com.marius.valeyou.data.repo.WelcomeRepo;
import com.marius.valeyou.data.repo.WelcomeRepoImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

       @Singleton
    @Provides
    static WelcomeRepo welcomeRepo(SharedPref sharedPref, WelcomeApi welcomeApi, NetworkErrorHandler networkErrorHandler) {
        return new WelcomeRepoImpl(sharedPref, welcomeApi, networkErrorHandler);
    }

}
