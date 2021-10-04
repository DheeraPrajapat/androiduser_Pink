/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.marius.valeyou.data.remote.helper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * A generic class that can provide a resource backed by both the sqlite database and the network.
 * <p>
 * You can read more about it in the <a href="https://developer.android.com/arch">Architecture
 * Guide</a>.
 *
 * @param <ResultType>
 * @param <RequestType>
 */
public abstract class ApiBoundResource<ResultType, RequestType> {
    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();

    @MainThread
    public ApiBoundResource() {
        result.setValue(Resource.loading((ResultType) null));
        final LiveData<ResultType> dbSource = loadFromDb();

        result.addSource(dbSource, new Observer<ResultType>() {
            @Override
            public void onChanged(@Nullable ResultType data) {
                result.removeSource(dbSource);

                if (shouldFetch(data)) {
                    fetchFromNetwork(dbSource);
                } else {
                    /*
                     * Because the dbSource is already queried, this will get immediate results
                     * from last cached value
                     */
                    result.addSource(dbSource, new Observer<ResultType>() {
                        @Override
                        public void onChanged(@Nullable ResultType newData) {
                            result.setValue(Resource.success(newData, null));

                            if (!shouldReceiveDbUpdates()) {
                                result.removeSource(dbSource);
                            }
                        }
                    });
                }
            }
        });
    }

    private void fetchFromNetwork(final LiveData<ResultType> dbSource) {
        createCall()
                .doOnSuccess(new Consumer<RequestType>() {
                    @Override
                    public void accept(RequestType response) throws Exception {
                        callResultOnSuccess(response);
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        callResultOnError(throwable);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<RequestType>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
                        /*
                         * Because the dbSource is already queried, this will get immediate results
                         * from last cached value
                         */
                        result.addSource(dbSource, new Observer<ResultType>() {
                            @Override
                            public void onChanged(@Nullable ResultType data) {
                                result.setValue(Resource.loading(data));
                                result.removeSource(dbSource);
                            }
                        });
                    }

                    @Override
                    public void onSuccess(@io.reactivex.annotations.NonNull RequestType requestType) {
                        /*
                         * We specially request a new live data,
                         * otherwise we will get immediately last cached value,
                         * which may not be updated with latest results received from network.
                         */
                        final LiveData<ResultType> newDbSource = loadFromDb();

                        result.addSource(newDbSource, new Observer<ResultType>() {
                            @Override
                            public void onChanged(@Nullable ResultType newData) {
                                result.setValue(Resource.success(newData, null));

                                if (!shouldReceiveDbUpdates()) {
                                    result.removeSource(newDbSource);
                                }
                            }
                        });
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        /*
                         * Because the dbSource is already queried, this will get immediate results
                         * from last cached value
                         */
                        result.addSource(dbSource, new Observer<ResultType>() {
                            @Override
                            public void onChanged(@Nullable ResultType newData) {
                                result.setValue(Resource.error(newData, null));

                                if (!shouldReceiveDbUpdates()) {
                                    result.removeSource(dbSource);
                                }
                            }
                        });
                    }
                });
    }

    // Called to get the cached data from the database
    @NonNull
    @MainThread
    protected abstract LiveData<ResultType> loadFromDb();

    // Called with the data in the database to decide whether it should be
    // fetched from the network.
    @MainThread
    protected abstract boolean shouldFetch(@Nullable ResultType resultType);

    // Called with the data in the database to decide whether it should be
    // fetched from the network.
    @MainThread
    protected boolean shouldReceiveDbUpdates() {
        return true;
    }

    // Called to create the API call.
    @NonNull
    @MainThread
    protected abstract Single<RequestType> createCall();

    // Called to handle success result of the API
    @WorkerThread
    protected abstract void callResultOnSuccess(@NonNull RequestType response);

    // Called to handle error result of the API
    @WorkerThread
    protected void callResultOnError(@NonNull Throwable throwable) {
    }

    // returns a LiveData that represents the resource
    public final LiveData<Resource<ResultType>> asLiveData() {
        return result;
    }
}