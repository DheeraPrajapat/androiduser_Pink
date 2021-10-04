package com.marius.valeyou.di.module;

import androidx.lifecycle.ViewModelProvider;

import com.marius.valeyou.di.ViewModelFactory;

import dagger.Binds;
import dagger.Module;

@Module(includes = {
        ActivityViewModelModule.class,
        FragmentViewModelModule.class
})
public abstract class ViewModelModule {
    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}
