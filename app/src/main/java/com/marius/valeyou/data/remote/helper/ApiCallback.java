package com.marius.valeyou.data.remote.helper;

public interface ApiCallback<T> {
    void onLoading();
    void onSuccess(T response, String successMsg);
    void onError(String errMsg);
}
