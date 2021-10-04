package com.marius.valeyou.di.module;

import android.content.Context;
import android.content.SharedPreferences;

import com.marius.valeyou.BuildConfig;
import com.marius.valeyou.data.local.SharedPref;
import com.marius.valeyou.data.local.SharedPrefImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.android.support.DaggerApplication;

@Module
public class LocalModule {
    @Singleton
    @Provides
    static SharedPreferences sharedPreferences(DaggerApplication daggerApplication) {
        return daggerApplication.getSharedPreferences(
                BuildConfig.APPLICATION_ID,
                Context.MODE_PRIVATE);
    }

    @Singleton
    @Provides
    static SharedPref sharedPref(SharedPreferences sharedPreferences) {
        return new SharedPrefImpl(sharedPreferences);
    }

/*

   @Singleton
    @Provides
    static AppDatabase appDatabase(DaggerApplication daggerApplication, RoomDatabase.Callback callback) {
        return Room.databaseBuilder(daggerApplication, AppDatabase.class, BuildConfig.APPLICATION_ID).addCallback(callback).fallbackToDestructiveMigration().build();
    }
*/

  /*  @Singleton
    @Provides
    static RoomDatabase.Callback roomDatabaseCallback() {
        return new RoomDatabase.Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                Log.d("sqlite", "Database created...");
            }

            @Override
            public void onOpen(@NonNull SupportSQLiteDatabase db) {
                super.onOpen(db);
                Log.d("sqlite", "Database opened...");
            }
        };
    }*/
}
