package com.marius.valeyou.di.module.androidcomponent;

import dagger.Module;

@Module(includes = {
        ActivityModule.class,
        FragmentModule.class,
        ServiceModule.class,
        BroadcastReceiverModule.class,
        ContentProviderModule.class
})
public abstract class AndroidComponentsModule {
}
