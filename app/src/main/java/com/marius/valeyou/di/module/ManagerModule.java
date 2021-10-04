package com.marius.valeyou.di.module;


import com.marius.valeyou.util.misc.RxBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ManagerModule {

    @Singleton
    @Provides
    static RxBus getBus() {
        return new RxBus();
    }



}